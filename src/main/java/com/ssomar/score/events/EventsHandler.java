package com.ssomar.score.events;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.player.commands.sudoop.SecurityOPCommands;
import com.ssomar.score.commands.runnable.player.events.*;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.damagewithoutknockback.DamageWithoutKnockbackListener;
import com.ssomar.score.editor.NewEditorInteractionsListener;
import com.ssomar.score.features.custom.cooldowns.CooldownsHandler;
import com.ssomar.score.nofalldamage.NoFallDamageListener;
import com.ssomar.score.usedapi.Dependency;
import com.ssomar.score.usedapi.JobsAPI;

public class EventsHandler {

    private static EventsHandler instance;

    private SCore main;

    public static EventsHandler getInstance() {
        if (instance == null) instance = new EventsHandler();
        return instance;
    }

    public void setup(SCore main) {
        this.main = main;
        setupEvents();
    }

    public void setupEvents() {
        main.getServer().getPluginManager().registerEvents(new SecurityOPCommands(), main);

        main.getServer().getPluginManager().registerEvents(new NoFallDamageListener(), main);

        main.getServer().getPluginManager().registerEvents(new DamageWithoutKnockbackListener(), main);

        main.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), main);

        main.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), main);

        /* No EntityToggleGlideEvent & EntityPickupItemEvent in 1.11 -*/
        if (!SCore.is1v11Less()) {
            main.getServer().getPluginManager().registerEvents(new StunEvent(), main);

            main.getServer().getPluginManager().registerEvents(new PlayerPickup(), main);
        }

        main.getServer().getPluginManager().registerEvents(CommandsHandler.getInstance(), main);

        main.getServer().getPluginManager().registerEvents(new KeepCustomFlyEvent(), main);

        main.getServer().getPluginManager().registerEvents(new XPBoostEvent(), main);

        main.getServer().getPluginManager().registerEvents(new DisableFlyActivationEvent(), main);

        if(!SCore.is1v12Less()) main.getServer().getPluginManager().registerEvents(new DisableGlideActivationEvent(), main);

        main.getServer().getPluginManager().registerEvents(PlaceholderLastDamageDealtEvent.getInstance(), main);

        main.getServer().getPluginManager().registerEvents(new OpenChestEvent(), main);

        main.getServer().getPluginManager().registerEvents(new DamageResistanceEvent(), main);

        main.getServer().getPluginManager().registerEvents(new DamageBoostEvent(), main);

        main.getServer().getPluginManager().registerEvents(new CooldownsHandler(), main);

        main.getServer().getPluginManager().registerEvents(new FixSpawnerPlaceEvent(), main);

        main.getServer().getPluginManager().registerEvents(new RemoveProjectileHitBlockEvent(), main);

        main.getServer().getPluginManager().registerEvents(new RemoveCancelDamageEventMetadataTagEvent(), main);

        /* Recode event */
        main.getServer().getPluginManager().registerEvents(new NewEditorInteractionsListener(), main);

        if(SCore.hasJetsMinions && GeneralConfig.getInstance().isJetMinionsGenerateBreakActivator()) main.getServer().getPluginManager().registerEvents(new FixJetsMinionsBlockBreakEvent(), main);

        if(!SCore.is1v13Less() && GeneralConfig.getInstance().isEnableDetectionEntitiesFromSpawner()) main.getServer().getPluginManager().registerEvents(new EntitiesFromSpawnerListener(), main);

        main.getServer().getPluginManager().registerEvents(CheckIfDamageIsPosssibleListener.getInstance(), main);

        main.getServer().getPluginManager().registerEvents(new TESTEVENT(), main);

        if(Dependency.JOBS.isInstalled())  main.getServer().getPluginManager().registerEvents(new JobsAPI(), main);
    }
}
