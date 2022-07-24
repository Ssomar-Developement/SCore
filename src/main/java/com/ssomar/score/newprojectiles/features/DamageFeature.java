package com.ssomar.score.newprojectiles.features;

/* public class DamageFeature extends DecorateurCustomProjectiles {

    private static final Boolean DEBUG = false;
    double damage;
    boolean askDamage;

    public DamageFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        damage = -1;
        askDamage = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        damage = projConfig.getDouble("damage", -1);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        if (DEBUG) SsomarDev.testMsg("Save damage: " + damage);
        config.set("damage", damage);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (DEBUG) SsomarDev.testMsg("Passage damage: + is abstract arrow " + (e instanceof AbstractArrow));
        if (e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (damage != -1) {
                if (DEBUG) SsomarDev.testMsg("Damage: " + damage);
                aA.setDamage(damage);
            }
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.DIAMOND_SWORD, 1, GUI.TITLE_COLOR + "Damage", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if (damage == -1) gui.updateActually(GUI.TITLE_COLOR + "Damage", "&cVANILLA DAMAGE");
        else gui.updateDouble(GUI.TITLE_COLOR + "Damage", damage);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change = StringConverter.decoloredString(GUI.TITLE_COLOR + "Damage");

        if (itemName.equals(change)) {
            requestChat = true;
            askDamage = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the new &eDamage&a: &7&o(Number)"));
            return true;
        }
        return false;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        if (gui.getActually(GUI.TITLE_COLOR + "Damage").contains("VANILLA DAMAGE")) damage = -1;
        else damage = gui.getDouble(GUI.TITLE_COLOR + "Damage");
        if (DEBUG) SsomarDev.testMsg("Extracted damage: " + damage);
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message) {
        if (cProj.messageForConfig(gui, player, message)) return true;
        if (askDamage) {
            double newDamage;
            try {
                newDamage = Double.valueOf(StringConverter.decoloredString(message));
            } catch (NumberFormatException e) {
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid number for the setting damage (" + message + ")"));
                return true;
            }
            if (newDamage == -1) gui.updateActually(GUI.TITLE_COLOR + "Damage", "&cVANILLA DAMAGE");
            else gui.updateDouble(GUI.TITLE_COLOR + "Damage", newDamage);
            gui.openGUISync(player);
            askDamage = false;
            requestChat = false;
            return true;
        }
        return false;
    }

}*/
