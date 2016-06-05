package com.example.gustavo.raiden;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class Background extends Image {
    private int yTop, yBottom, speed;
    private Rect sourceRect2;   // the rectangle to be drawn from the animation bitmap
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)

    public Background(Bitmap bitmap, int screenHeight, int screenWidth, int fps) {
        super(bitmap, screenHeight, screenWidth);
        this.frameTicker = 0;
        this.framePeriod = 1000 / fps;
        yTop = bitmap.getHeight() - screenHeight;
        yBottom = bitmap.getHeight();
        speed = 1;
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

            // define the rectangle to cut out sprite
            this.sourceRect.top = yTop;
            this.sourceRect.bottom = yBottom;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.argb(240, 255, 254, 224)); // needed so there isnt remains of dead bitmaps
        // where to draw the sprite
        Rect destRect = new Rect(0, 0, screenWidth, screenHeight + 200);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }
}
