package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class DyingShip extends Collidable {

    private final int frameNr;        // number of frames in animation
    private int currentFrame;   // the current frame

    public DyingShip(Bitmap bitmap, int FPS) {
        super(bitmap, 0, 0, FPS);
        currentFrame = 0;
        frameNr = 16 - 1;
        spriteWidth = bitmap.getWidth() / 16;
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
