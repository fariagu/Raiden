package com.raiden;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.raiden.client.MainActivity;
import com.raiden.logic.AimedBullet;
import com.raiden.logic.Droid;
import com.raiden.logic.Ship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 *//*
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)*/
public class ShipTest {
   // Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();

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

        assertEquals(false, s.isPoweredup());
    }

    @Test
    public void ShipVariables() throws Exception {
        Ship s = new Ship();

        s.setOldX(0);
    }

    @Test
    public void ShipScore() throws Exception {
        Ship s = new Ship();

        assertEquals(0, s.getScore());

        s.incScore();

        assertEquals(1, s.getScore());
    }
/*
    @Test
    public void ShipDies() throws Exception {
        Ship s = new Ship(BitmapFactory.decodeResource(activity.getResources(), R.drawable.shipsprite), 0, 0, 0);
        Droid d = new Droid(BitmapFactory.decodeResource(activity.getResources(), R.drawable.enemy),
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.bullet),
                0, 0, s, 0);

        assertEquals(true, s.isAlive());

        s.checkCollision(d);

        assertEquals(false, s.isAlive());
    }*/
}