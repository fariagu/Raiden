package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Speed;

/**
 * Created by Diogo on 03/06/2016.
 */
public class TripleBullet extends Bullet {
    private Bitmap bitmap;      // the actual bitmap
    private Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite
    private int framenr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)
    private int x, y;           // the X and Y coordinates
    private int xL, yL;         // the X and Y coordinates
    private int xR, yR;         // the X and Y coordinates
    private Speed speed;        // the speed with its directions
    private Speed speedL;       // the speed with its directions
    private Speed speedR;       // the speed with its directions
    private boolean alive;      // whether it's still active or not
    private int ticks;

    public TripleBullet(Bitmap bitmap, int x, int y, int fps) {
        this.frameTicker = 0;
        this.framePeriod = 1000 / fps;
        this.bitmap = bitmap;
        currentFrame = 0;
        framenr = 2;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.x = x;
        this.y = y;
        this.xL = x;
        this.yL = y;
        this.xR = x;
        this.yR = y;
        this.speed = new Speed(0, 15);
        this.speedL = new Speed(3, 15);
        this.speedR = new Speed(3, 15);
        this.speed.setyDirection(Speed.DIRECTION_UP);
        this.speedL.setyDirection(Speed.DIRECTION_UP);
        this.speedR.setyDirection(Speed.DIRECTION_UP);
        this.speedL.setxDirection(Speed.DIRECTION_LEFT);
        this.speedR.setxDirection(Speed.DIRECTION_RIGHT);
        this.alive = true;
        this.ticks = 0;
    }

    public void setX(int x) {
        this.x = x;
        this.xL = x;
        this.xR = x;
    }

    public void setY(int y) {
        this.y = y;
        this.yL = y;
        this.yR = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public void draw(Canvas canvas) {
        if (this.alive == true) {
            Rect destRect = new Rect(this.x - (bitmap.getWidth() / 2), this.y - (bitmap.getHeight() / 2),
                    this.x + (bitmap.getWidth() / 2), this.y + (bitmap.getHeight() / 2));
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);

            if (this.xL > 0) {
                Rect destRectL = new Rect(this.xL - (bitmap.getWidth() / 2), this.yL - (bitmap.getHeight() / 2),
                        this.xL + (bitmap.getWidth() / 2), this.yL + (bitmap.getHeight() / 2));
                canvas.drawBitmap(bitmap, sourceRect, destRectL, null);
            }

            if (this.xL < 1080) {
                Rect destRectR = new Rect(this.xR - (bitmap.getWidth() / 2), this.yR - (bitmap.getHeight() / 2),
                        this.xR + (bitmap.getWidth() / 2), this.yR + (bitmap.getHeight() / 2));
                canvas.drawBitmap(bitmap, sourceRect, destRectR, null);
            }

        }
    }

    /**
     * Method which updates the bullet's position
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            ticks++;
            if (ticks > 120) {
                ticks = 0;
                alive = false;
            } else {
                this.y += speed.getYv() * speed.getyDirection();

                this.xL += speedL.getXv() * speedL.getxDirection();
                this.yL += speedL.getYv() * speedL.getyDirection();

                this.xR += speedR.getXv() * speedR.getxDirection();
                this.yR += speedR.getYv() * speedR.getyDirection();
            }

            if (frameTicker % 10 == 0)
                currentFrame++; // increment the frame
            if (currentFrame >= framenr)
                currentFrame = 0;

            // define the rectangle to cut out sprite
            this.sourceRect.left = currentFrame * spriteWidth;
            this.sourceRect.right = this.sourceRect.left + spriteWidth;
        }
    }

}
