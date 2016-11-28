package data;

import com.data.PlayerService;
import com.data.PlayerServiceImpl;
import com.user.Bet;
import com.user.Player;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Objects;

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

        playerService.addBet(duplicateBet);
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

}
