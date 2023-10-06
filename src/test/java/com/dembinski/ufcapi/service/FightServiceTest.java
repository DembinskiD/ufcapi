package com.dembinski.ufcapi.service;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.mapper.FightMapper;
import com.dembinski.ufcapi.source.FightDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class FightServiceTest {

    @Autowired
    private FightMapper fightMapper;

    @Test
    void test_toFightMapper() {
        FightDTO testFightDto = FightDTO
                .builder()
                .date(LocalDate.of(2023, 10, 5))
                .promotion("testPromotion")
                .event("testEvent")
                .main_or_prelim("testMain")
                .card_placement("testCardPlacement")
                .fighter_1("testFighter1")
                .fighter_2("testFighter2")
                .rematch("testRematch")
                .winner("testWinner")
                .round(1)
                .method("testMethod")
                .time("testTime")
                .fighting_tomatoes_aggregate_rating("testFightingTomatoesAggregate")
                .fighting_tomatoes_number_ratings("testFightingTomatoesNumber")
                .build();

        Fight mappedFight = fightMapper.toFight(testFightDto);

        assertEquals(LocalDate.of(2023, 10, 5), mappedFight.getDate());
        assertEquals("testPromotion", mappedFight.getPromotion());
        assertEquals("testEvent", mappedFight.getEvent());
        assertEquals("testMain", mappedFight.getMain_or_prelim());
        assertEquals("testCardPlacement", mappedFight.getCard_placement());
        assertEquals("testFighter1", mappedFight.getFighter_1());
        assertEquals("testFighter2", mappedFight.getFighter_2());
        assertEquals("testRematch", mappedFight.getRematch());
        assertEquals("testWinner", mappedFight.getWinner());
        assertEquals(1, mappedFight.getRound());
        assertEquals("testMethod", mappedFight.getMethod());
        assertEquals("testTime", mappedFight.getTime());
        assertEquals("testFightingTomatoesAggregate", mappedFight.getFighting_tomatoes_aggregate_rating());
        assertEquals("testFightingTomatoesNumber", mappedFight.getFighting_tomatoes_number_ratings());
    }

    @Test
    void test_toFightDtoMapper() {
        Fight testFight = Fight
                .builder()
                .date(LocalDate.of(2023, 10, 5))
                .promotion("testPromotion")
                .event("testEvent")
                .main_or_prelim("testMain")
                .card_placement("testCardPlacement")
                .fighter_1("testFighter1")
                .fighter_2("testFighter2")
                .rematch("testRematch")
                .winner("testWinner")
                .round(1)
                .method("testMethod")
                .time("testTime")
                .fighting_tomatoes_aggregate_rating("testFightingTomatoesAggregate")
                .fighting_tomatoes_number_ratings("testFightingTomatoesNumber")
                .build();

        FightDTO mappedFight = fightMapper.toFightDto(testFight);

        assertEquals(LocalDate.of(2023, 10, 5), mappedFight.getDate());
        assertEquals("testPromotion", mappedFight.getPromotion());
        assertEquals("testEvent", mappedFight.getEvent());
        assertEquals("testMain", mappedFight.getMain_or_prelim());
        assertEquals("testCardPlacement", mappedFight.getCard_placement());
        assertEquals("testFighter1", mappedFight.getFighter_1());
        assertEquals("testFighter2", mappedFight.getFighter_2());
        assertEquals("testRematch", mappedFight.getRematch());
        assertEquals("testWinner", mappedFight.getWinner());
        assertEquals(1, mappedFight.getRound());
        assertEquals("testMethod", mappedFight.getMethod());
        assertEquals("testTime", mappedFight.getTime());
        assertEquals("testFightingTomatoesAggregate", mappedFight.getFighting_tomatoes_aggregate_rating());
        assertEquals("testFightingTomatoesNumber", mappedFight.getFighting_tomatoes_number_ratings());
    }
}