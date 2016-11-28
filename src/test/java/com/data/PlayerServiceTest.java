package com.data;

import com.GeneratePlayerBet;
import com.data.PlayerService;
import com.data.PlayerServiceImpl;
import com.user.Bet;
import com.user.Player;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.GeneratePlayerBet.generatePlayerBet;

/**
 * Created by rado on 11/28/16.
 */
@Test(suiteName = "playerService", groups = {"regression"})
public class PlayerServiceTest {

    private PlayerService playerService;

    @BeforeTest
    public void init() {
        playerService = new PlayerServiceImpl();
    }

    @Test
    public void testInit() {
        assert Objects.isNull(playerService.getAllPlayers());
        assert Objects.isNull(playerService.getAllBets());

        playerService.init();

        assert Objects.nonNull(playerService.getAllPlayers());
        assert playerService.getAllPlayers().isEmpty();

        assert Objects.nonNull(playerService.getAllBets());
        assert playerService.getAllBets().isEmpty();
    }

    @Test
    public void testAdd() {
        playerService.init();

        playerService.addBet(null);
        playerService.addPlayer(null);

        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        String playerName = "Test";
        String newBet = "ODD";
        short value = 10;
        Bet bet = new Bet(playerName, newBet, value);

        Player playerJustName = new Player(playerName);

        short amount = 60;
        short betCount = 10;
        Player player = new Player(playerName, amount, betCount);

        playerService.addBet(bet);
        playerService.addPlayer(playerJustName);

        assert playerService.getAllPlayers().size() == 1;
        assert playerService.getAllBets().size() == 1;

        assert playerService.getAllPlayers().stream()
                .filter(p -> p.getUserName().equals(playerName)
                                && p.getBet() == 0 && p.getAmount() == 0)
                .findFirst()
                .isPresent();

        assert playerService.getAllBets().stream()
                .filter(b -> b.getUserName().equals(bet.getUserName())
                            && b.getNewBet().equals(newBet) && b.getValue() == value)
                .findFirst()
                .isPresent();

        String evenBet = "EVEN";
        Bet duplicateBet = new Bet(playerName, evenBet, value);

        // duplicate Bet skip
        playerService.addBet(duplicateBet);

        // new data about same player update
        playerService.addPlayer(player);

        assert playerService.getAllPlayers().size() == 1;
        assert playerService.getAllBets().size() == 1;

        assert playerService.getAllBets().stream()
                .filter(b -> b.getUserName().equals(bet.getUserName())
                        && b.getNewBet().equals(newBet) && b.getValue() == value)
                .findFirst()
                .isPresent();

        assert playerService.getAllPlayers().stream()
                .filter(p -> p.getUserName().equals(playerName)
                        && p.getBet() == betCount && p.getAmount() == amount)
                .findFirst()
                .isPresent();
    }

    @Test
    public void testAddAll() {
        playerService.init();

        playerService.addAllPlayer(null);
        playerService.addAllBet(null);

        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        Set<Player> players = new HashSet<>();
        Set<Bet> bets = new HashSet<>();

        playerService.addAllPlayer(players);
        playerService.addAllBet(bets);

        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        final String nick = "Nick";
        Player player = new Player(nick);
        players.add(player);

        playerService.addAllPlayer(players);

        assert playerService.getAllPlayers().size() == 1;
        assert playerService.getAllPlayers().stream()
                .filter(p -> p.getUserName().equals(nick))
                .findFirst()
                .isPresent();

        String setBet = "36";
        short amount = 20;
        Bet bet = new Bet(nick, setBet, amount);
        bets.add(bet);
        playerService.addAllBet(bets);

        assert playerService.getAllBets().size() == 1;
        assert playerService.getAllBets().stream()
                .filter(p -> p.getUserName().equals(nick))
                .findFirst()
                .isPresent();
    }

    @Test
    public void testGetAndRemoveAllBets() {
        playerService.init();
        assert playerService.getAllBets().isEmpty();
        assert playerService.getAndRemoveAllBets().isEmpty();

        int size = 10;
        Map<Player, Bet> map = GeneratePlayerBet.generatePlayerBet(size);

        Set<Bet> bets = map.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        playerService.addAllBet(bets);

        assert playerService.getAllBets().size() == size;
        Set<Bet> gotBet = playerService.getAndRemoveAllBets();

        assert playerService.getAllBets().isEmpty();
        assert gotBet.size() == size;
    }


    @Test
    public void testGetCustomerByName() {
        playerService.init();
        assert playerService.getAllBets().isEmpty();
        assert playerService.getAndRemoveAllBets().isEmpty();

        assert !playerService.getCustomerByName(null).isPresent();
        assert !playerService.getCustomerByName("").isPresent();

        int size = 10;
        Map<Player, Bet> map = GeneratePlayerBet.generatePlayerBet(size);

        Set<Player> players = map.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        playerService.addAllPlayer(players);

        assert !playerService.getCustomerByName(null).isPresent();
        assert !playerService.getCustomerByName("").isPresent();

        assert !playerService.getCustomerByName("Vasya").isPresent();
        Player player = players.stream()
                .filter(p -> !"".equals(p.getUserName()))
                .findAny()
                .get();

        assert playerService.getCustomerByName(player.getUserName()).isPresent();
    }

}
