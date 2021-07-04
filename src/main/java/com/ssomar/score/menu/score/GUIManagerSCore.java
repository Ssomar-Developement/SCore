package com.ssomar.score.menu.score;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.conditions.RequestMessageInfo;
import com.ssomar.score.utils.StringConverter;

public abstract class GUIManagerSCore<T extends GUIAbstract> extends GUIManager<T>{
	
	public void clicked(Player p, ItemStack item) {
			InteractionClickedGUIManager<T> interact = new InteractionClickedGUIManager<T>();
			interact.player = p;
			this.clicked(item, interact);
	}
	
	public void clicked(Player p, ItemStack item, String title) {
		InteractionClickedGUIManager<T> interact = new InteractionClickedGUIManager<T>();
		interact.player = p;
		interact.title = title;
		this.clicked(item, interact);
}

	public void clicked(ItemStack item, InteractionClickedGUIManager<T> interact) {
		if(item != null && item.hasItemMeta()) {
			interact.cache = this.getCache();
			interact.sPlugin = cache.get(interact.player).getsPlugin();
			interact.sObject = cache.get(interact.player).getSObject();
			interact.sActivator = cache.get(interact.player).getSAct();
			interact.name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
			
			RequestMessageInfo msgInfos = new RequestMessageInfo();
			msgInfos.player = interact.player;
			msgInfos.sPlugin = interact.sPlugin;
			
			if(interact.name.contains("Reset")) {
				interact.resetGUI();
			}
			else if(interact.name.contains("Exit")) {
				interact.player.closeInventory();
			}
			else this.clicked(interact);
		}
	}

	public abstract void clicked(InteractionClickedGUIManager<T> interact);

}
