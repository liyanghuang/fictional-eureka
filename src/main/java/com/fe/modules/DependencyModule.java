package com.fe.modules;

import java.util.Random;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class DependencyModule extends AbstractModule{
    
    public void configure() {}

    // provide Random to use
    @Provides
    @Singleton
    public Random provideRandom() {
        return new Random();
    }

}
