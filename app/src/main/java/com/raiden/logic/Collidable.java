package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Collidable {

    long frameTicker;   // the time of the last frame update
    int framePeriod;    // milliseconds between each frame (1000/fps)
    Bitmap bitmap;      // the actual bitmap
    Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    int spriteHeight;   // the height of the sprite
    int x;
    int y;           // the X and Y coordinate of the object (top left of the image)
    boolean alive;      // if the power up was picked up

    Collidable() {
    }

    Collidable(Bitmap bitmap, int x, int y, int FPS) {
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

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    /**
     * Retorna a coordenada X do Collidable.
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
}
