package com.ssomar.score.menu.score;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.conditions.RequestMessageInfo;
import com.ssomar.score.utils.StringConverter;

public abstract class GUIManagerSCore<T extends GUIAbstract> extends GUIManager<T>{
	
	public void clicked(Player p, ItemStack item, ClickType click) {
			InteractionClickedGUIManager<T> interact = new InteractionClickedGUIManager<T>();
			interact.player = p;
			this.clicked(item, interact, click);
	}
	
	public void clicked(Player p, ItemStack item, String title, ClickType click) {
		InteractionClickedGUIManager<T> interact = new InteractionClickedGUIManager<T>();
		interact.player = p;
		interact.title = title;
		this.clicked(item, interact, click);
}

	public void clicked(ItemStack item, InteractionClickedGUIManager<T> interact, ClickType click) {
		if(item != null && item.hasItemMeta()) {
			interact.cache = this.getCache();
			interact.sPlugin = cache.get(interact.player).getsPlugin();
			interact.sObject = cache.get(interact.player).getSObject();
			interact.sActivator = cache.get(interact.player).getSAct();
			interact.name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
			interact.gui = cache.get(interact.player);
			
			RequestMessageInfo msgInfos = new RequestMessageInfo();
			msgInfos.player = interact.player;
			msgInfos.sPlugin = interact.sPlugin;
			
			interact.msgInfos = msgInfos;
			
			if(interact.name.contains("Reset")) {
				interact.resetGUI();
			}
			else if(interact.name.contains("Exit")) {
				interact.player.closeInventory();
			}
			else{				
				if(click.equals(ClickType.SHIFT_LEFT) || click.equals(ClickType.SHIFT_RIGHT)) {
					if(click.equals(ClickType.SHIFT_LEFT)) if(this.shiftLeftClicked(interact)) return;
					else if(this.shiftRightClicked(interact)) return;
					if(this.shiftClicked(interact)) return;
				}
				else {
					if(click.equals(ClickType.SHIFT_RIGHT) || click.equals(ClickType.RIGHT)) if(this.noShiftRightclicked(interact)) return;
					else if(this.noShiftLeftclicked(interact)) return;
					if(this.noShiftclicked(interact)) return;
				}
				
				if(click.equals(ClickType.SHIFT_RIGHT) || click.equals(ClickType.RIGHT)) {
					if(this.rightClicked(interact)) return;
				}
				else if(this.leftClicked(interact)) return;
				
				if(this.allClicked(interact)) return;
			}
		}
	}

	public abstract boolean allClicked(InteractionClickedGUIManager<T> i);
	
	public abstract boolean noShiftclicked(InteractionClickedGUIManager<T> i);
	
	public abstract boolean noShiftLeftclicked(InteractionClickedGUIManager<T> i);
	
	public abstract boolean noShiftRightclicked(InteractionClickedGUIManager<T> i);
	
	public abstract boolean shiftClicked(InteractionClickedGUIManager<T> i);
	
	public abstract boolean shiftLeftClicked(InteractionClickedGUIManager<T> i);
	
	public abstract boolean shiftRightClicked(InteractionClickedGUIManager<T> i);
	
	public abstract boolean leftClicked(InteractionClickedGUIManager<T> i);
	
	public abstract boolean rightClicked(InteractionClickedGUIManager<T> interact);
	

}