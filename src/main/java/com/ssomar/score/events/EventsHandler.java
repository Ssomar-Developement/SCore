package com.ssomar.score.events;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.player.commands.sudoop.SecurityOPCommands;
import com.ssomar.score.commands.runnable.player.events.*;
import com.ssomar.score.menu.InteractionGUI;
import com.ssomar.score.nofalldamage.NoFallDamageEvt;
import com.ssomar.score.sobject.sactivator.cooldowns.CooldownsHandler;
import com.ssomar.testRecode.editor.NewEditorInteractionsListener;

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

		/* No EntityToggleGlideEvent & EntityPickupItemEvent in 1.11 -*/
		if(!SCore.is1v11Less()) {
			main.getServer().getPluginManager().registerEvents(new StunEvent(), main);

			main.getServer().getPluginManager().registerEvents(new PlayerPickup(), main);
		}
		
		main.getServer().getPluginManager().registerEvents(new InteractionGUI(), main);
		
		main.getServer().getPluginManager().registerEvents(new CommandsHandler(), main);

		main.getServer().getPluginManager().registerEvents(new KeepCustomFlyEvent(), main);

		main.getServer().getPluginManager().registerEvents(new XPBoostEvent(), main);

		main.getServer().getPluginManager().registerEvents(new DisableFlyActivationEvent(), main);

		main.getServer().getPluginManager().registerEvents(new DisableGlideActivationEvent(), main);

		main.getServer().getPluginManager().registerEvents(PlaceholderLastDamageDealtEvent.getInstance(), main);

		main.getServer().getPluginManager().registerEvents(new DamageResistanceEvent(), main);

		main.getServer().getPluginManager().registerEvents(new DamageBoostEvent(), main);
		
		main.getServer().getPluginManager().registerEvents(new CooldownsHandler(), main);

		main.getServer().getPluginManager().registerEvents(new FixSpawnerPlaceEvent(), main);

		main.getServer().getPluginManager().registerEvents(new RemoveProjectileHitBlockEvent(), main);

		main.getServer().getPluginManager().registerEvents(new RemoveCancelDamageEventMetadataTagEvent(), main);

		main.getServer().getPluginManager().registerEvents(new TESTEVENT_TODELETE(), main);

		/** Recode event **/
		main.getServer().getPluginManager().registerEvents(new NewEditorInteractionsListener(), main);

	}

	public static EventsHandler getInstance() {
	    if (instance == null) instance = new EventsHandler();
	    return instance;
	 }
}
