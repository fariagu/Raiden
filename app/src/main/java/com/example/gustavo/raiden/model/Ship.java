package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.example.gustavo.raiden.model.components.Collision;

public class Ship extends Collidable {

    private final int frameNr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private boolean touched;    // if droid is touched/picked up
    private boolean poweredup;    // se apanhou o powerup
    private Bitmap staticShip;
    private int score;

    private int oldX;

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
     * Retorna se a Ship está a ser tocada.
     *
     * @return boolean
     */
    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public boolean isPoweredup() {
        return poweredup;
    }

    public Bitmap getStaticShip() {
        return staticShip;
    }

    public void setStaticShip(Bitmap staticShip) {
        this.staticShip = staticShip;
    }

    public int getScore() {
        return score;
    }

    public void incScore() {
        score++;
    }

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

    public boolean checkCollision(PowerUp p) {
        if (alive && p.isAlive())
            if (Collision.shipCollisionDetected(this, p)) {
                p.setAlive(false);
                poweredup = true;
                return true;
            }
        return false;
    }

    public void checkCollision(Droid d) {
        //if (d.isAlive())
        if (Collision.shipCollisionDetected(this, d))
            alive = false;
    }

    public void checkCollision(AimedBullet b) {
        if (alive && b.isAlive())
            if (Collision.shipCollisionDetected(this, b)) {
                alive = false;
                b.setAlive(false);
            }
    }

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
