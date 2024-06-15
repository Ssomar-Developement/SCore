package com.ssomar.score.features.custom.materialwithgroupsandtags.group;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.materialwithgroupsandtags.materialandtags.MaterialAndTagsFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.MaterialWithGroups;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class MaterialAndTagsGroupFeature extends FeatureWithHisOwnEditor<MaterialAndTagsGroupFeature, MaterialAndTagsGroupFeature, MaterialAndTagsGroupFeatureEditor, MaterialAndTagsGroupFeatureEditorManager> implements FeaturesGroup<MaterialAndTagsFeature> {

    private static final String symbolStart = "{";
    private static final String symbolEnd = "}";
    private static final String symbolEquals = ":";
    private static final String symbolSeparator = "\\+";
    private final static boolean DEBUG = false;
    private Map<String, MaterialAndTagsFeature> materialAndTags;
    private boolean acceptAir;
    private boolean acceptItems;
    private boolean acceptBlocks;
    private boolean notSaveIfEmpty;

    public MaterialAndTagsGroupFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings, boolean acceptAir, boolean acceptItems, boolean acceptBlocks, boolean notSaveIfEmpty) {
        super(parent, featureSettings);
        this.acceptAir = acceptAir;
        this.acceptItems = acceptItems;
        this.acceptBlocks = acceptBlocks;
        this.notSaveIfEmpty = notSaveIfEmpty;
        reset();
    }

    @Override
    public void reset() {
        this.materialAndTags = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                MaterialAndTagsFeature attribute = new MaterialAndTagsFeature(this, attributeID, acceptAir, acceptItems, acceptBlocks);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                materialAndTags.put(attributeID, attribute);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfEmpty && materialAndTags.size() == 0) {
            return;
        }
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : materialAndTags.keySet()) {
            materialAndTags.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public MaterialAndTagsGroupFeature getValue() {
        return this;
    }

    @Override
    public MaterialAndTagsGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oMaterial(s) added: &e" + materialAndTags.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public MaterialAndTagsFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (MaterialAndTagsFeature x : materialAndTags.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public MaterialAndTagsGroupFeature clone(FeatureParentInterface newParent) {
        MaterialAndTagsGroupFeature eF = new MaterialAndTagsGroupFeature(newParent, getFeatureSettings(), acceptAir, acceptItems, acceptBlocks, notSaveIfEmpty);
        HashMap<String, MaterialAndTagsFeature> newMaterialAndTags = new HashMap<>();
        for (String key : materialAndTags.keySet()) {
            newMaterialAndTags.put(key, materialAndTags.get(key).clone(eF));
        }
        eF.setMaterialAndTags(newMaterialAndTags);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(materialAndTags.values());
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if (section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        } else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof MaterialAndTagsGroupFeature && feature.getName().equals(getName())) {
                MaterialAndTagsGroupFeature eF = (MaterialAndTagsGroupFeature) feature;
                eF.setMaterialAndTags(this.getMaterialAndTags());
                break;
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        MaterialAndTagsGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "material";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!materialAndTags.containsKey(id)) {
                MaterialAndTagsFeature eF = new MaterialAndTagsFeature(this, id, acceptAir, acceptItems, acceptBlocks);
                materialAndTags.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, MaterialAndTagsFeature feature) {
        materialAndTags.remove(feature.getId());
    }

    public boolean isValid(Block block) {
        Optional<String> statesStr = Optional.empty();
        if (!SCore.is1v12Less()) {
            statesStr = Optional.ofNullable(block.getBlockData().getAsString(true));
        }
        return isValidMaterial(block.getType(), statesStr);
    }

    /**
     * Return map with Material and tags
     **/
    public Map<String, List<Map<String, String>>> extractCondition() {
        Map<String, List<Map<String, String>>> conditions = new HashMap<>();
        for (MaterialAndTagsFeature feature : materialAndTags.values()) {
            String materialStr = feature.getMaterial().getValue().get();
            Map<String, String> tags = new HashMap<>();

            if (feature.getTags().getValue().isPresent()) {
                String s = feature.getTags().getValue().get();
                if (s.contains(symbolStart)) {
                    String datas = s.split("\\" + symbolStart)[1].replace(symbolEnd, "");
                    for (String data : datas.split(symbolSeparator)) {
                        String[] dataSplit = data.split(symbolEquals);
                        SsomarDev.testMsg("INSERT CDT STATE: " + dataSplit[0] + "=" + dataSplit[1], DEBUG);
                        tags.put(dataSplit[0], dataSplit[1]);
                    }
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
        Map<String, List<Map<String, String>>> conditions = extractCondition();

        Map<String, String> states = new HashMap<>();
        try {
            SsomarDev.testMsg(">> verif statesStrOpt: " + statesStrOpt.isPresent(), DEBUG);
            if (statesStrOpt.isPresent()) {
                SsomarDev.testMsg(">> verif statesStr: " + statesStrOpt.get(), DEBUG);
                String statesStr = statesStrOpt.get().toUpperCase();
                if (statesStr.contains("[")) {
                    /* States are store like that TORCH[STATE1=VALUE1,STATE2=VALUE2] */

                    String[] spliter1 = statesStr.split("]");
                    String[] spliter2 = spliter1[0].split("\\[");

                    String[] spliterStates = spliter2[1].split(",");

                    for (String state : spliterStates) {
                        String[] spliterState = state.split("=");
                        SsomarDev.testMsg(">> spliterState: " + spliterState[0] + "=" + spliterState[1], DEBUG);
                        states.put(spliterState[0].toUpperCase(), spliterState[1].toUpperCase());
                    }
                }
            }
        } catch (Exception ignored) {
        }

        for (String mat : conditions.keySet()) {
            SsomarDev.testMsg("before verif: " + mat, DEBUG);
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
                            String keyUpper = key.toUpperCase();
                            SsomarDev.testMsg(">> verif key: " + key + " contains ? " + (states.containsKey(keyUpper)), DEBUG);
                            if (states.containsKey(keyUpper)) {
                                SsomarDev.testMsg("key value: " + tags.get(key).toUpperCase() + "and real >" + states.get(keyUpper), DEBUG);
                                if (!states.get(keyUpper).equals(tags.get(key).toUpperCase())) {
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
}
