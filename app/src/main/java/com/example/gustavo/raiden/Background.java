package com.example.gustavo.raiden;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

class Background extends Image {
    private final int speed;
    private final int framePeriod;    // milliseconds between each frame (1000/fps)
    private int yTop;
    private int yBottom;
    private long frameTicker;   // the time of the last frame update

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
        canvas.drawColor(Color.argb(240, 255, 254, 224)); // needed so there isn't remains of dead bitmaps
        // where to draw the sprite
        Rect destRect = new Rect(0, 0, screenWidth, screenHeight + 200);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }
}
