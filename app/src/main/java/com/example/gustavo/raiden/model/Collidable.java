package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Collision;

public class Collidable {

    protected long frameTicker;   // the time of the last frame update
    protected int framePeriod;    // milliseconds between each frame (1000/fps)
    protected Bitmap bitmap;      // the actual bitmap
    protected Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    protected int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    protected int spriteHeight;   // the height of the sprite
    protected int x, y;           // the X and Y coordinate of the object (top left of the image)
    protected boolean alive;      // if the power up was picked up

    public Collidable() {
    }

    public Collidable(Bitmap bitmap, int x, int y, int FPS) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;

        spriteWidth = bitmap.getWidth();
        spriteHeight = bitmap.getHeight();

        frameTicker = 0;
        framePeriod = 1000 / FPS;

        alive = true;
    }

    //Getters & Setters

    public long getFrameTicker() {
        return frameTicker;
    }

    public void setFrameTicker(long frameTicker) {
        this.frameTicker = frameTicker;
    }

    public int getFramePeriod() {
        return framePeriod;
    }

    public void setFramePeriod(int framePeriod) {
        this.framePeriod = framePeriod;
    }

    /**
     * Getter para obter o bitmap associado ao Collidable.
     *
     * @return Bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Setter para modificar o bitmap do Collidable.
     *
     * @param bitmap Nova imagem
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Getter para obter o Rect associado ao Collidable.
     *
     * @return Rect
     */
    public Rect getSourceRect() {
        return sourceRect;
    }

    public void setSourceRect(Rect sourceRect) {
        this.sourceRect = sourceRect;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    /**
     * Retorna a coordenada X do Collidable.
     *
     * @return int
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retorna a coordenada Y do Collidable.
     *
     * @return int
     */
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void draw(Canvas canvas) {
        if (alive) {
            // where to draw the sprite
            Rect destRect = new Rect(x - (spriteWidth / 2), y - (spriteHeight / 2),
                    x + (spriteWidth / 2), y + (spriteHeight / 2));
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);
        }
    }

    //generic
    public void checkCollision(Collidable c) {
        if (Collision.collisionDetected(bitmap, this.x, this.y, c.getBitmap(), c.getX(), c.getY())) {
            c.setAlive(false);
        }
    }

    public void checkCollision(Droid d) {
        if (Collision.shipCollisionDetected(bitmap, this.x, this.y, d.getBitmap(), d.getX(), d.getY())) {
            this.alive = false;
        }
    }

    public void checkCollision(AimedBullet b) {
        if (Collision.shipCollisionDetected(bitmap, this.x, this.y, b.getBitmap(), b.getX(), b.getY())) {
            alive = false;
            b.setAlive(false);
        }
    }
}
