package com.ssomar.score.features.custom.conditions.world.parent;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.conditions.world.WorldConditionFeature;
import com.ssomar.score.features.custom.conditions.world.WorldConditionRequest;
import com.ssomar.score.features.custom.conditions.world.condition.IfWeather;
import com.ssomar.score.features.custom.conditions.world.condition.IfWorldTime;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class WorldConditionsFeature extends FeatureWithHisOwnEditor<WorldConditionsFeature, WorldConditionsFeature, WorldConditionsFeatureEditor, WorldConditionsFeatureEditorManager> {

    private List<WorldConditionFeature> conditions;

    public WorldConditionsFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        reset();
    }

    @Override
    public void reset() {
        conditions = new ArrayList<>();
        /* List world features */
        conditions.add(new IfWeather(this));

        /* Number condition */
        conditions.add(new IfWorldTime(this));

    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            for (WorldConditionFeature condition : conditions) {
                error.addAll(condition.load(plugin, section, isPremiumLoading));
            }
        }

        return error;
    }

    public boolean verifConditions(World world, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event) {
        WorldConditionRequest request = new WorldConditionRequest(world, playerOpt, messageSender.getSp(), event);
        for (WorldConditionFeature condition : conditions) {
            if (!condition.verifCondition(request)) {
                if (messageSender != null && playerOpt.isPresent()) {
                    for (String error : request.getErrorsFinal()) {
                        messageSender.sendMessage(playerOpt.get(), error);
                    }
                }
                return false;
            }
        }
        return true;
    }

    public boolean verifConditions(World world, List<String> errors, @Nullable StringPlaceholder stringPlaceholder) {
        if(stringPlaceholder == null) stringPlaceholder = new StringPlaceholder();

        WorldConditionRequest request = new WorldConditionRequest(world, Optional.empty(), stringPlaceholder, null);
        for (WorldConditionFeature condition : conditions) {
            if (!condition.verifCondition(request)) {
                errors.addAll(request.getErrorsFinal());
                return false;
            }
        }
        return true;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (WorldConditionFeature condition : conditions) {
            condition.save(section);
        }
    }

    @Override
    public WorldConditionsFeature getValue() {
        return this;
    }

    @Override
    public WorldConditionsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7World condition(s) enabled: &e" + getWorldConditionEnabledCount();
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    public int getWorldConditionEnabledCount() {
        int i = 0;
        for (WorldConditionFeature condition : conditions) {
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
    public WorldConditionsFeature clone(FeatureParentInterface newParent) {
        WorldConditionsFeature clone = new WorldConditionsFeature(newParent, getFeatureSettings());
        List<WorldConditionFeature> clones = new ArrayList<>();
        for (WorldConditionFeature condition : conditions) {
            clones.add((WorldConditionFeature) condition.clone(clone));
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
            if (feature instanceof WorldConditionsFeature && feature.getName().equals(getName())) {
                WorldConditionsFeature bCF = (WorldConditionsFeature) feature;
                List<WorldConditionFeature> clones = new ArrayList<>();
                for (WorldConditionFeature condition : conditions) {
                    clones.add((WorldConditionFeature) condition);
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
        WorldConditionsFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
