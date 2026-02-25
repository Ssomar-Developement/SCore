package com.ssomar.score.features.custom.blocksAttacksFeatures.DamageReductionFeatures.group;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.blocksAttacksFeatures.DamageReductionFeatures.DamageReductionFeature;
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
import java.util.*;

@Getter
@Setter
public class DamageReductionGroupFeature extends FeatureWithHisOwnEditor<DamageReductionGroupFeature, DamageReductionGroupFeature, DamageReductionGroupFeatureEditor, DamageReductionGroupFeatureEditorManager> implements FeaturesGroup<DamageReductionFeature> {

    private Map<String, DamageReductionFeature> reductions;
    private boolean notSaveIfNoValue;

    public DamageReductionGroupFeature(FeatureParentInterface parent, boolean notSaveIfNoValue) {
        super(parent, FeatureSettingsSCore.damageReductions);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.reductions = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String enchantmentID : enchantmentsSection.getKeys(false)) {
                //ConfigurationSection enchantmentSection = enchantmentsSection.getConfigurationSection(enchantmentID);
                DamageReductionFeature enchantment = new DamageReductionFeature(this, enchantmentID);
                List<String> subErrors = enchantment.load(plugin, enchantmentsSection, isPremiumLoading);
                if (!subErrors.isEmpty()) {
                    error.addAll(subErrors);
                    continue;
                }
                reductions.put(enchantmentID, enchantment);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfNoValue && reductions.isEmpty()) return;
        ConfigurationSection enchantmentsSection = config.createSection(this.getName());
        for (String enchantmentID : reductions.keySet()) {
            reductions.get(enchantmentID).save(enchantmentsSection);
        }
        if(isSavingOnlyIfDiffDefault() && enchantmentsSection.getKeys(false).isEmpty()){
            config.set(getName(), null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));


    }

    @Override
    public DamageReductionGroupFeature getValue() {
        return this;
    }

    public void setValues(List<DamageReduction> reductions){
        this.reductions.clear();
        int i = 0;
        for (DamageReduction reduction : reductions) {
            DamageReductionFeature reductionFeature = new DamageReductionFeature(this, i + "");
            reductionFeature.fromDamageReduction(reduction);
            this.reductions.put(i+"", reductionFeature);
            i++;
        }
    }

    @Override
    public DamageReductionGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oDamageReduction(s) added: &e" + reductions.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public DamageReductionFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (DamageReductionFeature x : reductions.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public DamageReductionGroupFeature clone(FeatureParentInterface newParent) {
        DamageReductionGroupFeature eF = new DamageReductionGroupFeature(newParent, isNotSaveIfNoValue());
        HashMap<String, DamageReductionFeature> newReduction = new HashMap<>();
        for (String x : reductions.keySet()) {
            newReduction.put(x, reductions.get(x).clone(eF));
        }
        eF.setReductions(newReduction);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(reductions.values());
    }

    @Override
    public String getParentInfo() {
        if (getParent() == this) {
            return "";
        } else return getParent().getParentInfo();
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof DamageReductionGroupFeature) {
                DamageReductionGroupFeature eF = (DamageReductionGroupFeature) feature;
                eF.setReductions(getReductions());
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
        DamageReductionGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "damageReduction";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!reductions.containsKey(id)) {
                DamageReductionFeature eF = new DamageReductionFeature(this, id);
                reductions.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, DamageReductionFeature feature) {
        reductions.remove(feature.getId());
    }

    public List<DamageReduction> asList() {
        List<DamageReduction> list = new ArrayList<>();
        for (DamageReductionFeature reduction : reductions.values()) {
            list.add(reduction.asDamageReduction());
        }
        return list;
    }
}
