package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.json.Reader;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class FightsBy {

    private final List<Fight> allFights;

    public FightsBy(Reader reader) {
        this.allFights = reader.getAllFights();
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
    public String getDataByCustomQuery(@RequestParam Map<String, String> customQuery) {
        Predicate<Fight> finalPredicate = fight -> true;



        return customQuery.entrySet().toString();
    }

    private Predicate<Fight> byTypePredicate(String type) {
        return fight -> fight.getMain_or_prelim().equals(type);
    }

    private Predicate<Fight> byWinnerPredicate(String winner) {
        return fight -> fight.getWinner().contains(winner);
    }

    private Predicate<Fight> byDatePredicate(String date) {
        return fight -> fight.getDate().equals(date);
    }

    private Predicate<Fight> byFighterPredicate(String fighter) {
        return fight -> fight.getFighter_1().contains(fighter) || fight.getFighter_2().contains(fighter);
    }
}
