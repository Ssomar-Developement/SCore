package com.ssomar.score.features.custom.weaponFeatures;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.strings.StringConverter;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Weapon;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class WeaponFeatures extends FeatureWithHisOwnEditor<WeaponFeatures, WeaponFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItemNewPaperComponents {

    private BooleanFeature enable;
    private IntegerFeature disableBlockingTime;
    private IntegerFeature damagePerAttack;

    public WeaponFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.weaponFeatures);
        reset();
    }

    @Override
    public void reset() {
        enable = new BooleanFeature(this, true, FeatureSettingsSCore.enable);
        disableBlockingTime = new IntegerFeature(this, Optional.of(0), FeatureSettingsSCore.disableBlockingTime);
        damagePerAttack = new IntegerFeature(this, Optional.of(5), FeatureSettingsSCore.damagePerAttack);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(enable.load(plugin, section, isPremiumLoading));
            errors.addAll(disableBlockingTime.load(plugin, section, isPremiumLoading));
            errors.addAll(damagePerAttack.load(plugin, section, isPremiumLoading));

        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        enable.save(section);
        disableBlockingTime.save(section);
        damagePerAttack.save(section);
        if (isSavingOnlyIfDiffDefault() && section.getKeys(false).isEmpty()) {
            config.set(getName(), null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    @Override
    public WeaponFeatures getValue() {
        return this;
    }

    @Override
    public WeaponFeatures initItemParentEditor(GUI gui, int slot) {
        int len = 5;
        if (!SCore.is1v21v2Plus()) len = 6;
        String[] finalDescription = new String[getEditorDescription().length + len];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - len] = GUI.CLICK_HERE_TO_CHANGE;
        len--;

        if (enable.getValue())
            finalDescription[finalDescription.length - len] = "&7Enabled: &a&l✔";
        else
            finalDescription[finalDescription.length - len] = "&7Disabled: &c&l✘";
        len--;
        finalDescription[finalDescription.length - len] = "&7Disable Blocking Time: &e" + disableBlockingTime.getValue().get();
        len--;
        finalDescription[finalDescription.length - len] = "&7Damage Per Attack: &e" + damagePerAttack.getValue().get();
        len--;


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public boolean isAvailable() {
        return SCore.isPaperOrFork() && SCore.is1v21v5Plus();
    }

    @Override
    public boolean isApplicable(FeatureForItemArgs args) {
        return true;
    }

    @Override
    public void applyOnItemMeta(FeatureForItemArgs args) {

    }

    @Override
    public void loadFromItemMeta(FeatureForItemArgs args) {

    }

    @Override
    public void applyOnItem(@NotNull FeatureForItemArgs args) {
        if (isAvailable()) {
            if (enable.getValue()) {
                ItemStack item = args.getItem();
                SsomarDev.testMsg("Weapon features applyOnItem2", true);
                try {
                    item.setData(DataComponentTypes.WEAPON, Weapon.weapon().disableBlockingForSeconds(disableBlockingTime.getValue().get()).itemDamagePerAttack(damagePerAttack.getValue().get()));
                } catch (Exception e) {
                    Utils.sendConsoleMsg(SCore.plugin, "&cError while applying the weapon features on an item");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void loadFromItem(@NotNull FeatureForItemArgs args) {
        if (isAvailable()) {
            ItemStack item = args.getItem();
            if (item.hasData(DataComponentTypes.WEAPON)) {
                Weapon weapon = item.getData(DataComponentTypes.WEAPON);
                enable.setValue(true);
                disableBlockingTime.setValue(Optional.of((int)weapon.disableBlockingForSeconds()));
                damagePerAttack.setValue(Optional.of((int)weapon.itemDamagePerAttack()));
            }
        }
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.WEAPON;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public WeaponFeatures clone(FeatureParentInterface newParent) {
        WeaponFeatures dropFeatures = new WeaponFeatures(newParent);
        dropFeatures.setEnable(enable.clone(dropFeatures));
        dropFeatures.setDisableBlockingTime(disableBlockingTime.clone(dropFeatures));
        dropFeatures.setDamagePerAttack(damagePerAttack.clone(dropFeatures));

        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(enable);
        features.add(disableBlockingTime);
        features.add(damagePerAttack);
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
            if (feature instanceof WeaponFeatures) {
                WeaponFeatures hiders = (WeaponFeatures) feature;
                hiders.setEnable(enable);
                hiders.setDisableBlockingTime(disableBlockingTime);
                hiders.setDamagePerAttack(damagePerAttack);
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
