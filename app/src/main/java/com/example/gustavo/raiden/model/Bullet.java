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
    protected int framenr;        // number of frames in animation
    protected int currentFrame;   // the current frame
    protected Speed speed;        // the speed with its directions
    protected int ticks;
    private Ship ship;

    public Bullet() {
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

        ship = s;
        currentFrame = 0;
        framenr = 2;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        speed = new Speed(0, 15);
        speed.setyDirection(Speed.DIRECTION_UP);
        ticks = 0;
    }

    public Bullet(Bitmap bitmap, int FPS) {
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
    public Speed getSpeed() {
        return speed;
    }
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }
    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
    public Ship getShip() {
        return ship;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Method which updates the bullet's position
     */
    public void update(long gameTime) {
        if (this.alive) {
            if (gameTime > frameTicker + framePeriod) {
                frameTicker = gameTime;
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

    public boolean checkCollision(Droid enemy) {
        if (alive && enemy.isAlive())
            if (Collision.collisionDetected(bitmap, x, y, enemy.getBitmap(), enemy.getX(), enemy.getY())) {
                enemy.setAlive(false);
                enemy.setComeBackCounter(-1);
                if (ship.isAlive()){
                    ship.incScore();
                }

                return true;
            }
        return false;
    }
}
