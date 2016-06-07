package com.raiden;

import com.raiden.logic.DyingShip;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DyingShipTest {
    @Test
    public void DyingShipExplosion() throws Exception {
        DyingShip d = new DyingShip();

        d.setAlive(true);
        for (int i = 0; i < 50; i++){
            d.update(i);
        }
        assertTrue(!d.isAlive());
    }
}
