package com.example.gustavo.raiden.model.components;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Color;

/**
 * Created by Gustavo Faria on 03/06/2016.
 */
public class Colllision {
    public static boolean collisionDetected(Bitmap b1, int x1, int y1, Bitmap b2, int x2, int y2) {

        Rect bounds1 = new Rect(x1, y1, x1+b1.getWidth(), y1+b1.getHeight());
        Rect bounds2 = new Rect(x2, y2, x2+b2.getWidth(), y2+b2.getHeight());

        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int b1Pixel = b1.getPixel(i-x1, j-y1);
                    int b2Pixel = b2.getPixel(i-x2, j-y2);
                    if (isFilled(b1Pixel) && isFilled(b2Pixel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = (int) Math.max(rect1.left, rect2.left);
        int top = (int) Math.max(rect1.top, rect2.top);
        int right = (int) Math.min(rect1.right, rect2.right);
        int bottom = (int) Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }
}
