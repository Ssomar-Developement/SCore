package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.editor.Suggestion;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireSubTextEditorInEditor;
import com.ssomar.scoretestrecode.features.FeatureReturnCheckPremium;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class ListMaterialFeature extends FeatureAbstract<List<Material>, ListMaterialFeature> implements FeatureRequireSubTextEditorInEditor {

    private List<Material> value;
    private List<Material> defaultValue;

    public ListMaterialFeature(FeatureParentInterface parent, String name, List<Material> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
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
                Material mat = Material.valueOf(s);
                value.add(mat);
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the Material value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Materials available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
            }
        }
        FeatureReturnCheckPremium<List<Material>> checkPremium = checkPremium("List of Materials", value, Optional.of(defaultValue), isPremiumLoading);
        if (checkPremium.isHasError()) value = checkPremium.getNewValue();
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        List<String> list = new ArrayList<>();
        for (Material material : value) {
            list.add(material.toString());
        }
        config.set(this.getName(), list);
    }

    @Override
    public List<Material> getValue() {
        return value;
    }

    @Override
    public ListMaterialFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getCurrentValues(), "&cEMPTY");
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
    }

    @Override
    public ListMaterialFeature clone() {
        ListMaterialFeature clone = new ListMaterialFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
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
            Material mat = Material.valueOf(message);
            value.add(mat);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a Material &6>> Materials available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
        }
    }

    @Override
    public List<String> getCurrentValues() {
        List<String> currentValues = new ArrayList<>();
        for (Material mat : value) {
            currentValues.add(mat.name());
        }
        return currentValues;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (Material mat : Material.values()) {
            map.put(mat.toString(), new Suggestion(mat+"", "&6[" + "&e" + mat + "&6]", "&7Add &e" + mat));
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        value = new ArrayList<>();
        for (String s : (List<String>) manager.currentWriting.get(editor)) {
            s = StringConverter.decoloredString(s);
            try {
                Material mat = Material.valueOf(s);
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
