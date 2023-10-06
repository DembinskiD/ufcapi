package com.dembinski.ufcapi.source;

import com.dembinski.ufcapi.data.FightList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class FightDTOReaderTest {

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