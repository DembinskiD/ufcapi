package com.dembinski.ufcapi.service;

import com.dembinski.ufcapi.data.FightList;
import com.dembinski.ufcapi.mapper.FightMapper;
import com.dembinski.ufcapi.repository.FightListRepository;
import com.dembinski.ufcapi.source.FightDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FightListService {

    private final String apiKey;
    private final String uri;
    private final int updateEveryDays;

    public FightListService(@Value("${api.key}") String apiKey,
                            @Value("${api.update.every}") int updateEveryDays,
                            FightMapper fightMapper,
                            FightListRepository fightListRepository) {
        this.apiKey = apiKey;
        this.updateEveryDays = updateEveryDays;
        this.fightMapper = fightMapper;
        this.fightListRepository = fightListRepository;
        this.uri = String.format("https://fightingtomatoes.com/API/%s/Any/Any/Any", apiKey);
    }

    private final FightMapper fightMapper;
    private final FightListRepository fightListRepository;

    public List<FightList> getAll() {
        return fightListRepository.findAll();
    }

    public FightList getFightListFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<FightDto> list;

        String stringData = Objects.requireNonNull(restTemplate.getForObject(uri, String.class))
                .lines()
                .filter(line -> line.startsWith("[") && line.endsWith("]"))
                .findFirst()
                .orElse("");
        try {
            list = Arrays.asList(mapper.readValue(stringData, FightDto[].class));
        } catch (IOException e) {
            log.error("Cannot read value from stringData.");
            list = new ArrayList<>();
        }

        return createFightList(list);
    }

    private FightList createFightList(List<FightDto> fightDtoList) {
        return FightList
                .builder()
                .fights(fightMapper.toListFight(fightDtoList))
                .createdAt(LocalDate.now())
                .build();
    }

    private boolean shouldUpdateData(FightList fightList) {
        int daysSinceLastUpdate = fightList.getCreatedAt().until(LocalDate.now()).isNegative() ?
                LocalDate.now().until(fightList.getCreatedAt()).getDays() : fightList.getCreatedAt().until(LocalDate.now()).getDays();
        return daysSinceLastUpdate > updateEveryDays;
    }
}
