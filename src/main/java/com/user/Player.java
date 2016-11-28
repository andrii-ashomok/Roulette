package com.user;

/**
 * Created by rado on 11/27/16.
 */
public class Player {

    private String userName;
    private int totalBet;
    private int totalWin;


    public Player(String userName) {
        this.userName = userName;
    }

    public Player(String userName, int totalWin, int totalBet) {
        this.userName = userName;
        this.totalBet = totalBet;
        this.totalWin = totalWin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(int totalBet) {
        this.totalBet = totalBet;
    }

    public int getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(int totalWin) {
        this.totalWin = totalWin;
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
