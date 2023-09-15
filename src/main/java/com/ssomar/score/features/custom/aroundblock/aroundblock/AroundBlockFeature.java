package com.ssomar.score.features.custom.aroundblock.aroundblock;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.list.ListDetailedMaterialFeature;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
public class AroundBlockFeature extends FeatureWithHisOwnEditor<AroundBlockFeature, AroundBlockFeature, AroundBlockFeatureEditor, AroundBlockFeatureEditorManager> {

    private IntegerFeature southValue;
    private IntegerFeature northValue;
    private IntegerFeature westValue;
    private IntegerFeature eastValue;
    private IntegerFeature aboveValue;
    private IntegerFeature underValue;

    private ColoredStringFeature errorMessage;

    private ListDetailedMaterialFeature blockTypeMustBe;

    private String id;

    public AroundBlockFeature(FeatureParentInterface parent, String id) {
        super(parent, "AroundBlock", TM.g(Text.FEATURES_AROUNDBLOCK_NAME), TM.gA(Text.FEATURES_AROUNDBLOCK_DESCRIPTION), Material.STONE, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.southValue = new IntegerFeature(this, "southValue", Optional.of(0), TM.g(Text.FEATURES_AROUNDBLOCK_FEATURES_SOUTHVALUE_NAME), TM.gA(Text.FEATURES_AROUNDBLOCK_FEATURES_SOUTHVALUE_DESCRIPTION), GUI.CLOCK, false);
        this.northValue = new IntegerFeature(this, "northValue", Optional.of(0), TM.g(Text.FEATURES_AROUNDBLOCK_FEATURES_NORTHVALUE_NAME) , TM.gA(Text.FEATURES_AROUNDBLOCK_FEATURES_NORTHVALUE_DESCRIPTION), GUI.CLOCK, false);
        this.westValue = new IntegerFeature(this, "westValue", Optional.of(0), TM.g(Text.FEATURES_AROUNDBLOCK_FEATURES_WESTVALUE_NAME), TM.gA(Text.FEATURES_AROUNDBLOCK_FEATURES_WESTVALUE_DESCRIPTION), GUI.CLOCK, false);
        this.eastValue = new IntegerFeature(this, "eastValue", Optional.of(0), TM.g(Text.FEATURES_AROUNDBLOCK_FEATURES_EASTVALUE_NAME), TM.gA(Text.FEATURES_AROUNDBLOCK_FEATURES_EASTVALUE_DESCRIPTION), GUI.CLOCK, false);
        this.aboveValue = new IntegerFeature(this, "aboveValue", Optional.of(0), TM.g(Text.FEATURES_AROUNDBLOCK_FEATURES_ABOVEVALUE_NAME), TM.gA(Text.FEATURES_AROUNDBLOCK_FEATURES_ABOVEVALUE_DESCRIPTION), GUI.CLOCK, false);
        this.underValue = new IntegerFeature(this, "underValue", Optional.of(0), TM.g(Text.FEATURES_AROUNDBLOCK_FEATURES_UNDERVALUE_NAME), TM.gA(Text.FEATURES_AROUNDBLOCK_FEATURES_UNDERVALUE_DESCRIPTION), GUI.CLOCK, false);

        this.errorMessage = new ColoredStringFeature(this, "errorMsg", Optional.of("&c&oA block is not placed correctly !"), TM.g(Text.FEATURES_AROUNDBLOCK_FEATURES_ERRORMESSAGE_NAME), TM.gA(Text.FEATURES_AROUNDBLOCK_FEATURES_ERRORMESSAGE_DESCRIPTION), GUI.WRITABLE_BOOK, false, false);

        this.blockTypeMustBe = new ListDetailedMaterialFeature(this, "blockTypeMustBe", new ArrayList<>(),  TM.g(Text.FEATURES_AROUNDBLOCK_FEATURES_BLOCKTYPEMUSTBE_NAME), TM.gA(Text.FEATURES_AROUNDBLOCK_FEATURES_BLOCKTYPEMUSTBE_DESCRIPTION), GUI.WRITABLE_BOOK, false, true, true);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.southValue.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.northValue.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.westValue.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.eastValue.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.aboveValue.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.underValue.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.errorMessage.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.blockTypeMustBe.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the AroundBlockFeature with its options because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
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
        this.southValue.save(attributeConfig);
        this.northValue.save(attributeConfig);
        this.westValue.save(attributeConfig);
        this.eastValue.save(attributeConfig);
        this.aboveValue.save(attributeConfig);
        this.underValue.save(attributeConfig);
        this.errorMessage.save(attributeConfig);
        this.blockTypeMustBe.save(attributeConfig);
    }

    public boolean verif(Block block, Optional<Player> playerOpt, List<String> errors) {

        Location targetLoc;
        Block targetBlock;

        targetLoc = block.getLocation();
        targetLoc.add(-westValue.getValue().get() + eastValue.getValue().get(), -underValue.getValue().get() + aboveValue.getValue().get(), -northValue.getValue().get() + southValue.getValue().get());

        targetBlock = targetLoc.getBlock();

        boolean valid = blockTypeMustBe.verifBlock(targetBlock);

        if (playerOpt.isPresent() && !valid && errorMessage.getValue().isPresent())
            errors.add(errorMessage.getValue().get());
        return valid;
    }

    @Override
    public AroundBlockFeature getValue() {
        return this;
    }

    @Override
    public AroundBlockFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public AroundBlockFeature clone(FeatureParentInterface newParent) {
        AroundBlockFeature eF = new AroundBlockFeature(newParent, id);
        eF.setSouthValue(this.southValue.clone(eF));
        eF.setNorthValue(this.northValue.clone(eF));
        eF.setWestValue(this.westValue.clone(eF));
        eF.setEastValue(this.eastValue.clone(eF));
        eF.setAboveValue(this.aboveValue.clone(eF));
        eF.setUnderValue(this.underValue.clone(eF));
        eF.setErrorMessage(this.errorMessage.clone(eF));
        eF.setBlockTypeMustBe(this.blockTypeMustBe.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(this.southValue, this.northValue, this.westValue, this.eastValue, this.aboveValue, this.underValue, this.errorMessage, this.blockTypeMustBe));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection parentSection = getParent().getConfigurationSection();
        if (parentSection.isConfigurationSection(getId())) {
            return parentSection.getConfigurationSection(getId());
        } else return parentSection.createSection(getId());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof AroundBlockFeature) {
                AroundBlockFeature aFOF = (AroundBlockFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setSouthValue(this.southValue);
                    aFOF.setNorthValue(this.northValue);
                    aFOF.setWestValue(this.westValue);
                    aFOF.setEastValue(this.eastValue);
                    aFOF.setAboveValue(this.aboveValue);
                    aFOF.setUnderValue(this.underValue);
                    aFOF.setErrorMessage(this.errorMessage);
                    aFOF.setBlockTypeMustBe(this.blockTypeMustBe);
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
        AroundBlockFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
