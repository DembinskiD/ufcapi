package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.json.Reader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class Fights {

    private final List<Fight> allFights;

    public Fights(Reader reader) {
        this.allFights = reader.getAllFights();
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
