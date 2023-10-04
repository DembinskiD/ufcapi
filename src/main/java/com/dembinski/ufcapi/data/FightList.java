package com.dembinski.ufcapi.data;

import com.dembinski.ufcapi.source.Fight;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
public record FightList(List<Fight> fights, LocalDate createdAt) implements Serializable {
}
