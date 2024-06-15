package com.ssomar.score.features.types.list;

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
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListDetailedEffectFeature extends FeatureAbstract<List<String>, ListDetailedEffectFeature> implements FeatureRequireSubTextEditorInEditor {

    private static final String symbolNegation = "!";

    private static final Boolean DEBUG = true;
    private List<String> value;
    private List<String> defaultValue;
    private boolean notSaveIfEqualsToDefaultValue;

    public ListDetailedEffectFeature(FeatureParentInterface parent, List<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        reset();
    }

    public List<String> load(SPlugin plugin, List<String> entries, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = new ArrayList<>();
        for (String s : entries) {
            String baseValue = StringConverter.decoloredString(s);
            String checkValue = baseValue;
            if(checkValue.startsWith(symbolNegation)) checkValue = checkValue.substring(1);
            String entityTypeStr = checkValue;

            try {
                PotionEffectType.getByName(entityTypeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                errors.add("&cERROR, Couldn't load the EntityType value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> EntityTypes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html");
                continue;
            }
            value.add(baseValue.toUpperCase());
        }
        FeatureReturnCheckPremium<List<String>> checkPremium = checkPremium("List of Detailed effects", value, Optional.of(defaultValue), isPremiumLoading);
        if (checkPremium.isHasError()) value = checkPremium.getNewValue();
        return errors;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        return this.load(plugin, config.getStringList(this.getName()), isPremiumLoading);
    }

    public List<String> getWhiteListValues() {
        List<String> whiteList = new ArrayList<>();
        for (String s : value) {
            if (!s.startsWith(symbolNegation)) whiteList.add(s);
        }
        return whiteList;
    }

    public List<String> getBlackListValues() {
        List<String> blackList = new ArrayList<>();
        for (String s : value) {
            if (s.startsWith(symbolNegation)) blackList.add(s.substring(1));
        }
        return blackList;
    }

    public boolean isValidEffect(@NotNull PotionEffectType type) {

        List<String> whiteList = getWhiteListValues();
        List<String> blackList = getBlackListValues();


        for (String t : blackList) {
            if (t.toUpperCase().equals(type.getName().toUpperCase())) {
                return false;
            }
        }

        /* empty = accept all */
        if (whiteList.isEmpty()){
            return true;
        }

        for (String t : whiteList) {
            if (t.toUpperCase().equals(type.getName().toUpperCase())) {
               return true;
            }
        }

        return false;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (notSaveIfEqualsToDefaultValue) {
            if (defaultValue.containsAll(value)) {
                config.set(this.getName(), null);
                return;
            }
        }
        config.set(this.getName(), value);
    }

    public List<String> getValues() {
        return value;
    }

    @Override
    public ListDetailedEffectFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getValues(), "&cEMPTY");
    }


    @Override
    public ListDetailedEffectFeature clone(FeatureParentInterface newParent) {
        ListDetailedEffectFeature clone = new ListDetailedEffectFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValues());
        return clone;
    }

    @Override
    public void reset() {
        this.value = new ArrayList<>(defaultValue);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        String s = StringConverter.decoloredString(message);
        if(s.startsWith(symbolNegation)) s = s.substring(1);
        String entityTypeStr = s;

        try {
            PotionEffectType.getByName(entityTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered contains an invalid Effect ! (Check the wiki or spigot if you want the list)");
        }
        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        return value;
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        return new ArrayList<>();
    }

    @Override
    public String getTips() {
        return "&8Example &7&oSPEED";
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        value = (List<String>) manager.currentWriting.get(editor);
        for (int i = 0; i < value.size(); i++) {
            value.set(i, StringConverter.decoloredString(value.get(i)));
        }
        manager.requestWriting.remove(editor);
        manager.activeTextEditor.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }


    @Override
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        space(playerEditor);
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
