package com.roulette;

import com.user.Bet;

import java.util.Set;

/**
 * Created by rado on 11/27/16.
 */
public interface RouletteService {

    int BET_SIZE = 37;

    void scheduleRoulette();

    boolean isBetWon(Bet bet, WonBet wonBet);

    void printResult(final Set<Bet> bets, int wonNumber);

}
