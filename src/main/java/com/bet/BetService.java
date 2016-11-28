package com.bet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rado on 11/27/16.
 */
public interface BetService {
    List<String> BET_LIST = new ArrayList<String>() {{
        add("1"); add("2"); add("3"); add("4"); add("5");
        add("6"); add("7"); add("8"); add("9"); add("10");
        add("11"); add("12"); add("13"); add("14"); add("15");
        add("16"); add("17"); add("18"); add("19"); add("20");
        add("21"); add("22"); add("23"); add("24"); add("25");
        add("26"); add("27"); add("28"); add("29"); add("30");
        add("31"); add("32"); add("33"); add("34"); add("35");
        add("36"); add("EVEN"); add("ODD");
    }};

    String BET_SPLIT = " ";

    List<String> EXIT_COMMAND_LIST = Arrays.asList("stop", "exit", "quit");

    default List<String> readInputData(String fileName) throws IOException, URISyntaxException {
        return Files.lines(Paths.get(fileName)).collect(Collectors.toList());
    }

    default boolean isInputDataValid(Set lines) {
        return Objects.nonNull(lines) && !lines.isEmpty();
    }

    /**
     * Method wait while players input bets.
     * After player input his bet he needs to push ENTER button.
     * If player wants to exit from the game, he needs to write any of key words:
     * stop, exit, quit
     */
    void startToTakeABet();

    /**
     * 1. Read players data from input file name, if file name is null or empty
     * it uses default file, check application.properties to find bonus.default.input.file.path.
     * Input file needs to contain: player name
     * or optionality it can contains additional values like: total win and total bet
     * that split by comma.
     * 2. Method validates data by lines;
     * 3. Method converts lines into Player.class objects;
     * 4. Method saves players into player cache;
     * @param fileName - path to file
     */
    void initInputData(String fileName);

    /**
     * Check that player cache is available after input file read
     * @return - <b>true</b> - player cache initialized <b>successfully</b>,
     * <b>false</b> - player cache initialize <b>failed</b>;
     */
    boolean isPlayersInitializedSuccess();

    /**
     * Method check if the line from inputted file could be transform into Bet.class object;
     * @param strBet - line from inputted file
     * @return - <b>true</b> - line can be transform into Bet.class object;
     * <b>false</b> - line is not valid;
     */
    boolean isBetValid(String[] strBet);

}
