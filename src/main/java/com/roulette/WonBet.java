package com.roulette;

/**
 * Created by rado on 11/27/16.
 */
public final class WonBet {

    private final int wonNumber;
    private final boolean isEVEN;
    private final boolean isODD;

    public WonBet(final int wonNumber) {
        this.wonNumber = wonNumber;
        this.isEVEN = checkEVEN();
        this.isODD = checkODD();
    }

    /**
     * This rules create using information from
     * https://en.wikipedia.org/wiki/Roulette
     * chapter 'Roulette wheel number sequence'
     * There you can find next information:
     * In number ranges from 1 to 10 and 19 to 28, odd numbers are red and even are black.
     * In ranges from 11 to 18 and 29 to 36, odd numbers are black and even are red.
     * @return
     */

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
