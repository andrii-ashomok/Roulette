package bet;

import com.bet.BetService;
import com.data.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
                .allMatch(s -> !"".equals(s));

        File bonusFile = Paths.get("target/test-classes/files/bonusInputFile").toFile();
        File regularFile = Paths.get("target/test-classes/files/regularInputFile").toFile();

    }


}
