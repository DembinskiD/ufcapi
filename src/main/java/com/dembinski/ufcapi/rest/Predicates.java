package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.data.Fight;
import org.springframework.stereotype.Service;

import java.util.Map.Entry;
import java.util.function.Predicate;

@Service
public class Predicates {
    FightPredicate allFightPredicate = new AllFightPredicate();
    FightPredicate byTypePredicate = new ByTypePredicate();
    FightPredicate byWinnerPredicate = new ByWinnerPredicate();
    FightPredicate byDatePredicate = new ByDatePredicate();
    FightPredicate byFighterPredicate = new ByFighterPredicate();

    public Predicate<Fight> getPredicate(Entry<String, String> inputMethod) {
        return switch (inputMethod.getKey()) {
            case "type" -> byTypePredicate.getPredicate(inputMethod.getValue());
            case "winner" -> byWinnerPredicate.getPredicate(inputMethod.getValue());
            case "date" -> byDatePredicate.getPredicate(inputMethod.getValue());
            case "fighter" -> byFighterPredicate.getPredicate(inputMethod.getValue());
            default -> allFightPredicate.getPredicate(inputMethod.getValue());
        };
    }
}

interface FightPredicate {
    default Predicate<Fight> getPredicate(String value) {
        return fight -> true;
    }
}

class AllFightPredicate implements FightPredicate {
}

class ByTypePredicate implements FightPredicate {
    @Override
    public Predicate<Fight> getPredicate(String value) {
        return fight -> fight.getMain_or_prelim().equalsIgnoreCase(value);
    }
}

class ByWinnerPredicate implements FightPredicate {
    @Override
    public Predicate<Fight> getPredicate(String value) {
        return fight -> fight.getWinner().contains(value);
    }
}

class ByFighterPredicate implements FightPredicate {
    @Override
    public Predicate<Fight> getPredicate(String value) {
        return fight -> fight.getFighter_1().contains(value) || fight.getFighter_2().contains(value);
    }
}

class ByDatePredicate implements FightPredicate {
    @Override
    public Predicate<Fight> getPredicate(String value) {
        return fight -> fight.getDate().equalsIgnoreCase(value);
    }
}


