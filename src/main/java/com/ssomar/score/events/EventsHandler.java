package com.ssomar.score.events;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.player.commands.sudoop.SecurityOPCommands;
import com.ssomar.score.menu.InteractionGUI;
import com.ssomar.score.nofalldamage.NoFallDamageEvt;
import com.ssomar.score.sobject.sactivator.cooldowns.CooldownsHandler;

public class EventsHandler {
	
	private static EventsHandler instance;
	
	private SCore main;
	
	public void setup(SCore main) {
		this.main = main;
		setupEvents();
	}
	
	public void setupEvents() {
		main.getServer().getPluginManager().registerEvents(new SecurityOPCommands(), main);
		
		main.getServer().getPluginManager().registerEvents(new NoFallDamageEvt(), main);
		
		main.getServer().getPluginManager().registerEvents(new PlayerReconnexion(), main);
		
		main.getServer().getPluginManager().registerEvents(new PlayerPickup(), main);
		
		main.getServer().getPluginManager().registerEvents(new InteractionGUI(), main);
		
		main.getServer().getPluginManager().registerEvents(new CommandsHandler(), main);

		main.getServer().getPluginManager().registerEvents(new KeepCustomFlyEvent(), main);
		
		main.getServer().getPluginManager().registerEvents(new CooldownsHandler(), main);

		main.getServer().getPluginManager().registerEvents(new FixSpawnerPlaceEvent(), main);

		main.getServer().getPluginManager().registerEvents(new RemoveProjectileHitBlockEvent(), main);

		main.getServer().getPluginManager().registerEvents(new RemoveCancelDamageEventMetadataTagEvent(), main);
	}

	public static EventsHandler getInstance() {
	    if (instance == null) instance = new EventsHandler();
	    return instance;
	 }
}
