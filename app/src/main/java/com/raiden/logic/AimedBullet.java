package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.raiden.logic.components.Speed;

import java.util.Random;

/**
 * AimedBullet is a special Bullet.
 * When it's created the AimedBullet will target the player's ship position.
 */
public class AimedBullet extends Bullet {
    /**
     * Constructor for the AimedBullet
     *
     * @param bitmap image
     * @param FPS    game fps
     */
    public AimedBullet(Bitmap bitmap, int FPS) {
        super(bitmap, FPS);
        spriteWidth = bitmap.getWidth();
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.speed = new Speed(5, 5);
    }

    public AimedBullet() {
        super();
        spriteWidth = 0;
        spriteHeight = 0;
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.speed = new Speed(5, 5);
    }

    /**
     * Method which updates the AimedBullet's position
     * @param gameTime fps controller
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (alive) {
                ticks++;
                if(ticks > 50) {
                    Random r = new Random();
                    ticks = -r.nextInt(20);
                    alive = false;
                }

                x += (speed.getXv() * speed.getxDirection());
                y += (speed.getYv() * speed.getyDirection());
            }
        }
    }
}
