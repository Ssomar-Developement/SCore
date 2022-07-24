package com.ssomar.score.newprojectiles.features;

/* public class GravityFeature extends DecorateurCustomProjectiles {

    boolean isGravity;

    public GravityFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        isGravity = true;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        isGravity = projConfig.getBoolean("gravity", true);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("gravity", isGravity);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        e.setGravity(isGravity);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        Material mat = Material.FEATHER;
        if (!SCore.is1v11Less()) mat = Material.ELYTRA;
        gui.addItem(mat, 1, GUI.TITLE_COLOR + "Gravity", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(GUI.TITLE_COLOR + "Gravity", isGravity);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeBounce = StringConverter.decoloredString(GUI.TITLE_COLOR + "Gravity");

        if (itemName.equals(changeBounce)) {
            boolean bool = gui.getBoolean(GUI.TITLE_COLOR + "Gravity");
            gui.updateBoolean(GUI.TITLE_COLOR + "Gravity", !bool);
        } else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        isGravity = gui.getBoolean(GUI.TITLE_COLOR + "Gravity");
    }
}*/
