package com.dembinski.ufcapi.service;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.mapper.FightMapper;
import com.dembinski.ufcapi.repository.FightRepository;
import com.dembinski.ufcapi.source.FightReader;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class FightService {

    private final FightMapper fightMapper;
    private final FightRepository fightRepository;
    private final FightReader fightReader;

    @PostConstruct
    void loadDB() {
        loadDatabase();
    }

    public List<Fight> loadDatabase() {
        return fightRepository.saveAll(fightMapper.toListFight(fightReader.getAllFights()));
    }
}
