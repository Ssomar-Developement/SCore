package com.ssomar.score.pack.api;

/**
 * A platform for Inject, like Fabric or Paper.
 */
public interface InjectPlatform {
    /**
     * Registers the injector.
     *
     * @param injector The injector.
     */
    void registerInjector(Injector injector);
}