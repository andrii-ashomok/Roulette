package com;

import com.bet.BetService;
import com.roulette.RouletteService;
import com.user.Bet;
import com.user.Player;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by rado on 11/28/16.
 */
public class GeneratePlayerBet {

    public static Map<Player, Bet> generatePlayerBet(int count) {

        SecureRandom random = new SecureRandom();
        Map<Player, Bet> customPlayBetMap = new HashMap<>();
        // Player variables
        String nickName;
        short bet;
        short amount;
        // Bet variables
        String newBet;
        short value;

        int index;
        for (int i=0; i < count; i++) {
            nickName = new BigInteger(130, random).toString(32);

            index = ThreadLocalRandom.current().nextInt(0, RouletteService.BET_SIZE);
            newBet = BetService.BET_LIST.get(index);

            bet = (short) index;

            index = ThreadLocalRandom.current().nextInt(1, 100);
            value = (short) index;

            index = ThreadLocalRandom.current().nextInt(1, 100);
            amount = (short) index;

            customPlayBetMap.put(
                    new Player(nickName, amount, bet),
                    new Bet(nickName, newBet, value));
        }

        return customPlayBetMap;
    }
}
