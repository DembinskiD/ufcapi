package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.service.FightService;
import com.dembinski.ufcapi.source.FightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FightsBy {

    private final Predicates predicates;
    private final FightService fightService;


    @GetMapping("/getByType")
    public List<FightDTO> getFightsByType(@RequestParam String type) {
        return fightService.getAllDto().stream()
                .filter(fight -> fight.getMain_or_prelim().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @GetMapping("/getByDate")
    public List<FightDTO> getFightsByDate(@RequestParam String date) {
        return fightService.getAllDto().stream()
                .filter(fight -> fight.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @GetMapping("/getByFighter")
    public List<FightDTO> getFightsByFighter(@RequestParam String fighterName) {
        return fightService.getAllDto().stream()
                .filter(fight -> fight.getFighter_1().contains(fighterName) || fight.getFighter_2().contains(fighterName))
                .collect(Collectors.toList());
    }

    @GetMapping("/getByWinner")
    public List<FightDTO> getFightsByWinner(@RequestParam String fighterName) {
        return fightService.getAllDto().stream()
                .filter(fight -> fight.getWinner().contains(fighterName))
                .collect(Collectors.toList());
    }

    @GetMapping("/customQuery")
    public List<FightDTO> getDataByCustomQuery(@RequestParam Map<String, String> customQuery) {
        Predicate<FightDTO> fightPredicate = customQuery.entrySet().stream().map(predicates::getPredicate).reduce(Predicate::and).orElse(fight -> true);

        return fightService.getAllDto()
                .stream()
                .filter(fightPredicate)
                .collect(Collectors.toList());
    }
}
