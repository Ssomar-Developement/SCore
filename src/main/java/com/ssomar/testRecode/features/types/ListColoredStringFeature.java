package com.ssomar.testRecode.features.types;

import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.editor.NewInteractionClickedGUIManager;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireMultipleMessageInEditor;
import com.ssomar.testRecode.features.FeatureRequireOneMessageInEditor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.menu.conditions.RequestMessage.space;

@Getter @Setter
public class ListColoredStringFeature extends FeatureAbstract<List<String>, ListColoredStringFeature> implements FeatureRequireMultipleMessageInEditor {

    private List<String> value;
    private List<String> defaultValue;

    public ListColoredStringFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        value = config.getStringList(getName());
        return new ArrayList<>();
    }

    @Override
    public void save(ConfigurationSection config) {
       config.set(getName(), value);
    }

    @Override
    public List<String> getValue() {
        return value;
    }

    @Override
    public ListColoredStringFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getValue(), "&cEMPTY");
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        value = ((GUI)manager.getCache().get(player)).getConditionListWithColor(getEditorName(), "&cEMPTY");
    }

    @Override
    public ListColoredStringFeature clone() {
        ListColoredStringFeature clone = new ListColoredStringFeature(getParent(), getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;

    }

    @Override
    public void askInEditorFirstTime(Player editor, NewGUIManager manager) {
        manager.currentWriting.put(editor, getValue());
        askInEditor(editor, manager);
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        manager.requestWriting.put(editor, getEditorName());
        editor.closeInventory();
        space(editor);
        showEditor(editor, manager);
        space(editor);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        return Optional.empty();
    }

    @Override
    public void addMessageValue(Player editor, NewGUIManager manager, String message) {
        List<String> value = (List<String>) manager.currentWriting.get(editor);
        value.add(message);
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    private void showEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom "+getEditorName()+":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName()+":", true, true, true, true,
                true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
