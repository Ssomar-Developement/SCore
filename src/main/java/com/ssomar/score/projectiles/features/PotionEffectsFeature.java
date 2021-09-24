package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectsFeature extends DecorateurCustomProjectiles {

    private List<PotionEffect> potionEffects;

    public PotionEffectsFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        potionEffects = new ArrayList<>();
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        if (projConfig.isConfigurationSection("potionEffects"))
            potionEffects = this.loadPotionEffects(projConfig.getConfigurationSection("potionEffects"));
        return cProj.loadConfiguration() && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof ThrownPotion) {
            ThrownPotion lp = (ThrownPotion) e;

            try {
                ItemStack item = lp.getItem();
                PotionMeta pMeta = (PotionMeta) item.getItemMeta();
                for (PotionEffect pE : this.potionEffects) {
                    pMeta.addCustomEffect(pE, true);
                }
                item.setItemMeta((ItemMeta) pMeta);
                lp.setItem(item);
            } catch (NoSuchMethodError exception) {
            }
        }
        cProj.transformTheProjectile(e, launcher);
    }

    public List<PotionEffect> loadPotionEffects(ConfigurationSection config) {
        List<PotionEffect> potionEffects = new ArrayList<>();
        for (String effectID : config.getKeys(false)) {
            ConfigurationSection effectSection = config.getConfigurationSection(effectID);
            if (effectSection.contains("potionEffectType")) {
                PotionEffectType pET = null;
                try {
                    for (PotionEffectType value : PotionEffectType.values()) {
                        if (value.getName().equalsIgnoreCase(effectSection.getString("potionEffectType", ""))) {
                            pET = value;
                            break;
                        }
                    }
                    if (pET == null) {
                        // if (showError)
                        continue;
                    }
                    int duration = effectSection.getInt("duration", 30);
                    duration = duration * 20;
                    int amplifier = effectSection.getInt("amplifier", 0);
                    boolean isAmbient = effectSection.getBoolean("isAmbient", true);
                    boolean hasParticles = effectSection.getBoolean("hasParticles", true);

                    PotionEffect pE = new PotionEffect(pET, duration, amplifier, isAmbient, hasParticles);
                    potionEffects.add(pE);
                } catch (Exception e) {
                    continue;
                }
            } else {
                // if (showError)
                continue;
            }
        }
        return potionEffects;
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.BELL, 1, gui.TITLE_COLOR+"Potion effects", false, false, gui.CLICK_HERE_TO_CHANGE);
        return gui;
    }

}
