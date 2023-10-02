package com.dembinski.ufcapi.json;

import com.dembinski.ufcapi.data.FightList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
@Slf4j
class FightReaderTest {

    @Autowired
    FightReader fightReader;

    @Test
    void readFightListFromFile() {
        fightReader.removeExistingFightList();
        fightReader.checkIfFileExists();
        FightList fightList = fightReader.readFightListFromFile();
        log.info(fightList.toString());
    }

    @Test
    void readFromAPI_test() {
        FightList fightList = fightReader.getFightListFromAPI();
        log.info(fightList.toString());
    }

    @Test
    void testAllFights() {
        fightReader.getAllFights();
    }
}