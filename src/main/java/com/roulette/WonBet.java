package com.roulette;

/**
 * Created by rado on 11/27/16.
 */
final class WonBet {

    private final int wonNumber;
    private final boolean isEVEN;
    private final boolean isODD;

    WonBet(final int wonNumber) {
        this.wonNumber = wonNumber;
        this.isEVEN = checkEVEN();
        this.isODD = checkODD();
    }

    private boolean checkEVEN() {
        return wonNumber >= 19 && wonNumber <= 36;

    }

    private boolean checkODD() {
        return wonNumber >= 1 && wonNumber <= 18;

    }

    public int getWonNumber() {
        return wonNumber;
    }

    public boolean isEVEN() {
        return isEVEN;
    }

    public boolean isODD() {
        return isODD;
    }
}
