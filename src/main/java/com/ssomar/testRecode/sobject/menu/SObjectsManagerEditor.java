package com.ssomar.testRecode.sobject.menu;

import com.ssomar.executableitems.configs.ingame.items.DefaultItemsGUI;
import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.menu.NewGUIManager;
import com.ssomar.testRecode.menu.NewInteractionClickedGUIManager;
import org.bukkit.entity.Player;

public class SObjectsManagerEditor extends NewGUIManager<SObjectsEditorAbstract> {

	private static SObjectsManagerEditor instance;

	public void startEditing(Player editor, SObjectsEditorAbstract gui) {
		cache.put(editor, gui);
		gui.openGUISync(editor);
	}

	@Override
	public boolean allClicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> i) {
		if(i.decoloredName.contains("FOLDER: ")){
			i.gui.goToFolder(i.name);
		}
		else if(i.decoloredName.contains("Path")){
			i.gui.goBack();
		}
		else if(i.coloredDeconvertName.contains(SObjectsEditorAbstract.NEW)){
			i.gui.sendMessageCreate(i.player);
		}
		else if(i.decoloredName.contains("Default Premium") || i.name.contains(" from Custom packs")){
			new DefaultItemsGUI(i.player).openGUISync(i.player);
		}
		else return false;
		return true;
	}

	@Override
	public boolean noShiftclicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> i) {
		return false;
	}

	@Override
	public boolean noShiftLeftclicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> i) {
		return false;
	}

	@Override
	public boolean shiftLeftClicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> i) {
		if(i.decoloredName.contains(GUI.OBJECT_ID)) {
			i.player.closeInventory();
			String id = i.decoloredName.split(GUI.OBJECT_ID)[1];
			i.gui.sendMessageDelete(id, i.player);
			return true;
		}

		return false;
	}

	@Override
	public boolean shiftRightClicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> i) {
		if(i.decoloredName.contains(GUI.OBJECT_ID) && !i.decoloredName.contains("ERROR ID")) {
			i.gui.giveSObject(i.decoloredName.split(GUI.OBJECT_ID)[1], i.player);
			return true;
		}
		return false;
	}

	@Override
	public boolean leftClicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> i) {
		return false;
	}

	@Override
	public boolean rightClicked(NewInteractionClickedGUIManager<SObjectsEditorAbstract> interact) {
		return false;
	}

	@Override
	public void receiveMessage(Player p, String message, NewInteractionClickedGUIManager<SObjectsEditorAbstract> interact) {

	}

	@Override
	public void reset(NewInteractionClickedGUIManager<SObjectsEditorAbstract> interact) {

	}

	@Override
	public void back(NewInteractionClickedGUIManager<SObjectsEditorAbstract> interact) {

	}

	@Override
	public void nextPage(NewInteractionClickedGUIManager<SObjectsEditorAbstract> interact) {
		interact.gui.goNextPage();
	}

	@Override
	public void previousPage(NewInteractionClickedGUIManager<SObjectsEditorAbstract> interact) {
		interact.gui.goPreviousPage();
	}

	@Override
	public void save(NewInteractionClickedGUIManager<SObjectsEditorAbstract> interact) {

	}

	public static SObjectsManagerEditor getInstance(){
		if(instance == null) instance = new SObjectsManagerEditor();
		return instance;
	}
}
