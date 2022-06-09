package com.ssomar.scoretestrecode.features.custom.commands.player;

import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommandManager;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.commands.CommandsEditor;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireMultipleMessageInEditor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class PlayerCommandsFeature extends FeatureAbstract<List<String>, PlayerCommandsFeature> implements FeatureRequireMultipleMessageInEditor {

    private List<String> value;

    public PlayerCommandsFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = PlayerCommandManager.getInstance().getCommands(plugin, config.getStringList(getName()), errors, getParent().getParentInfo());
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), value);
    }

    @Override
    public List<String> getValue() {
        return value;
    }

    @Override
    public PlayerCommandsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Your "+getEditorName()+": ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        List<String> update = new ArrayList<>();
        if(value.size() > 10) {
            for(int i = 0; i < 10; i++) update.add(value.get(i));
            update.add("&6... &e" + value.size() + " &6commands");
        }
        else update.addAll(value);
        for(int i = 0; i < update.size(); i++) {
            String command = update.get(i);
            if(command.length() > 40) command = command.substring(0, 39) + "...";
            update.set(i, command);
        }
        gui.updateConditionList(getEditorName(), update, "&cEMPTY");
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {}

    @Override
    public PlayerCommandsFeature clone() {
        PlayerCommandsFeature clone = new PlayerCommandsFeature(getParent(), this.getName(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = new ArrayList<>();
    }

    @Override
    public void askInEditorFirstTime(Player editor, NewGUIManager manager) {
        List<SCommand> commands = new ArrayList<>();
        for (PlayerCommand command : PlayerCommandManager.getInstance().getCommands()) {
            commands.add(command);
        }
        CommandsEditor.getInstance().start(editor, value, commands);
        askInEditor(editor, manager);
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        manager.requestWriting.put(editor, getEditorName());
        CommandsEditor.getInstance().sendEditor(editor);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        return Optional.empty();
    }

    @Override
    public void addMessageValue(Player editor, NewGUIManager manager, String message) {}


    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        value = (List<String>) CommandsEditor.getInstance().finish(editor);
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
