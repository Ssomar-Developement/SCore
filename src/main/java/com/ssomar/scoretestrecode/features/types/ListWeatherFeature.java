package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireMultipleMessageInEditor;
import com.ssomar.scoretestrecode.features.FeatureReturnCheckPremium;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.menu.conditions.RequestMessage.space;

@Getter
@Setter
public class ListWeatherFeature extends FeatureAbstract<List<String>, ListWeatherFeature> implements FeatureRequireMultipleMessageInEditor {

    private List<String> value;
    private List<String> defaultValue;

    public ListWeatherFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = config.getStringList(this.getName());
        for (int i = 0; i < value.size(); i++) {
            if(isValidWeather(value.get(i).toUpperCase())) {
                value.set(i, value.get(i).toUpperCase());
            } else {
                errors.add("&cERROR, Couldn't load the Weather value of " + this.getName() + " from config, value: " + value.get(i)+ " &7&o"+getParent().getParentInfo()+" &6>> Weather available: RAIN, CLEAR, STORM");
                continue;
            }
            value.set(i, StringConverter.decoloredString(value.get(i)));
        }
        FeatureReturnCheckPremium<List<String>> checkPremium = checkPremium("List of Weather", value, Optional.of(defaultValue), isPremiumLoading);
        if(checkPremium.isHasError()) value = checkPremium.getNewValue();
        return errors;
    }

    public boolean isValidWeather(String value) {
        List<String> weathers = new ArrayList<>();
        weathers.add("RAIN");
        weathers.add("STORM");
        weathers.add("CLEAR");

        return weathers.contains(value);
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
    public ListWeatherFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getValue(), "&cEMPTY");
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        value = ((GUI) manager.getCache().get(player)).getConditionListWithColor(getEditorName(), "&cEMPTY");
    }

    @Override
    public ListWeatherFeature clone() {
        ListWeatherFeature clone = new ListWeatherFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
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
        if(isValidWeather(message.toUpperCase())) {
            return Optional.empty();
        } else {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a weather. &7RAIN, CLEAR or STORM");
        }
    }

    @Override
    public void addMessageValue(Player editor, NewGUIManager manager, String message) {
        List<String> value = (List<String>) manager.currentWriting.get(editor);
        value.add(StringConverter.decoloredString(message));
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                askInEditor(editor, manager);
            }
        };
        runnable.runTask(SCore.plugin);
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        value = (List<String>) manager.currentWriting.get(editor);
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    private void showEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();
        suggestions.put("STORM", "&7STORM");
        suggestions.put("RAIN", "&bRAIN");
        suggestions.put("CLEAR", "&aCLEAR");

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, true, "Possible weather: ", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
