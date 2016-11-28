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

    @Override
    public void init() {
        bets = new HashSet<>();
        players = new HashSet<>();
    }

    @Override
    public void addBet(Bet bet) {
        if (Objects.nonNull(bet))
            bets.add(bet);
    }

    @Override
    public void addPlayer(Player player) {
        if (Objects.nonNull(player)) {

            if (players.contains(player))
                players.remove(player);

            players.add(player);
        }
    }

    @Override
    public void addPlayerAll(Set<Player> player) {
        players.removeAll(player);
        players.addAll(player);
    }

    @Override
    public Set<Bet> getAndRemoveAllBets() {
        Set<Bet> outputBets = new HashSet<>(bets);
        bets.clear();

        return outputBets;
    }

    @Override
    public Set<Bet> getAllBets() {
        return bets;
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

    public void removeAllPlayers() {
        players.clear();
    }

    public void removeAllBets() {
        bets.clear();
    }

}
