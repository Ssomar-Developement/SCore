package com.ssomar.score.features.custom.detailedblocks;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.FixedMaterial;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.list.ListDetailedMaterialFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class DetailedBlocks extends FeatureWithHisOwnEditor<DetailedBlocks, DetailedBlocks, DetailedBlocksEditor, DetailedBlocksEditorManager> implements Serializable {

    private ListDetailedMaterialFeature blocks;
    private BooleanFeature cancelEventIfNotValid;
    private ColoredStringFeature messageIfNotValid;

    public DetailedBlocks(FeatureParentInterface parent) {
        super(parent, "detailedBlocks", "Detailed Blocks", new String[]{"&7&oMake the activator run", "&7&oonly for certain blocks", "&7&oempty = all blocks"}, FixedMaterial.getMaterial(Arrays.asList("GRASS_BLOCK", "GRASS")), false);
        reset();
    }

    @Override
    public void reset() {
        this.blocks = new ListDetailedMaterialFeature(this, "blocks", new ArrayList<>(), "Blocks", new String[]{"&7&oBlocks"}, FixedMaterial.getMaterial(Arrays.asList("GRASS_BLOCK", "GRASS")), false, false);
        this.cancelEventIfNotValid = new BooleanFeature(this, "cancelEventIfNotValid", false, "Cancel event if not valid", new String[]{"&7&oCancel the event if the block is not valid?"}, Material.LEVER, false, false);
        this.messageIfNotValid = new ColoredStringFeature(this, "messageIfNotValid", Optional.ofNullable("&4&l[Error] &cthe block is not correct !"), "Message if not valid", new String[]{"&7&oMessage if the block is not valid?"}, GUI.WRITABLE_BOOK, false, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(blocks.load(plugin, section, isPremiumLoading));
            errors.addAll(cancelEventIfNotValid.load(plugin, section, isPremiumLoading));
            errors.addAll(messageIfNotValid.load(plugin, section, isPremiumLoading));
        }

        return errors;
    }

    public boolean isValidMaterial(@NotNull Material material, Optional<String> statesStrOpt, Optional<Player> playerOpt, Event event, StringPlaceholder sp) {
        //SsomarDev.testMsg("isValidMaterial ?");
        if (!blocks.isValidMaterial(material, statesStrOpt) && !blocks.getValue().isEmpty()) {
            //SsomarDev.testMsg("isValidMaterial NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            if (event != null && cancelEventIfNotValid.getValue() && event instanceof Cancellable) {
                ((Cancellable) event).setCancelled(true);
            }
            if (playerOpt.isPresent() && messageIfNotValid.getValue().isPresent()) {
                SendMessage.sendMessageNoPlch(playerOpt.get(), sp.replacePlaceholder(messageIfNotValid.getValue().get()));
            }
            return false;
        }
        //SsomarDev.testMsg("isValidMaterial yesssssssssssssssssss");
        return true;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        blocks.save(section);
        cancelEventIfNotValid.save(section);
        messageIfNotValid.save(section);
    }

    @Override
    public DetailedBlocks getValue() {
        return this;
    }

    @Override
    public DetailedBlocks initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = gui.CLICK_HERE_TO_CHANGE;
        if (blocks.getValue().isEmpty())
            finalDescription[finalDescription.length - 3] = "&7Blocks: &e&lALL BLOCKS";
        else
            finalDescription[finalDescription.length - 3] = "&7Detailed Blocks: &a+" + blocks.getValue().size();

        if (messageIfNotValid.getValue().isPresent()) {
            finalDescription[finalDescription.length - 2] = "&7Message if NV: &e" + messageIfNotValid.getValue().get();
        } else {
            finalDescription[finalDescription.length - 2] = "&7Message if NV: &cNO MESSAGE";
        }

        if (cancelEventIfNotValid.getValue())
            finalDescription[finalDescription.length - 1] = "&7Cancel Event if NV: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Cancel Event if NV: &c&l✘";

        for (int i = 0; i < finalDescription.length; i++) {
            String command = finalDescription[i];
            if (command.length() > 40) command = command.substring(0, 39) + "...";
            finalDescription[i] = command;
        }


        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public DetailedBlocks clone(FeatureParentInterface newParent) {
        DetailedBlocks dropFeatures = new DetailedBlocks(newParent);
        dropFeatures.setBlocks(blocks.clone(dropFeatures));
        dropFeatures.setCancelEventIfNotValid(cancelEventIfNotValid.clone(dropFeatures));
        dropFeatures.setMessageIfNotValid(messageIfNotValid.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(blocks, cancelEventIfNotValid, messageIfNotValid));
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
            if (feature instanceof DetailedBlocks) {
                DetailedBlocks hiders = (DetailedBlocks) feature;
                hiders.setBlocks(blocks);
                hiders.setCancelEventIfNotValid(cancelEventIfNotValid);
                hiders.setMessageIfNotValid(messageIfNotValid);
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
        DetailedBlocksEditorManager.getInstance().startEditing(player, this);
    }

}
