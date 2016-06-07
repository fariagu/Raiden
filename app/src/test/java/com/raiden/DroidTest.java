package com.raiden;

import com.raiden.logic.Droid;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DroidTest {
    @Test
    public void DroidMoves() throws Exception {
        Droid d = new Droid();

        assertEquals(0, d.getX());
        assertEquals(0, d.getY());

        for (int i = 0; i < 10; i++) {
            d.update(i);
        }

        assertEquals(true, (d.getX() > 0));
        assertEquals(true, (d.getY() > 0));
    }
}
