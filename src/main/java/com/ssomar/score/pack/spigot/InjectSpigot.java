package com.ssomar.score.pack.spigot;

import com.ssomar.score.pack.api.InjectPlatform;
import com.ssomar.score.pack.api.Injector;
import com.ssomar.score.pack.spigot.interceptor.ClientConnectionInterceptor;
import com.ssomar.score.utils.logging.Utils;
import io.netty.channel.ChannelPipeline;

import java.util.ArrayList;
import java.util.List;

public class InjectSpigot implements InjectPlatform {
    public static InjectSpigot INSTANCE = new InjectSpigot();
    private final List<Injector> injectors = new ArrayList<>();
    private boolean hasInitialized = false;

    private ClientConnectionInterceptor connectionInterceptor = new ClientConnectionInterceptor();

    private InjectSpigot() {
    }

    private boolean isRunningPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public void registerInjector(Injector injector) {
        if (injectors.contains(injector)) {
            return;
        }
        try {
            // Only call install() once when the first injector is registered
            // The handler added by install() will automatically use all injectors in the list
            // for new client connections
            if (injectors.isEmpty()) {
                connectionInterceptor.install((channel) -> {
                    ChannelPipeline pipeline = channel.pipeline();
                    injectors.forEach(pipeline::addFirst);
                });
            }
            // For subsequent injectors, we don't need to do anything extra
            // The handler is already installed and will use the updated injectors list
            // Note: This won't affect existing connections, only new ones

            injectors.add(injector);

            Utils.sendConsoleMsg("Injector &e" + injector.getClass().getName() + " &7registered (TPack selfhosting)");
        }
        catch (Exception e) {
            e.printStackTrace();
            Utils.sendConsoleMsg("&cFailed to register injector &e" + injector.getClass().getName());
        }

    }

    public void unregisterAllInjectors() {
        // Copy to avoid ConcurrentModificationException
        List<Injector> injectorsCopy = new ArrayList<>(this.injectors);
        injectorsCopy.forEach(this::unregisterInjector);
    }

    /**
     * Unregisters an injector from the list so new connections won't receive it.
     * Note: This does not affect existing client connections, which will retain
     * the injector until they disconnect.
     *
     * @param injector The injector to unregister
     * @return true if successfully unregistered, false otherwise
     */
    public boolean unregisterInjector(Injector injector) {
        if (injector == null || !injectors.contains(injector)) {
            return false;
        }

        boolean removed = injectors.remove(injector);

        if (removed) {
            Utils.sendConsoleMsg("Injector &e" + injector.getClass().getName() + " &7unregistered (TPack selfhosting)");
        }

        return removed;
    }
}