package com.ssomar.score.features.custom.conditions.custom.parent;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.conditions.custom.CustomConditionFeature;
import com.ssomar.score.features.custom.conditions.custom.CustomConditionRequest;
import com.ssomar.score.features.custom.conditions.custom.condition.IfNotOwnerOfTheEI;
import com.ssomar.score.features.custom.conditions.custom.condition.IfOwnerOfTheEI;
import com.ssomar.score.features.custom.conditions.custom.condition.confirmation.IfNeedPlayerConfirmation;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomConditionsFeature extends FeatureWithHisOwnEditor<CustomConditionsFeature, CustomConditionsFeature, CustomConditionsFeatureEditor, CustomConditionsFeatureEditorManager> {

    private List<CustomConditionFeature> conditions;
    private SPlugin sPlugin;

    public CustomConditionsFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings, SPlugin sPlugin) {
        super(parent, featureSettings);
        this.sPlugin = sPlugin;
        reset();
    }

    @Override
    public void reset() {
        conditions = new ArrayList<>();
        /* Boolean features */
        conditions.add(new IfNeedPlayerConfirmation(this));
        /* Conditions specific to EI */
        if(sPlugin.getShortName().equalsIgnoreCase("ei")) {
            conditions.add(new IfOwnerOfTheEI(this));
            conditions.add(new IfNotOwnerOfTheEI(this));
        }

    }



    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            for (CustomConditionFeature condition : conditions) {
                error.addAll(condition.load(plugin, section, isPremiumLoading));
            }
        }

        return error;
    }

    public boolean verifConditions(Player player, ItemStack itemStack, @NotNull SendMessage messageSender, @Nullable Event event) {
        CustomConditionRequest request = new CustomConditionRequest(player, itemStack, messageSender.getSp(), event);
        for (CustomConditionFeature condition : conditions) {
            if (!condition.verifCondition(request)) {
                if (player != null) {
                    for (String error : request.getErrorsFinal()) {
                        messageSender.sendMessage(player, error);
                    }
                }
                return false;
            }
        }
        return true;
    }


    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (CustomConditionFeature condition : conditions) {
            condition.save(section);
        }
    }

    @Override
    public CustomConditionsFeature getValue() {
        return this;
    }

    @Override
    public CustomConditionsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7Block condition(s) enabled: &e" + getBlockConditionEnabledCount();
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    public int getBlockConditionEnabledCount() {
        int i = 0;
        for (CustomConditionFeature condition : conditions) {
            if (condition.hasCondition()) {
                i++;
            }
        }
        return i;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public CustomConditionsFeature clone(FeatureParentInterface newParent) {
        CustomConditionsFeature clone = new CustomConditionsFeature(newParent, getFeatureSettings(), getSPlugin());
        List<CustomConditionFeature> clones = new ArrayList<>();
        for (CustomConditionFeature condition : conditions) {
            clones.add((CustomConditionFeature) condition.clone(clone));
        }
        clone.setConditions(clones);
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(conditions);
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
            if (feature instanceof CustomConditionsFeature && feature.getName().equals(getName())) {
                CustomConditionsFeature bCF = (CustomConditionsFeature) feature;
                List<CustomConditionFeature> clones = new ArrayList<>();
                for (CustomConditionFeature condition : conditions) {
                    clones.add((CustomConditionFeature) condition);
                }
                bCF.setConditions(clones);
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
        CustomConditionsFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
