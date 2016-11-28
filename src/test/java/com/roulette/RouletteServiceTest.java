package com.roulette;

import com.data.PlayerService;
import com.user.Bet;
import com.user.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Created by rado on 11/28/16.
 */
@ContextConfiguration("/roulette.xml")
@Test(suiteName = "com/roulette", groups = {"regression"})
public class RouletteServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RouletteService rouletteService;

    @Autowired
    private PlayerService playerService;

    @Test
    public void testIsBetWon() {

        assert !rouletteService.isBetWon(null, null);

        String playerName = "Ann";
        String betTitle = "EVEN";
        short amount = 5;
        Bet betEVEN = new Bet(playerName, betTitle, amount);

        assert !rouletteService.isBetWon(betEVEN, null);

        WonBet wonBetODD = new WonBet(1);
        WonBet wonBetEVEN = new WonBet(19);
        WonBet wonBet36 = new WonBet(36);

        assert !rouletteService.isBetWon(null, wonBetODD);
        assert !rouletteService.isBetWon(betEVEN, wonBetODD);
        assert rouletteService.isBetWon(betEVEN, wonBetEVEN);

        playerName = "Jorge";
        betTitle = "67";
        amount = 5;
        Bet batBet = new Bet(playerName, betTitle, amount);

        assert !rouletteService.isBetWon(batBet, wonBetODD);

        playerName = "Michael";
        betTitle = "ODD";
        amount = 5;
        Bet betODD = new Bet(playerName, betTitle, amount);

        assert rouletteService.isBetWon(betODD, wonBetODD);

        playerName = "Mick";
        betTitle = "36";
        amount = 5;
        Bet bet = new Bet(playerName, betTitle, amount);

        assert rouletteService.isBetWon(bet, wonBet36);

    }

    @Test
    public void testScheduleRoulette() {

        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        rouletteService.scheduleRoulette();

        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        String playerName = "Mick";
        String betTitle = "36";
        short amount = 5;
        Bet bet = new Bet(playerName, betTitle, amount);
        playerService.addBet(bet);

        Player player = new Player(playerName);
        playerService.addPlayer(player);

        assert playerService.getAllPlayers().size() == 1;
        assert playerService.getAllBets().size() == 1;

        rouletteService.scheduleRoulette();

        assert playerService.getAllPlayers().size() == 1;
        assert playerService.getAllBets().isEmpty();
    }

}
