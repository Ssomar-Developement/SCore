package com.ssomar.score.sobject.menu.defaultobjects;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.menu.SObjectsWithFileEditor;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.entity.Player;

public class NewDefaultObjectsEditorManager extends NewGUIManager<NewDefaultObjectsEditor> {

    private static NewDefaultObjectsEditorManager instance;

    public static NewDefaultObjectsEditorManager getInstance() {
        if (instance == null) instance = new NewDefaultObjectsEditorManager();
        return instance;
    }

    public void startEditing(Player editor, NewDefaultObjectsEditor gui) {
        cache.put(editor, gui);
        gui.openGUISync(editor);
    }

    @Override
    public boolean allClicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> i) {
        if (i.decoloredName.contains("Give to you all items")) {
            i.gui.giveAllObjects(i.player);
        } else if (i.coloredDeconvertName.contains(SObjectsWithFileEditor.CREATION_ID)) {
            i.gui.giveSObject(i.decoloredName.split(StringConverter.decoloredString(GUI.CREATION_ID))[1].trim(), i.player);
        } else return false;
        return true;
    }

    @Override
    public boolean noShiftclicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> i) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> i) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> i) {
        return false;
    }

    @Override
    public boolean shiftClicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> i) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> i) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> i) {
        return false;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> i) {
        return false;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {
        return false;
    }

    @Override
    public boolean middleClicked(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {
        return false;
    }

    @Override
    public void receiveMessage(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }

    @Override
    public void receiveMessagePreviousPage(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }

    @Override
    public void receiveMessageNextPage(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }

    @Override
    public void receiveMessageNoValue(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }

    @Override
    public void receiveMessageFinish(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }

    @Override
    public void receiveMessageValue(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }

    @Override
    public void newObject(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }

    @Override
    public void reset(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }

    @Override
    public void back(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {
        interact.gui.goBack(interact.player);
    }

    @Override
    public void nextPage(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {
        interact.gui.goNextPage();
    }

    @Override
    public void previousPage(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {
        interact.gui.goPreviousPage();
    }

    @Override
    public void save(NewInteractionClickedGUIManager<NewDefaultObjectsEditor> interact) {

    }
}
