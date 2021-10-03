package com.ssomar.score.projectiles.features.Particles;

import com.ssomar.executableitems.items.Item;
import com.ssomar.executableitems.items.ItemManager;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.activator.requiredei.RequiredEIsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEI;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEIManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ParticleGUIManager extends GUIManager<ParticleGUI> {

	private static ParticleGUIManager instance;

	public void startEditing(Player p, ParticlesFeature.CustomParticle particle, int index, String projID) {
		cache.put(p, new ParticleGUI(particle, index, projID));
		cache.get(p).openGUISync(p);
	}

	// TODO manager of particle GUI

	@Override
	public void saveTheConfiguration(Player p) {

	}
}
