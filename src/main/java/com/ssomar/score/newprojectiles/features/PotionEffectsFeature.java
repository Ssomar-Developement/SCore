package com.ssomar.score.newprojectiles.features;

/* public class PotionEffectsFeature extends DecorateurCustomProjectiles {

    private List<PotionEffect> potionEffects;
    private boolean isLingering;

    public PotionEffectsFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        potionEffects = new ArrayList<>();
        isLingering = false;
    }

    public PotionEffectsFeature(CustomProjectile cProj, boolean isLingering) {
        super.cProj = cProj;
        potionEffects = new ArrayList<>();
        this.isLingering = isLingering;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        if (projConfig.isConfigurationSection("potionEffects"))
            potionEffects = this.loadPotionEffects(projConfig.getConfigurationSection("potionEffects"), showError);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        // #TODO save potion effects
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof ThrownPotion) {
            ThrownPotion lp = (ThrownPotion) e;

            try {
                ItemStack item = lp.getItem();
                if (isLingering) item.setType(Material.LINGERING_POTION);
                else item.setType(Material.SPLASH_POTION);
                //SsomarDev.testMsg("item::: "+item.getType());
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

    public List<PotionEffect> loadPotionEffects(ConfigurationSection config, boolean showError) {
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
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        // TODO effects GUI
        gui.addItem(Material.POTION, 1, GUI.TITLE_COLOR + "Potion effects", false, false, "&c&oNOT EDITABLE IN GAME FOR THE MOMENT");
        return gui;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
    }

}*/
