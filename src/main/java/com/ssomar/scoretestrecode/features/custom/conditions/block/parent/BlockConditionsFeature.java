package com.ssomar.scoretestrecode.features.custom.conditions.block.parent;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.scoretestrecode.features.custom.conditions.block.condition.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
public class BlockConditionsFeature extends FeatureWithHisOwnEditor<BlockConditionsFeature, BlockConditionsFeature, BlockConditionsFeatureEditor, BlockConditionsFeatureEditorManager> {

    private List<BlockConditionFeature> conditions;

    public BlockConditionsFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription) {
        super(parent, name, editorName, editorDescription, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        conditions = new ArrayList<>();
        /** Boolean features **/
        conditions.add(new IfIsPowered(this));
        conditions.add(new IfMustBeNotPowered(this));
        conditions.add(new IfMustBeNatural(this));
        conditions.add(new IfPlayerMustBeOnTheBlock(this));
        conditions.add(new IfNoPlayerMustBeOnTheBlock(this));
        conditions.add(new IfPlantFullyGrown(this));

        /** Number condition features **/
        conditions.add(new IfBlockAge(this));
        conditions.add(new IfBlockLocationX(this));
        conditions.add(new IfBlockLocationY(this));
        conditions.add(new IfBlockLocationZ(this));
        conditions.add(new IfUsage(this));

        conditions.add(new AroundBlockConditions(this));

    }

    public boolean verifConditions(Block b, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event) {
        for (BlockConditionFeature condition : conditions) {
            if (!condition.verifCondition(b, playerOpt, messageSender, event)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            for (BlockConditionFeature condition : conditions) {
                error.addAll(condition.load(plugin, section, isPremiumLoading));
            }
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (BlockConditionFeature condition : conditions) {
            condition.save(section);
        }
    }

    @Override
    public BlockConditionsFeature getValue() {
        return this;
    }

    @Override
    public BlockConditionsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7Block condition(s) enabled: &e" + getBlockConditionEnabledCount();
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    public int getBlockConditionEnabledCount() {
        int i = 0;
        for (BlockConditionFeature condition : conditions) {
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
    public BlockConditionsFeature clone(FeatureParentInterface newParent) {
        BlockConditionsFeature clone = new BlockConditionsFeature(newParent, getName(), getEditorName(), getEditorDescription());
        List<BlockConditionFeature> clones = new ArrayList<>();
        for (BlockConditionFeature condition : conditions) {
            clones.add((BlockConditionFeature) condition.clone(clone));
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
            if (feature instanceof BlockConditionsFeature && feature.getName().equals(getName())) {
                BlockConditionsFeature bCF = (BlockConditionsFeature) feature;
                List<BlockConditionFeature> clones = new ArrayList<>();
                for (BlockConditionFeature condition : conditions) {
                    clones.add((BlockConditionFeature) condition);
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
        BlockConditionsFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
