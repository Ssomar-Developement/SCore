package com.ssomar.score.features.custom.potionsettings;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.potioneffects.group.PotionEffectGroupFeature;
import com.ssomar.score.features.custom.potioneffects.potioneffect.PotionEffectFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColorIntegerFeature;
import com.ssomar.score.features.types.PotionTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.features.SProjectileFeatureInterface;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class PotionSettingsFeature extends FeatureWithHisOwnEditor<PotionSettingsFeature, PotionSettingsFeature, PotionSettingsFeatureEditor, PotionSettingsFeatureEditorManager> implements SProjectileFeatureInterface {

    private ColorIntegerFeature color;
    private PotionTypeFeature potiontype;
    private BooleanFeature potionExtended;
    private BooleanFeature potionUpgraded;
    private PotionEffectGroupFeature potionEffects;

    public PotionSettingsFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.potionSettings);
        reset();
    }

    @Override
    public void reset() {
        this.color = new ColorIntegerFeature(this, Optional.empty(), FeatureSettingsSCore.potionColor);
        this.potiontype = new PotionTypeFeature(this, Optional.ofNullable(PotionType.WATER), FeatureSettingsSCore.potionType);
        this.potionExtended = new BooleanFeature(this, false, FeatureSettingsSCore.potionExtended, false);
        this.potionUpgraded = new BooleanFeature(this, false, FeatureSettingsSCore.potionUpgraded, false);
        this.potionEffects = new PotionEffectGroupFeature(this, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection potionSettings = config.getConfigurationSection(getName());
            errors.addAll(this.color.load(plugin, potionSettings, isPremiumLoading));
            errors.addAll(this.potiontype.load(plugin, potionSettings, isPremiumLoading));
            if(!SCore.is1v20v5Plus()) {
                errors.addAll(this.potionExtended.load(plugin, potionSettings, isPremiumLoading));
                errors.addAll(this.potionUpgraded.load(plugin, potionSettings, isPremiumLoading));
            }
            errors.addAll(this.potionEffects.load(plugin, potionSettings, isPremiumLoading));
            //SsomarDev.testMsg("§aPotion settings loaded for the feature §e"+this.getName(), true);
        }
        else {
            //SsomarDev.testMsg("§cNo potion settings found for the feature §e"+this.getName(), true);
        }

        return errors;
    }

    public List<String> load(SPlugin plugin, ItemStack item, boolean isPremiumLoading) {
        ItemMeta meta;
        if (item.hasItemMeta() && (meta = item.getItemMeta()) instanceof PotionMeta) {
            PotionMeta pMeta = ((PotionMeta) meta);
            if (!SCore.is1v11Less()) {
                if (pMeta.getColor() != null) color.setValue(Optional.of(pMeta.getColor().asRGB()));
                //potiontype.setValue(Optional.of(pMeta.getBasePotionData().getType()));
                //potionExtended.setValue(pMeta.getBasePotionData().isExtended());
                //potionUpgraded.setValue(pMeta.getBasePotionData().isUpgraded());
            }

            int i = 0;
            for (PotionEffect pE : pMeta.getCustomEffects()) {
                PotionEffectFeature pEF = new PotionEffectFeature(potionEffects, "pEffect" + i);
                pEF.getType().setValue(Optional.of(pE.getType()));
                pEF.getAmplifier().setValue(Optional.of(pE.getAmplifier()));
                pEF.getDuration().setValue(Optional.of(pE.getDuration()));
                pEF.getAmbient().setValue(pE.isAmbient());
                pEF.getParticles().setValue(pE.hasParticles());
                pEF.getIcon().setValue(pE.hasIcon());
                potionEffects.getEffects().put("pEffect" + i, pEF);
                i++;
            }
        }
        return new ArrayList<>();
    }

    public ItemStack clear(@NotNull ItemStack item) {
        ItemMeta meta;
        if (item.hasItemMeta() && (meta = item.getItemMeta()) instanceof PotionMeta) {
            PotionMeta pMeta = ((PotionMeta) meta);
            pMeta.clearCustomEffects();
            item.setItemMeta(pMeta);
        }
        return item;
    }

    public ItemStack addTo(@NotNull ItemStack item) {
        //SsomarDev.testMsg("§aPotion settings added to the item §e"+potiontype.getValue().get(), true);
        //SsomarDev.testMsg("§aPotion settings added to the item §e"+item.getType()+ " has meta "+item.hasItemMeta(), true);
        ItemMeta meta;
        if (item.hasItemMeta() && (meta = item.getItemMeta()) instanceof PotionMeta) {
            //SsomarDev.testMsg("§aPotion settings 2", true);
            PotionMeta pMeta = ((PotionMeta) meta);
            if (!SCore.is1v11Less()) {
               // SsomarDev.testMsg("§aPotion settings 3", true);
                if (color.getValue().isPresent()) pMeta.setColor(Color.fromRGB(color.getValue().get()));
                if (potiontype.getValue().isPresent()) {
                   // SsomarDev.testMsg("§aPotion settings 4", true);
                    if(SCore.is1v20v5Plus()){
                        pMeta.setBasePotionType(potiontype.getValue().get());
                    }
                    else {
                        try {
                            PotionData data = new PotionData(potiontype.getValue().get(), potionExtended.getValue() && potiontype.getValue().get().isExtendable(), potionUpgraded.getValue() && potiontype.getValue().get().isUpgradeable());
                            PotionMeta.class.getMethod("setBasePotionData", PotionData.class).invoke(pMeta, data);
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
            }
            for (PotionEffectFeature pE : this.potionEffects.getEffects().values()) {
                pMeta.addCustomEffect(pE.getPotionEffect(), true);
            }
            //SsomarDev.testMsg("§aPotion settings 5", true);
            item.setItemMeta(pMeta);
        }

        return item;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection potionSettings = config.createSection(getName());
        color.save(potionSettings);
        potiontype.save(potionSettings);
        if(!SCore.is1v20v5Plus()) {
            potionExtended.save(potionSettings);
            potionUpgraded.save(potionSettings);
        }
        potionEffects.save(potionSettings);
    }

    @Override
    public PotionSettingsFeature getValue() {
        return this;
    }

    @Override
    public PotionSettingsFeature initItemParentEditor(GUI gui, int slot) {
        int settingsNumber = 6;
        if(SCore.is1v20v5Plus()) settingsNumber = 4;

        String[] finalDescription = new String[getEditorDescription().length + settingsNumber];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - settingsNumber] = GUI.CLICK_HERE_TO_CHANGE;
        settingsNumber--;

        if (SCore.is1v11Less())
            finalDescription[finalDescription.length - settingsNumber] = "&7Potion color : &c&lNot for 1.11 or lower";
        else if (color.getValue().isPresent())
            finalDescription[finalDescription.length - settingsNumber] = "&7Potion color : &e" + color.getValue().get();
        else finalDescription[finalDescription.length - settingsNumber] = "&7Potion color : &cNO VALUE";
        settingsNumber--;

        if (SCore.is1v11Less())
            finalDescription[finalDescription.length - settingsNumber] = "&7Potion type : &c&lNot for 1.11 or lower";
        else finalDescription[finalDescription.length - settingsNumber] = "&7Potion type : &e" + potiontype.getValue().get();
        settingsNumber--;

        if(!SCore.is1v20v5Plus()) {
            if (SCore.is1v11Less())
                finalDescription[finalDescription.length - settingsNumber] = "&7Potion extended : &c&lNot for 1.11 or lower";
            else if (potionExtended.getValue())
                finalDescription[finalDescription.length - settingsNumber] = "&7Potion extended: &a&l✔";
            else
                finalDescription[finalDescription.length - settingsNumber] = "&7Potion extended: &c&l✘";
            settingsNumber--;

            if (SCore.is1v11Less())
                finalDescription[finalDescription.length - settingsNumber] = "&7Potion upgraded : &c&lNot for 1.11 or lower";
            else if (potionUpgraded.getValue())
                finalDescription[finalDescription.length - settingsNumber] = "&7Potion upgraded: &a&l✔";
            else
                finalDescription[finalDescription.length - settingsNumber] = "&7Potion upgraded: &c&l✘";
            settingsNumber--;
        }

        finalDescription[finalDescription.length - settingsNumber] = "&7Potion effects: &e" + potionEffects.getValue().getEffects().size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public PotionSettingsFeature clone(FeatureParentInterface newParent) {
        PotionSettingsFeature dropFeatures = new PotionSettingsFeature(newParent);
        dropFeatures.setColor(getColor().clone(dropFeatures));
        dropFeatures.setPotiontype(getPotiontype().clone(dropFeatures));
        dropFeatures.setPotionExtended(getPotionExtended().clone(dropFeatures));
        dropFeatures.setPotionUpgraded(getPotionUpgraded().clone(dropFeatures));
        dropFeatures.setPotionEffects(getPotionEffects().clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        if (!SCore.is1v11Less()) {
            features.add(color);
            features.add(potiontype);
            features.add(potionExtended);
            features.add(potionUpgraded);
        }
        features.add(potionEffects);
        return features;
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
            if (feature instanceof PotionSettingsFeature) {
                PotionSettingsFeature hiders = (PotionSettingsFeature) feature;
                hiders.setColor(getColor());
                hiders.setPotiontype(getPotiontype());
                hiders.setPotionExtended(getPotionExtended());
                hiders.setPotionUpgraded(getPotionUpgraded());
                hiders.setPotionEffects(getPotionEffects());
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
        PotionSettingsFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof ThrownPotion) {
            ThrownPotion lp = (ThrownPotion) e;
            try {
                ItemStack item = lp.getItem();
                item = addTo(item);
                lp.setItem(item);
            } catch (NoSuchMethodError ignored) {
            }
        }
    }

}
