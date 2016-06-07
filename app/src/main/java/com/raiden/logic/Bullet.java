package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.raiden.logic.components.Collision;
import com.raiden.logic.components.Speed;

/**
 * The Bullet class is the weapon for the Ship class.
 * Used to destroy the Droids.
 */
public class Bullet extends Collidable {
    final int framenr;        // number of frames in animation
    int currentFrame;   // the current frame
    Speed speed;        // the speed with its directions
    int ticks;
    int MAX_TICKS = 120;

    /**
     * The main constructor for the Bullet.
     *
     * @param bitmap the sprite of the bullet.
     * @param s      the user's ship for the position the Bullet is to be created.
     * @param FPS    the FPS the game is using.
     */
    public Bullet(Bitmap bitmap, Ship s, int FPS) {
        super(bitmap, s.getX(), s.getY(), FPS);

        framePeriod = 500 / FPS;
        currentFrame = 0;
        framenr = 2;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        speed = new Speed(0, 15);
        speed.setyDirection(Speed.DIRECTION_UP);
        ticks = 0;
    }

    /**
     * A constructor for the Bullet without a specified position to be created.
     *
     * @param bitmap the Sprite
     * @param FPS    the FPS the game is using.
     */
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

    /**
     * Setter to change the speed the Bullet is traveling.
     * @param speed
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * Change the time left for the bullet to be resetted.
     * @param ticks
     */
    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    /**
     * Method which updates the bullet's position, living state and velocity.
     * @param gameTime the gametime to know if there should be an update.
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (this.alive) {
                ticks++;
                if (ticks > MAX_TICKS) {
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

    /**
     * Handler for the collision between a bullet and an enemy.
     * Destroying a Droid will increase the player's score.
     * @param enemy the Droid that collided with the Bullet.
     * @param ship the player for score update.
     * @return boolean true if there was a collision.
     */
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
