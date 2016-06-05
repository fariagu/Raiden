package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class DyingShip extends Collidable {

    private int frameNr;        // number of frames in animation
    private int currentFrame;   // the current frame

    public DyingShip(Bitmap bitmap, int x, int y, int FPS, int frameCount) {
        super(bitmap, x, y, FPS);
        currentFrame = 0;
        frameNr = frameCount - 1;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        alive = false;
    }

    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (alive) {
                currentFrame++;

                if (currentFrame > frameNr) {
                    currentFrame = 0;
                    alive = false;
                }

                // define the rectangle to cut out sprite
                this.sourceRect.left = currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
            }
        }
    }

}
