package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.json.FightReader;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {OnThisDay.class, FightReader.class})
class OnThisDayTest {

    @Mock
    private OnThisDay onThisDay;

    @Test
    void getListOfFights() {
        Mockito.doReturn(getListOfTestFights()).when(onThisDay).getAllFights();
        Mockito.doReturn(LocalDate.of(2023, 9, 13)).when(onThisDay).getThisDay();


        List<Fight> fights = onThisDay.whatHappenedOnThisDay();
        List<Boolean> didFightsHappen = getListOfTestFights()
                .stream()
                .map(fight -> onThisDay.didFightHappenOnThisDay(fight))
                .toList();

        assertEquals(3, didFightsHappen.stream().map(x -> x.equals(true)).toList().size());
        assertEquals(3, fights.size());
        assertEquals(3, fights.stream().filter(fight -> fight.getDate().equals("2023-09-13")).count());
    }

    private List<Fight> getListOfTestFights() {
        return List.of(
                createFight("2023-09-13"),
                createFight("2023-09-13"),
                createFight("2023-09-14"),
                createFight("2023-09-15"),
                createFight("2023-09-16"));
    }

    private Fight createFight(String date) {
        return Fight.builder()
                .date(date)
                .build();
    }
}