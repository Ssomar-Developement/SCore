package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
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
public class ListDetailedEntityFeature extends FeatureAbstract<List<String>, ListDetailedEntityFeature> implements FeatureRequireSubTextEditorInEditor {

    private List<String> value;
    private List<String> defaultValue;
    private Optional<List<Suggestion>> suggestions;

    private static final String symbolStart = "{";
    private static final String symbolEnd= "}";
    private static final String symbolEquals = ":";
    private static final String symbolSeparator = "\\+";

    private static final Boolean DEBUG = true;

    public ListDetailedEntityFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, Optional<List<Suggestion>> suggestions) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.suggestions = suggestions;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = new ArrayList<>();
        for(String s : config.getStringList(this.getName())){
            s = StringConverter.decoloredString(s);
            String entityTypeStr = s;

            if(s.contains(symbolStart)){
                entityTypeStr = s.split("\\"+symbolStart)[0];
                if(SCore.hasNBTAPI) {
                    String datas = s.split("\\" + symbolStart)[1].replace(symbolEnd, "");
                    for (String data : datas.split(symbolSeparator)) {
                        String[] dataSplit = data.split(symbolEquals);
                        if (dataSplit.length != 2) {
                            errors.add("&cERROR, Couldn't load the The tags value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Example : ZOMBIE{CustomName:\"My name\"}   PIG{Invulnerable:1} ");
                            continue;
                        }
                    }
                }
                else errors.add("&cERROR, Couldn't load the The tags value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Because it requires the plugin NBTAPI ");
            }

            try {
                EntityType.valueOf(entityTypeStr.toUpperCase());
            }catch (IllegalArgumentException e) {
                errors.add("&cERROR, Couldn't load the EntityType value of " + this.getName() + " from config, value: " + s+ " &7&o"+getParent().getParentInfo()+" &6>> EntityTypes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html");
                continue;
            }
            value.add(s);
        }
        FeatureReturnCheckPremium<List<String>> checkPremium = checkPremium("List of Detailed entities", value, Optional.of(defaultValue), isPremiumLoading);
        if(checkPremium.isHasError()) value = checkPremium.getNewValue();
        return errors;
    }

    /** Return map with entitytype and tags **/
    public Map<EntityType, List<Map<String, String>>> extractCondition(){
        Map<EntityType, List<Map<String, String>>> conditions = new HashMap<>();
        for(String s : value){
            String entityTypeStr = s;
            EntityType type;
            Map<String, String> tags = new HashMap<>();

            if(s.contains(symbolStart)){
                entityTypeStr = s.split("\\"+symbolStart)[0];
                String datas = s.split("\\"+symbolStart)[1].replace(symbolEnd, "");
                for(String data : datas.split(symbolSeparator)){
                    String[] dataSplit = data.split(symbolEquals);
                    tags.put(dataSplit[0], dataSplit[1]);
                }
            }
            type = EntityType.valueOf(entityTypeStr.toUpperCase());

            if(conditions.containsKey(type)){
                conditions.get(type).add(tags);
            }
            else {
                conditions.put(type, new ArrayList<>(Arrays.asList(tags)));
            }
        }
        return conditions;
    }

    public boolean isValidEntity(@NotNull Entity entity){
        Map<EntityType, List<Map<String, String>>> conditions = extractCondition();
        EntityType type = entity.getType();
        for(EntityType t : conditions.keySet()){
            if(t.equals(type)){
                List<Map<String, String>> tagsList = conditions.get(t);
                if(tagsList.isEmpty()) return true;
                for(Map<String, String> tags : tagsList){
                    boolean invalid = false;
                    if(tags.isEmpty()) return true;
                    else{
                        NBTEntity nbtent = new NBTEntity(entity);
                        for(String key : tags.keySet()){
                            String value = tags.get(key);
                            NBTType nbtType = nbtent.getType(key);

                            SsomarDev.testMsg("VERIF key: " + key + " value: " + value+ " type: " + type, DEBUG);

                            switch (nbtType) {
                                /* The tag was not found */
                                case NBTTagEnd:
                                    return false;
                                case NBTTagByte:
                                    if(value.equals("0") || value.equals("1")){
                                        SsomarDev.testMsg("Byte: " + nbtent.getByte(key), DEBUG);
                                        if(nbtent.getByte(key) != Byte.parseByte(value)) invalid = true;
                                    }
                                    break;
                                case NBTTagShort:
                                    break;
                                case NBTTagInt:
                                    Optional<Integer> optInt = NTools.getInteger(value);
                                    if(optInt.isPresent()){
                                        SsomarDev.testMsg("Int: " + nbtent.getInteger(key), DEBUG);
                                        if(nbtent.getInteger(key) != optInt.get()) invalid = true;
                                    }
                                    break;
                                case NBTTagLong:
                                    Optional<Long> optLong = NTools.getLong(value);
                                    if(optLong.isPresent()){
                                        SsomarDev.testMsg("Long: " + nbtent.getLong(key), DEBUG);
                                        if(nbtent.getLong(key) != optLong.get()) invalid = true;
                                    }
                                    break;
                                case NBTTagFloat:
                                    Optional<Float> optFloat = NTools.getFloat(value);
                                    if(optFloat.isPresent()){
                                        SsomarDev.testMsg("Float: " + nbtent.getFloat(key), DEBUG);
                                        if(nbtent.getFloat(key) != optFloat.get()) invalid = true;
                                    }
                                    break;
                                case NBTTagDouble:
                                    Optional<Double> optDouble = NTools.getDouble(value);
                                    if(optDouble.isPresent()){
                                        SsomarDev.testMsg("Double: " + nbtent.getDouble(key),DEBUG);
                                        if(nbtent.getDouble(key) != optDouble.get()) invalid = true;
                                    }
                                    break;
                                case NBTTagByteArray:
                                    break;
                                case NBTTagIntArray:
                                    break;
                                case NBTTagString:
                                    if(value.toLowerCase().equals("true") || value.toLowerCase().equals("false")){
                                        SsomarDev.testMsg("Boolean: " + nbtent.getBoolean(key), DEBUG);
                                        if(nbtent.getBoolean(key) != Boolean.parseBoolean(value)) invalid = true;
                                    }
                                    else{
                                        if(key.equalsIgnoreCase(("CustomName"))){
                                            String customName = entity.getCustomName();
                                            SsomarDev.testMsg("String: " + customName, DEBUG);
                                            if (!StringConverter.decoloredString(customName).equals(value)) invalid = true;
                                        }
                                        else {
                                            SsomarDev.testMsg("String: " + nbtent.getString(key), DEBUG);
                                            if (!nbtent.getString(key).contains("\"" + value + "\"")) invalid = true;
                                        }
                                    }
                                    break;
                                case NBTTagList:
                                    break;
                                case NBTTagCompound:
                                    break;
                            }
                            if(invalid) break;
                        }
                        if(invalid) continue;

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
    public ListDetailedEntityFeature initItemParentEditor(GUI gui, int slot) {
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
    public ListDetailedEntityFeature clone() {
        ListDetailedEntityFeature clone = new ListDetailedEntityFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), suggestions);
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

        if(s.contains(symbolStart)){
            entityTypeStr = s.split("\\"+symbolStart)[0];
            if(SCore.hasNBTAPI) {
                String datas = s.split("\\" + symbolStart)[1].replace(symbolEnd, "");
                for (String data : datas.split(symbolSeparator)) {
                    String[] dataSplit = data.split(symbolEquals);
                    if (dataSplit.length != 2) {
                        return Optional.of("&4&l[ERROR] &cThe message you entered contains an invalid format  &6>> Example : ZOMBIE{CustomName:\"My name\"}   PIG{Invulnerable:1} &7(Check the wiki if you want more examples)");
                    }
                }
            }
            else return Optional.of("&4&l[ERROR] &cThe message you entered contains Tags, but it requires the plugin NBTAPI ! so please install it or don't enter tags !");
        }

        try {
            EntityType.valueOf(entityTypeStr.toUpperCase());
        }catch (IllegalArgumentException e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered contains an invalid EntityType ! (Check the wiki if you want the list)");
        }
        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        return value;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        if(suggestions.isPresent()) return suggestions.get();
        return new ArrayList<>();
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        value = (List<String>) manager.currentWriting.get(editor);
        for(int i = 0; i < value.size(); i++) {
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
