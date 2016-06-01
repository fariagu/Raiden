package com.example.gustavo.raiden.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import static java.lang.Math.pow;

public class Particle {
    public static final int STATE_ALIVE = 1;	// particle is alive
    public static final int STATE_DEAD = 0;		// particle is dead

    public static final int DEFAULT_LIFETIME 	= 200;	// play with this
    public static final int MAX_DIMENSION		= 5;	// the maximum width or height
    public static final int MAX_SPEED			= 10;	// maximum speed (per update)

    private int state;			// particle is alive or dead
    private float width;		// width of the particle
    private float height;		// height of the particle
    private float x, y;			// horizontal and vertical position
    private double dx, dy;		// vertical and horizontal velocity
    private int age;			// current age of the particle
    private int lifetime;		// particle dies when it reaches this value
    private int color;			// the color of the particle
    private Paint paint;		// internal use to avoid instantiation

    public Particle(int x, int y) {
        Random generator = new Random();
        this.x = x;
        this.y = y;
        this.state = Particle.STATE_ALIVE;
        this.lifetime = DEFAULT_LIFETIME;
        this.width = 1 + generator.nextInt(MAX_DIMENSION);
        this.height = this.width; // square
        this.age = 0;
        this.dx = (generator.nextInt(MAX_SPEED * 2) - MAX_SPEED);
        this.dy = (generator.nextInt(MAX_SPEED * 2) - MAX_SPEED);

        if ((pow(dx, 2) + pow(dy, 2)) > pow(MAX_SPEED, 2)) { // velocity cant be higher then MAX_SPEED
            dx *= 0.7;
            dy *= 0.7;
        }
        generator = new Random();
        int r = generator.nextInt(255);
        int g = generator.nextInt(255);
        int b = generator.nextInt(255);
        this.color = Color.argb(255, generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
        this.paint = new Paint(this.color);
    }

    // getters & setters
    public static int getStateAlive() {return STATE_ALIVE;}
    public static int getStateDead() {return STATE_DEAD;}
    public static int getDefaultLifetime() {return DEFAULT_LIFETIME;}
    public static int getMaxDimension() {return MAX_DIMENSION;}
    public static int getMaxSpeed() {return MAX_SPEED;}
    public int getState() {return state;}
    public void setState(int state) {this.state = state;}
    public boolean isAlive() {
        return this.state == STATE_ALIVE;
    }
    public boolean isDead() {
        return this.state == STATE_DEAD;
    }
    public float getWidth() {return width;}
    public void setWidth(float width) {this.width = width;}
    public float getHeight() {return height;}
    public void setHeight(float height) {this.height = height;}
    public float getX() {return x;}
    public void setX(float x) {this.x = x;}
    public float getY() {return y;}
    public void setY(float y) {this.y = y;}
    public double getDx() {return dx;}
    public void setDx(double dx) {this.dx = dx;}
    public double getDy() {return dy;}
    public void setDy(double dy) {this.dy = dy;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public int getLifetime() {return lifetime;}
    public void setLifetime(int lifetime) {this.lifetime = lifetime;}
    public Paint getPaint() {return paint;}
    public void setPaint(Paint paint) {this.paint = paint;}

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
