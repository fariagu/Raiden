package com.raiden.logic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import static java.lang.Math.pow;

class Particle {
    private static final int STATE_ALIVE = 1;    // particle is alive
    private static final int STATE_DEAD = 0;        // particle is dead

    private static final int DEFAULT_LIFETIME = 200;    // play with this
    private static final int MAX_DIMENSION = 5;    // the maximum width or height
    private static final int MAX_SPEED = 10;    // maximum speed (per update)
    private final int lifetime;        // particle dies when it reaches this value
    private final Paint paint;        // internal use to avoid instantiation
    private int state;			// particle is alive or dead
    private float width;		// width of the particle
    private float height;		// height of the particle
    private float x, y;			// horizontal and vertical position
    private double dx, dy;		// vertical and horizontal velocity
    private int age;			// current age of the particle

    public Particle(int x, int y) {
        Random generator = new Random();
        this.x = x;
        this.y = y;
        this.state = Particle.STATE_ALIVE;
        this.lifetime = DEFAULT_LIFETIME;
        this.width = 1 + generator.nextInt(MAX_DIMENSION);
        //noinspection SuspiciousNameCombination
        this.height = this.width; // square
        this.age = 0;
        this.dx = (generator.nextInt(MAX_SPEED * 2) - MAX_SPEED);
        this.dy = (generator.nextInt(MAX_SPEED * 2) - MAX_SPEED);

        if ((pow(dx, 2) + pow(dy, 2)) > pow(MAX_SPEED, 2)) { // velocity cant be higher then MAX_SPEED
            dx *= 0.7;
            dy *= 0.7;
        }
        int color = Color.argb(255, generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
        this.paint = new Paint(color);
    }

    public boolean isAlive() {
        return this.state == STATE_ALIVE;
    }

    public void update() {
        if (this.state != STATE_DEAD) {
            this.x += this.dx;
            this.y += this.dy;

            // extract alpha
            int a = this.paint.getAlpha() - 2; // fade
            if (a <= 0) { // if reached transparency kill the particle
                this.state = STATE_DEAD;
            } else {
                this.paint.setAlpha(a);
                this.age++; // increase the age of the particle
                this.width *= 0.95;
				this.height *= 0.95;
            }
            if (this.age >= this.lifetime) {	// reached the end if its life
                this.state = STATE_DEAD;
            }
        }
    }

    public void draw(Canvas canvas) {
        paint.setARGB(255, 128, 255, 50);
        canvas.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, paint);
    }
}
