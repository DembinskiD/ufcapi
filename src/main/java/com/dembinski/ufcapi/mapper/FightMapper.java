package com.dembinski.ufcapi.mapper;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.source.FightDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FightMapper {

    FightDTO toFightDto(Fight fight);

    Fight toFight(FightDTO fightDto);

    List<FightDTO> toListFightDto(List<Fight> fightList);

    List<Fight> toListFight(List<FightDTO> fightDtoList);

}
