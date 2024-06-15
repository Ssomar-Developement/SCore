package com.ssomar.score.features.custom.materialwithgroupsandtags.materialandtags;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.MaterialWithGroupsFeature;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.MaterialWithGroups;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class MaterialAndTagsFeature extends FeatureWithHisOwnEditor<MaterialAndTagsFeature, MaterialAndTagsFeature, MaterialAndTagsFeatureEditor, MaterialAndTagsFeatureEditorManager> {

    private static final String symbolStart = "{";
    private static final String symbolEnd = "}";
    private static final String symbolEquals = ":";
    private static final String symbolSeparator = "\\+";
    private MaterialWithGroupsFeature material;
    private UncoloredStringFeature tags;
    private String id;
    private boolean acceptAir;
    private boolean acceptItems;
    private boolean acceptBlocks;

    public MaterialAndTagsFeature(FeatureParentInterface parent, String id, boolean acceptAir, boolean acceptItems, boolean acceptBlocks) {
        super(parent, FeatureSettingsSCore.materialAndTags);
        this.id = id;
        this.acceptAir = acceptAir;
        this.acceptItems = acceptItems;
        this.acceptBlocks = acceptBlocks;
        reset();
    }

    @Override
    public void reset() {
        this.material = new MaterialWithGroupsFeature(this, Optional.of(MaterialWithGroups.getMaterialWithGroupsList(acceptAir, acceptItems, acceptBlocks).get(0)), FeatureSettingsSCore.material, acceptAir, acceptItems, acceptBlocks);
        this.tags = new UncoloredStringFeature(this, Optional.empty(), FeatureSettingsSCore.tags, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.material.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.tags.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Material and Tags option because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }


    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.material.save(attributeConfig);
        this.tags.save(attributeConfig);
    }

    @Override
    public MaterialAndTagsFeature getValue() {
        return this;
    }

    @Override
    public MaterialAndTagsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = "&7Material: &e" + material.getValue().get();
        if (tags.getValue().isPresent()) {
            String command = tags.getValue().get();
            if (command.length() > 40) command = command.substring(0, 39) + "...";
            finalDescription[finalDescription.length - 2] = "&7Tags: &e" + command;
        } else finalDescription[finalDescription.length - 2] = "&7Tags: &cNO TAGS DEFINED";
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(MaterialWithGroups.getMaterial(material.getValue().get()), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public MaterialAndTagsFeature clone(FeatureParentInterface newParent) {
        MaterialAndTagsFeature eF = new MaterialAndTagsFeature(newParent, id, isAcceptAir(), isAcceptItems(), isAcceptBlocks());
        eF.setMaterial(material.clone(eF));
        eF.setTags(tags.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(material, tags));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return getParent().getConfigurationSection();
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof MaterialAndTagsFeature) {
                MaterialAndTagsFeature aFOF = (MaterialAndTagsFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setMaterial(material);
                    aFOF.setTags(tags);
                    break;
                }
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        MaterialAndTagsFeatureEditorManager.getInstance().startEditing(player, this);
    }
}
