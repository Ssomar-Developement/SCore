package com.ssomar.score.features.custom.useperday;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.useperday.manager.UsagePerDayManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class UsePerDayFeature extends FeatureWithHisOwnEditor<UsePerDayFeature, UsePerDayFeature, UsePerDayFeatureEditor, UsePerDayFeatureEditorManager> {

    private IntegerFeature maxUsePerDay;
    private ColoredStringFeature messageIfMaxReached;
    private BooleanFeature cancelEventIfMaxReached;
    private String id;

    public UsePerDayFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.usePerDay);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.maxUsePerDay = new IntegerFeature(this, Optional.ofNullable(-1), FeatureSettingsSCore.maxUsePerDay);
        this.messageIfMaxReached = new ColoredStringFeature(this, Optional.ofNullable("&4&l[ERROR] &c&oYou have reached the max use per day"), FeatureSettingsSCore.messageIfMaxReached, false);
        this.cancelEventIfMaxReached = new BooleanFeature(this,  false, FeatureSettingsSCore.cancelEventIfMaxReached, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(maxUsePerDay.load(plugin, section, isPremiumLoading));
            if (maxUsePerDay.getValue().get() < -1) {
                maxUsePerDay.setValue(Optional.ofNullable(-1));
            }
            errors.addAll(messageIfMaxReached.load(plugin, section, isPremiumLoading));
            errors.addAll(cancelEventIfMaxReached.load(plugin, section, isPremiumLoading));
        }

        return errors;
    }

    public boolean verify(@NotNull Player player, @Nullable Event event, @Nullable StringPlaceholder sp) {
        if (!maxUsePerDay.getValue().isPresent() || maxUsePerDay.getValue().get() == -1) {
            return true;
        } else {
            /* Verification of usage per day */
            if (UsagePerDayManager.getInstance().getCount(player.getName(), this.getId()) >= maxUsePerDay.getValue().get()) {
                sendMessageIfMaxReached(player, sp);
                if (event != null && event instanceof Cancellable && cancelEventIfMaxReached.getValue()) {
                    ((Cancellable) event).setCancelled(true);
                }
                return false;
            } else {
                return true;
            }
        }
    }

    public void incrementUsage(@NotNull Player player) {
        if (maxUsePerDay.getValue().get() != -1)
            UsagePerDayManager.getInstance().insertUsage(player.getName(), this.getId());
    }

    public void sendMessageIfMaxReached(@NotNull Player player, @Nullable StringPlaceholder sp) {
        String message = messageIfMaxReached.getValue().get();
        if (StringConverter.decoloredString(message).isEmpty()) return;
        if (sp != null) {
            message = sp.replacePlaceholder(message);
        }
        player.sendMessage(StringConverter.coloredString(message));
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        if (maxUsePerDay.getValue().get() != -1) {
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
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 4] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        if (maxUsePerDay.getValue().get() != -1)
            finalDescription[finalDescription.length - 3] = "&7Max Use per Day: &e" + maxUsePerDay.getValue().get();
        else
            finalDescription[finalDescription.length - 3] = "&7Max Use per Day: &c&l✘";
        finalDescription[finalDescription.length - 2] = "&7Message if Max: &e" + messageIfMaxReached.getValue().get();

        if (cancelEventIfMaxReached.getValue())
            finalDescription[finalDescription.length - 1] = "&7Cancel event if Max: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Cancel event if Max: &c&l✘";

        for (int i = 0; i < finalDescription.length; i++) {
            String command = finalDescription[i];
            if (command.length() > 40) command = command.substring(0, 39) + "...";
            finalDescription[i] = command;
        }

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public UsePerDayFeature clone(FeatureParentInterface newParent) {
        UsePerDayFeature dropFeatures = new UsePerDayFeature(newParent, id);
        dropFeatures.setMaxUsePerDay(maxUsePerDay.clone(dropFeatures));
        dropFeatures.setMessageIfMaxReached(messageIfMaxReached.clone(dropFeatures));
        dropFeatures.setCancelEventIfMaxReached(cancelEventIfMaxReached.clone(dropFeatures));
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
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof UsePerDayFeature) {
                UsePerDayFeature hiders = (UsePerDayFeature) feature;
                if (maxUsePerDay.getValue().get() < -1) maxUsePerDay.setValue(Optional.ofNullable(-1));
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
        if (this.isRequirePremium() && !isPremium()) return;
        UsePerDayFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
