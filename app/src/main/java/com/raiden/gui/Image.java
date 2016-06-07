package com.raiden.gui;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Image {

    protected final int screenWidth;    // the width of the screen
    protected final int screenHeight;   // the height of the screen
    protected Bitmap bitmap;      // the actual bitmap
    protected Rect sourceRect;    // the rectangle to be drawn from the animation bitmap

    public Image() {
        bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.RGB_565);
        this.screenHeight = 1000;
        this.screenWidth = 1000;
        this.sourceRect = new Rect(0, 0, 10, 10);
    }

    public Image(Bitmap bitmap, int screenHeight, int screenWidth) {
        this.bitmap = bitmap;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.sourceRect = new Rect(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
    }

}
