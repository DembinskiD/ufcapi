package com.dembinski.ufcapi.json;

import com.dembinski.ufcapi.data.FightList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
class FightReaderTest {

    @Autowired
    FightReader fightReader;

    @Test
    void test1() {
        fightReader.saveToFile(FightList.builder()
                .fights(new ArrayList<>())
                .createdAt(LocalDate.now())
                .build());
    }
}