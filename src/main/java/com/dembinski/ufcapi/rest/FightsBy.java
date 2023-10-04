package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.source.Fight;
import com.dembinski.ufcapi.source.FightReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class FightsBy {

    private final List<Fight> allFights;
    private final Predicates predicates;

    public FightsBy(FightReader fightReader, Predicates predicates) {
        this.allFights = fightReader.getAllFights();
        this.predicates = predicates;
    }

    @GetMapping("/getByType")
    public List<Fight> getFightsByType(@RequestParam String type) {
        return this.allFights.stream()
                .filter(fight -> fight.getMain_or_prelim().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @GetMapping("/getByDate")
    public List<Fight> getFightsByDate(@RequestParam String date) {
        return allFights.stream()
                .filter(fight -> fight.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @GetMapping("/getByFighter")
    public List<Fight> getFightsByFighter(@RequestParam String fighterName) {
        return allFights.stream()
                .filter(fight -> fight.getFighter_1().contains(fighterName) || fight.getFighter_2().contains(fighterName))
                .collect(Collectors.toList());
    }

    @GetMapping("/getByWinner")
    public List<Fight> getFightsByWinner(@RequestParam String fighterName) {
        return allFights.stream()
                .filter(fight -> fight.getWinner().contains(fighterName))
                .collect(Collectors.toList());
    }

    @GetMapping("/customQuery")
    public List<Fight> getDataByCustomQuery(@RequestParam Map<String, String> customQuery) {
        Predicate<Fight> fightPredicate = customQuery.entrySet().stream().map(predicates::getPredicate).reduce(Predicate::and).orElse(fight -> true);

        return allFights
                .stream()
                .filter(fightPredicate)
                .collect(Collectors.toList());
    }
}
