package com.ssomar.score.features.custom.conditions.player.parent;

import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.condition.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.player.condition.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
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
public class PlayerConditionsFeature extends FeatureWithHisOwnEditor<PlayerConditionsFeature, PlayerConditionsFeature, PlayerConditionsFeatureEditor, PlayerConditionsFeatureEditorManager> {

    private List<PlayerConditionFeature> conditions;

    public PlayerConditionsFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription) {
        super(parent, name, editorName, editorDescription, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        conditions = new ArrayList<>();
        /** Boolean features **/
        conditions.add(new IfSneaking(this));
        conditions.add(new IfNotSneaking(this));
        conditions.add(new IfSprinting(this));
        conditions.add(new IfNotSprinting(this));
        conditions.add(new IfFlying(this));
        conditions.add(new IfNotFlying(this));
        conditions.add(new IfBlocking(this));
        conditions.add(new IfNotBlocking(this));
        conditions.add(new IfGliding(this));
        conditions.add(new IfNotGliding(this));
        conditions.add(new IfSwimming(this));
        conditions.add(new IfNotSwimming(this));
        conditions.add(new IfStunned(this));
        conditions.add(new IfIsOnFire(this));
        conditions.add(new IfIsNotOnFire(this));
        conditions.add(new IfIsInTheAir(this));
        conditions.add(new IfIsNotInTheAir(this));
        conditions.add(new IfPlayerMustBeInHisTown(this));
        conditions.add(new IfPlayerMustBeOnHisClaim(this));
        conditions.add(new IfPlayerMustBeOnHisIsland(this));
        conditions.add(new IfPlayerMustBeOnHisPlot(this));

        /** Number condition features **/
        conditions.add(new IfCursorDistance(this));
        conditions.add(new IfLightLevel(this));
        conditions.add(new IfPlayerEXP(this));
        conditions.add(new IfPlayerLevel(this));
        conditions.add(new IfPlayerFoodLevel(this));
        conditions.add(new IfPlayerHealth(this));
        conditions.add(new IfPosX(this));
        conditions.add(new IfPosY(this));
        conditions.add(new IfPosZ(this));


        /** List uncolored string **/
        conditions.add(new IfHasPermission(this));
        conditions.add(new IfNotHasPermission(this));
        conditions.add(new IfHasTag(this));
        conditions.add(new IfNotHasTag(this));

        /** List Material **/
        conditions.add(new IfTargetBlock(this));
        conditions.add(new IfNotTargetBlock(this));
        conditions.add(new IfIsInTheBlock(this));
        conditions.add(new IfIsNotInTheBlock(this));
        conditions.add(new IfIsOnTheBlock(this));
        conditions.add(new IfIsNotOnTheBlock(this));

        /** List colored string **/


        /** List EntityType **/
        conditions.add(new IfPlayerMounts(this));
        conditions.add(new IfPlayerNotMounts(this));

        /** List Biome **/
        conditions.add(new IfInBiome(this));
        conditions.add(new IfNotInBiome(this));

        /** List Region **/
        conditions.add(new IfInRegion(this));
        conditions.add(new IfNotInRegion(this));

        /** List World **/
        conditions.add(new IfInWorld(this));
        conditions.add(new IfNotInWorld(this));

        /** List Effects with level **/
        conditions.add(new IfPlayerHasEffect(this));
        conditions.add(new IfPlayerHasEffectEquals(this));
        conditions.add(new IfPlayerNotHasEffect(this));

        /** Has executableitems **/
        conditions.add(new IfHasExecutableItems(this));
        conditions.add(new IfHasNotExecutableItems(this));

        /** Has item **/
        conditions.add(new IfHasItems(this));
        conditions.add(new IfHasNotItems(this));

    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            for (PlayerConditionFeature condition : conditions) {
                error.addAll(condition.load(plugin, section, isPremiumLoading));
            }
        }

        return error;
    }

    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event) {
        for (PlayerConditionFeature condition : conditions) {
            if (!condition.verifCondition(player, playerOpt, messageSender, event)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (PlayerConditionFeature condition : conditions) {
            condition.save(section);
        }
    }

    @Override
    public PlayerConditionsFeature getValue() {
        return this;
    }

    @Override
    public PlayerConditionsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7Player condition(s) enabled: &e" + getPlayerConditionEnabledCount();
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    public int getPlayerConditionEnabledCount() {
        int i = 0;
        for (PlayerConditionFeature condition : conditions) {
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
    public PlayerConditionsFeature clone(FeatureParentInterface newParent) {
        PlayerConditionsFeature clone = new PlayerConditionsFeature(newParent, getName(), getEditorName(), getEditorDescription());
        List<PlayerConditionFeature> clones = new ArrayList<>();
        for (PlayerConditionFeature condition : conditions) {
            clones.add((PlayerConditionFeature) condition.clone(clone));
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
            if (feature instanceof PlayerConditionsFeature && feature.getName().equals(getName())) {
                PlayerConditionsFeature bCF = (PlayerConditionsFeature) feature;
                List<PlayerConditionFeature> clones = new ArrayList<>();
                for (PlayerConditionFeature condition : conditions) {
                    clones.add((PlayerConditionFeature) condition);
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
        PlayerConditionsFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
