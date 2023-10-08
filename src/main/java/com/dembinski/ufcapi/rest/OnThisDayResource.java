package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.service.FightService;
import com.dembinski.ufcapi.source.FightDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@Getter
public class OnThisDayResource {

    private final FightService fightService;

    @GetMapping("/onThisDay")
    public List<FightDto> whatHappenedOnThisDay() {
        return getAllFights()
                .stream()
                .filter(this::didFightHappenOnThisDay)
                .collect(Collectors.toList());
    }

    protected List<FightDto> getAllFights() {
        return fightService.getAllDto();
    }

    protected boolean didFightHappenOnThisDay(FightDto fightDTO) {
        try {
            LocalDate fightDate = fightDTO.getDate();
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
