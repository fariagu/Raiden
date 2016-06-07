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

    @Test
    public void DroidVariables(){
        Droid d = new Droid();
        d.setScreenHeight(10);
        d.setScreenWidth(10);
        d.setAbsspeed(20);
        d.setComeBackCounter(-1);


        d.getSpeed().setxDirection(1);
        d.getSpeed().setyDirection(1);
        d.getSpeed().setXv(5);
        d.getSpeed().setYv(5);

        assertEquals(1, d.getSpeed().getxDirection());
        assertEquals(1, d.getSpeed().getyDirection());
        assertEquals(5, d.getSpeed().getXv(), 0.0001);
        assertEquals(5, d.getSpeed().getYv(), 0.0001);

        assertEquals(0, d.getBullet().getSpriteHeight());
        assertEquals(0, d.getBullet().getSpriteWidth());
    }
}
