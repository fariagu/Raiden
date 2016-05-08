package com.example.gustavo.raiden.model;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Diogo on 08/05/2016.
 */
public class Explosion {

    public static final int STATE_ALIVE 	= 1;	// at least 1 particle is alive
    public static final int STATE_DEAD 		= 0;	// all particles are dead
    private static final String TAG = Explosion.class.getSimpleName();
    private Particle[] particles;			// particles in the explosion
    private int x, y;						// the explosion's origin
    private int size;						// number of particles
    private int state;						// whether it's still active or not

    public Explosion(int particleNr, int x, int y) {
        Log.d(TAG, "Explosion created at " + x + "," + y);
        this.state = STATE_ALIVE;
        this.particles = new Particle[particleNr];
        for (int i = 0; i < this.particles.length; i++) {
            Particle p = new Particle(x, y);
            this.particles[i] = p;
        }
        this.size = particleNr;
    }

    public static String getTAG() {return TAG;}
    public Particle[] getParticles() {return particles;}
    public void setParticles(Particle[] particles) {this.particles = particles;}
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}
    public int getY() {return y;}
    public void setY(int y) {this.y = y;}
    public int getSize() {return size;}
    public void setSize(int size) {this.size = size;}
    public int getState() {return state;}
    public void setState(int state) {this.state = state;}
    public boolean isAlive() {
        return this.state == STATE_ALIVE;
    }
    public boolean isDead() {
        return this.state == STATE_DEAD;
    }

    public void update() {
        if (this.state != STATE_DEAD) {
            boolean isDead = true;
            for (int i = 0; i < this.particles.length; i++)
                if (this.particles[i].isAlive()) {
                    this.particles[i].update();
                    isDead = false;
                }
            if (isDead)
                this.state = STATE_DEAD;
        }
    }

    public void draw(Canvas canvas) {
        for(int i = 0; i < this.particles.length; i++)
            if (this.particles[i].isAlive())
                this.particles[i].draw(canvas);
    }
}