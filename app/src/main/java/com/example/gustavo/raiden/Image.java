package com.example.gustavo.raiden;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Image {

    protected Bitmap bitmap;      // the actual bitmap
    protected Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    protected int screenWidth;    // the width of the screen
    protected int screenHeight;   // the height of the screen

    public Image(Bitmap bitmap, int screenHeight, int screenWidth) {
        this.bitmap = bitmap;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.sourceRect = new Rect(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect(0, 0,
                screenWidth, screenHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }
}
