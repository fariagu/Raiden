package com.raiden;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.raiden.logic.*;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void ShipMoves() throws Exception {
        DisplayMetrics d = new DisplayMetrics();
        int[] colors = new int[20*20];
        Arrays.fill(colors, 0);
        Bitmap b = Bitmap.createBitmap(d, colors, 20, 20, Bitmap.Config.RGB_565);
        Ship s = new Ship(b, 10, 10, 0);

        assertEquals(10, s.getX());
        assertEquals(10, s.getY());
    }
}