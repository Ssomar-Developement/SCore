package com.ssomar.score.features.types.list;

import com.ssomar.executableblocks.executableblocks.ExecutableBlockObject;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlockPlacedInterface;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.usedapi.ItemsAdderAPI;
import com.ssomar.score.utils.emums.MaterialWithGroups;
import com.ssomar.score.utils.strings.StringConverter;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
public class ListDetailedMaterialFeature extends ListFeatureAbstract<String, ListDetailedMaterialFeature> implements Serializable {

    private static final String symbolStart = "{";
    private static final String symbolEnd = "}";
    private static final String symbolEquals = ":";
    private static final String symbolSeparator = "\\+";
    private static final Boolean DEBUG = false;
    private List<String> listOfCustomBlocksPluginSupported;

    /* If it checks blocks tags or not, if not it checks item tags */
    private boolean forBlocks;

    public ListDetailedMaterialFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue, boolean forBlocks) {
        super(parent, name, "List of Materials", editorName, editorDescription, editorMaterial, defaultValue, requirePremium, notSaveIfEqualsToDefaultValue);
        this.listOfCustomBlocksPluginSupported = new ArrayList<>();
        if (SCore.hasItemsAdder) listOfCustomBlocksPluginSupported.add("ITEMSADDER");
        if (SCore.hasExecutableBlocks) listOfCustomBlocksPluginSupported.add("EXECUTABLEBLOCKS");
        //if(SCore.hasOraxen) listOfCustomBlocksPluginSupported.add("ORAXEN");
        this.forBlocks = forBlocks;
        reset();
    }

    @Override
    public List<String> loadValues(List<String> entries, List<String> errors) {
        List<String> values = new ArrayList<>();
        for (String s : entries) {
            s = StringConverter.decoloredString(s.toUpperCase());
            String materialStr = s;

            boolean isCustomBlock = false;
            for (String customPlugin : listOfCustomBlocksPluginSupported) {
                if (materialStr.startsWith(customPlugin)) {
                    values.add(s);
                    isCustomBlock = true;
                    break;
                }
            }
            if (isCustomBlock) continue;

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

            if (!MaterialWithGroups.getMaterialWithGroups(materialStr.toUpperCase()).isPresent()) {
                errors.add("&cERROR, Couldn't load the Material + GROUPS value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Check the wiki if you want the list of materials and groups");
                continue;
            }
            values.add(s);
        }

        return values;
    }

    @Override
    public String transfromToString(String value) {
        return value;
    }

    /**
     * Return map with material and tags
     **/
    public Map<String, List<Map<String, String>>> extractConditions(List<String> values) {
        Map<String, List<Map<String, String>>> conditions = new HashMap<>();
        for (String s : values) {
            String materialStr = s;
            Map<String, String> tags = new HashMap<>();

            if (s.contains(symbolStart)) {
                materialStr = s.split("\\" + symbolStart)[0];
                String datas = s.split("\\" + symbolStart)[1].replace(symbolEnd, "");
                for (String data : datas.split(symbolSeparator)) {
                    String[] dataSplit = data.split(symbolEquals);
                    tags.put(dataSplit[0].toUpperCase(), dataSplit[1]);
                }
            }

            if (conditions.containsKey(materialStr.toUpperCase())) {
                conditions.get(materialStr.toUpperCase()).add(tags);
            } else {
                conditions.put(materialStr.toUpperCase(), new ArrayList<>(Collections.singletonList(tags)));
            }
        }
        return conditions;
    }

    public boolean isValidMaterial(@NotNull Material material, Optional<String> statesStrOpt) {
        boolean forValuesBool = isValidMaterial(true, getValues(), material, statesStrOpt);
        SsomarDev.testMsg(">> verif forValuesBool: " + forValuesBool, DEBUG);
        boolean forBlacklistValuesBool = isValidMaterial(false, getBlacklistedValues(), material, statesStrOpt);
        SsomarDev.testMsg(">> verif forBlacklistValuesBool: " + forBlacklistValuesBool, DEBUG);
        return forValuesBool && !forBlacklistValuesBool;
    }

    public boolean isValidMaterial(boolean ifEmpty, List<String> references, @NotNull Material material, Optional<String> statesStrOpt) {
        Map<String, List<Map<String, String>>> conditions = extractConditions(references);
        if (conditions.isEmpty()) return ifEmpty;

        String symbolStart = "{";
        String symbolStartSplit = "\\{";
        String symbolEnd = "}";
        String symbolEquals = ":";
        String symbolSeparator = ",";

        if (forBlocks) {
            symbolStart = "[";
            symbolStartSplit = "\\[";
            symbolEnd = "]";
            symbolEquals = "=";
            symbolSeparator = ",";
        }

        // TODO PROBLEM FOR ITEM TAGS IT WORKS ONLY WITH CUSTOMODELDATA
        Map<String, String> states = new HashMap<>();
        try {
            SsomarDev.testMsg(">> verif statesStrOpt: " + statesStrOpt.isPresent(), DEBUG);
            if (statesStrOpt.isPresent()) {
                SsomarDev.testMsg(">> verif statesStr: " + statesStrOpt.get(), DEBUG);
                String statesStr = statesStrOpt.get().toUpperCase();
                if (statesStr.contains(symbolStart)) {
                    /* States are store like that TORCH[STATE1=VALUE1,STATE2=VALUE2] */

                    String[] spliter1 = statesStr.split(symbolEnd);
                    String[] spliter2 = spliter1[0].split(symbolStartSplit);

                    SsomarDev.testMsg(">> spliter2: " + spliter2[1], DEBUG);

                    String[] spliterStates = spliter2[1].split(symbolSeparator);

                    for (String state : spliterStates) {
                        String[] spliterState = state.split(symbolEquals);
                        SsomarDev.testMsg(">> spliterState: " + spliterState[0] + "=" + spliterState[1], DEBUG);
                        states.put(spliterState[0].toUpperCase(), spliterState[1].toUpperCase());
                    }
                }
            }
        } catch (Exception ignored) {
            //ignored.printStackTrace();
        }

        for (String mat : conditions.keySet()) {
            if (MaterialWithGroups.verif(material, mat)) {
                SsomarDev.testMsg(">> verif mat: " + mat, DEBUG);
                List<Map<String, String>> tagsList = conditions.get(mat);
                SsomarDev.testMsg(">> verif tagsList empty ?: " + tagsList.isEmpty(), DEBUG);
                if (tagsList.isEmpty()) return true;
                for (Map<String, String> tags : tagsList) {
                    boolean invalid = false;
                    if (tags.isEmpty()) return true;
                    else {
                        for (String key : tags.keySet()) {
                            key = key.toUpperCase();
                            if (states.containsKey(key)) {
                                if (!states.get(key).equalsIgnoreCase(tags.get(key))) {
                                    invalid = true;
                                    break;
                                }
                            } else invalid = true;

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


    /* Plugin name - list of ID */
    public Map<String, List<String>> extractCustomBlocksConditions(List<String> values) {
        Map<String, List<String>> conditions = new HashMap<>();
        for (String s : values) {
            String materialStr = s;
            SsomarDev.testMsg(">> materialStr: " + materialStr, DEBUG);
            for (String customPlugin : listOfCustomBlocksPluginSupported) {
                if (materialStr.startsWith(customPlugin)) {
                    if (conditions.containsKey(customPlugin))
                        conditions.get(customPlugin).add(materialStr.split(":")[1]);
                    else
                        conditions.put(customPlugin, new ArrayList<>(Collections.singletonList(materialStr.split(":")[1])));
                    break;
                }
            }
        }
        return conditions;
    }

    public boolean isValidCustomBlockOnly(@NotNull Block block) {
        boolean forValuesBool = isValidCustomBlock(true, getValues(), block);
        SsomarDev.testMsg(">> CUSTOMBLOCK verif forValuesBool: " + forValuesBool, DEBUG);

        boolean forBlacklistValuesBool = isValidCustomBlock(false, getBlacklistedValues(), block);
        SsomarDev.testMsg(">> CUSTOMBLOCK verif forBlacklistValuesBool: " + forBlacklistValuesBool, DEBUG);

        return forValuesBool && !forBlacklistValuesBool;
    }

    public boolean isValidCustomBlock(boolean ifEmpty, List<String> references, @NotNull Block block) {
        Map<String, List<String>> conditions = extractCustomBlocksConditions(references);
        if (conditions.isEmpty()) return ifEmpty;
        for (String customPlugin : listOfCustomBlocksPluginSupported) {
            SsomarDev.testMsg(">> verif customPlugin: " + customPlugin, DEBUG);
            if (conditions.containsKey(customPlugin)) {
                SsomarDev.testMsg(">> verif conditions: YES ", DEBUG);
                if (customPlugin.equals("ITEMSADDER") && SCore.hasItemsAdder) {
                    Optional<String> customOpt = ItemsAdderAPI.getCustomBlockID(block);
                    SsomarDev.testMsg(">> IA customOpt: " + customOpt.isPresent(), DEBUG);
                    if (customOpt.isPresent()) {
                        SsomarDev.testMsg(">> IA customOpt: " + customOpt.get(), DEBUG);
                        for (String id : conditions.get(customPlugin)) {
                            SsomarDev.testMsg(">> IA id: " + id, DEBUG);
                            if (customOpt.get().equalsIgnoreCase(id)) return true;
                        }
                    }
                } else if (customPlugin.equals("EXECUTABLEBLOCKS") && SCore.hasExecutableBlocks) {
                    Optional<ExecutableBlockPlacedInterface> customOpt = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getExecutableBlockPlaced(block);
                    SsomarDev.testMsg(">> EB customOpt: " + customOpt.isPresent(), DEBUG);
                    if (customOpt.isPresent()) {
                        SsomarDev.testMsg(">> EB customOpt: " + customOpt.get().getExecutableBlockID(), DEBUG);
                        for (String id : conditions.get(customPlugin)) {
                            SsomarDev.testMsg(">> EB id: " + id, DEBUG);
                            if (customOpt.get().getExecutableBlockID().equalsIgnoreCase(id)) return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean verifBlock(@NotNull Block block){
        return verifBlock(block, null, null);
    }

    public boolean verifBlock(@NotNull Block block, @Nullable Material material, @Nullable Optional<String> statesStrOpt){
        if(material == null) material = block.getType();
        if(statesStrOpt == null) {
            if(!SCore.is1v12Less()) statesStrOpt = Optional.of(block.getBlockData().getAsString(true));
            else statesStrOpt = Optional.empty();
        }

        boolean forValuesBoolMat = isValidMaterial(getValue().isEmpty(), getValues(), material, statesStrOpt);
        SsomarDev.testMsg(">> verif forValuesBool: " + forValuesBoolMat, DEBUG);

        boolean forBlacklistValuesBoolMat = isValidMaterial(false, getBlacklistedValues(), material, statesStrOpt);
        SsomarDev.testMsg(">> verif forBlacklistValuesBool: " + forBlacklistValuesBoolMat, DEBUG);


        boolean forValuesBool = isValidCustomBlock(getValue().isEmpty(), getValues(), block);
        SsomarDev.testMsg(">> CUSTOMBLOCK verif forValuesBool: " + forValuesBool, DEBUG);

        boolean forBlacklistValuesBool = isValidCustomBlock(false, getBlacklistedValues(), block);
        SsomarDev.testMsg(">> CUSTOMBLOCK verif forBlacklistValuesBool: " + forBlacklistValuesBool, DEBUG);

        return (forValuesBool || forValuesBoolMat ) && (!forBlacklistValuesBool && !forBlacklistValuesBoolMat);
    }

    public boolean isValidCustomItemOnly(@NotNull ItemStack item) {
        boolean forValuesBool = isValidCustomItem(true, getValues(), item);
        boolean forBlacklistValuesBool = isValidCustomItem(false, getBlacklistedValues(), item);
        return forValuesBool && !forBlacklistValuesBool;
    }

    public boolean isValidCustomItem(boolean ifEmpty, List<String> references, @NotNull ItemStack item) {
        Map<String, List<String>> conditions = extractCustomBlocksConditions(references);
        if (conditions.isEmpty()) return ifEmpty;
        for (String customPlugin : listOfCustomBlocksPluginSupported) {
            SsomarDev.testMsg(">> verif customPlugin: " + customPlugin, DEBUG);
            if (conditions.containsKey(customPlugin)) {
                SsomarDev.testMsg(">> verif conditions: YES ", DEBUG);
                if (customPlugin.equals("ITEMSADDER") && SCore.hasItemsAdder) {
                    Optional<String> customOpt = ItemsAdderAPI.getCustomItemID(item);
                    SsomarDev.testMsg(">> customOpt: " + customOpt.isPresent(), DEBUG);
                    if (customOpt.isPresent()) {
                        SsomarDev.testMsg(">> customOpt: " + customOpt.get(), DEBUG);
                        for (String id : conditions.get(customPlugin)) {
                            SsomarDev.testMsg(">> id: " + id, DEBUG);
                            if (customOpt.get().equalsIgnoreCase(id)) return true;
                        }
                    }
                }
            } else if (customPlugin.equals("EXECUTABLEBLOCKS") && SCore.hasExecutableBlocks) {
                ExecutableBlockObject customOpt = (ExecutableBlockObject) ExecutableBlocksAPI.getExecutableBlockObject(item);
                if (!customOpt.isValid()) continue;
                for (String id : conditions.get(customPlugin)) {
                    SsomarDev.testMsg(">> id: " + id, DEBUG);
                    if (customOpt.getConfig().getId().equalsIgnoreCase(id)) return true;
                }

            }
        }

        return false;
    }

    public boolean verifItem(@NotNull ItemStack item){
        Material material = item.getType();
        Optional<String> statesStrOpt = Optional.empty();
        if(statesStrOpt == null) {
            String str = "";
            if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
                NBTItem nbti = new NBTItem(item);
                SsomarDev.testMsg("isValid DetailedItems >> "+nbti.toString(), true);
                str = nbti.toString();
            }
            statesStrOpt = Optional.of(str);
        }

        boolean forValuesBoolMat = isValidMaterial(getValue().isEmpty(), getValues(), material, statesStrOpt);
        SsomarDev.testMsg(">> verif forValuesBool: " + forValuesBoolMat, DEBUG);

        boolean forBlacklistValuesBoolMat = isValidMaterial(false, getBlacklistedValues(), material, statesStrOpt);
        SsomarDev.testMsg(">> verif forBlacklistValuesBool: " + forBlacklistValuesBoolMat, DEBUG);


        boolean forValuesBool = isValidCustomItem(getValue().isEmpty(), getValues(), item);
        SsomarDev.testMsg(">> CUSTOMBLOCK verif forValuesBool: " + forValuesBool, DEBUG);

        boolean forBlacklistValuesBool = isValidCustomItem(getValue().isEmpty(), getBlacklistedValues(), item);
        SsomarDev.testMsg(">> CUSTOMBLOCK verif forBlacklistValuesBool: " + forBlacklistValuesBool, DEBUG);

        return (forValuesBool || forValuesBoolMat ) && (!forBlacklistValuesBool && !forBlacklistValuesBoolMat);
    }

    @Override
    public ListDetailedMaterialFeature clone(FeatureParentInterface newParent) {
        ListDetailedMaterialFeature clone = new ListDetailedMaterialFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue(), isForBlocks());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    @Override
    public Optional<String> verifyMessage(String message) {
        String s = StringConverter.decoloredString(message).replace("!", "");
        String str = s;

        boolean isCustomBlock = false;
        for (String customPlugin : listOfCustomBlocksPluginSupported) {
            if (str.startsWith(customPlugin)) {
                isCustomBlock = true;
                break;
            }
        }
        if (isCustomBlock) return Optional.empty();

        if (s.contains(symbolStart)) {
            str = s.split("\\" + symbolStart)[0];
            String datas = s.split("\\" + symbolStart)[1].replace(symbolEnd, "");
            for (String data : datas.split(symbolSeparator)) {
                String[] dataSplit = data.split(symbolEquals);
                if (dataSplit.length != 2) {
                    return Optional.of("&4&l[ERROR] &cThe message you entered contains an invalid format  &6>> Example : FURNACE{lit:true}  BEETROOTS{age:3}, List of tags: https://minecraft.fandom.com/wiki/Block_states and &7(Check the wiki if you want more examples)");
                }
            }
        }
        if (!MaterialWithGroups.getMaterialWithGroups(str.toUpperCase()).isPresent()) {
            return Optional.of("&4&l[ERROR] &cThe message you entered contains an invalid Material + GROUPS ! (Check the wiki if you want the list)");
        }

        return Optional.empty();
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return new ArrayList<>();
    }

    @Override
    public List<Suggestion> getSuggestions() {
        List<Suggestion> suggestions = new ArrayList<>();
        for (MaterialWithGroups materialWithGroups : MaterialWithGroups.values()) {
            suggestions.add(new Suggestion(materialWithGroups.toString(), "&6[&e" + materialWithGroups + "&6]", "&7Add &e" + materialWithGroups));
        }
        return suggestions;
    }

    @Override
    public String getTips() {
        if (forBlocks) return "&8Example &7&oFURNACE &8- &7&oBEETROOTS{age:3} &8- &7&oITEMSADDER:turquoise_block";
        else return "&8Example &7&oDIAMOND_SWORD &8- &7&oTORCH{CustomModelData:3} &8- &7&oITEMSADDER:ruby_sword";
    }

    @Override
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your detailed " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
