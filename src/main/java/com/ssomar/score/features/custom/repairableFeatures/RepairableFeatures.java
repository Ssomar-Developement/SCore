package com.ssomar.score.features.custom.repairableFeatures;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Repairable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RepairableFeatures extends FeatureWithHisOwnEditor<RepairableFeatures, RepairableFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItem {

    private BooleanFeature enable;
    private IntegerFeature repairCost;

    public RepairableFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.repairableFeatures);
        reset();
    }

    @Override
    public void reset() {
        enable = new BooleanFeature(this, false, FeatureSettingsSCore.enable);
        repairCost = new IntegerFeature(this, Optional.of(2), FeatureSettingsSCore.repairCost);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            for (FeatureInterface feature : getFeatures()) {
                errors.addAll(feature.load(plugin, section, isPremiumLoading));
            }

        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (FeatureInterface feature : getFeatures()) {
            feature.save(section);
        }
        if(isSavingOnlyIfDiffDefault() && section.getKeys(false).isEmpty()){
            config.set(getName(), null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    @Override
    public RepairableFeatures getValue() {
        return this;
    }

    @Override
    public RepairableFeatures initItemParentEditor(GUI gui, int slot) {
        int len = 3;
        String[] finalDescription = new String[getEditorDescription().length + len];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - len] = GUI.CLICK_HERE_TO_CHANGE;
        len--;

        finalDescription[finalDescription.length - len] = "&7Enable: &e" + (enable.getValue() ? "&a&l✔" : "&c&l✘");
        len--;
        finalDescription[finalDescription.length - len] = "&7Repair Cost: &e" + repairCost.getValue().get();
        len--;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }


    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RepairableFeatures clone(FeatureParentInterface newParent) {
        RepairableFeatures dropFeatures = new RepairableFeatures(newParent);
        dropFeatures.setEnable(enable.clone(dropFeatures));
        dropFeatures.setRepairCost(repairCost.clone(dropFeatures));

        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(enable);
        features.add(repairCost);
        return features;
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof RepairableFeatures) {
                RepairableFeatures hiders = (RepairableFeatures) feature;
                hiders.setEnable(enable);
                hiders.setRepairCost(repairCost);
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        GenericFeatureParentEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public boolean isAvailable() {
        return SCore.is1v21v2Plus();
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {
        return args.getMeta() instanceof Repairable;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {

        if (!isAvailable() || !isApplicable(args)) return;
        if (enable.getValue()) {
            Repairable repairable = (Repairable) args.getMeta();
            repairable.setRepairCost(repairCost.getValue().get());
        }

    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {

        if (!isAvailable() || !isApplicable(args)) return;
        Repairable repairable = (Repairable) args.getMeta();
        if(((Repairable) args.getMeta()).hasRepairCost()) {
            enable.setValue(true);
            repairCost.setValue(Optional.of(repairable.getRepairCost()));
        }
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.REPAIRABLE;
    }
}
