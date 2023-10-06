package com.dembinski.ufcapi.service;

import com.dembinski.ufcapi.mapper.FightMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class FightService {

    private final FightMapper fightMapper;
}
