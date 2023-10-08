package com.dembinski.ufcapi.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
@Entity
@Table(name = "fight_list")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class FightList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "id")
    private List<Fight> fights;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
