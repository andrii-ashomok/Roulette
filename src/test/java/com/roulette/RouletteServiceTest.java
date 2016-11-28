package com.roulette;

import com.GeneratePlayerBet;
import com.data.PlayerService;
import com.user.Bet;
import com.user.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by rado on 11/28/16.
 */
@ContextConfiguration("/roulette.xml")
@Test(suiteName = "roulette", groups = {"regression"})
public class RouletteServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RouletteService rouletteService;

    @Autowired
    private PlayerService playerService;

    private final int SIZE = 10;
    private Map<Player, Bet> playerBetMap = GeneratePlayerBet.generatePlayerBet(SIZE);

    @BeforeMethod
    @AfterMethod
    public void clean() {
        playerService.init();
    }

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
        assert betEVEN.isWon() && betEVEN.getWonRate() == 2;

        playerName = "Jorge";
        betTitle = "67";
        amount = 5;
        Bet batBet = new Bet(playerName, betTitle, amount);

        assert !rouletteService.isBetWon(batBet, wonBetODD);
        assert !batBet.isWon() && batBet.getWonRate() == 0;

        playerName = "Michael";
        betTitle = "ODD";
        amount = 5;
        Bet betODD = new Bet(playerName, betTitle, amount);

        assert rouletteService.isBetWon(betODD, wonBetODD);
        assert betODD.isWon() && betODD.getWonRate() == 2;

        playerName = "Mick";
        betTitle = "36";
        amount = 5;
        Bet bet = new Bet(playerName, betTitle, amount);

        assert rouletteService.isBetWon(bet, wonBet36);
        assert bet.isWon() && bet.getWonRate() == 36;

    }

    @Test
    public void testScheduleRoulette() {

        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        rouletteService.scheduleRoulette();

        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        playerService.addAllBet(playerBetMap
                .values()
                .stream()
                .collect(Collectors.toSet()));

        playerService.addAllPlayer(playerBetMap.keySet());

        assert playerService.getAllPlayers().size() == SIZE;
        assert playerService.getAllBets().size() == SIZE;

        rouletteService.scheduleRoulette();

        assert playerService.getAllPlayers().size() == SIZE;
        assert playerService.getAllBets().isEmpty();
    }

    @Test
    public void testPrintResult() {
        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        int wonNumber = ThreadLocalRandom.current().nextInt(1, RouletteService.BET_SIZE);
        WonBet wonBet = new WonBet(wonNumber);

        rouletteService.printResult(playerBetMap.values().stream().collect(Collectors.toSet()), wonNumber);

        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        playerService.addAllPlayer(playerBetMap.keySet());

        assert playerService.getAllPlayers().size() == SIZE;

        Player p = (Player) playerBetMap.keySet().toArray()[ThreadLocalRandom.current().nextInt(0, SIZE)];

        Player customPlayer = new Player(p.getUserName(), p.getTotalWin(), p.getTotalBet());

        playerBetMap.values().forEach(b -> rouletteService.isBetWon(b, wonBet));

        rouletteService.printResult(playerBetMap.values().stream().collect(Collectors.toSet()), wonNumber);

        assert customPlayer.getUserName().equals(p.getUserName());
        assert customPlayer.getTotalBet() < p.getTotalBet();
        assert customPlayer.getTotalWin() <= p.getTotalWin();
    }

}
