package com.dembinski.ufcapi.source;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FightDTO implements Serializable {
    LocalDate date;
    String promotion;
    String event;
    String main_or_prelim;
    String card_placement;
    String fighter_1;
    String fighter_2;
    String rematch;
    String winner;
    int round;
    String method;
    String time;
    String fighting_tomatoes_aggregate_rating;
    String fighting_tomatoes_number_ratings;
}
