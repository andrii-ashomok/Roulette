package com.roulette;

import com.user.Bet;

/**
 * Created by rado on 11/27/16.
 */
public interface RouletteService {

    int BET_SIZE = 37;

    void scheduleRoulette();

    boolean isBetWon(Bet bet, WonBet wonBet);

}
