import com.bet.BetService;
import com.bet.BetServiceImpl;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Objects;

/**
 * Created by rado on 11/27/16.
 */
public class Main {

    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler(
                (t, e) -> System.err.println("ERR001 - Uncaught exception occurred - " +
                        e.getMessage() + ", " + e.getStackTrace()));

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring-config.xml");
        context.registerShutdownHook();

        BetService playerService = context.getBean(BetServiceImpl.class);
        playerService.initInputData(args[0]);

        if (playerService.isPlayersInitializedSuccess()) {
            context.start();

            playerService.startToTakeABet();
        }

    }



}
