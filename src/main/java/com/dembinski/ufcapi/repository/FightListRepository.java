package com.dembinski.ufcapi.repository;

import com.dembinski.ufcapi.data.FightList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FightListRepository extends JpaRepository<FightList, Long> {
}
