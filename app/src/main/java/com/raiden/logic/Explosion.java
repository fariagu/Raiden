package com.raiden.logic;

import android.graphics.Canvas;
import android.util.Log;

/**
 * The mini explosion that appears everytime the screen is touched.
 */
public class Explosion {

    private static final int STATE_ALIVE = 1;    // at least 1 particle is alive
    private static final int STATE_DEAD = 0;    // all particles are dead
    private static final String TAG = Explosion.class.getSimpleName();
    private final Particle[] particles;            // particles in the explosion
    private int state;						// whether it's still active or not

    /**
     * The constructor of the explosion.
     *
     * @param x horizontal coordinate of where it will appear.
     * @param y vertical coordinate of where it will appear.
     */
    public Explosion(int x, int y) {
        Log.d(TAG, "Explosion created at " + x + "," + y);
        this.state = STATE_ALIVE;
        this.particles = new Particle[200];
        for (int i = 0; i < this.particles.length; i++) {
            Particle p = new Particle(x, y);
            this.particles[i] = p;
        }
    }

    /**
     * Setter to change from alive or dead.
     * @param state The state the explosion is
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Getter to know if the explosion is alive.
     * @return True if alive
     */
    public boolean isAlive() {
        return this.state == STATE_ALIVE;
    }

    /**
     * Getter to know if the explosion is dead.
     * @return True if dead
     */
    public boolean isDead() {
        return this.state == STATE_DEAD;
    }

    /**
     * Method which updates the Explosion.
     */
    public void update() {
        if (this.state != STATE_DEAD) {
            boolean isDead = true;
            for (Particle particle : this.particles)
                if (particle.isAlive()) {
                    particle.update();
                    isDead = false;
                }
            if (isDead)
                this.setState(STATE_DEAD);
        }
    }

    /**
     * Invokes the draw of all the particles.
     * @param canvas mobile screen
     */
    public void draw(Canvas canvas) {
        for (Particle particle : this.particles)
            if (particle.isAlive())
                particle.draw(canvas);
    }
}