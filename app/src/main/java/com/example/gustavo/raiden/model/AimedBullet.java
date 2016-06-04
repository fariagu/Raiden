package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Speed;

public class AimedBullet extends Bullet {
    public AimedBullet(Bitmap bitmap, int FPS) {
        super(bitmap, 0, 0, FPS);
        spriteWidth = bitmap.getWidth();
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.speed = new Speed();
    }

    public AimedBullet(Bitmap bitmap, int x, int y, int FPS) {
        super(bitmap, x, y, FPS);
        spriteWidth = bitmap.getWidth();
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        speed = new Speed();
    }

    /**
     * Method which updates the aimedbullet's position
     */
    public void update(long gameTime) {
        if (this.alive){
            if (gameTime > frameTicker + framePeriod) {
                frameTicker = gameTime;
                ticks++;
                if(ticks > 150) {
                    ticks = 0;
                    this.alive = false;
                }

                x += (speed.getXv() * speed.getxDirection());
                y += (speed.getYv() * speed.getyDirection());
            }
        }
    }

}
