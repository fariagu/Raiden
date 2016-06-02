package com.example.gustavo.raiden;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Diogo on 02/06/2016.
 */
public class Background {
    int yTop, yBottom, speed;
    private Bitmap bitmap;    // the actual bitmap
    private Rect sourceRect;// the rectangle to be drawn from the animation bitmap
    private Rect sourceRect2;// the rectangle to be drawn from the animation bitmap
    private int screenWidth;    // the width of the screen
    private int screenHeight;   // the height of the screen
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)

    public Background(Bitmap bitmap, int screenHeight, int screenWidth) {
        this.frameTicker = 0;
        this.framePeriod = 1000 / 60;//fps;
        this.bitmap = bitmap;
        //this.whrelation = this.screenHeight / this.screenWidth;
        this.screenHeight = 5 * screenHeight / 6;
        this.screenWidth = screenWidth;
        this.sourceRect = new Rect(0, 0, this.screenWidth, this.screenHeight);
        yTop = bitmap.getHeight() - screenHeight;
        yBottom = bitmap.getHeight();
        speed = 2;
    }

    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            yTop -= speed;
            yBottom -= speed;

            if (yBottom < 0) { //> spriteHeight){
                yTop = bitmap.getHeight() - screenHeight;
                yBottom = screenHeight;
            }
        }

        // define the rectangle to cut out sprite
        this.sourceRect.top = yTop;
        this.sourceRect.bottom = yBottom;
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect(0, 0, screenWidth, 2 * screenHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }
}
