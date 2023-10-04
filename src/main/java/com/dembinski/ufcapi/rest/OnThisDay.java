package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.json.FightReader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@Getter
public class OnThisDay {

    private final FightReader fightReader;
    private List<Fight> allFights;

    @PostConstruct
    void getListOfFights() {
        this.allFights = fightReader.getAllFights();
    }

    @GetMapping("/onThisDay")
    public List<Fight> whatHappenedOnThisDay() {
        return getAllFights()
                .stream()
                .filter(this::didFightHappenOnThisDay)
                .collect(Collectors.toList());
    }

    protected boolean didFightHappenOnThisDay(Fight fight) {
        try {
            LocalDate fightDate = LocalDate.parse(fight.getDate(), DateTimeFormatter.ISO_DATE);
            LocalDate thisDay = getThisDay();

            return (thisDay.getMonth().equals(fightDate.getMonth())) && (thisDay.getDayOfMonth() == fightDate.getDayOfMonth());
        } catch (DateTimeException exception) {
            log.debug("Returning false due to DateTimeException in didFightHappenOnThisDay: {}", exception.toString());
            return false;
        }
    }

    protected LocalDate getThisDay() {
        return LocalDate.now();
    }
}
