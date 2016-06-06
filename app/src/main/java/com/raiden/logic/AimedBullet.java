package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.raiden.logic.components.Speed;

import java.util.Random;

public class AimedBullet extends Bullet {
    public AimedBullet(Bitmap bitmap, int FPS) {
        super(bitmap, FPS);
        spriteWidth = bitmap.getWidth();
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.speed = new Speed(5, 5);
    }

    /**
     * Method which updates the AimedBullet's position
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
