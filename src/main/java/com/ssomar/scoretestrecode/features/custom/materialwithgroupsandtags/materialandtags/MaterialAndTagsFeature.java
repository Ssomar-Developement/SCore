package com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.materialandtags;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.MaterialWithGroups;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.types.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class MaterialAndTagsFeature extends FeatureWithHisOwnEditor<MaterialAndTagsFeature, MaterialAndTagsFeature, MaterialAndTagsFeatureEditor, MaterialAndTagsFeatureEditorManager> {

    private MaterialWithGroupsFeature material;
    private String id;

    public MaterialAndTagsFeature(FeatureParentInterface parent, String id) {
        super(parent, "materialAndTags", "Material and Tags", new String[]{"&7&oA material with its options"}, Material.STONE, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.material = new MaterialWithGroupsFeature(this, "material", Optional.of(MaterialWithGroups.getMaterialWithGroupsList().get(0)), "Material", new String[]{"&7&oThe material"}, Material.STONE, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if(config.isConfigurationSection(id)){
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.material.load(plugin, enchantmentConfig, isPremiumLoading));
        }
        else{
            errors.add("&cERROR, Couldn't load the Attribute with its options because there is not section with the good ID: "+id+" &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("("+id+")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.material.save(attributeConfig);
    }

    @Override
    public MaterialAndTagsFeature getValue() {
        return this;
    }

    @Override
    public MaterialAndTagsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7Material: &e" + material.getValue().get();
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(MaterialWithGroups.getMaterial(material.getValue().get()), 1, slot, gui.TITLE_COLOR + getEditorName()+ " - "+"("+id+")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public MaterialAndTagsFeature clone() {
        MaterialAndTagsFeature eF = new MaterialAndTagsFeature(getParent(), id);
        eF.setMaterial(material.clone());
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(material));
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
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof MaterialAndTagsFeature) {
                MaterialAndTagsFeature aFOF = (MaterialAndTagsFeature) feature;
                if(aFOF.getId().equals(id)) {
                    aFOF.setMaterial(material);
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

    public boolean isValid(Block block) {
        // #TODO check if block is valid
        return false;
    }

    public boolean isValid(Material type, BlockData blockData) {
        // #TODO check if block is valid
        return false;
    }


}
