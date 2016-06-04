package com.example.gustavo.raiden;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by Diogo on 02/06/2016.
 */
public class Background {
    int yTop, yBottom, speed;
    private Bitmap bitmap;      // the actual bitmap
    private Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    private Rect sourceRect2;   // the rectangle to be drawn from the animation bitmap
    private int screenWidth;    // the width of the screen
    private int screenHeight;   // the height of the screen
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)

    public Background(Bitmap bitmap, int screenHeight, int screenWidth, int fps) {
        this.frameTicker = 0;
        this.framePeriod = 1000 / fps;
        this.bitmap = bitmap;
        this.screenHeight = 5 * screenHeight / 6;
        this.screenWidth = screenWidth;
        this.sourceRect = new Rect(0, 0, this.bitmap.getWidth(), this.screenHeight);
        yTop = bitmap.getHeight() - screenHeight + 250;
        yBottom = bitmap.getHeight() + 250;
        speed = 1;
    }

    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            yTop -= speed;
            yBottom -= speed;

            if (yBottom < 0) { //> spriteHeight){
                yTop = bitmap.getHeight() - screenHeight + 250;
                yBottom = screenHeight + 250;
            }
        }

        // define the rectangle to cut out sprite
        this.sourceRect.top = yTop;
        this.sourceRect.bottom = yBottom;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.argb(255, 255, 250, 205)); // needed so there isnt remains of dead bitmaps
        // where to draw the sprite
        Rect destRect = new Rect(0, 0, 2 * this.screenWidth / 3, 19 * this.screenHeight / 10);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }
}