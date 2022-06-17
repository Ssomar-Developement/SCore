package com.ssomar.scoretestrecode.features.custom.useperday;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import com.ssomar.scoretestrecode.features.types.ColoredStringFeature;
import com.ssomar.scoretestrecode.features.types.IntegerFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class UsePerDayFeature extends FeatureWithHisOwnEditor<UsePerDayFeature, UsePerDayFeature, UsePerDayFeatureEditor, UsePerDayFeatureEditorManager> {

    private IntegerFeature maxUsePerDay;
    private ColoredStringFeature messageIfMaxReached;
    private BooleanFeature cancelEventIfMaxReached;

    public UsePerDayFeature(FeatureParentInterface parent) {
        super(parent, "usePerDay", "Use per day", new String[]{"&7&oUse per day features"}, Material.BUCKET, false);
        reset();
    }

    @Override
    public void reset() {
        this.maxUsePerDay = new IntegerFeature(this, "maxUsePerDay", Optional.ofNullable(-1), "Max use per day", new String[]{"&7&oMax use per day"}, Material.BUCKET, false);
        this.messageIfMaxReached = new ColoredStringFeature(this, "messageIfMaxReached", Optional.ofNullable("&4&l[ERROR] &c&oYou have reached the max use per day"), "Message if max reached", new String[]{"&7&oMessage if max reached"}, GUI.WRITABLE_BOOK, false, false);
        this.cancelEventIfMaxReached = new BooleanFeature(this, "cancelEventIfMaxReached", false, "Cancel event if max reached", new String[]{"&7&oCancel event if max reached"}, Material.LEVER, false, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if(config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(maxUsePerDay.load(plugin, section, isPremiumLoading));
            errors.addAll(messageIfMaxReached.load(plugin, section, isPremiumLoading));
            errors.addAll(cancelEventIfMaxReached.load(plugin, section, isPremiumLoading));
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        if(maxUsePerDay.getValue().get() != -1) {
            ConfigurationSection section = config.createSection(getName());
            maxUsePerDay.save(section);
            messageIfMaxReached.save(section);
            cancelEventIfMaxReached.save(section);
        }
    }

    @Override
    public UsePerDayFeature getValue() {
        return this;
    }

    @Override
    public UsePerDayFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = gui.CLICK_HERE_TO_CHANGE;
        if(maxUsePerDay.getValue().get() != -1)
            finalDescription[finalDescription.length - 3] = "&7Max Use per Day: &e"+maxUsePerDay.getValue().get();
        else
            finalDescription[finalDescription.length - 3] = "&7Hide enchantments: &c&l✘";
        finalDescription[finalDescription.length - 2] = "&7Message if Max: &e"+messageIfMaxReached.getValue().get();

        if(cancelEventIfMaxReached.getValue())
            finalDescription[finalDescription.length - 1] = "&7Cancel event if Max: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Cancel event if Max: &c&l✘";


        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public UsePerDayFeature clone() {
        UsePerDayFeature dropFeatures = new UsePerDayFeature(getParent());
        dropFeatures.setMaxUsePerDay(maxUsePerDay.clone());
        dropFeatures.setMessageIfMaxReached(messageIfMaxReached.clone());
        dropFeatures.setCancelEventIfMaxReached(cancelEventIfMaxReached.clone());
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(maxUsePerDay, messageIfMaxReached, cancelEventIfMaxReached));
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
            if(feature instanceof UsePerDayFeature) {
                UsePerDayFeature hiders = (UsePerDayFeature) feature;
                hiders.setMaxUsePerDay(maxUsePerDay);
                hiders.setMessageIfMaxReached(messageIfMaxReached);
                hiders.setCancelEventIfMaxReached(cancelEventIfMaxReached);
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
        UsePerDayFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
