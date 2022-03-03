package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt.AroundBlockConditionsGUIManager;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;

public class BlockConditionsGUIManager extends GUIManagerConditions<BlockConditionsGUI>{

	private static BlockConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {
		cache.put(p, new BlockConditionsGUI(sPlugin, sObject, sActivator, bC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
		
		BlockConditions bC;
		if(detail.contains("target")) bC = i.sObject.getActivator(i.sActivator.getID()).getTargetBlockConditions();
		else bC = i.sObject.getActivator(i.sActivator.getID()).getBlockConditions();
			
		BlockConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, bC, detail);
		return true;
	}
	
	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		
		BlockConditions bC = (BlockConditions) cache.get(i.player).getConditions();
		
		if(i.name.contains(BlockConditionsGUI.IF_IS_POWERED)) {
			i.gui.changeBoolean(BlockConditionsGUI.IF_IS_POWERED);
		}
		else if(i.name.contains(BlockConditionsGUI.IF_MUST_BE_NOT_POWERED)) {
			i.gui.changeBoolean(BlockConditionsGUI.IF_MUST_BE_NOT_POWERED);
		}
		else if(i.name.contains(BlockConditionsGUI.IF_MUST_BE_NATURAL)) {
			i.gui.changeBoolean(BlockConditionsGUI.IF_MUST_BE_NATURAL);
		}
		else if(i.name.contains(BlockConditionsGUI.IF_PLANT_FULLY_GROWN)) {
			i.gui.changeBoolean(BlockConditionsGUI.IF_PLANT_FULLY_GROWN);
		}
		else if(i.name.contains(BlockConditionsGUI.IF_PLAYER_MUST_BE_ON_THE_BLOCK)) {
			i.gui.changeBoolean(BlockConditionsGUI.IF_PLAYER_MUST_BE_ON_THE_BLOCK);
		}
		else if(i.name.contains(BlockConditionsGUI.IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK)){
			i.gui.changeBoolean(BlockConditionsGUI.IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK);
		}
		else if(i.name.contains(BlockConditionsGUI.AROUND_BLOCK_CDT)) {
			AroundBlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, bC.getBlockAroundConditions(), cache.get(i.player).getDetail());
		}
		else if(i.name.contains(BlockConditionsGUI.BLOCK_X_CDT)) {
			requestWriting.put(i.player,BlockConditionsGUI.BLOCK_X_CDT);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF BLOCK POS X:"));

			this.showCalculationGUI(i.player, "Pos X", cache.get(i.player).getIfPosX());
			space(i.player);
		}
		else if(i.name.contains(BlockConditionsGUI.BLOCK_X_CDT2)) {
			requestWriting.put(i.player,BlockConditionsGUI.BLOCK_X_CDT2);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF BLOCK POS X 2:"));

			this.showCalculationGUI(i.player, "Pos X 2", cache.get(i.player).getIfPosX());
			space(i.player);
		}
		else if(i.name.contains(BlockConditionsGUI.BLOCK_Y_CDT)) {
			requestWriting.put(i.player,BlockConditionsGUI.BLOCK_Y_CDT);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF BLOCK POS Y:"));

			this.showCalculationGUI(i.player, "Pos Y", cache.get(i.player).getIfPosX());
			space(i.player);
		}
		else if(i.name.contains(BlockConditionsGUI.BLOCK_Y_CDT2)) {
			requestWriting.put(i.player,BlockConditionsGUI.BLOCK_Y_CDT2);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF BLOCK POS Y 2:"));

			this.showCalculationGUI(i.player, "Pos Y 2", cache.get(i.player).getIfPosX());
			space(i.player);
		}
		else if(i.name.contains(BlockConditionsGUI.BLOCK_Z_CDT)) {
			requestWriting.put(i.player,BlockConditionsGUI.BLOCK_Z_CDT);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF BLOCK POS Z:"));

			this.showCalculationGUI(i.player, "Pos Z", cache.get(i.player).getIfPosX());
			space(i.player);
		}
		else if(i.name.contains(BlockConditionsGUI.BLOCK_Z_CDT2)) {
			requestWriting.put(i.player,BlockConditionsGUI.BLOCK_Z_CDT2);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF BLOCK POS Z 2:"));

			this.showCalculationGUI(i.player, "Pos Z", cache.get(i.player).getIfPosX());
			space(i.player);
		}
		else return false;
		
