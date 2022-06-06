package com.ssomar.testRecode.features.custom.activators.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.FeaturesGroup;
import com.ssomar.testRecode.features.custom.activators.activator.NewSActivator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class ActivatorsFeature extends FeatureWithHisOwnEditor<ActivatorsFeature, ActivatorsFeature, ActivatorsFeatureEditor, ActivatorsFeatureEditorManager> implements FeaturesGroup<NewSActivator> {

    private Map<String, NewSActivator> activators;
    private NewSActivator builderInstance;

    public ActivatorsFeature(FeatureParentInterface parent, NewSActivator builderInstance) {
        super(parent, "activators", "Activators", new String[]{"&7&oThe activators / triggers"}, Material.BEACON, false);
        this.builderInstance = builderInstance;
        reset();
    }

    @Override
    public void reset() {
        this.activators = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if(config.isConfigurationSection(this.getName())) {
            ConfigurationSection activatorsSection = config.getConfigurationSection(this.getName());
            for(String activatorID : activatorsSection.getKeys(false)) {
                NewSActivator activator = builderInstance.getBuilderInstance(this, activatorID);
                List<String> subErrors = activator.load(plugin, activatorsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                activators.put(activatorID, activator);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for(String enchantmentID : activators.keySet()) {
            activators.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public ActivatorsFeature getValue() {
        return this;
    }

    @Override
    public ActivatorsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

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
    public ActivatorsFeature clone() {
        ActivatorsFeature eF = new ActivatorsFeature(getParent(), builderInstance);
        eF.setActivators(new HashMap<>(this.getActivators()));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(activators.values());
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if(section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        }
        else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof ActivatorsFeature) {
                ActivatorsFeature eF = (ActivatorsFeature) feature;
                eF.setActivators(this.getActivators());
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
        ActivatorsFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "activator";
        for(int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if(!activators.containsKey(id)) {
                NewSActivator activator = builderInstance.getBuilderInstance(this, id);
                activators.put(id, activator);
                activator.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, NewSActivator feature) {
        activators.remove(feature.getId());
    }
}
