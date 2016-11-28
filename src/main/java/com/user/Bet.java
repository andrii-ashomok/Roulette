package com.user;

/**
 * Created by rado on 11/27/16.
 */
public class Bet {

    private String userName;
    private String newBet;
    private short value;
    private short wonRate;
    private boolean isWon;

    public Bet(String userName, String newBet, short value) {
        this.userName = userName;
        this.newBet = newBet;
        this.value = value;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewBet() {
        return newBet;
    }

    public void setNewBet(String newBet) {
        this.newBet = newBet;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public short getWonRate() {
        return wonRate;
    }

    public void setWonRate(short wonRate) {
        this.wonRate = wonRate;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bet bet = (Bet) o;
        return userName.equals(bet.userName);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        return result;
    }
}
