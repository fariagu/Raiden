package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.raiden.logic.components.Collision;
import com.raiden.logic.components.Speed;

/**
 * This is the class of the bullets fired from the ship after it is powered up
 */
public class TripleBullet extends Bullet {
    private final Speed speedL;       // the speed with its directions
    private final Speed speedR;       // the speed with its directions
    private int xL, yL;         // the X and Y coordinates
    private int xR, yR;         // the X and Y coordinates

    /**
     * The main constructor for the Triplebullet.
     *
     * @param bitmap
     * @param s
     * @param FPS
     */
    public TripleBullet(Bitmap bitmap, Ship s, int FPS) {
        super(bitmap, s, FPS);
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);

        MAX_TICKS = 180;

        xL = x;
        yL = y;
        speedL = new Speed(1, speed.getYv());
        speedL.setyDirection(Speed.DIRECTION_UP);
        speedL.setxDirection(Speed.DIRECTION_LEFT);

        xR = x;
        yR = y;
        speedR = new Speed(1, speed.getYv());
        speedR.setyDirection(Speed.DIRECTION_UP);
        speedR.setxDirection(Speed.DIRECTION_RIGHT);
    }

    /**
     * TripleBullet Constructor necessary for testing
     */
    public TripleBullet() {
        super();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);

        MAX_TICKS = 180;

        xL = x;
        yL = y;
        speedL = new Speed(1, speed.getYv());
        speedL.setyDirection(Speed.DIRECTION_UP);
        speedL.setxDirection(Speed.DIRECTION_LEFT);

        xR = x;
        yR = y;
        speedR = new Speed(1, speed.getYv());
        speedR.setyDirection(Speed.DIRECTION_UP);
        speedR.setxDirection(Speed.DIRECTION_RIGHT);
    }

    /**
     * Setter for the x position
     *
     * @param x coord of the center.
     */
    public void setX(int x) {
        this.x = x;
        xL = x;
        xR = x;
    }

    /**
     * Setter for the y position
     *
     * @param y coord of the center.
     */
    public void setY(int y) {
        this.y = y;
        yL = y;
        yR = y;
    }

    /**
     * Draws the bitmap to the screen of the mobile.
     *
     * @param canvas mobile screen
     */
    public void draw(Canvas canvas) {
        if (alive) {
            Rect destRect = new Rect(x - (spriteWidth / 2), y - (spriteHeight / 2),
                    x + (spriteWidth / 2), y + (spriteHeight / 2));
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);

            if (this.xL > 0) {
                Rect destRectL = new Rect(xL - (spriteWidth / 2), yL - (spriteHeight / 2),
                        xL + (spriteWidth / 2), yL + (bitmap.getHeight() / 2));
                canvas.drawBitmap(bitmap, sourceRect, destRectL, null);
            }

            if (this.xL < 1080) {
                Rect destRectR = new Rect(xR - (spriteWidth / 2), yR - (spriteHeight / 2),
                        xR + (spriteWidth / 2), yR + (spriteHeight / 2));
                canvas.drawBitmap(bitmap, sourceRect, destRectR, null);
            }

        }
    }

    /**
     * Method which updates the bullet's position
     *
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

    /**
     * Handler for the collision between a bullet and an enemy.
     * Destroying a Droid will increase the player's score.
     *
     * @param enemy the Droid that collided with the Bullet.
     * @param ship the player for score update.
     * @return
     */
    public boolean checkCollision(Droid enemy, Ship ship) {
        if (alive && enemy.isAlive()) {
            if (Collision.collisionDetected(this, enemy)) {
                enemy.setAlive(false);
                enemy.setComeBackCounter(-1);
                if (ship.isAlive()){
                    ship.incScore();
                }

                return true;
            }
            if (Collision.collisionDetected(bitmap, this.xR, this.yR, enemy.getBitmap(), enemy.getX(), enemy.getY())) {
                enemy.setAlive(false);
                enemy.setComeBackCounter(-1);
                if (ship.isAlive()){
                    ship.incScore();
                }

                return true;
            }
            if (Collision.collisionDetected(bitmap, this.xL, this.yL, enemy.getBitmap(), enemy.getX(), enemy.getY())) {
                enemy.setAlive(false);
                enemy.setComeBackCounter(-1);
                if (ship.isAlive()){
                    ship.incScore();
                }

                return true;
            }
        }
        return false;
    }
}
