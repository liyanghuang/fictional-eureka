package com.fe.util;

import java.util.Random;

import com.google.inject.Inject;

public class RNGImpl implements RNG{

    private Random random;

    @Inject
    public RNGImpl(Random random) {
        this.random = random;
    }

    @Override
    public int randInt() {
        return random.nextInt();
    }

    // returns random int 0 - end
    @Override
    public int randInt(int end) {
        return random.nextInt(end);
    }

    // returns random int start - end
    @Override
    public int randInt(int start, int end) {
        return random.nextInt(start, end);
    }

    // returns true with percent chance
    @Override
    public boolean testChance(float percent) {
        return random.nextFloat() < percent;
    }
    
}
