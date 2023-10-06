package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.source.FightDTO;
import com.dembinski.ufcapi.source.FightReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class Fights {

    private final FightReader fightReader;

    private final List<FightDTO> allFightDTOS = new ArrayList<>();

    @PostConstruct
    void initializeFightList() {
        this.allFightDTOS.addAll(fightReader.getAllFights());
    }

    @GetMapping({"/", "/getAll"})
    public List<FightDTO> getAllFightDTOS() {
        return allFightDTOS;
    }

    @GetMapping("/count")
    public Long getAllFightsCount(@RequestParam Optional<String> type) {
        return type.isPresent() ?
                this.allFightDTOS.stream()
                        .filter(x -> x.getMain_or_prelim().equalsIgnoreCase(type.get()))
                        .count() :
                this.allFightDTOS.size();
    }


}
