package com.raiden;

import com.raiden.logic.Explosion;

import org.bouncycastle.jce.provider.asymmetric.ec.KeyFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExplosionTest {
    @Test
    public void ExplosionTest()throws Exception {
        Explosion e = new Explosion(0, 0);

        assertTrue(e.isAlive());

        e.setState(0);
        e.update();

        assertTrue(e.isDead());
    }
}
