package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.source.FightDTO;
import com.dembinski.ufcapi.source.FightReader;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {OnThisDay.class, FightReader.class})
class OnThisDayTest {

    @Mock
    private FightReader fightReader;

    @Spy
    private OnThisDay onThisDay = new OnThisDay(fightReader);

    @Test
    void getListOfFights() {
        Mockito.doReturn(getListOfTestFights()).when(onThisDay).getAllFightDTOS();
        Mockito.doReturn(LocalDate.of(2023, 9, 13)).when(onThisDay).getThisDay();


        List<FightDTO> fights = onThisDay.whatHappenedOnThisDay();

        assertEquals(2, fights.size());
        assertEquals(2, fights.stream().filter(fight -> fight.getDate().equals(LocalDate.of(2023, 9, 13))).count());
    }

    private List<FightDTO> getListOfTestFights() {
        return List.of(
                createFight("2023-09-13"),
                createFight("2023-09-13"),
                createFight("2023-09-14"),
                createFight("2023-09-15"),
                createFight("2023-09-16"));
    }

    private FightDTO createFight(String date) {
        return FightDTO.builder()
                .date(LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                .build();
    }
}