package com.example.gustavo.raiden;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gustavo.raiden.model.Bullet;
import com.example.gustavo.raiden.model.Droid;
import com.example.gustavo.raiden.model.Explosion;
import com.example.gustavo.raiden.model.Particle;
import com.example.gustavo.raiden.model.Ship;

/**
 * This is the main surface that handles the ontouch events and draws the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();

	private MainThread thread;
	private Droid droid;
	private Bullet bullet;
	private Explosion[] explosions;
	private Particle prtcl;
	private Ship ship;
	private int offsetX;
	private int offsetY;

	private int oldX,oldY;

	private String avgFps;
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create Droid and load bitmap
		droid = new Droid(50, 50, context);

		bullet = new Bullet(50, 50, context, droid);

		//create Particle
		prtcl = new Particle(200, 500);

		// create Ship and load bitmap
		ship = new Ship(
				BitmapFactory.decodeResource(getResources(), R.drawable.shipsprite)
				, 82, 182	// initial position
				, 35, 38	// width and height of sprite
				, 11, 11);	// FPS and number of frames in the animation

		// create the game loop thread
		thread = new MainThread(getHolder(), this);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		explosions = new Explosion[50];
		for (int i = 0; i < explosions.length; i++) {
			explosions[i] = null;
		}

		// at this point the surface is created and we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly.");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			offsetX = (int)event.getX() - ship.getX();
			offsetY = (int)event.getY() - ship.getY();

			int currentExplosion = 0; // check if explosion is null or if it is still active
			Explosion explosion = explosions[currentExplosion];
			while (explosion != null && explosion.isAlive() && currentExplosion < explosions.length) {
				currentExplosion++;
				explosion = explosions[currentExplosion];
			}
			if (explosion == null || explosion.isDead()) {
				explosion = new Explosion(200, (int)event.getX(), (int)event.getY());
				explosions[currentExplosion] = explosion;
			}

			// delegating event handling to the droid
			ship.handleActionDown((int)event.getX(), (int)event.getY());
			oldX = ship.getX(); oldY = ship.getY();

			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			oldX = ship.getX(); oldY = ship.getY();
			ship.setOldX(oldX);
			ship.setOldY(oldY);
			// the gestures
			if (ship.isTouched()) {
				// the droid was picked up and is being dragged
				ship.setX((int)event.getX() - offsetX);
				ship.setY((int)event.getY() - offsetY);
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if (ship.isTouched()) {
				ship.setTouched(false);
			}
		}
		return true;
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK); // needed so there isnt remains of dead bitmaps
		if (prtcl.isAlive())
			prtcl.draw(canvas);

		for(int i=0; i< explosions.length; i++)
			if (explosions[i] != null && explosions[i].isAlive())
				explosions[i].draw(canvas);

		droid.draw(canvas);
		bullet.draw(canvas);
		ship.draw(canvas);

		// display fps
		displayFps(canvas, avgFps);
	}

	private void displayFps(Canvas canvas, String FPS) {
		if (canvas != null && FPS != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(FPS, this.getWidth() - 50, 20, paint);
		}
	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		// Updates
		droid.update();
		prtcl.update();
		bullet.update();

		for(int i=0; i < explosions.length; i++)
			if (explosions[i] != null && explosions[i].isAlive())
				explosions[i].update();

		ship.update(System.currentTimeMillis());
	}

}
