package com.roulette;

import com.user.Bet;

import java.util.Set;

/**
 * Created by rado on 11/27/16.
 */
public interface RouletteService {

    int BET_SIZE = 37;

    /**
     * Scheduled method, run every 30 sec
     * and set random winner number (between 1 - 36)
     * If players data is empty, method skipped.
     * If Bet data is empty, method skipped.
     * Method check all bets and mark that as WIN or LOSE
     * after that it print to console Bet's result and total result statistics
     */
    void scheduleRoulette();

    /**
     * Method check if the bet is win or lose
     * @param bet - user bet
     * @param wonBet - class contains winner number and methods that give answer,
     *               does winner number <b>EVEN</b> or <b>ODD</b>
     * @return - <b>true</b> - input Bet <b>win</b>, <b>false</b> - input Bet <b>lose</b>;
     */
    boolean isBetWon(Bet bet, WonBet wonBet);

    /**
     * Method print to <b>console</b> result of the roulette.
     * It includes statistics for all the bets
     * and statistics about players 'total win', 'total bet'
     * @param bets - bets that users set <b>in console</b>
     * @param wonNumber - class contains winner number
     */
    void printResult(final Set<Bet> bets, int wonNumber);

}
