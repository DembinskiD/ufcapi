package com.dembinski.ufcapi.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;

@Entity
@Table(name = "Fight")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
public class Fight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @NotNull
    private LocalDate date;

    @NotNull
    private String promotion;

    @NotNull
    private String event;

    @NotNull
    private String main_or_prelim;

    @NotNull
    private String card_placement;

    @NotNull
    private String fighter_1;

    @NotNull
    private String fighter_2;

    @NotNull
    private String rematch;

    @NotNull
    private String winner;

    @NotNull
    private int round;

    @NotNull
    private String method;

    @NotNull
    private String time;

    @NotNull
    private String fighting_tomatoes_aggregate_rating;

    @NotNull
    private String fighting_tomatoes_number_ratings;
}
