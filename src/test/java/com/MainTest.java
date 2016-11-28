package com;

import org.testng.annotations.Test;

/**
 * Created by rado on 11/27/16.
 */
@Test(suiteName = "main", groups = {"integration"})
public class MainTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMainMethodByIllegalArgumentException() {
        String[] args = new String[] {};
        Main.main(args);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMainMethodByNullPointerException() {
        String[] args = null;
        Main.main(args);
    }

}
