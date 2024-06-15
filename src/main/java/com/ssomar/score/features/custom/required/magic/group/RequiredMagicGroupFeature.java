package com.ssomar.score.features.custom.required.magic.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.required.RequiredPlayerInterface;
import com.ssomar.score.features.custom.required.magic.magic.RequiredMagicFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class RequiredMagicGroupFeature extends FeatureWithHisOwnEditor<RequiredMagicGroupFeature, RequiredMagicGroupFeature, RequiredMagicGroupFeatureEditor, RequiredMagicGroupFeatureEditorManager> implements FeaturesGroup<RequiredMagicFeature>, RequiredPlayerInterface {

    private Map<String, RequiredMagicFeature> requiredMagics;
    private ColoredStringFeature errorMessage;
    private BooleanFeature cancelEventIfError;

    public RequiredMagicGroupFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.requiredMagics);
        reset();
    }

    @Override
    public void reset() {
        this.requiredMagics = new HashMap<>();
        this.errorMessage = new ColoredStringFeature(this, Optional.of("&4&l>> &cError you don't have the required magics"), FeatureSettingsSCore.errorMessage, true);
        this.cancelEventIfError = new BooleanFeature(this, false, FeatureSettingsSCore.cancelEventIfError, true);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection requiredItemGroupSection = config.getConfigurationSection(this.getName());
            for (String attributeID : requiredItemGroupSection.getKeys(false)) {
                if (attributeID.equals(errorMessage.getName()) || attributeID.equals(cancelEventIfError.getName())) {
                    continue;
                }
                if (!isPremiumLoading && isRequirePremium()) {
                    error.add("&cERROR, Couldn't load the " + attributeID + " value of " + this.getEditorName() + " from config &6>> Because it's a premium feature !");
                    break;
                }
                RequiredMagicFeature attribute = new RequiredMagicFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, requiredItemGroupSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                requiredMagics.put(attributeID, attribute);
            }
            errorMessage.load(plugin, requiredItemGroupSection, isPremiumLoading);
            cancelEventIfError.load(plugin, requiredItemGroupSection, isPremiumLoading);
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : requiredMagics.keySet()) {
            requiredMagics.get(enchantmentID).save(attributesSection);
        }
        errorMessage.save(attributesSection);
        cancelEventIfError.save(attributesSection);
    }

    @Override
    public RequiredMagicGroupFeature getValue() {
        return this;
    }

    @Override
    public RequiredMagicGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 2] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oRequiredMagic(s) added: &e" + requiredMagics.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RequiredMagicGroupFeature clone(FeatureParentInterface newParent) {
        RequiredMagicGroupFeature eF = new RequiredMagicGroupFeature(newParent);
        HashMap<String, RequiredMagicFeature> newRequiredExecutableItems = new HashMap<>();
        for (String enchantmentID : requiredMagics.keySet()) {
            newRequiredExecutableItems.put(enchantmentID, requiredMagics.get(enchantmentID).clone(eF));
        }
        eF.setRequiredMagics(newRequiredExecutableItems);
        eF.setErrorMessage(this.getErrorMessage().clone(eF));
        eF.setCancelEventIfError(this.getCancelEventIfError().clone(eF));
        eF.setPremium(this.isPremium());
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>(requiredMagics.values());
        features.add(errorMessage);
        features.add(cancelEventIfError);
        return features;
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo()+ ".("+getName()+")";
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
            if (feature instanceof RequiredMagicGroupFeature) {
                RequiredMagicGroupFeature eF = (RequiredMagicGroupFeature) feature;
                eF.setRequiredMagics(this.getRequiredMagics());
                eF.setErrorMessage(this.getErrorMessage());
                eF.setCancelEventIfError(this.getCancelEventIfError());
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
        if (!isPremium() && this.isRequirePremium()) return;
        RequiredMagicGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "requiredMagic_";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!requiredMagics.containsKey(id)) {
                RequiredMagicFeature eF = new RequiredMagicFeature(this, id);
                requiredMagics.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, RequiredMagicFeature feature) {
        requiredMagics.remove(feature.getId());
    }

    @Override
    public RequiredMagicFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (RequiredMagicFeature rEI : requiredMagics.values()) {
            if (rEI.isTheFeatureClickedParentEditor(featureClicked)) return rEI;
        }
        return null;
    }

    @Override
    public boolean verify(Player player, Event event) {
        for (RequiredMagicFeature eF : requiredMagics.values()) {
            if (!eF.verify(player, event)) {
                if (errorMessage.getValue().isPresent()) {
                    SendMessage.sendMessageNoPlch(player, errorMessage.getValue().get());
                }
                if (cancelEventIfError.getValue() && event instanceof Cancellable) {
                    ((Cancellable) event).setCancelled(true);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void take(Player player) {
        for (RequiredMagicFeature eF : requiredMagics.values()) {
            eF.take(player);
        }
    }
}
