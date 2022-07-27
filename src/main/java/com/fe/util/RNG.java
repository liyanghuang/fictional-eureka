package com.fe.util;

public interface RNG {

    public int randInt();
    public int randInt(int end);
    public int randInt(int start, int end);
    public boolean testChance(float percent);

}
