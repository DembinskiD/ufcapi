package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.source.FightDTO;
import com.dembinski.ufcapi.source.FightReader;
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
    private List<FightDTO> allFightDTOS;

    @PostConstruct
    void getListOfFights() {
        this.allFightDTOS = fightReader.getAllFights();
    }

    @GetMapping("/onThisDay")
    public List<FightDTO> whatHappenedOnThisDay() {
        return getAllFightDTOS()
                .stream()
                .filter(this::didFightHappenOnThisDay)
                .collect(Collectors.toList());
    }

    protected boolean didFightHappenOnThisDay(FightDTO fightDTO) {
        try {
            LocalDate fightDate = LocalDate.parse(fightDTO.getDate(), DateTimeFormatter.ISO_DATE);
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
