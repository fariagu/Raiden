package com.raiden;

import android.graphics.Canvas;

import com.raiden.logic.AimedBullet;
import com.raiden.logic.Droid;
import com.raiden.logic.Ship;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ShipTest {
    @Test
    public void ShipMoves() throws Exception {
        Ship s = new Ship();

        assertEquals(0, s.getX());
        assertEquals(0, s.getY());

        s.setX(10);
        s.setY(10);

        s.update(0);

        assertEquals(s.getX(), 10);
        assertEquals(s.getY(), 10);
    }

    @Test
    public void ShipBooleans() throws Exception {
        Ship s = new Ship();

        s.setTouched(false);
        s.setAlive(false);

        assertEquals(false, s.isTouched());
        assertEquals(false, s.isAlive());

        s.setTouched(true);
        s.setAlive(true);

        assertEquals(true, s.isTouched());
        assertEquals(true, s.isAlive());
    }

    @Test
    public void ShipScore() throws Exception {
        Ship s = new Ship();

        assertEquals(0, s.getScore());
    }

    @Test
    public void ShipDies() throws Exception {
        Ship s = new Ship();
        s.setStaticShip(s.getBitmap());
        Droid d = new Droid();
        //Canvas canvas = new Canvas(s.getBitmap());
        //canvas.drawBitmap(d.getBitmap(), d.getX(), d.getY(), null);
        //AimedBullet b = new AimedBullet();

        assertEquals(true, s.isAlive());

        s.checkCollision(d);

        assertEquals(false, s.isAlive());
    }
}