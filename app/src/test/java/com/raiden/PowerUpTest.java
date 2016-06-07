package com.raiden;

import com.raiden.logic.PowerUp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PowerUpTest {
    @Test
    public void PowerUpMoves() throws Exception {
        PowerUp p = new PowerUp();
        p.setX(0);
        p.setY(0);
        p.setAlive(true);

        assertEquals(0, p.getX());
        assertEquals(0, p.getY());

        for (int i = 0; i < 100; i++) {
            p.update(i);
        }

        assertEquals(true, (p.getX() > 0));
        assertEquals(true, (p.getY() > 0));
    }
}
