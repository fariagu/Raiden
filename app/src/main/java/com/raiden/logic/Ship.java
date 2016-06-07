package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.raiden.logic.components.Collision;

/**
 * Ship class is the user's medium of interacting with the game
 */
public class Ship extends Collidable {

    private final int frameNr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private boolean touched;    // if droid is touched/picked up
    private boolean poweredup;    // se apanhou o powerup
    private Bitmap staticShip;
    private int score;

    private int oldX;

    /**
     * Ship Constructor necessary for testing
     */
    public Ship() {
        super();
        currentFrame = 0;
        frameNr = 0;
        touched = false;
        poweredup = false;
        score = 0;
    }

    /**
     * The main constructor for the Ship
     *
     * @param bitmap
     * @param x
     * @param y
     * @param FPS
     */
    public Ship(Bitmap bitmap, int x, int y, int FPS) {
        super(bitmap, x, y, FPS);
        this.oldX = x;
        framePeriod = 2000 / FPS;
        currentFrame = 5;
        frameNr = 11 - 1;
        spriteWidth = bitmap.getWidth() / 11;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        poweredup = false;
        score = 0;
    }

    //Getters & Setters

    /**
     * Getter for touched boolean
     *
     * @return boolean
     */
    public boolean isTouched() {
        return touched;
    }

    /**
     * Setter for touched boolean
     *
     * @param touched
     */
    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    /**
     * Setter for oldX
     *
     * @param oldX
     */
    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    /**
     * Getter for powereup boolean
     *
     * @return
     */
    public boolean isPoweredup() {
        return poweredup;
    }

    /**
     * Getter for staticShip
     *
     * @return
     */
    public Bitmap getStaticShip() {
        return staticShip;
    }

    /**
     * Setter for staticShip
     *
     * @param staticShip
     */
    public void setStaticShip(Bitmap staticShip) {
        this.staticShip = staticShip;
    }

    /**
     * Getter for score
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Increments score
     */
    public void incScore() {
        score++;
    }

    /**
     * Method which updates the ship's status
     *
     * @param gameTime
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (touched) {
                if (oldX > x) {
                    currentFrame--;
                } else if (oldX < x) {
                    currentFrame++;
                } else {//isto faz com que mesmo a tocar volte ao estado inicial
                    if (currentFrame > (frameNr / 2))
                        currentFrame--;
                    else if (currentFrame < (frameNr / 2))
                        currentFrame++;
                    else {
                        currentFrame = frameNr / 2;
                    }
                }
            } else {
                if (currentFrame > (frameNr / 2))
                    currentFrame--;
                else if (currentFrame < (frameNr / 2))
                    currentFrame++;
                else {
                    currentFrame = frameNr / 2;
                }
            }
            if (currentFrame < 0)
                currentFrame = 0;
            if (currentFrame > frameNr)
                currentFrame = frameNr;

            // define the rectangle to cut out sprite
            this.sourceRect.left = currentFrame * spriteWidth;
            this.sourceRect.right = this.sourceRect.left + spriteWidth;

        }
    }

    /**
     * Method that handles user input
     *
     * @param eventX
     * @param eventY
     */
    public void handleActionDown(int eventX, int eventY) {
        if (alive) {
            if (eventX >= (x - 50 - spriteWidth) && (eventX <= (x + 50 + bitmap.getWidth() / frameNr))) {
                if (eventY >= (y - 50 - spriteHeight / 2) && (eventY <= (y + 50 + spriteHeight / 2))) {

                    setTouched(true);// ship touched
                    oldX = eventX;
                } else {
                    setTouched(false);
                }
            } else {
                setTouched(false);
            }
        }
    }

    /**
     * Method for collision testing between ship and powerup
     *
     * @param p
     * @return
     */
    public boolean checkCollision(PowerUp p) {
        if (alive && p.isAlive())
            if (Collision.shipCollisionDetected(this, p)) {
                p.setAlive(false);
                poweredup = true;
                return true;
            }
        return false;
    }

    /**
     * Method for collision testing between ship and enemy
     *
     * @param d
     */
    public void checkCollision(Droid d) {
        //if (d.isAlive())
        if (Collision.shipCollisionDetected(this, d))
            alive = false;
    }

    /**
     * Method for collision testing between ship and enemy's bullet
     *
     * @param ab
     */
    public void checkCollision(AimedBullet ab) {
        if (alive && ab.isAlive())
            if (Collision.shipCollisionDetected(this, ab)) {
                alive = false;
                ab.setAlive(false);
            }
    }

    /**Method that displays the score on the top left corner
     *
     * @param canvas
     */
    public void displayScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);

        String showScore = Integer.toString(score);
        canvas.drawText(showScore, 10, 50, paint);
    }
}
