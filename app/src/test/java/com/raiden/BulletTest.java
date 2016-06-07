package com.raiden;

import com.raiden.logic.Bullet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
