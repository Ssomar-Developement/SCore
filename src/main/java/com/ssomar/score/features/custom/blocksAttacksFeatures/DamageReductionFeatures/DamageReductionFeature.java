package com.ssomar.score.features.custom.blocksAttacksFeatures.DamageReductionFeatures;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.DoubleFeature;
import com.ssomar.score.features.types.list.ListDamageTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import io.papermc.paper.datacomponent.item.blocksattacks.DamageReduction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class DamageReductionFeature extends FeatureWithHisOwnEditor<DamageReductionFeature, DamageReductionFeature, GenericFeatureParentEditor, GenericFeatureParentEditorManager>  {

    private DoubleFeature baseDamageBlocked;
    private DoubleFeature factorDamageBlocked;
    private DoubleFeature horizontalBlockingAngle;
    private ListDamageTypeFeature damageTypes;

    private String id;

    public DamageReductionFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.damageReduction);
        this.id = id;
        reset();
    }

    public DamageReduction asDamageReduction() {
        return DamageReduction.damageReduction().base(baseDamageBlocked.getValue().get().floatValue())
                .factor(factorDamageBlocked.getValue().get().floatValue())
                .horizontalBlockingAngle(horizontalBlockingAngle.getValue().get().floatValue())
                .type(damageTypes.asRegistryKeySet())
                .build();
    }

    public void fromDamageReduction(DamageReduction damageReduction) {
        baseDamageBlocked.setValue(Optional.of((double) damageReduction.base()));
        factorDamageBlocked.setValue(Optional.of((double) damageReduction.factor()));
        horizontalBlockingAngle.setValue(Optional.of((double) damageReduction.horizontalBlockingAngle()));
        damageTypes.fromRegistryKeySet(damageReduction.type());
    }

    @Override
    public void reset() {
        baseDamageBlocked = new DoubleFeature(this, Optional.of(0.0), FeatureSettingsSCore.baseDamageBlocked);
        factorDamageBlocked = new DoubleFeature(this, Optional.of(0.0), FeatureSettingsSCore.factorDamageBlocked);
        horizontalBlockingAngle = new DoubleFeature(this, Optional.of(90.0), FeatureSettingsSCore.horizontalBlockingAngle);
        damageTypes = new ListDamageTypeFeature(this, new ArrayList<>(), FeatureSettingsSCore.damageTypes);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(baseDamageBlocked.load(plugin, section, isPremiumLoading));
            errors.addAll(factorDamageBlocked.load(plugin, section, isPremiumLoading));
            errors.addAll(horizontalBlockingAngle.load(plugin, section, isPremiumLoading));
            if(horizontalBlockingAngle.getValue().orElse(90.0) <=0){
                errors.add("&cERROR, The horizontal blocking angle must be greater than 0 &7&o" + getParent().getParentInfo());
                horizontalBlockingAngle.setValue(Optional.of(90.0));
            }
            errors.addAll(damageTypes.load(plugin, section, isPremiumLoading));

        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection section = config.createSection(id);
        baseDamageBlocked.save(section);
        factorDamageBlocked.save(section);
        horizontalBlockingAngle.save(section);
        damageTypes.save(section);
        if (isSavingOnlyIfDiffDefault() && section.getKeys(false).isEmpty()) {
            config.set(id, null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(id, StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    @Override
    public DamageReductionFeature getValue() {
        return this;
    }

    @Override
    public DamageReductionFeature initItemParentEditor(GUI gui, int slot) {
        int len = 5;
        String[] finalDescription = new String[getEditorDescription().length + len];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - len] = GUI.CLICK_HERE_TO_CHANGE;
        len--;

        finalDescription[finalDescription.length - len] = "&7Base Damage Blocked: &e" + baseDamageBlocked.getValue().get();
        len--;
        finalDescription[finalDescription.length - len] = "&7Factor Damage Blocked: &e" + factorDamageBlocked.getValue().get();
        len--;
        finalDescription[finalDescription.length - len] = "&7Horizontal Blocking Angle: &e" + horizontalBlockingAngle.getValue().get();
        len--;
        finalDescription[finalDescription.length - len] = "&7Damage Types: &e" + damageTypes.getValues();
        len--;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public DamageReductionFeature clone(FeatureParentInterface newParent) {
        DamageReductionFeature dropFeatures = new DamageReductionFeature(newParent, id);
        dropFeatures.setBaseDamageBlocked(baseDamageBlocked.clone(dropFeatures));
        dropFeatures.setFactorDamageBlocked(factorDamageBlocked.clone(dropFeatures));
        dropFeatures.setHorizontalBlockingAngle(horizontalBlockingAngle.clone(dropFeatures));
        dropFeatures.setDamageTypes(damageTypes.clone(dropFeatures));

        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(baseDamageBlocked);
        features.add(factorDamageBlocked);
        features.add(horizontalBlockingAngle);
        features.add(damageTypes);
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
            if (feature instanceof DamageReductionFeature) {
                DamageReductionFeature hiders = (DamageReductionFeature) feature;
                hiders.setBaseDamageBlocked(baseDamageBlocked);
                hiders.setFactorDamageBlocked(factorDamageBlocked);
                hiders.setHorizontalBlockingAngle(horizontalBlockingAngle);
                hiders.setDamageTypes(damageTypes);
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

}
