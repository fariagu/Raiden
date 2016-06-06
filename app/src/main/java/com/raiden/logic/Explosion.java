package com.raiden.logic;

import android.graphics.Canvas;
import android.util.Log;

public class Explosion {

    private static final int STATE_ALIVE = 1;    // at least 1 particle is alive
    private static final int STATE_DEAD = 0;    // all particles are dead
    private static final String TAG = Explosion.class.getSimpleName();
    private final Particle[] particles;            // particles in the explosion
    private int state;						// whether it's still active or not

    public Explosion(int x, int y) {
        Log.d(TAG, "Explosion created at " + x + "," + y);
        this.state = STATE_ALIVE;
        this.particles = new Particle[200];
        for (int i = 0; i < this.particles.length; i++) {
            Particle p = new Particle(x, y);
            this.particles[i] = p;
        }
    }

    public boolean isAlive() {
        return this.state == STATE_ALIVE;
    }
    public boolean isDead() {
        return this.state == STATE_DEAD;
    }

    public void update() {
        if (this.state != STATE_DEAD) {
            boolean isDead = true;
            for (Particle particle : this.particles)
                if (particle.isAlive()) {
                    particle.update();
                    isDead = false;
                }
            if (isDead)
                this.state = STATE_DEAD;
        }
    }

    public void draw(Canvas canvas) {
        for (Particle particle : this.particles)
            if (particle.isAlive())
                particle.draw(canvas);
    }
}