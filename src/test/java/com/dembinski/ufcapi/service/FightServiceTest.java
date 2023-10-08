package com.dembinski.ufcapi.service;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.mapper.FightMapper;
import com.dembinski.ufcapi.source.FightDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
class FightServiceTest {

    @Autowired
    private FightMapper fightMapper;

    @Test
    void test_toFightMapper() {
        FightDto testFightDto = getFightDto(1);

        Fight mappedFight = fightMapper.toFight(testFightDto);

        assertEquals(LocalDate.of(2023, 10, 5), mappedFight.getDate());
        assertEquals("testPromotion 1", mappedFight.getPromotion());
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

    private FightDto getFightDto(int val) {
        return FightDto
                .builder()
                .date(LocalDate.of(2023, 10, 5))
                .promotion("testPromotion " + val)
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
    }

    @Test
    void test_toFightDtoMapper() {
        Fight testFight = getFight(1);

        FightDto mappedFight = fightMapper.toFightDto(testFight);

        assertEquals(LocalDate.of(2023, 10, 5), mappedFight.getDate());
        assertEquals("testPromotion 1", mappedFight.getPromotion());
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

    private Fight getFight(int val) {
        return Fight
                .builder()
                .date(LocalDate.of(2023, 10, 5))
                .promotion("testPromotion " + val)
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
    }

    private List<Fight> generateFights(int amt) {
        return IntStream.range(0, amt).mapToObj(this::getFight).toList();
    }

    private List<FightDto> generateFightDtos(int amt) {
        return IntStream.range(0, amt).mapToObj(this::getFightDto).toList();
    }

    @Test
    void test_toFightDtoList() {
        List<Fight> sourceList = generateFights(10);

        List<FightDto> targetList = fightMapper.toListFightDto(sourceList);

        assertEquals(10, targetList.size());
        assertNotNull(targetList);
        targetList.forEach(Assertions::assertNotNull);
    }

    @Test
    void test_toFightList() {
        List<FightDto> sourceList = generateFightDtos(10);

        List<Fight> targetList = fightMapper.toListFight(sourceList);

        assertEquals(10, targetList.size());
        assertNotNull(targetList);
        targetList.forEach(Assertions::assertNotNull);
    }
}