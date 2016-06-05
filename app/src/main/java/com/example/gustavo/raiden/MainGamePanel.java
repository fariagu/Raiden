package com.example.gustavo.raiden;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.gustavo.raiden.model.Bullet;
import com.example.gustavo.raiden.model.Droid;
import com.example.gustavo.raiden.model.DyingShip;
import com.example.gustavo.raiden.model.Explosion;
import com.example.gustavo.raiden.model.PowerUp;
import com.example.gustavo.raiden.model.Ship;
import com.example.gustavo.raiden.model.TripleBullet;
import com.example.gustavo.raiden.model.components.Speed;

import java.util.Random;

/**
 * This is the main surface that handles the ontouch events and draws the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private MainThread thread;
    private Droid[] enemies;
    private DyingShip[] deads;
    private Explosion[] explosions;
    private TripleBullet[] tripleBullets;
    private Bullet[] firingmode;
    private PowerUp powerup;

    private Bitmap backgroundimg = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundds);
    private Bitmap bulletsprite = BitmapFactory.decodeResource(getResources(), R.drawable.shoot);
    private Bitmap bulletsprite2 = BitmapFactory.decodeResource(getResources(), R.drawable.shoot2);
    private Bitmap shipsprite = BitmapFactory.decodeResource(getResources(), R.drawable.shipsprite);
    private Bitmap shipsprite2 = BitmapFactory.decodeResource(getResources(), R.drawable.shipsprite2);
    private Bitmap staticship = BitmapFactory.decodeResource(getResources(), R.drawable.staticship);
    private Bitmap enemybulletsprite = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
    private Bitmap enemysprite = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
    private Bitmap powerupsprite = BitmapFactory.decodeResource(getResources(), R.drawable.powerup);
    private Bitmap explosion = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);

    private Background background;
    private Ship ship;

    private int FPS = 20;
    private int NR_BULLETS = 4;
    private int NR_ENEMIES = 10;

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
        background = new Background(backgroundimg, metrics.heightPixels, metrics.widthPixels, FPS);

        // create Ship and load bitmap
        ship = new Ship(
                shipsprite
                , metrics.widthPixels / 2, 5 * metrics.heightPixels / 6 // initial position
                , FPS, 11);    // FPS and number of frames in the animation

        ship.setStaticShip(staticship);

        // create Droid and load bitmap

        Droid.setScreenHeight(metrics.heightPixels);
        Droid.setScreenWidth(metrics.widthPixels);
        enemies = new Droid[NR_ENEMIES];
        deads = new DyingShip[NR_ENEMIES];
        Random r = new Random();
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Droid(enemysprite, enemybulletsprite,
                    r.nextInt(metrics.widthPixels - enemysprite.getWidth()),
                    r.nextInt((metrics.heightPixels * 2 / 3) - enemysprite.getHeight()),
                    ship, FPS);
            enemies[i].getBullet().setTicks(-(r.nextInt(50)+25));
            enemies[i].getBullet().setAlive(false);

            deads[i] = new DyingShip(explosion, 0, 0, FPS, 16);
        }

        //create a powerup
        powerup = new PowerUp(powerupsprite, 100, 100, FPS);


        tripleBullets = new TripleBullet[NR_BULLETS];
        for (int i = 0; i < tripleBullets.length; i++) {
            tripleBullets[i] = new TripleBullet(bulletsprite2, ship, FPS);
            tripleBullets[i].setShip(ship);
            tripleBullets[i].setTicks(i * 120 / NR_BULLETS);
        }

        firingmode = new Bullet[NR_BULLETS];
        for (int i = 0; i < firingmode.length; i++) {
            firingmode[i] = new Bullet(bulletsprite, ship, FPS);
            firingmode[i].setTicks(i * (120 / NR_BULLETS));
        }

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
        int oldX, oldY;
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

        background.draw(canvas);// needed so there isnt remains of dead bitmaps

        for (Explosion e : explosions)
            if (e != null && e.isAlive()) {
                e.draw(canvas);
            }

        if (ship.isPoweredup())
            for (TripleBullet tp : tripleBullets)
                tp.draw(canvas);
        else
            for (Bullet b : firingmode)
                b.draw(canvas);

        for (Droid d : enemies)
            d.draw(canvas);

        for (DyingShip ds : deads)
            ds.draw(canvas);

        powerup.draw(canvas);

        if (ship.isAlive()) {
            ship.draw(canvas);
            ship.displayScore(canvas);
        } else {
            ship.gameOver(canvas);
        }

        // display fps
        displayFps(canvas, avgFps);
    }

    private void displayFps(Canvas canvas, String FPS) {
        if (canvas != null && FPS != null) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            paint.setColor(Color.BLACK);
            paint.setTextSize(40);
            canvas.drawText(FPS, this.getWidth() - 200, 30, paint);
        }
    }

    /**
     * This is the game update method. It iterates through all the objects
     * and calls their update method if they have one or calls specific
     * engine's update method.
     */
    public void update() {
        background.update(System.currentTimeMillis());

        for (Droid i : enemies){
            if (i.isAlive()){
                ship.checkCollision(i);
            }
            if (i.getBullet().isAlive()){
                ship.checkCollision(i.getBullet());
            }
        }

        //wall collisions
        for (int i = 0; i < enemies.length; i++) {

            //se morrer nao precisa de fazer o calculo das paredes

            if (enemies[i].isAlive()) {

                deads[i].setAlive(false);
                deads[i].setX(enemies[i].getX());
                deads[i].setY(enemies[i].getY());
/*
                // check collision with right wall if heading right
                if (enemies[i].getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                        && enemies[i].getX() + enemies[i].getBitmap().getWidth() / 2 >= getWidth()) {
                    enemies[i].getSpeed().toggleXDirection();
                }
                // check collision with left wall if heading left
                if (enemies[i].getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                        && enemies[i].getX() - enemies[i].getBitmap().getWidth() / 2 <= 0) {
                    enemies[i].getSpeed().toggleXDirection();
                }
                // check collision with bottom wall if heading down
                if (enemies[i].getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                        && enemies[i].getY() + enemies[i].getBitmap().getHeight() / 2 >= getHeight()) {
                    enemies[i].getSpeed().toggleYDirection();
                }
                // check collision with top wall if heading up
                if (enemies[i].getSpeed().getyDirection() == Speed.DIRECTION_UP
                        && enemies[i].getY() - enemies[i].getBitmap().getHeight() / 2 <= 0) {
                    enemies[i].getSpeed().toggleYDirection();
                }*/
            } else {
                deads[i].update(System.currentTimeMillis());
            }
            enemies[i].update(System.currentTimeMillis());
        }

        for (Explosion i : explosions)
            if (i != null && i.isAlive()) {
                i.update();
            }

        if (ship.checkCollision(powerup)) {
            ship.setBitmap(shipsprite2);

            //necessary so first triple bullet isn't fired from ship's initial position
            for (TripleBullet i : tripleBullets){
                i.setX(ship.getX());
                i.setY(ship.getY());
            }
        }

        if (ship.isPoweredup()) {
            for (TripleBullet tp : tripleBullets) {
                if (tp.isAlive()) {
                    for (int i = 0; i < enemies.length; i++) {//this collision kills enemy droids
                        if (tp.checkCollision(enemies[i]))
                            deads[i].setAlive(true);
                    }
                    tp.update(System.currentTimeMillis());
                } else {
                    tp.setAlive(true);
                    tp.setTicks(0);
                    tp.setX(ship.getX());
                    tp.setY(ship.getY());
                }
            }
            for (Bullet i : firingmode)
                i.setAlive(false);
        } else
            for (Bullet b : firingmode) {
                if (b.isAlive()) {
                    for (int i = 0; i < enemies.length; i++) {//this collision kills enemy droids
                        if (b.checkCollision(enemies[i]))
                            deads[i].setAlive(true);
                    }
                    b.update(System.currentTimeMillis());
                } else {
                    b.setAlive(true);
                    b.setTicks(0);
                    b.setX(ship.getX());
                    b.setY(ship.getY());
                }
            }

        if (ship.isAlive()){
            ship.update(System.currentTimeMillis());
        }


        //this.running = ship.isAlive();
    }
}
