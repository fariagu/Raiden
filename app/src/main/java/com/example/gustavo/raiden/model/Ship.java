package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Collision;

public class Ship extends Collidable {
    private static final String TAG = Ship.class.getSimpleName();

    private int frameNr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private boolean touched;    // if droid is touched/picked up
    private boolean poweredup;    // se apanhou o powerup
    private Bitmap staticShip;

    private int oldX, oldY;     // the old X and Y coordinate of the object to calculate de frame

    public Ship(Bitmap bitmap, int x, int y, int FPS, int frameCount) {
        super(bitmap, x, y, FPS);
        this.oldX = x;
        this.oldY = y;
        currentFrame = 5;
        frameNr = frameCount - 1;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        poweredup = false;
    }

    public static String getTAG() {
        return TAG;
    }

    //Getters & Setters

    /**
     * Getter para obter o número de Frames do sprite da Ship.
     *
     * @return int
     */
    public int getFrameNr() {
        return frameNr;
    }
    public void setFrameNr(int frameNr) {
        this.frameNr = frameNr;
    }
    /**
     * Getter para obter o Frame actual no sprite da Ship.
     *
     * @return int
     */
    public int getCurrentFrame() {
        return currentFrame;
    }
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
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
    public int getOldX() {
        return oldX;
    }
    public void setOldX(int oldX) {
        this.oldX = oldX;
    }
    public int getOldY() {
        return oldY;
    }
    public void setOldY(int oldY) {
        this.oldY = oldY;
    }
    public boolean isPoweredup() {
        return poweredup;
    }
    public void setPoweredup(boolean poweredup) {
        this.poweredup = poweredup;
    }
    public Bitmap getStaticShip() {
        return staticShip;
    }
    public void setStaticShip(Bitmap staticShip) {
        this.staticShip = staticShip;
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
        if (eventX >= (x - 50 - spriteWidth) && (eventX <= (x + 50 + bitmap.getWidth() / frameNr))) {
            if (eventY >= (y - 50 - spriteHeight / 2) && (eventY <= (y + 50 + spriteHeight / 2))) {

                setTouched(true);// ship touched
                oldX = eventX;
                oldY = eventY;
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }

    }

    public boolean checkCollision(PowerUp p) {
        if (Collision.collisionDetected(this, p)) {
            p.setAlive(false);
            poweredup = true;
            return true;
        }
        return false;
    }

    public void checkCollision(Droid d){
        //if (d.isAlive())
            if (Collision.shipCollisionDetected(this, d))
                alive = false;
    }

    public void checkCollision(AimedBullet b){
        if (Collision.shipCollisionDetected(this, b)) {
            alive = false;
            b.setAlive(false);
        }
    }

    public void gameOver(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("GAME OVER", 10, 50, paint);
    }
}
