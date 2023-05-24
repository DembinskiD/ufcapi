package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.json.Reader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class Fights {

    private final Reader reader;

    @GetMapping("/")
    public List<Fight> getAllFights() {
        return reader.getAllFights();
    }

    @GetMapping("/{type}")
    public List<Fight> getFightsByType(@PathVariable String type) {
        return this.reader.getAllFights().stream()
                .filter(fight -> fight.getMain_or_prelim().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @GetMapping("/count")
    public Long getAllFightsCount(@RequestParam Optional<String> type) {
        return type.isPresent() ?
                this.reader.getAllFights().stream()
                        .filter(x -> x.getMain_or_prelim().equalsIgnoreCase(type.get()))
                        .count() :
                this.reader.getAllFights().size();
    }
}
