package com.ssomar.score.features.custom.detailedeffects;

import com.ssomar.score.features.*;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.list.ListDetailedEffectFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class DetailedEffects extends FeatureWithHisOwnEditor<DetailedEffects, DetailedEffects, DetailedEffectsEditor, DetailedEffectsEditorManager> implements Serializable {

    private ListDetailedEffectFeature effects;
    private BooleanFeature cancelEventIfNotValid;
    private transient ColoredStringFeature messageIfNotValid;

    public DetailedEffects(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        reset();
    }

    public DetailedEffects(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.detailedEffects);
        reset();
    }


    @Override
    public void reset() {
        this.effects = new ListDetailedEffectFeature(this, new ArrayList<>(), FeatureSettingsSCore.effects, false);
        this.cancelEventIfNotValid = new BooleanFeature(this, false, FeatureSettingsSCore.cancelEventIfNotValid, false);
        this.messageIfNotValid = new ColoredStringFeature(this, Optional.empty() /* Optional.ofNullable("&4&l[Error] &cthe block is not correct !") */, FeatureSettingsSCore.messageIfNotValid, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(effects.load(plugin, section, isPremiumLoading));
            errors.addAll(cancelEventIfNotValid.load(plugin, section, isPremiumLoading));
            errors.addAll(messageIfNotValid.load(plugin, section, isPremiumLoading));
        }

        return errors;
    }

    public boolean isValid(@NotNull PotionEffectType type, Optional<Player> playerOpt, Event event, StringPlaceholder sp) {
        if (effects.getValues().isEmpty() && effects.getBlackListValues().isEmpty()) return true;

        if (effects.isValidEffect(type)) {
            return true;
        } else {
            if (event != null && cancelEventIfNotValid.getValue() && event instanceof Cancellable) {
                ((Cancellable) event).setCancelled(true);
            }
            if (playerOpt.isPresent() && messageIfNotValid != null && messageIfNotValid.getValue().isPresent()) {
                SendMessage.sendMessageNoPlch(playerOpt.get(), sp.replacePlaceholder(messageIfNotValid.getValue().get()));
            }
            return false;
        }
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        effects.save(section);
        cancelEventIfNotValid.save(section);
        messageIfNotValid.save(section);
    }

    @Override
    public DetailedEffects getValue() {
        return this;
    }

    @Override
    public DetailedEffects initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        if (effects.getValues().isEmpty() && !effects.getBlackListValues().isEmpty())
            finalDescription[finalDescription.length - 3] = "&7Blocks: &e&lALL EFFECTS";
        else
            finalDescription[finalDescription.length - 3] = "&7Detailed Effects: &a+" + (effects.getCurrentValues().size());

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


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public DetailedEffects clone(FeatureParentInterface newParent) {
        DetailedEffects dropFeatures = new DetailedEffects(newParent, getFeatureSettings());
        dropFeatures.setEffects(effects.clone(dropFeatures));
        dropFeatures.setCancelEventIfNotValid(cancelEventIfNotValid.clone(dropFeatures));
        dropFeatures.setMessageIfNotValid(messageIfNotValid.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(effects, cancelEventIfNotValid, messageIfNotValid));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo() + ".(" + getName() + ")";
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
            if (feature instanceof DetailedEffects && feature.getEditorName().equals(getEditorName())) {
                DetailedEffects hiders = (DetailedEffects) feature;
                hiders.setEffects(effects);
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
        DetailedEffectsEditorManager.getInstance().startEditing(player, this);
    }

}
