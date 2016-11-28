package com.data;

import com.user.Bet;
import com.user.Player;

import java.util.Optional;
import java.util.Set;

/**
 * Created by rado on 11/27/16.
 */
public interface PlayerService {

    /**
     * Initialize players cache and bets cache
     */
    void init();

    /**
     * Add the bet into bet cache, if input bet already in cache it'd not inserted
     * @param bet - object of Bet.class
     */
    void addBet(Bet bet);

    /**
     * Get and remove all bets from cache
     * @return - all bets
     */
    Set<Bet> getAndRemoveAllBets();

    /**
     *
     * @return - all bets
     */
    Set<Bet> getAllBets();

    /**
     * Add new player into player cache, if input player already in cache it'd replaced by new one
     * @param player - new player
     */
    void addPlayer(Player player);

    void addAllPlayer(Set<Player> player);

    void addAllBet(Set<Bet> player);

    Set<Player> getAllPlayers();

    /**
     * Method trying to find Player by his nickname
     * @param playerName - player nickname
     * @return - optional of player
     */
    Optional<Player> getCustomerByName(String playerName);

    void removeAllBets();

    void removeAllPlayers();
}
