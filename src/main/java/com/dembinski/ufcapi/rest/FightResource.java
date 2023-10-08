package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.service.FightService;
import com.dembinski.ufcapi.source.FightDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FightResource {

    private final FightService fightService;


    @GetMapping({"/", "/getAll"})
    public List<FightDto> getAllFightDTOS() {
        return fightService.getAllDto();
    }

    @GetMapping("/count")
    public Long getAllFightsCount(@RequestParam Optional<String> type) {
        return fightService.countAll();
    }
}
