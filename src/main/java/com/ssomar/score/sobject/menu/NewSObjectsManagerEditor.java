package com.ssomar.score.sobject.menu;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.menu.defaultobjects.NewDefaultObjectsEditor;
import com.ssomar.score.sobject.menu.defaultobjects.NewDefaultObjectsEditorManager;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.entity.Player;

public class NewSObjectsManagerEditor extends NewGUIManager<NewSObjectsEditorAbstract> {

    private static NewSObjectsManagerEditor instance;

    public static NewSObjectsManagerEditor getInstance() {
        if (instance == null) instance = new NewSObjectsManagerEditor();
        return instance;
    }

    public void startEditing(Player editor, NewSObjectsEditorAbstract gui) {
        cache.put(editor, gui);
        gui.openGUISync(editor);
    }

    @Override
    public boolean allClicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> i) {
        if (i.coloredDeconvertName.contains(TM.g(Text.EDITOR_FOLDER_NAME))) {
            i.gui.goToFolder(i.localizedName);
        } else if (i.coloredDeconvertName.contains(TM.g(Text.EDITOR_PATH_NAME))) {
            i.gui.goBack();
        } else if (i.coloredDeconvertName.contains(NewSObjectsEditorAbstract.NEW)) {
            i.gui.sendMessageCreate(i.player);
        } else if (i.coloredDeconvertName.contains(TM.g(Text.EDITOR_PREMADE_PREMIUM_NAME).replace("%object%", i.gui.getObjectName())) || i.coloredDeconvertName.contains(TM.g(Text.EDITOR_PREMADE_PACKS_NAME).replace("%object%", i.gui.getObjectName()))) {
            NewDefaultObjectsEditorManager.getInstance().startEditing(i.player, new NewDefaultObjectsEditor(i.gui.getSPlugin(), i.gui.getManager(), i.gui.getLoader(), i.gui));
        } else if (i.coloredDeconvertName.contains(NewSObjectsEditorAbstract.CREATION_ID)) {
            i.gui.openEditorSObject(i.decoloredName.split(StringConverter.decoloredString(GUI.CREATION_ID))[1].trim(), i.player);
        } else return false;
        return true;
    }

    @Override
    public boolean noShiftclicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> i) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> i) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> i) {
        return false;
    }

    @Override
    public boolean shiftClicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> i) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> i) {
        if (i.decoloredName.contains(StringConverter.decoloredString(GUI.CREATION_ID))) {
            i.player.closeInventory();
            String id = i.decoloredName.split(StringConverter.decoloredString(GUI.CREATION_ID))[1].trim();
            i.gui.sendMessageDelete(id, i.player);
            return true;
        }

        return false;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> i) {
        if (i.decoloredName.contains(StringConverter.decoloredString(GUI.CREATION_ID)) && !i.decoloredName.contains("ERROR ID")) {
            i.gui.giveSObject(i.decoloredName.split(StringConverter.decoloredString(GUI.CREATION_ID))[1].trim(), i.player);
            return true;
        }
        return false;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> i) {
        return false;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {
        return false;
    }

    @Override
    public boolean middleClicked(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {
        return false;
    }

    @Override
    public void receiveMessage(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void receiveMessagePreviousPage(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void receiveMessageNextPage(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void receiveMessageNoValue(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void receiveMessageFinish(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void receiveMessageValue(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void newObject(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void reset(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void back(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }

    @Override
    public void nextPage(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {
        interact.gui.goNextPage();
    }

    @Override
    public void previousPage(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {
        interact.gui.goPreviousPage();
    }

    @Override
    public void save(NewInteractionClickedGUIManager<NewSObjectsEditorAbstract> interact) {

    }
}
