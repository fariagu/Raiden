package com.raiden;

import com.raiden.logic.AimedBullet;
import com.raiden.logic.Bullet;
import com.raiden.logic.TripleBullet;
import com.raiden.logic.components.Speed;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BulletTest {
    @Test
    public void BulletFires(){
        Bullet b = new Bullet();
        b.setX(50);
        b.setY(500);

        for (int i = 0; i < 10; i++){
            b.update(i);
        }

        assertEquals(50, b.getX());
        assertEquals(true, (b.getY()< 500));

        b.setTicks(120);
        b.update(10);
        assertEquals(false, b.isAlive());
    }

    @Test
    public void AimedBulletFires() throws Exception {
        AimedBullet b = new AimedBullet();
        Speed s = new Speed(5, 5);
        b.setX(10);
        b.setY(10);
        b.setSpeed(s);

        for (int i = 0; i < 10; i++){
            b.update(i);
        }

        assertTrue(b.getX() > 10);
        assertTrue(b.getY() > 10);

        s.toggleXDirection();
        s.toggleYDirection();
    }

    @Test
    public void TripleBulletFires() throws Exception {
        TripleBullet b = new TripleBullet();
        b.setX(50);
        b.setY(500);

        for (int i = 0; i < 10; i++){
            b.update(i);
        }

        assertTrue(b.getX() > 10);
        assertTrue(b.getY() > 10);
    }
}
