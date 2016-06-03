package com.example.gustavo.raiden;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.gustavo.raiden.model.*;
import com.example.gustavo.raiden.model.components.*;

/**
 * This is the main surface that handles the ontouch events and draws the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private MainThread thread;
    private Droid droid;
    private Explosion[] explosions;
    private TripleBullet[] firingmode;
    private AimedBullet enemyBullet;

    private Bitmap backgroundimg = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundds);
    private Bitmap bulletsprite = BitmapFactory.decodeResource(getResources(), R.drawable.shoot);
    private Bitmap shipsprite = BitmapFactory.decodeResource(getResources(), R.drawable.shipsprite);
    private Bitmap enemybulletsprite = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
    private Bitmap enemysprite = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);

    private Background background;
    private Particle prtcl;
    private Ship ship;

    private int oldX, oldY;
    private int FPS = 20;

    private String avgFps;

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

		Point ScreenSize = new Point();
		display.getSize(ScreenSize);

		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		Log.d("ApplicationTagName", "Display width in px is " + metrics.widthPixels + " and height is " + metrics.heightPixels);
		background = new Background(backgroundimg, metrics.widthPixels, metrics.heightPixels);


        //create Particle
        prtcl = new Particle(200, 500);

		// create Ship and load bitmap
        ship = new Ship(
                shipsprite
                , metrics.widthPixels / 2, 5 * metrics.heightPixels / 6 // initial position
                , 35, 38    // width and height of sprite
                , FPS, 11);    // FPS and number of frames in the animation
        firingmode = new TripleBullet[4];
        for (int i = 0; i < firingmode.length; i++) {
            firingmode[i] = new TripleBullet(bulletsprite, ship.getX(), 10 + ship.getY(), FPS);
            firingmode[i].setTicks(i * 30);
        }

        // create Droid and load bitmap
		droid = new Droid(enemysprite, enemybulletsprite, 50, 50, ship);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    public void setAvgFps(String avgFps) {
        this.avgFps = avgFps;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

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

            int currentExplosion = 0; // check if explosion is null or if it is still active
            Explosion explosion = explosions[currentExplosion];
            while (explosion != null && explosion.isAlive() && currentExplosion < explosions.length) {
                currentExplosion++;
                explosion = explosions[currentExplosion];
            }
            if (explosion == null || explosion.isDead()) {
                explosion = new Explosion(200, (int) event.getX(), (int) event.getY());
                explosions[currentExplosion] = explosion;
            }

            ship.handleActionDown((int) event.getX(), (int) event.getY());

            oldX = ship.getX();
            oldY = ship.getY();

            // check if in the lower part of the screen we exit
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity) getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            oldX = ship.getX();
            oldY = ship.getY();
            ship.setOldX(oldX);
            ship.setOldY(oldY);
            // the gestures
            if (ship.isTouched()) {
                // the droid was picked up and is being dragged
                ship.setX((int) event.getX());
                ship.setY((int) event.getY());
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
        //canvas.drawColor(Color.BLACK); // needed so there isnt remains of dead bitmaps

        background.draw(canvas);

        if (prtcl.isAlive())
            prtcl.draw(canvas);

        for (Explosion i : explosions)
            if (i != null && i.isAlive()) {
                i.draw(canvas);
            }

		/*for (int i = 0; i < firingmode.length; i++) {
            droid.draw(canvas);
		}*/

        for (TripleBullet i : firingmode) {
            if (i.isAlive()) {
                i.draw(canvas);
            }
        }

		//enemyBullet.draw(canvas);

        droid.draw(canvas);
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
        background.update(System.currentTimeMillis());

        // check collision with right wall if heading right
        if (droid.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                && droid.getX() + droid.getBitmap().getWidth() / 2 >= getWidth()) {
            droid.getSpeed().toggleXDirection();
        }
        // check collision with left wall if heading left
        if (droid.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                && droid.getX() - droid.getBitmap().getWidth() / 2 <= 0) {
            droid.getSpeed().toggleXDirection();
        }
        // check collision with bottom wall if heading down
        if (droid.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                && droid.getY() + droid.getBitmap().getHeight() / 2 >= getHeight()) {
            droid.getSpeed().toggleYDirection();
        }
        // check collision with top wall if heading up
        if (droid.getSpeed().getyDirection() == Speed.DIRECTION_UP
                && droid.getY() - droid.getBitmap().getHeight() / 2 <= 0) {
            droid.getSpeed().toggleYDirection();
        }

        // Updates
        droid.update();
        prtcl.update();

        for (Explosion i : explosions)
            if (i != null && i.isAlive()) {
                i.update();
            }

        for (TripleBullet i : firingmode) {
            if (i.isAlive()) {
                i.update(System.currentTimeMillis());
            } else {
                i.setAlive(true);
                i.setTicks(0);
                i.setX(ship.getX());
                i.setY(ship.getY());
            }
        }
		}

		ship.update(System.currentTimeMillis());
	}
}
