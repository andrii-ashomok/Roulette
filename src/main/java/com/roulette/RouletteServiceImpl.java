package com.roulette;

import com.data.PlayerService;
import com.user.Bet;
import com.user.BetCategory;
import com.user.Player;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by rado on 11/27/16.
 */
public class RouletteServiceImpl implements RouletteService {

    private PlayerService playerService;
    
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void scheduleRoulette() {
        int random = ThreadLocalRandom.current().nextInt(0, BET_SIZE);
        final WonBet wonBet = new WonBet(random);

        Set<Bet> bets = playerService.getAndRemoveAllBets();
        Set<Player> players = playerService.getAllPlayers();

        printResult(bets, wonBet.getWonNumber());
    }

    public boolean isBetWon(final Bet bet, final WonBet wonBet) {

        if (BetCategory.EVEN.name().equals(bet.getNewBet()) && wonBet.isEVEN()) {
            bet.setWon(true);
            bet.setWonRate((short) 2);

        } else if (BetCategory.ODD.name().equals(bet.getNewBet()) && wonBet.isODD()) {
            bet.setWon(true);
            bet.setWonRate((short) 2);

        } else if (wonBet.getWonNumber() == Integer.valueOf(bet.getNewBet())) {
            bet.setWon(true);
            bet.setWonRate((short) 36);

        } else {
            bet.setWon(false);
            bet.setWonRate((short) 0);
        }

        return bet.isWon();
    }


    private void printResult(final Set<Bet> bets, int wonNumber) {

        System.out.println("Number: " + wonNumber);
        System.out.println("Player\t\t Bet\t Outcome\t Winnings");
        System.out.println("---------");

        String result;
        for (Bet bet : bets) {

            result = bet.isWon() ? "WIN" : "LOSE";

            System.out.println(bet.getUserName() + "\t\t" + bet.getNewBet() + "\t"
            + result + "\t" + bet.getValue()*bet.getWonRate());
        }

    }


}
