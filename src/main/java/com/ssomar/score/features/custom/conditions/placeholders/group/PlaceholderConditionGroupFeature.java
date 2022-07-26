package com.ssomar.score.features.custom.conditions.placeholders.group;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.FeaturesGroup;
import com.ssomar.score.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PlaceholderConditionGroupFeature extends FeatureWithHisOwnEditor<PlaceholderConditionGroupFeature, PlaceholderConditionGroupFeature, PlaceholderConditionGroupFeatureEditor, PlaceholderConditionGroupFeatureEditorManager> implements FeaturesGroup<PlaceholderConditionFeature> {

    private Map<String, PlaceholderConditionFeature> attributes;

    public PlaceholderConditionGroupFeature(FeatureParentInterface parent) {
        super(parent, "placeholdersConditions", "Placeholders Conditions", new String[]{"&7&oThe placeholders conditions"}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        this.attributes = new HashMap<>();
    }

    public boolean verify(Player player, Player target, Event event) {
        return verify(player, target, null, event);
    }

    public boolean verify(Player player, Player target, @Nullable StringPlaceholder sp, Event event) {
        for (PlaceholderConditionFeature attribute : attributes.values()) {
            if (!attribute.verify(player, target, sp)) {
                String message = attribute.getMessageIfNotValid().getValue().get();
                if (sp != null) {
                    message = sp.replacePlaceholder(message);
                }
                SendMessage.sendMessageNoPlch(player, message);
                if (event != null && event instanceof Cancellable && attribute.getCancelEventIfNotValid().getValue()) {
                    ((Cancellable) event).setCancelled(true);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                PlaceholderConditionFeature attribute = new PlaceholderConditionFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                attributes.put(attributeID, attribute);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : attributes.keySet()) {
            attributes.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public PlaceholderConditionGroupFeature getValue() {
        return this;
    }

    @Override
    public PlaceholderConditionGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7&oPlaceholder cdt(s) added: &e" + attributes.size();
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public PlaceholderConditionFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (PlaceholderConditionFeature x : attributes.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public PlaceholderConditionGroupFeature clone(FeatureParentInterface newParent) {
        PlaceholderConditionGroupFeature eF = new PlaceholderConditionGroupFeature(newParent);
        HashMap<String, PlaceholderConditionFeature> newAttributes = new HashMap<>();
        for (String x : attributes.keySet()) {
            newAttributes.put(x, attributes.get(x).clone(eF));
        }
        eF.setAttributes(newAttributes);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(attributes.values());
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
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
            if (feature instanceof PlaceholderConditionGroupFeature && feature.getName().equals(getName())) {
                PlaceholderConditionGroupFeature eF = (PlaceholderConditionGroupFeature) feature;
                eF.setAttributes(this.getAttributes());
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
        PlaceholderConditionGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "plchCdt";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!attributes.containsKey(id)) {
                PlaceholderConditionFeature eF = new PlaceholderConditionFeature(this, id);
                attributes.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, PlaceholderConditionFeature feature) {
        attributes.remove(feature.getId());
    }

}
