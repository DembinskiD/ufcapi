package com.dembinski.ufcapi.service;

import com.dembinski.ufcapi.data.FightList;
import com.dembinski.ufcapi.mapper.FightMapper;
import com.dembinski.ufcapi.repository.FightListRepository;
import com.dembinski.ufcapi.source.FightDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FightListService {

    private final String apiKey = "5a881fececbfe0a958072272a50be4ea6d67d534"; //todo move it to properties
    private final String uri = String.format("https://fightingtomatoes.com/API/%s/Any/Any/Any", apiKey); //todo move it to properties
    private final int updateEveryDays = 10; //todo move it to properties
    private final FightMapper fightMapper;

    private final FightListRepository fightListRepository;

    public List<FightList> getAll() {
        return fightListRepository.findAll();
    }

    public List<FightList> getFightLists() {
        return fightListRepository.findAll();
    }

    public FightList getFightListFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<FightDTO> list;

        String stringData = Objects.requireNonNull(restTemplate.getForObject(uri, String.class))
                .lines()
                .filter(line -> line.startsWith("[") && line.endsWith("]"))
                .findFirst()
                .orElse("");
        try {
            list = Arrays.asList(mapper.readValue(stringData, FightDTO[].class));
        } catch (IOException e) {
            log.error("Cannot read value from stringData.");
            list = new ArrayList<>();
        }

        return createFightList(list);
    }

    private FightList createFightList(List<FightDTO> fightDTOList) {
        return FightList
                .builder()
                .fights(fightMapper.toListFight(fightDTOList))
                .createdAt(LocalDate.now())
                .build();
    }

    private boolean shouldUpdateData(FightList fightList) {
        int daysSinceLastUpdate = fightList.getCreatedAt().until(LocalDate.now()).isNegative() ?
                LocalDate.now().until(fightList.getCreatedAt()).getDays() : fightList.getCreatedAt().until(LocalDate.now()).getDays();
        return daysSinceLastUpdate > updateEveryDays;
    }



}
