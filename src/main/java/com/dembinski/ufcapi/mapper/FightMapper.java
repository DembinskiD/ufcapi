package com.dembinski.ufcapi.mapper;

import com.dembinski.ufcapi.data.Fight;
import com.dembinski.ufcapi.source.FightDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FightMapper {

    FightDto toFightDto(Fight fight);

    Fight toFight(FightDto fightDto);

    List<FightDto> toListFightDto(List<Fight> fightList);

    List<Fight> toListFight(List<FightDto> fightDtoList);

}
