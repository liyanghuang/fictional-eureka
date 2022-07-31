package com.fe.util;

import java.util.Random;

import com.google.inject.Inject;

public class RNGImpl implements RNG{

    private final Random random;

    @Inject
    public RNGImpl(final Random random) {
        this.random = random;
    }

    @Override
    public int randInt() {
        return random.nextInt();
    }

    // returns random int 0 - end
    @Override
    public int randInt(final int end) {
        return random.nextInt(end);
    }

    // returns random int start - end
    @Override
    public int randInt(final int start, final int end) {
        return random.nextInt(start, end);
    }

    // returns true with percent chance
    @Override
    public boolean testChance(final float percent) {
        return random.nextFloat() < percent;
    }
    
}