		return true;
	}
	
	public void receivedMessage(Player p, String message) {
		boolean notExit = true;
		SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		String plName = sPlugin.getNameDesign();

		if(message.contains("exit")) {
			boolean pass = false;
			if(StringConverter.decoloredString(message).equals("exit with delete")) {
				
				if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_X_CDT)) {
					cache.get(p).updateIfPosX("");
				}
				else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_X_CDT2)) {
					cache.get(p).updateIfPosX2("");
				}
				else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_Y_CDT)) {
					cache.get(p).updateIfPosY("");
				}
				else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_Y_CDT2)) {
					cache.get(p).updateIfPosY2("");
				}
				else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_Z_CDT)) {
					cache.get(p).updateIfPosZ("");
				}
				else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_Z_CDT2)) {
					cache.get(p).updateIfPosZ2("");
				}
				
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				pass = true;
			}
			if(StringConverter.decoloredString(message).equals("exit") || pass) {
				
				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit=false;
			}
		}
		if(notExit) {
//			if(message.contains("delete line <")) {	
//				this.deleteLine(message, p);
//				this.showTheGoodEditor(requestWriting.get(p), p);
//				space(p);
//				space(p);			
//			}
			
			if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_X_CDT)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosX(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos X please !"));
					this.showCalculationGUI(p, "Pos X", cache.get(p).getIfPosX());
				}
			}
			else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_X_CDT2)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosX2(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos X 2 please !"));
					this.showCalculationGUI(p, "Pos X 2", cache.get(p).getIfPosX2());
				}
			}
			else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_Y_CDT)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosY(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos Y please !"));
					this.showCalculationGUI(p, "Pos Y", cache.get(p).getIfPosY());
				}
			}
			else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_Y_CDT2)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosY2(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos Y 2 please !"));
					this.showCalculationGUI(p, "Pos Y 2", cache.get(p).getIfPosY2());
				}
			}
			else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_Z_CDT)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosZ(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos Z please !"));
					this.showCalculationGUI(p, "Pos Z", cache.get(p).getIfPosZ());
				}
			}
			else if(requestWriting.get(p).equals(BlockConditionsGUI.BLOCK_Z_CDT2)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosZ2(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos Z 2 please !"));
					this.showCalculationGUI(p, "Pos Z 2", cache.get(p).getIfPosZ2());
				}
			}
			
		}

	}


	public static BlockConditionsGUIManager getInstance() {
		if(instance == null) instance = new BlockConditionsGUIManager();
		return instance;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sAct = cache.get(p).getSAct();
		//String plName = sPlugin.getNameDesign();

		BlockConditions bC = (BlockConditions) cache.get(p).getConditions();

		bC.setIfIsPowered(cache.get(p).getBoolean(BlockConditionsGUI.IF_IS_POWERED));
		bC.setIfMustBeNotPowered(cache.get(p).getBoolean(BlockConditionsGUI.IF_MUST_BE_NOT_POWERED));
		bC.setIfMustBeNatural(cache.get(p).getBoolean(BlockConditionsGUI.IF_MUST_BE_NATURAL));
		bC.setIfPlantFullyGrown(cache.get(p).getBoolean(BlockConditionsGUI.IF_PLANT_FULLY_GROWN));
		bC.setIfBlockLocationX(cache.get(p).getIfPosX());
		bC.setIfBlockLocationX2(cache.get(p).getIfPosX2());
		bC.setIfBlockLocationY(cache.get(p).getIfPosY());
		bC.setIfBlockLocationY2(cache.get(p).getIfPosY2());
		bC.setIfBlockLocationZ(cache.get(p).getIfPosZ());
		bC.setIfBlockLocationZ2(cache.get(p).getIfPosZ2());
		bC.setIfPlayerMustBeOnTheBlock(cache.get(p).getBoolean(BlockConditionsGUI.IF_PLAYER_MUST_BE_ON_THE_BLOCK));
		bC.setIfNoPlayerMustBeOnTheBlock(cache.get(p).getBoolean(BlockConditionsGUI.IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK));

		BlockConditions.saveBlockConditions(sPlugin, sObject, sAct, bC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
	}

}
