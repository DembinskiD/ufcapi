package com.dembinski.ufcapi.source;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class FightDTO implements Serializable {
    private String date; //todo to be changed for LocalDate
    private String promotion;
    private String event;
    private String main_or_prelim;
    private String card_placement;
    private String fighter_1;
    private String fighter_2;
    private String rematch;
    private String winner;
    private int round;
    private String method;
    private String time;
    private String fighting_tomatoes_aggregate_rating;
    private String fighting_tomatoes_number_ratings;
}
