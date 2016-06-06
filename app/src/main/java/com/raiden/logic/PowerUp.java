package com.raiden.logic;

import android.graphics.Bitmap;

import com.raiden.logic.components.Speed;

/**
 * PowerUp changes the shooting patern of the ship.
 */
public class PowerUp extends Collidable {

    private final Speed speed;        // the speed with its directions

    public PowerUp(Bitmap bitmap, int FPS) {
        super(bitmap, 100, 150, FPS);
        speed = new Speed();
        alive = false;
    }

    /**
     * Method which updates the powerup's internal state every tick
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            //noinspection ConstantConditions
            if (alive) {
                x += speed.getXv() * speed.getxDirection();
                y += speed.getYv() * speed.getyDirection();
            }
        }
    }
}
