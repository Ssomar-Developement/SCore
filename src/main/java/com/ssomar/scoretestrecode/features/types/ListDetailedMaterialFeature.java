package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.MaterialWithGroups;
import com.ssomar.score.utils.NTools;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.editor.Suggestion;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireSubTextEditorInEditor;
import com.ssomar.scoretestrecode.features.FeatureReturnCheckPremium;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
@Setter
public class ListDetailedMaterialFeature extends FeatureAbstract<List<String>, ListDetailedMaterialFeature> implements FeatureRequireSubTextEditorInEditor {

    private List<String> value;
    private List<String> defaultValue;

    private static final String symbolStart = "{";
    private static final String symbolEnd = "}";
    private static final String symbolEquals = ":";
    private static final String symbolSeparator = "\\+";

    private static final Boolean DEBUG = true;

    public ListDetailedMaterialFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = new ArrayList<>();
        for (String s : config.getStringList(this.getName())) {
            s = StringConverter.decoloredString(s.toUpperCase());
            String materialStr = s;

            if (s.contains(symbolStart)) {
                materialStr = s.split("\\" + symbolStart)[0];

                String datas = s.split("\\" + symbolStart)[1].replace(symbolEnd, "");
                for (String data : datas.split(symbolSeparator)) {
                    String[] dataSplit = data.split(symbolEquals);
                    if (dataSplit.length != 2) {
                        errors.add("&cERROR, Couldn't load the The tags value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Example : FURNACE{lit:true}  BEETROOTS{age:3}, List of tags: https://minecraft.fandom.com/wiki/Block_states");
                        continue;
                    }
                }
            }
            if(!MaterialWithGroups.getMaterialWithGroups(materialStr.toUpperCase()).isPresent()){
                errors.add("&cERROR, Couldn't load the Material + GROUPS value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Check the wiki if you want the list of materials and groups");
                continue;
            }
            value.add(s);
        }

        FeatureReturnCheckPremium<List<String>> checkPremium = checkPremium("List of Detailed materials", value, Optional.of(defaultValue), isPremiumLoading);
        if (checkPremium.isHasError()) value = checkPremium.getNewValue();
        return errors;
    }

    /**
     * Return map with material and tags
     **/
    public Map<String, List<Map<String, String>>> extractCondition() {
        Map<String, List<Map<String, String>>> conditions = new HashMap<>();
        for (String s : value) {
            String materialStr = s;
            Map<String, String> tags = new HashMap<>();

            if (s.contains(symbolStart)) {
                materialStr = s.split("\\" + symbolStart)[0];
                String datas = s.split("\\" + symbolStart)[1].replace(symbolEnd, "");
                for (String data : datas.split(symbolSeparator)) {
                    String[] dataSplit = data.split(symbolEquals);
                    tags.put(dataSplit[0], dataSplit[1]);
                }
            }

            if (conditions.containsKey(materialStr.toUpperCase())) {
                conditions.get(materialStr.toUpperCase()).add(tags);
            } else {
                conditions.put(materialStr.toUpperCase(), new ArrayList<>(Arrays.asList(tags)));
            }
        }
        return conditions;
    }

    public boolean isValidMaterial(@NotNull Material material, Optional<String> statesStrOpt) {
        Map<String, List<Map<String, String>>> conditions = extractCondition();

        Map<String, String> states = new HashMap<>();
        try {
            SsomarDev.testMsg(">> verif statesStrOpt: "+statesStrOpt.isPresent(), DEBUG);
            if(statesStrOpt.isPresent()) {
                SsomarDev.testMsg(">> verif statesStr: "+statesStrOpt.get(), DEBUG);
                String statesStr = statesStrOpt.get().toUpperCase();
                if (statesStr.contains("[")) {
                    /* States are store like that TORCH[STATE1=VALUE1,STATE2=VALUE2] */

                    String[] spliter1 = statesStr.split("\\]");
                    String[] spliter2 = spliter1[0].split("\\[");

                    String[] spliterStates = spliter2[1].split("\\,");

                    for (String state : spliterStates) {
                        String[] spliterState = state.split("\\=");
                        SsomarDev.testMsg(">> spliterState: "+spliterState[0]+"="+spliterState[1], DEBUG);
                        states.put(spliterState[0].toUpperCase(), spliterState[1].toUpperCase());
                    }
                }
            }
        } catch (Exception ignored) {}

        for (String mat : conditions.keySet()) {
            if (MaterialWithGroups.verif(material, mat)) {
                List<Map<String, String>> tagsList = conditions.get(mat);
                if (tagsList.isEmpty()) return true;
                for (Map<String, String> tags : tagsList) {
                    boolean invalid = false;
                    if(tags.isEmpty()) return true;
                    else {
                        for (String key : tags.keySet()) {
                            key = key.toUpperCase();
                            if(states.containsKey(key)) {
                                if(!states.get(key).equals(tags.get(key).toUpperCase())) {
                                    invalid = true;
                                    break;
                                }
                            }
                            else invalid = true;

                            if (invalid) break;
                        }

                        if (invalid) continue;

                        return true;
                    }
                }
            }
        }
        return false;
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
    public ListDetailedMaterialFeature initItemParentEditor(GUI gui, int slot) {
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
    public ListDetailedMaterialFeature clone() {
        ListDetailedMaterialFeature clone = new ListDetailedMaterialFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        String s = StringConverter.decoloredString(message);
        String entityTypeStr = s;

        if (s.contains(symbolStart)) {
            entityTypeStr = s.split("\\" + symbolStart)[0];
                String datas = s.split("\\" + symbolStart)[1].replace(symbolEnd, "");
                for (String data : datas.split(symbolSeparator)) {
                    String[] dataSplit = data.split(symbolEquals);
                    if (dataSplit.length != 2) {
                        return Optional.of("&4&l[ERROR] &cThe message you entered contains an invalid format  &6>> Example : FURNACE{lit:true}  BEETROOTS{age:3}, List of tags: https://minecraft.fandom.com/wiki/Block_states and &7(Check the wiki if you want more examples)");
                    }
                }
        }
        if(!MaterialWithGroups.getMaterialWithGroups(entityTypeStr.toUpperCase()).isPresent()){
            return Optional.of("&4&l[ERROR] &cThe message you entered contains an invalid Material + GROUPS ! (Check the wiki if you want the list)");
        }

        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        return value;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        return new ArrayList<>();
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
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
