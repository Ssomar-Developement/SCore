package com.ssomar.testRecode.features.custom.required.items.item;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.AttributeSlot;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.custom.required.RequiredPlayerInterface;
import com.ssomar.testRecode.features.types.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class RequiredItemFeature extends FeatureWithHisOwnEditor<RequiredItemFeature, RequiredItemFeature, RequiredItemFeatureEditor, RequiredItemFeatureEditorManager> implements RequiredPlayerInterface {

    private MaterialFeature material;
    private IntegerFeature amount;
    private String id;

    public RequiredItemFeature(FeatureParentInterface parent, String id) {
        super(parent, "RequiredItem", "Required Item", new String[]{"&7&oA required item"}, Material.STONE, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.material = new MaterialFeature(this, "material", Optional.of(Material.STONE), "Material", new String[]{"&7&oThe material"}, Material.STONE, false);
        this.amount = new IntegerFeature(this, "amount", Optional.of(1), "Amount", new String[]{"&7&oThe amount"}, GUI.CLOCK, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if(config.isConfigurationSection(id)){
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.material.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.amount.load(plugin, enchantmentConfig, isPremiumLoading));
        }
        else{
            errors.add("&cERROR, Couldn't load the Required Item because there is not section with the good ID: "+id+" &7&o" + getParent().getParentInfo());
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
        this.amount.save(attributeConfig);
    }

    @Override
    public RequiredItemFeature getValue() {
        return this;
    }

    @Override
    public RequiredItemFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = "&7Material: &e" + material.getValue().get().name();
        finalDescription[finalDescription.length - 1] = "&7Amount: &e" + amount.getValue().get();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName()+ " - "+"("+id+")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public RequiredItemFeature clone() {
        RequiredItemFeature eF = new RequiredItemFeature(getParent(), id);
        eF.setMaterial(material.clone());
        eF.setAmount(amount.clone());
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(material, amount));
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
            if(feature instanceof RequiredItemFeature) {
                RequiredItemFeature aFOF = (RequiredItemFeature) feature;
                if(aFOF.getId().equals(id)) {
                    aFOF.setMaterial(material);
                    aFOF.setAmount(amount);
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
        RequiredItemFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public boolean verify(Player player, Event event) {
        return false;
    }

    @Override
    public void take(Player player) {

    }
}
