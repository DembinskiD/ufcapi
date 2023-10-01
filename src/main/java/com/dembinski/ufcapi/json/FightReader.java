package com.dembinski.ufcapi.json;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.data.FightList;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FightReader {
    private final List<Fight> allFights;
    private final String apiKey = "5a881fececbfe0a958072272a50be4ea6d67d534";
    private final String uri = String.format("https://fightingtomatoes.com/API/%s/Any/Any/Any", apiKey);
    private final int updateEveryDays = 10;
    private String TARGET_FILE_NAME = "full_data.json";

    public FightReader() {
        this.allFights = generateFightList();
    }

    private FightList readFightListFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(TARGET_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (FightList) objectInputStream.readObject();
        } catch (IOException | NullPointerException e) {
            log.debug("Cannot read from file. Reading from API.");
            return getFightListFromAPI();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void saveToFile(FightList fightList) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(TARGET_FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(fightList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private List<Fight> generateFightList() {
        FightList existingData = readFightListFromFile();

        return new ArrayList<>(); //todo continue check if file exist, if should be updated and eventually update here[
    }

    private FightList getFightListFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<Fight> list;

        String stringData = Objects.requireNonNull(restTemplate.getForObject(uri, String.class)).
                lines()
                .filter(line -> line.startsWith("[") && line.endsWith("]"))
                .findFirst()
                .orElse("");
        try {
            list = Arrays.asList(mapper.readValue(stringData, Fight[].class));
        } catch (IOException e) {
            log.error("Cannot read value from stringData.");
            list = new ArrayList<>();
        }

        FightList fightList = createListOfFights(list);
        saveToFile(fightList);
        return fightList;

    }

    private FightList createListOfFights(List<Fight> fightList) {
        return FightList
                .builder()
                .fights(fightList)
                .createdAt(LocalDate.now())
                .build();
    }

    private boolean shouldUpdateData(FightList fightList) {
        int daysSinceLastUpdate = fightList.createdAt().until(LocalDate.now()).isNegative() ?
                LocalDate.now().until(fightList.createdAt()).getDays() : fightList.createdAt().until(LocalDate.now()).getDays();
        return daysSinceLastUpdate > updateEveryDays;
    }

    public List<Fight> getAllFights() {
        return this.allFights;
    }
}
