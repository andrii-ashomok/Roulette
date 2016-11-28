package com.bet;

import com.data.PlayerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;
import com.user.Bet;
import com.user.Player;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rado on 11/27/16.
 */
public class BetServiceImpl implements BetService {

    private PlayerService playerService;

    @Value("${regular.default.input.file.path}")
    private String defaultFile;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void initInputData(String fileName) {
        StopWatch watch = new StopWatch();
        watch.start();

        if (Objects.isNull(fileName) || fileName.isEmpty())
            fileName = defaultFile;

        try {

            List<String> lines = readInputData(fileName);

            Set<Player> players = lines.stream()
                    .map(String::trim)
                    .filter(s -> Objects.nonNull(s) && !s.isEmpty())
                    .map(s -> {
                        String[] user = s.split(",");

                        if (user.length < 1)
                            return null;

                        String playerName = user[0].trim();

                        if ("".equals(playerName))
                            return null;

                        Player player = new Player(playerName);

                        if (user.length == 3) {

                            try {

                                if (!"".equals(user[1]))
                                    player.setAmount(Float.valueOf(user[1])
                                            .shortValue());


                                if (!"".equals(user[2]))
                                    player.setBet(Float.valueOf(user[2])
                                            .shortValue());

                            } catch (NumberFormatException e) {
                                System.err.println("Player has incorrect data: " + s
                                        + " cause: " + e.getMessage());

                                return null;
                            }
                        }

                        return player;

                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            playerService.addPlayerAll(players);

            watch.stop();

            System.out.println(players.size() + " players read from file: " + fileName
                            +", duration: " + watch.getTotalTimeMillis() + " ms");

        } catch (URISyntaxException  | IOException e) {
            watch.stop();

            System.err.println("Could not read any user from input file: " + fileName
                            + ", duration: " + watch.getTotalTimeMillis() + " ms. Cause: " + e.getMessage());
        }
    }

    @Override
    public boolean isPlayersInitializedSuccess() {
        return isInputDataValid(playerService.getAllPlayers());
    }


    @Override
    public void startToTakeABet() {

        Scanner s = new Scanner(System.in);

        String line;
        while (EXIT_COMMAND_LIST.contains(line = s.next())) {

            String[] strBet = line.split(BET_SPLIT);
            if (isBetValid(strBet)) {

                Bet bet = new Bet(strBet[0], strBet[1], Short.valueOf(strBet[2]));
                playerService.addBet(bet);

            }
        }
    }

    @Override
    public boolean isBetValid(String[] strBet) {
        if (strBet.length != 3) {
            System.err.println("Inputted data was incorrect. Please try like in example: Tiki_Monkey 2 1.0");
            return false;
        }

        String userName = strBet[0];
        Optional<Player> customerOptional = playerService.getCustomerByName(userName);

        if (!customerOptional.isPresent()) {
            System.err.println("No such user: " + userName + " in system");
            return false;
        }

        String bet = strBet[1].trim();

        if (!BET_LIST.contains(bet)) {
            System.err.println("No such bet in this game: " + bet);
            return false;
        }

        if ("".equals(strBet[2].trim())) {
            System.err.println("You set amount: " + strBet[2] + ". Try like in example: Tiki_Monkey 2 1.0");
            return false;
        }

        try {
            Float.valueOf(strBet[2]).shortValue();
        } catch (NumberFormatException e) {
            System.err.println("You set amount is incorrect: " + strBet[2]);
            return false;
        }


        return true;

    }

}
