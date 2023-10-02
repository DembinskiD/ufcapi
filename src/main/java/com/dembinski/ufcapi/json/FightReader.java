package com.dembinski.ufcapi.json;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.data.FightList;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FightReader {
    private final String apiKey = "5a881fececbfe0a958072272a50be4ea6d67d534"; //todo move it to properties
    private final String uri = String.format("https://fightingtomatoes.com/API/%s/Any/Any/Any", apiKey); //todo move it to properties
    private final int updateEveryDays = 10; //todo move it to properties
    private String TARGET_FILE_NAME = "full_data.json"; //todo as above

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

    private void removeExistingFightList() {
        try {
            Files.delete(Path.of(TARGET_FILE_NAME));
        } catch (IOException e) {
            log.debug("Issue removing existing file {}", TARGET_FILE_NAME);
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

    public List<Fight> getAllFights() {
        if (Files.exists(Path.of(TARGET_FILE_NAME))) {
            FightList fightList = readFightListFromFile();
            if (shouldUpdateData(fightList)) {
                removeExistingFightList();
                FightList fightListFromAPI = getFightListFromAPI();
                saveToFile(fightListFromAPI);
                return fightListFromAPI.fights();
            } else {
                return fightList.fights();
            }
        } else {
            FightList fightListFromAPI = getFightListFromAPI();
            saveToFile(fightListFromAPI);
            return fightListFromAPI.fights();
        }
    }

    private FightList getFightListFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<Fight> list;

        String stringData = Objects.requireNonNull(restTemplate.getForObject(uri, String.class))
                .lines()
                .filter(line -> line.startsWith("[") && line.endsWith("]"))
                .findFirst()
                .orElse("");
        try {
            list = Arrays.asList(mapper.readValue(stringData, Fight[].class));
        } catch (IOException e) {
            log.error("Cannot read value from stringData.");
            list = new ArrayList<>();
        }

        return createFightList(list);
    }

    private FightList createFightList(List<Fight> fightList) {
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
}
