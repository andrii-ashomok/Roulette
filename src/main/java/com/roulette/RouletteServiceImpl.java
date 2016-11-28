package com.roulette;

import com.data.PlayerService;
import com.user.Bet;
import com.user.BetCategory;
import com.user.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rado on 11/27/16.
 */
public class RouletteServiceImpl implements RouletteService {

    private PlayerService playerService;
    
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void scheduleRoulette() {
        Set<Bet> bets = playerService.getAndRemoveAllBets();
        if (bets.isEmpty())
            return;

        if (playerService.getAllPlayers().isEmpty()) {
            System.err.println("No players registered in the game");
            return;
        }

        int random = ThreadLocalRandom.current().nextInt(0, BET_SIZE);
        final WonBet wonBet = new WonBet(random);

        bets.forEach(b -> isBetWon(b, wonBet));
        System.out.println("=========================");
        printResult(bets, wonBet.getWonNumber());
        System.out.println("=========================");

    }


    @Override
    public boolean isBetWon(final Bet bet, final WonBet wonBet) {

        if (Objects.isNull(bet) || Objects.isNull(wonBet))
            return false;

        if (BetCategory.EVEN.name().equalsIgnoreCase(bet.getNewBet())) {

            if (wonBet.isEVEN()) {
                bet.setWon(true);
                bet.setWonRate(2);

                return true;
            }

        } else if (BetCategory.ODD.name().equalsIgnoreCase(bet.getNewBet())) {

            if (wonBet.isODD()) {
                bet.setWon(true);
                bet.setWonRate( 2);

                return true;
            }

        } else if (wonBet.getWonNumber() == Integer.valueOf(bet.getNewBet())) {
            bet.setWon(true);
            bet.setWonRate(36);

            return true;
        }

        bet.setWon(false);
        bet.setWonRate(0);

        return bet.isWon();
    }


    @Override
    public void printResult(final Set<Bet> bets, int wonNumber) {

        System.out.println("Number: " + wonNumber);
        System.out.println("Player\t\t Bet\t Outcome\t Winnings");
        System.out.println("---------");

        String result;
        Player player;
        Optional<Player> optional;
        int totalBet;
        int totalWin;
        int wonValue;
        List<Player> playerBet = new ArrayList<>();

        for (Bet bet : bets) {

            optional = playerService.getCustomerByName(bet.getUserName());

            if (!optional.isPresent())
                continue;

            player = optional.get();
            result = bet.isWon() ? "WIN" : "LOSE";

            wonValue = bet.getValue() * bet.getWonRate();
            if (bet.isWon()) {
                totalWin = wonValue + player.getTotalWin();
                player.setTotalWin(totalWin);
            }

            totalBet = player.getTotalBet() + bet.getValue();
            player.setTotalBet(totalBet);

            playerBet.add(player);

            System.out.println(bet.getUserName() + "\t\t " + bet.getNewBet() + "\t\t "
                    + result + "\t\t " + wonValue);
        }

        System.out.println();

        System.out.println("Player\t\t Total Win\t Total Bet");
        System.out.println("---------");
        playerBet.forEach(p -> System.out.println(p.getUserName() + "\t\t "
                + p.getTotalWin() + "\t\t " + p.getTotalBet()));

    }


}
