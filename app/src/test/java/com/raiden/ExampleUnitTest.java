package com.raiden;

import android.util.DisplayMetrics;

import com.raiden.logic.Ship;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void ShipMoves() throws Exception {
        DisplayMetrics d = new DisplayMetrics();
        Ship s = new Ship();

        assertEquals(0, s.getX());
        assertEquals(0, s.getY());
    }
}