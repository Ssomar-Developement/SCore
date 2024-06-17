package com.ssomar.score.features.custom.recognition;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

@Getter
@Setter
public class ListRecognitionFeature extends FeatureAbstract<List<Recognition>, ListRecognitionFeature> implements FeatureRequireSubTextEditorInEditor {

    private List<Recognition> value;
    private List<Recognition> defaultValue;

    public ListRecognitionFeature(FeatureParentInterface parent, List<Recognition> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = new ArrayList<>();
        for (String s : config.getStringList(this.getName())) {
            s = StringConverter.decoloredString(s);
            try {
                Recognition mat = Recognition.valueOf(s.toUpperCase());
                value.add(mat);
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the Recognition value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Recognition available: MATERIAL, NAME, LORE, HIDE_ATTRIBUTE, By default its HIDE_ATTRIBUTE !");
            }
        }
        FeatureReturnCheckPremium<List<Recognition>> checkPremium = checkPremium("List of Recognitions", value, Optional.of(defaultValue), isPremiumLoading);
        if (checkPremium.isHasError()) value = checkPremium.getNewValue();
        return errors;
    }

    public boolean isClassicRecognition() {
        return value.isEmpty() || value.contains(Recognition.HIDE_ATTRIBUTE);
    }

    public List<Recognition> getCustomRecognitions() {
        List<Recognition> customRecognitions = new ArrayList<>();
        if (value.contains(Recognition.MATERIAL)) customRecognitions.add(Recognition.MATERIAL);
        if (value.contains(Recognition.NAME)) customRecognitions.add(Recognition.NAME);
        if (value.contains(Recognition.LORE)) customRecognitions.add(Recognition.LORE);
        return customRecognitions;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), getCurrentValues());
    }

    @Override
    public List<Recognition> getValue() {
        return value;
    }

    @Override
    public ListRecognitionFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getCurrentValues(), "&cEMPTY");
    }

    @Override
    public ListRecognitionFeature clone(FeatureParentInterface newParent) {
        ListRecognitionFeature clone = new ListRecognitionFeature(newParent, getDefaultValue(), getFeatureSettings());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        message = StringConverter.decoloredString(message);
        try {
            Recognition mat = Recognition.valueOf(message);
            value.add(mat);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a Recognition &6>> Recognition available: MATERIAL, NAME, LORE, HIDE_ATTRIBUTE, By default its HIDE_ATTRIBUTE !");
        }
    }

    @Override
    public List<String> getCurrentValues() {
        List<String> currentValues = new ArrayList<>();
        for (Recognition mat : value) {
            currentValues.add(mat.name());
        }
        return currentValues;
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (EntityDamageEvent.DamageCause mat : EntityDamageEvent.DamageCause.values()) {
            map.put(mat.toString(), new Suggestion(mat + "", "&6[" + "&e" + mat + "&6]", "&7Add &e" + mat));
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public String getTips() {
        return "";
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        value = new ArrayList<>();
        for (String s : (List<String>) manager.currentWriting.get(editor)) {
            s = StringConverter.decoloredString(s);
            try {
                Recognition mat = Recognition.valueOf(s);
                value.add(mat);
            } catch (Exception e) {
            }
        }

        manager.requestWriting.remove(editor);
        manager.activeTextEditor.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }


    @Override
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, true, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
