package com.ssomar.score.features.custom.aroundblock.aroundblock;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlockPlacedInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
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

    private ListUncoloredStringFeature blockMustBeExecutableBlock;

    private MaterialAndTagsGroupFeature blockTypeMustBe;

    private MaterialAndTagsGroupFeature blockTypeMustNotBe;

    private String id;

    public AroundBlockFeature(FeatureParentInterface parent, String id) {
        super(parent, "AroundBlock", "AroundBlock", new String[]{}, Material.STONE, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.southValue = new IntegerFeature(this, "southValue", Optional.of(0), "South Value", new String[]{"&7&oThe south value"}, GUI.CLOCK, false);
        this.northValue = new IntegerFeature(this, "northValue", Optional.of(0), "North Value", new String[]{"&7&oThe north value"}, GUI.CLOCK, false);
        this.westValue = new IntegerFeature(this, "westValue", Optional.of(0), "West Value", new String[]{"&7&oThe west value"}, GUI.CLOCK, false);
        this.eastValue = new IntegerFeature(this, "eastValue", Optional.of(0), "East Value", new String[]{"&7&oThe east value"}, GUI.CLOCK, false);
        this.aboveValue = new IntegerFeature(this, "aboveValue", Optional.of(0), "Above Value", new String[]{"&7&oThe above value"}, GUI.CLOCK, false);
        this.underValue = new IntegerFeature(this, "underValue", Optional.of(0), "Under Value", new String[]{"&7&oThe under value"}, GUI.CLOCK, false);

        this.errorMessage = new ColoredStringFeature(this, "errorMsg", Optional.of("&c&oA block is not placed correctly !"), "Error Message", new String[]{"&7&oThe error message"}, GUI.WRITABLE_BOOK, false, false);

        this.blockMustBeExecutableBlock = new ListUncoloredStringFeature(this, "blockMustBeExecutableBlock", new ArrayList(), "Block Must Be Executable Block", new String[]{"&7&oThe block must be executable block"}, GUI.WRITABLE_BOOK, false, false, Optional.empty());

        this.blockTypeMustBe = new MaterialAndTagsGroupFeature(this, "blockTypeMustBe", "Block Type Must Be", new String[]{"&7&oThe block type must be"}, true, false, true, false);

        this.blockTypeMustNotBe = new MaterialAndTagsGroupFeature(this, "blockTypeMustNotBe", "Block Type Must Not Be", new String[]{"&7&oThe block type must not be"}, true, false, true, false);
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
            errors.addAll(this.blockMustBeExecutableBlock.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.blockTypeMustBe.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.blockTypeMustNotBe.load(plugin, enchantmentConfig, isPremiumLoading));
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
        this.blockMustBeExecutableBlock.save(attributeConfig);
        this.blockTypeMustBe.save(attributeConfig);
        this.blockTypeMustNotBe.save(attributeConfig);
    }

    public boolean verif(Block block, Optional<Player> playerOpt, SendMessage messageSender) {

        Location targetLoc;
        Block targetBlock;

        targetLoc = block.getLocation();
        targetLoc.add(-westValue.getValue().get() + eastValue.getValue().get(), -underValue.getValue().get() + aboveValue.getValue().get(), -northValue.getValue().get() + southValue.getValue().get());

        targetBlock = targetLoc.getBlock();

        boolean valid = true;

        if (this.blockTypeMustBe.getMaterialAndTags().size() != 0) {
            valid = valid && this.blockTypeMustBe.isValid(targetBlock);
        }

        if (this.blockTypeMustNotBe.getMaterialAndTags().size() != 0)
            valid = valid && !this.blockTypeMustNotBe.isValid(targetBlock);

        targetLoc.add(0.5, 0.5, 0.5);
        if (SCore.hasExecutableBlocks && this.blockMustBeExecutableBlock.getValue().size() != 0) {
            Optional<ExecutableBlockPlacedInterface> eBP = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getExecutableBlockPlaced(targetLoc);
            valid = valid && (eBP.isPresent() && this.blockMustBeExecutableBlock.getValue().contains(eBP.get().getExecutableBlockID()));
        }

        if (playerOpt.isPresent() && !valid && errorMessage.getValue().isPresent())
            messageSender.sendMessage(playerOpt.get(), errorMessage.getValue().get());
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
        /*finalDescription[finalDescription.length - 5] = "&7Attribute: &e" + attribute.getValue().get().name();
        finalDescription[finalDescription.length - 4] = "&7Operation: &e" + operation.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Amount: &e" + amount.getValue().get();
        finalDescription[finalDescription.length - 2] = "&7Slot: &e" + this.slot.getValue().get().name();*/
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
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
        eF.setBlockMustBeExecutableBlock(this.blockMustBeExecutableBlock.clone(eF));
        eF.setBlockTypeMustBe(this.blockTypeMustBe.clone(eF));
        eF.setBlockTypeMustNotBe(this.blockTypeMustNotBe.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(this.southValue, this.northValue, this.westValue, this.eastValue, this.aboveValue, this.underValue, this.errorMessage, this.blockMustBeExecutableBlock, this.blockTypeMustBe, this.blockTypeMustNotBe));
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
                    aFOF.setBlockMustBeExecutableBlock(this.blockMustBeExecutableBlock);
                    aFOF.setBlockTypeMustBe(this.blockTypeMustBe);
                    aFOF.setBlockTypeMustNotBe(this.blockTypeMustNotBe);
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
