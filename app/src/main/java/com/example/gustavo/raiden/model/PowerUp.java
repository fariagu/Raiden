package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.gustavo.raiden.model.components.Speed;

/**
 * Created by Diogo on 03/06/2016.
 */
public class PowerUp {
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)
    private Bitmap bitmap;      // the actual bitmap
    private int x, y;           // the X and Y coordinate
    private boolean alive;      // if the power up was picked up
    private Speed speed;        // the speed with its directions

    public PowerUp(Bitmap bitmap, int x, int y, int FPS) {
        frameTicker = 0;
        framePeriod = 1000 / FPS;
        alive = true;
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        speed = new Speed();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void draw(Canvas canvas) {
        if (alive)
            canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    /**
     * Method which updates the powerup's internal state every tick
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (alive) {
                x += (speed.getXv() * speed.getxDirection());
                y += (speed.getYv() * speed.getyDirection());
            }
        }
    }
}
