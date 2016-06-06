package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Collision;
import com.example.gustavo.raiden.model.components.Speed;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against
 * the wall and done other terrible things with.
 * Wait till it gets a weapon!
 */
public class Bullet extends Collidable {
    final int framenr;        // number of frames in animation
    int currentFrame;   // the current frame
    Speed speed;        // the speed with its directions
    int ticks;

    Bullet() {
        this.frameTicker = 0;
        this.framePeriod = 0;
        currentFrame = 0;
        framenr = 2;
        spriteWidth = 0;
        spriteHeight = 0;
        sourceRect = new Rect(0, 0, 1, 1);
        this.speed = new Speed(0, 0);
        this.alive = false;
        this.ticks = 0;
    }

    public Bullet(Bitmap bitmap, Ship s, int FPS) {
        super(bitmap, s.getX(), s.getY(), FPS);

        currentFrame = 0;
        framenr = 2;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        speed = new Speed(0, 15);
        speed.setyDirection(Speed.DIRECTION_UP);
        ticks = 0;
    }

    Bullet(Bitmap bitmap, int FPS) {
        super(bitmap, 0, 0, FPS);

        currentFrame = 0;
        framenr = 2;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        speed = new Speed(0, 15);
        speed.setyDirection(Speed.DIRECTION_UP);
        ticks = 0;
    }

    //Getters & Setters

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    /**
     * Method which updates the bullet's position
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (this.alive) {
                ticks++;
                if (ticks > 120) {
                    ticks = 0;
                    alive = false;
                } else {
                    this.y += speed.getYv() * speed.getyDirection();
                }


                if (frameTicker % 10 == 0)
                    currentFrame++; // increment the frame
                if (currentFrame >= framenr) {
                    currentFrame = 0;
                }

                // define the rectangle to cut out sprite
                this.sourceRect.left = currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
            }
        }
    }

    public boolean checkCollision(Droid enemy, Ship ship) {
        if (alive && enemy.isAlive())
            if (Collision.collisionDetected(this, enemy)) {
                enemy.setAlive(false);
                enemy.setComeBackCounter(-1);
                if (ship.isAlive()) {
                    ship.incScore();
                }

                return true;
            }
        return false;
    }
}
