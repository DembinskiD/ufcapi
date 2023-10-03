package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.json.FightReader;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
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
public class Fights {

    private final FightReader fightReader;

    private List<Fight> allFights;

    @PostConstruct
    void getListOfFights() {
        this.allFights = fightReader.getAllFights();
    }

    @GetMapping({"/", "/getAll"})
    public List<Fight> getAllFights() {
        return allFights;
    }

    @GetMapping("/count")
    public Long getAllFightsCount(@RequestParam Optional<String> type) {
        return type.isPresent() ?
                this.allFights.stream()
                        .filter(x -> x.getMain_or_prelim().equalsIgnoreCase(type.get()))
                        .count() :
                this.allFights.size();
    }


}
