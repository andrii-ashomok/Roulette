package com.data;

import com.user.Bet;
import com.user.Player;

import java.util.Optional;
import java.util.Set;

/**
 * Created by rado on 11/27/16.
 */
public interface PlayerService {

    void addBet(Bet bet);

    Set<Bet> getAndRemoveAllBets();

    Set<Bet> getAllBets();

    void addPlayer(Player player);

    void addPlayer(Set<Player> player);

    Set<Player> getAllPlayers();

    Optional<Player> getCustomerByName(String playerName);
}
