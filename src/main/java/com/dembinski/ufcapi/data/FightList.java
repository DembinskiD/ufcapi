package com.dembinski.ufcapi.data;

import com.dembinski.ufcapi.source.FightDTO;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
public record FightList(List<FightDTO> fightDTOS, LocalDate createdAt) implements Serializable {
}
