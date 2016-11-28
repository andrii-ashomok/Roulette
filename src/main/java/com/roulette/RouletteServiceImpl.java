package com.roulette;

import com.data.PlayerService;
import com.user.Bet;
import com.user.BetCategory;
import com.user.Player;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
        int random = ThreadLocalRandom.current().nextInt(0, BET_SIZE);
        final WonBet wonBet = new WonBet(random);

        Set<Bet> bets = playerService.getAndRemoveAllBets();
        Set<Player> players = playerService.getAllPlayers();

        if (!players.isEmpty()) {
            printResult(bets, wonBet.getWonNumber());
        }
    }

    @Override
    public boolean isBetWon(final Bet bet, final WonBet wonBet) {

        if (Objects.isNull(bet) || Objects.isNull(wonBet))
            return false;

        if (BetCategory.EVEN.name().equalsIgnoreCase(bet.getNewBet())) {

            if (wonBet.isEVEN()) {
                bet.setWon(true);
                bet.setWonRate((short) 2);

                return true;
            }

        } else if (BetCategory.ODD.name().equalsIgnoreCase(bet.getNewBet())) {

            if (wonBet.isODD()) {
                bet.setWon(true);
                bet.setWonRate((short) 2);

                return true;
            }

        } else if (wonBet.getWonNumber() == Integer.valueOf(bet.getNewBet())) {
            bet.setWon(true);
            bet.setWonRate((short) 36);

            return true;
        }


        bet.setWon(false);
        bet.setWonRate((short) 0);


        return bet.isWon();
    }


    private void printResult(final Set<Bet> bets, int wonNumber) {

        System.out.println("Number: " + wonNumber);
        System.out.println("Player\t\t Bet\t Outcome\t Winnings");
        System.out.println("---------");

        List<Bet> existsPlayerBets = bets.stream()
                .filter(b -> playerService.getCustomerByName(b.getUserName()).isPresent())
                .collect(Collectors.toList());

        String result;
        for (Bet bet : existsPlayerBets) {

            result = bet.isWon() ? "WIN" : "LOSE";

            System.out.println(bet.getUserName() + "\t\t " + bet.getNewBet() + "\t\t "
                    + result + "\t\t " + (bet.getValue() * bet.getWonRate()));
        }

    }


}
