package com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.blockcdt.BlockConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.AroundBlockCondition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class AroundBlockConditionsGUIManager extends GUIManagerSCore<AroundBlockConditionsGUI>{

	private static AroundBlockConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, List<AroundBlockCondition> list, String detail) {
		cache.put(p, new AroundBlockConditionsGUI(sPlugin, sObject, sActivator, list, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public void clicked(InteractionClickedGUIManager<AroundBlockConditionsGUI> i) {
		String cPage  = StringConverter.decoloredString(i.title);
		
		if (i.name.contains("Next page")) {
			cache.replace(i.player, new AroundBlockConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]) + 1, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getList(), cache.get(i.player).getDetail()));
			cache.get(i.player).openGUISync(i.player);
		} else if (i.name.contains("Previous page")) {
			cache.replace(i.player, new AroundBlockConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]) - 1, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getList(), cache.get(i.player).getDetail()));
			cache.get(i.player).openGUISync(i.player);
		}
		else if (i.name.contains("New Around block cdt")) {
			i.player.closeInventory();
			AroundBlockConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getList(), cache.get(i.player).getDetail());
		} 
		
		else if(i.name.contains("Back")) {
			BlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sActivator.getBlockConditions(), cache.get(i.player).getDetail());
		}
		else if(i.name.isEmpty()){
			return;
		}
		else {
			AroundBlockCondition aBC = null;
			for (AroundBlockCondition place :  cache.get(i.player).getList()) {
				if (place.getId().equals(StringConverter.decoloredString(i.name).split("✦ ID: ")[1]))
					aBC = place;
			}
			if (aBC != null)
				AroundBlockConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, aBC, cache.get(i.player).getDetail());
			else {
				i.player.sendMessage(StringConverter.coloredString(
						"&4&l"+i.sPlugin.getNameDesign()+" &cCan't load this block around cdt, pls contact the developper on discord if you see this message"));
				AroundBlockConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getList(), cache.get(i.player).getDetail());
			}
		}

		cache.remove(i.player);
	}

	public void shiftLeftClicked(Player p, ItemStack item, String title) {
		String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
		String cPage = StringConverter.decoloredString(title);
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		try {
			String id = name.split("✦ ID: ")[1];
			AroundBlockCondition.deleteBACCdt(sPlugin, sObject, sActivator, id, cache.get(p).getDetail());
			LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
			sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
			sActivator = sObject.getActivator(sActivator.getID());
			cache.replace(p, new AroundBlockConditionsGUI(Integer.valueOf(cPage.split("Page ")[1]), sPlugin, sObject, sActivator, sActivator.getBlockConditions().getBlockAroundConditions(), cache.get(p).getDetail()));
			cache.get(p).openGUISync(p);
		}
		catch(Exception e) {

		}

	}

	public static AroundBlockConditionsGUIManager getInstance() {
		if(instance == null) instance = new AroundBlockConditionsGUIManager();
		return instance;
	}
}
