package bet;

import com.bet.BetService;
import com.data.PlayerService;
import com.user.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by rado on 11/27/16.
 */
@ContextConfiguration("/betService.xml")
@Test(suiteName = "bet", groups = {"integration"})
public class BetServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BetService betService;

    @Autowired
    private PlayerService playerService;

    @Value("${regular.default.input.file.path}")
    private String defaultFile;

    @Value("${bonus.default.input.file.path}")
    private String defaultBonusFile;

    @BeforeMethod
    @AfterMethod
    public void init() {
        playerService.init();
    }

    @Test
    public void testInitInputData() throws URISyntaxException, IOException {
        assert playerService.getAllPlayers().isEmpty();
        assert playerService.getAllBets().isEmpty();

        List<String> lines = Files.lines(Paths.get(defaultFile)).collect(Collectors.toList());

        betService.initInputData(null);

        assert playerService.getAllPlayers().size() == lines.size();
        assert playerService.getAllPlayers().stream()
                .allMatch(Objects::nonNull);
        assert playerService.getAllPlayers().stream()
                .allMatch(p -> !"".equals(p.getUserName()));

        playerService.getAllPlayers().stream()
                .allMatch(p -> p.getAmount() == 0 && p.getBet() == 0);

        File bonusFile = Paths.get("target/test-classes/files/bonusInputFile").toFile();
        File badFile = Paths.get("target/test-classes/files/badInputFile").toFile();

        betService.initInputData(bonusFile.getAbsolutePath());

        lines = Files.lines(Paths.get(bonusFile.getAbsolutePath())).collect(Collectors.toList());

        assert playerService.getAllPlayers().size() == lines.size();
        assert playerService.getAllPlayers().stream()
                .allMatch(Objects::nonNull);
        assert playerService.getAllPlayers().stream()
                .allMatch(p -> !"".equals(p.getUserName()));
        playerService.getAllPlayers().stream()
                .allMatch(p -> p.getAmount() > 0 && p.getBet() > 0);

        betService.initInputData(badFile.getAbsolutePath());

        assert playerService.getAllPlayers().size() == lines.size();
        assert playerService.getAllPlayers().stream()
                .allMatch(Objects::nonNull);
    }

    @Test
    public void testIsInitializedSuccess() {
        assert !betService.isPlayersInitializedSuccess();

        playerService.addPlayer(new Player("1"));

        assert betService.isPlayersInitializedSuccess();
    }

    @Test
    public void testIsBetValid() {
        assert playerService.getAllPlayers().isEmpty();

        String testBet = "";
        assert !betService.isBetValid(testBet.split(BetService.BET_SPLIT));

        testBet = " ";
        assert !betService.isBetValid(testBet.split(BetService.BET_SPLIT));

        testBet = "1";
        assert !betService.isBetValid(testBet.split(BetService.BET_SPLIT));

        String playerName = "John";
        playerService.addPlayer(new Player(playerName));
        assert playerService.getAllPlayers().size() == 1;

        testBet = "1";
        assert !betService.isBetValid(testBet.split(BetService.BET_SPLIT));

        assert !betService.isBetValid(playerName.split(BetService.BET_SPLIT));
        assert !betService.isBetValid(playerName.concat("  ").split(BetService.BET_SPLIT));

        assert !betService.isBetValid(playerName.concat(" 56 4.0").split(BetService.BET_SPLIT));
        assert !betService.isBetValid(playerName.concat(" EVEN A").split(BetService.BET_SPLIT));
        assert !betService.isBetValid(playerName.concat(" EVEN, ").split(BetService.BET_SPLIT));
        assert !betService.isBetValid(playerName.concat(" EVEN '").split(BetService.BET_SPLIT));
        assert betService.isBetValid(playerName.concat(" EVEN 4.0").split(BetService.BET_SPLIT));

    }

}
