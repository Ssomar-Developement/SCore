package com.ssomar.score.features.types.list;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public abstract class ListFeatureAbstract<T, Y extends FeatureInterface<List<T>, Y>> extends FeatureAbstract<List<T>, Y> implements FeatureRequireSubTextEditorInEditor {
    private List<T> values;
    private List<T> blacklistedValues;
    private List<T> defaultValue;
    private String featureName;


    public ListFeatureAbstract(FeatureParentInterface parent, String featureName, List<T> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.featureName = featureName;
        reset();
    }

    public ListFeatureAbstract() {
        super(null, null);
        defaultValue = new ArrayList<>();
        featureName = null;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> entries = config.getStringList(this.getName());
        return load(plugin, entries, isPremiumLoading);
    }

    public List<String> load(SPlugin plugin, List<String> entries, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        List<String> forValue = new ArrayList<>();
        List<String> forBlacklistedValues = new ArrayList<>();
        for (String s : entries) {
            // https://discord.com/channels/701066025516531753/1373125335742283819
            if(SCore.isMohist()) s = s.replaceAll("Ã‚Â", "");
            //SsomarDev.testMsg("load: " + s, true);
            if (s.startsWith("!")) {
                s = s.substring(1);
                //SsomarDev.testMsg("blacklisted: " + s, true);
                forBlacklistedValues.add(s);
            } else {
                forValue.add(s);
            }
        }
        values = loadValues(forValue, errors);
        blacklistedValues = loadValues(forBlacklistedValues, errors);

        FeatureReturnCheckPremium<List<T>> checkPremium = checkPremium(featureName, values, Optional.of(defaultValue), isPremiumLoading);
        if (checkPremium.isHasError()) {
            values = checkPremium.getNewValue();
            errors.add(checkPremium.getError());
        }
        return errors;
    }

    public abstract List<T> loadValues(List<String> entries, List<String> errors);

    public abstract String transfromToString(T value);

    @Override
    public void save(ConfigurationSection config) {
        List<T> valuesTyped = new ArrayList<>(values);
        valuesTyped.addAll(blacklistedValues);
        //SsomarDev.testMsg("save deVal s: " + defaultValue.size() + " val s: " + value.size() + " >> " + (defaultValue.containsAll(value)));
        if (isSavingOnlyIfDiffDefault() && new HashSet<>(defaultValue).containsAll(valuesTyped)) {
            //SsomarDev.testMsg("notSaveIfEqualsToDefaultValue: " + this.getName(), true);
            config.set(this.getName(), null);
            return;
        }
        config.set(this.getName(), getCurrentValues());
        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));
    }

    @Override
    public Y initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = TM.g(Text.EDITOR_CURRENTLY_NAME);

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return (Y) this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getCurrentValues(), "&cEMPTY");
    }

    @Override
    public void reset() {
        this.values = new ArrayList<>(defaultValue);
        this.blacklistedValues = new ArrayList<>();
    }

    /* Be careful it returns only whitelist*/
    @Override
    public List<T> getValue() {
        return values;
    }

    @Override
    public List<String> getCurrentValues() {
        List<String> currentValues = new ArrayList<>();
        for (T value : values) {
            currentValues.add(transfromToString(value));
        }
        for (T value : blacklistedValues) {
            currentValues.add("!" + transfromToString(value));
        }
        //SsomarDev.testMsg("currentValues: " + currentValues, true);
        return currentValues;
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        message = message.replace("!", "");
        return verifyMessage(message);
    }

    public abstract Optional<String> verifyMessage(String message);

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        List<String> values = StringConverter.coloredString((List<String>) manager.currentWriting.get(editor));
        load(SCore.plugin, values, this.isRequirePremium());
        manager.requestWriting.remove(editor);
        manager.activeTextEditor.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

}
