package com.data;

import com.user.Bet;
import com.user.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Created by rado on 11/27/16.
 */
public class PlayerServiceImpl implements PlayerService {

    private Set<Bet> bets;
    private Set<Player> players;

    public void init() {
        bets = new HashSet<>();
        players = new HashSet<>();
    }

    public void addBet(Bet bet) {
        if (Objects.nonNull(bet))
            bets.add(bet);
    }

    public void addPlayer(Player player) {
        if (Objects.nonNull(player))
            players.add(player);
    }

    public void addPlayer(Set<Player> player) {
        players.addAll(player);
    }

    public Set<Bet> getAndRemoveAllBets() {
        Set<Bet> outputBets = new HashSet<>(bets);
        bets.clear();

        return outputBets;
    }

    @Override
    public Set<Bet> getAllBets() {
        return new HashSet<>(bets);
    }

    @Override
    public Set<Player> getAllPlayers() {
        return players;
    }

    @Override
    public Optional<Player> getCustomerByName(String playerName) {
        return players.stream()
                .filter(customer -> customer.getUserName().equalsIgnoreCase(playerName))
                .findFirst();
    }
}
