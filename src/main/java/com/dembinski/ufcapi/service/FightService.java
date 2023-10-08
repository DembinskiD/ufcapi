package com.dembinski.ufcapi.service;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.mapper.FightMapper;
import com.dembinski.ufcapi.repository.FightRepository;
import com.dembinski.ufcapi.source.FightDto;
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

    public List<Fight> getAll() {
        return fightRepository.findAll();
    }

    public List<FightDto> getAllDto() {
        return fightMapper.toListFightDto(fightRepository.findAll());
    }

    public long countAll() {
        return fightRepository.count();
    }
}
