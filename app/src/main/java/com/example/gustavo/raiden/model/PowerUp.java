package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;

import com.example.gustavo.raiden.model.components.Speed;

/**
 * PowerUp changes the shooting patern of the ship.
 */
public class PowerUp extends Collidable {

    private Speed speed;        // the speed with its directions

    public PowerUp(Bitmap bitmap, int x, int y, int FPS) {
        super(bitmap, x, y, FPS);
        speed = new Speed();
        alive = false;
    }

    /**
     * Method which updates the powerup's internal state every tick
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (alive) {
                x += speed.getXv() * speed.getxDirection();
                y += speed.getYv() * speed.getyDirection();
            }
        }
    }
}
