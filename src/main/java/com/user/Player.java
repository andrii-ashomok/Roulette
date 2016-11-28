package com.user;

/**
 * Created by rado on 11/27/16.
 */
public class Player {

    private String userName;
    private short bet;
    private short amount;


    public Player(String userName) {
        this.userName = userName;
    }

    public Player(String userName, short amount, short bet) {
        this.userName = userName;
        this.bet = bet;
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public short getBet() {
        return bet;
    }

    public void setBet(short bet) {
        this.bet = bet;
    }

    public short getAmount() {
        return amount;
    }

    public void setAmount(short amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return userName.equals(player.userName);

    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        return result;
    }
}
