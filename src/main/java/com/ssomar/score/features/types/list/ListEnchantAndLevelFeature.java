package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireSubTextEditorInEditor;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class ListEnchantAndLevelFeature extends FeatureAbstract<Map<Enchantment, Integer>, ListEnchantAndLevelFeature> implements FeatureRequireSubTextEditorInEditor {

    private Map<Enchantment, Integer> value;
    private Map<Enchantment, Integer> defaultValue;
    private boolean notSaveIfEqualsToDefaultValue;

    public ListEnchantAndLevelFeature(FeatureParentInterface parent, Map<Enchantment, Integer> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = transformEnchants(config.getStringList(this.getName()), errors);
        return errors;
    }

    public Map<Enchantment, Integer> transformEnchants(List<String> enchantsConfig, List<String> errorList) {
        Map<Enchantment, Integer> result = new HashMap<>();
        for (String s : enchantsConfig) {
            Enchantment enchant;
            int level;
            String[] decomp;

            boolean error = false;
            if (s.contains(":")) {
                decomp = s.split(":");
                try {
                    enchant = Enchantment.getByName(decomp[0]);
                    if (enchant == null) {
                        error = true;
                    } else {
                        level = Integer.parseInt(decomp[1]);
                        result.put(enchant, level);
                    }
                } catch (Exception e) {
                    error = true;
                }
                if (error)
                    errorList.add("&cERROR, Couldn't load the Enchant with level value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> correct form > ENCHANTMENT:LEVEL  example> POWER:1 !");
            }
        }
        return result;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfEqualsToDefaultValue && value.isEmpty()) return;
        config.set(this.getName(), this.getCurrentValues());
    }

    public Map<Enchantment, Integer> getValues() {
        return value;
    }

    @Override
    public ListEnchantAndLevelFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), this.getCurrentValues(), "&cEMPTY");
    }

    @Override
    public ListEnchantAndLevelFeature clone(FeatureParentInterface newParent) {
        ListEnchantAndLevelFeature clone = new ListEnchantAndLevelFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValues());
        return clone;
    }

    @Override
    public void reset() {
        this.value = new HashMap<>(defaultValue);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        String s = StringConverter.decoloredString(message);
        String[] decomp;
        Enchantment enchant;
        int level;
        boolean error = false;
        if (s.contains(":")) {
            decomp = s.split(":");
            try {
                enchant = Enchantment.getByName(decomp[0]);
                if (enchant == null) {
                    error = true;
                } else {
                    Integer.parseInt(decomp[1]);
                }
            } catch (Exception e) {
                error = true;
            }
            if (error)
                return Optional.of("&4&l[ERROR] &cThe message you entered &8(&7" + s + "&8)&c is not an enchant with level &6>> correct form > ENCHANTMENT:LEVEL  example> POWER:1 !");
        }
        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> entry : value.entrySet()) {
            result.add(entry.getKey().getName() + ":" + entry.getValue());
        }
        return result;
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (Enchantment enchant : Enchantment.values()) {
            map.put(enchant.getName(), new Suggestion(enchant.getName() + ":1", "&6[" + "&e" + enchant.getName() + "&6]", "&7Add &e" + enchant.getName()));
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public String getTips() {
        return "&8Example &7&oPOWER:1";
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        List<String> preResult = (List<String>) manager.currentWriting.get(editor);
        for (int i = 0; i < preResult.size(); i++) {
            preResult.set(i, StringConverter.decoloredString(preResult.get(i)));
        }
        value = transformEnchants(preResult, new ArrayList<>());
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
