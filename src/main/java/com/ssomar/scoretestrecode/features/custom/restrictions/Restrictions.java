package com.ssomar.scoretestrecode.features.custom.restrictions;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter @Setter
public class Restrictions extends FeatureWithHisOwnEditor<Restrictions, Restrictions, RestrictionsEditor, RestrictionsEditorManager> {

    private Map<RestrictionEnum, BooleanFeature> restrictions;
    private final static boolean NOT_SAVE_RESTRICTIONS = true;

    public Restrictions(FeatureParentInterface parent) {
        super(parent, "restrictions", "Restrictions", new String[]{"&7&oThe restrictions features"}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        restrictions = new HashMap<>();
        restrictions.put(RestrictionEnum.CANCEL_DROP, new BooleanFeature(this, "cancel-item-drop", false, "Cancel Item Drop", new String[]{"&7&oCancel the drop of the item"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_PLACE, new BooleanFeature(this, "cancel-item-place", false, "Cancel Item Placement", new String[]{"&7&oCancel the placement of the item"}, Material.LEVER,false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_TOOL_INTERACTIONS, new BooleanFeature(this, "cancel-tool-interactions", false, "Cancel Tool Interactions", new String[]{"&7&oCancel the interactions of the tool"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_CONSUMPTION, new BooleanFeature(this, "cancel-consumption", false, "Cancel Consumption", new String[]{"&7&oThe item can't be", "&7&oconsumed"}, Material.POTION, false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_CRAFT, new BooleanFeature(this, "cancel-item-craft-no-custom", false, "Cancel Craft", new String[]{"&7&oThe item can't be used", "&7&oto craft vanilla item"}, Material.CRAFTING_TABLE, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ALL_CRAFT, new BooleanFeature(this, "cancel-item-craft", false, "Cancel All Craft", new String[]{"&7&oThe item can't be used", "&7&oto craft any item", "&7&o(Even custom items)"}, Material.CRAFTING_TABLE, false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_DEPOSIT_IN_CHEST, new BooleanFeature(this, "cancel-deposit-in-chest", false, "Cancel Deposit in Chest", new String[]{"&7&oThe item can't be", "&7&oto deposit in a chest"}, Material.CHEST, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_DEPOSIT_IN_FURNACE, new BooleanFeature(this, "cancel-deposit-in-furnace", false, "Cancel Deposit in Furnace", new String[]{"&7&oThe item can't be", "&7&oto deposit in a furnace"}, Material.FURNACE, false, NOT_SAVE_RESTRICTIONS));
        if(!SCore.is1v13Less()) restrictions.put(RestrictionEnum.CANCEL_STONE_CUTTER, new BooleanFeature(this, "cancel-stone-cutter", false, "Cancel Stone Cutter", new String[]{"&7&oThe item can't be", "&7&oplaced in stone cutter"}, Material.STONECUTTER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ENCHANT, new BooleanFeature(this, "cancel-enchant", false, "Cancel Enchant", new String[]{"&7&oThe item can't be", "&7&oenchanted"}, Material.ENCHANTING_TABLE, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_BREWING, new BooleanFeature(this, "cancel-brewing", false, "Cancel Brewing", new String[]{"&7&oThe item can't be", "&7&oplaced in a brewing stand"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ANVIL, new BooleanFeature(this, "cancel-anvil", false, "Cancel Anvil", new String[]{"&7&oThe item can't be", "&7&oplaced in an anvil"}, Material.ANVIL, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ACTION_ENCHANT_IN_ANVIL, new BooleanFeature(this, "cancel-enchant-anvil", false, "Cancel Action Enchant in Anvil", new String[]{"&7&oThe item can't be", "&7&oenchanted in an anvil"}, Material.ANVIL, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ACTION_RENAME_IN_ANVIL, new BooleanFeature(this, "cancel-rename-anvil", false, "Cancel Action Rename in Anvil", new String[]{"&7&oThe item can't be", "&7&orenamed in an anvil"}, Material.ANVIL, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_BEACON, new BooleanFeature(this, "cancel-beacon", false, "Cancel Beacon", new String[]{"&7&oThe item can't be", "&7&oplaced in a beacon"}, Material.BEACON, false, NOT_SAVE_RESTRICTIONS));
        if(!SCore.is1v13Less()) restrictions.put(RestrictionEnum.CANCEL_CARTOGRAPHY, new BooleanFeature(this, "cancel-cartography", false, "Cancel Cartography", new String[]{"&7&oThe item can't be", "&7&oplaced in a cartography table"}, Material.CARTOGRAPHY_TABLE, false, NOT_SAVE_RESTRICTIONS));
        if(!SCore.is1v13Less()) restrictions.put(RestrictionEnum.CANCEL_COMPOSTER, new BooleanFeature(this, "cancel-composter", false, "Cancel Composter", new String[]{"&7&oThe item can't be", "&7&oplaced in a composter"}, Material.COMPOSTER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_DISPENSER, new BooleanFeature(this, "cancel-dispenser", false, "Cancel Dispenser", new String[]{"&7&oThe item can't be", "&7&oplaced in a dispenser"}, Material.DISPENSER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_DROPPER, new BooleanFeature(this, "cancel-dropper", false, "Cancel Dropper", new String[]{"&7&oThe item can't be", "&7&oplaced in a dropper"}, Material.DROPPER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_HOPPER, new BooleanFeature(this, "cancel-hopper", false, "Cancel Hopper", new String[]{"&7&oThe item can't be", "&7&oplaced in a hopper"}, Material.HOPPER, false, NOT_SAVE_RESTRICTIONS));
        if(!SCore.is1v13Less()) restrictions.put(RestrictionEnum.CANCEL_LECTERN, new BooleanFeature(this, "cancel-lectern", false, "Cancel Lectern", new String[]{"&7&oThe item can't be", "&7&oplaced in a lectern"}, Material.LECTERN, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_LOOM, new BooleanFeature(this, "cancel-loom", false, "Cancel Loom", new String[]{"&7&oThe item can't be", "&7&oplaced in a loom"}, Material.LOOM, false, NOT_SAVE_RESTRICTIONS));
        if(!SCore.is1v13Less()) restrictions.put(RestrictionEnum.CANCEL_GRIND_STONE, new BooleanFeature(this, "cancel-grind-stone", false, "Cancel Grind Stone", new String[]{"&7&oThe item can't be", "&7&oplaced in a grind stone"}, Material.GRINDSTONE, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ITEM_FRAME, new BooleanFeature(this, "cancel-item-frame", false, "Cancel Item Frame", new String[]{"&7&oThe item can't be", "&7&oplaced in an item frame"}, Material.ITEM_FRAME, false, NOT_SAVE_RESTRICTIONS));
        if(!SCore.is1v13Less()) restrictions.put(RestrictionEnum.CANCEL_SMITHING_TABLE, new BooleanFeature(this, "cancel-smithing-table", false, "Cancel Smithing Table", new String[]{"&7&oThe item can't be", "&7&oplaced in a smithing table"}, Material.SMITHING_TABLE, false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_MERCHANT, new BooleanFeature(this, "cancel-merchant", false, "Cancel Merchant", new String[]{"&7&oThe item can't be", "&7&otraded in a merchant"}, Material.VILLAGER_SPAWN_EGG, false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_ITEM_BURN, new BooleanFeature(this, "cancel-item-burn", false, "Cancel Item Burn", new String[]{"&7&oThe item can't burn"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ITEM_DELETE_BY_CACTUS, new BooleanFeature(this, "cancel-item-delete-by-cactus", false, "Cancel Item Delete by Cactus", new String[]{"&7&oThe item can't be", "&7&odestroyed by a cactus"}, Material.CACTUS, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ITEM_DELETE_BY_LIGHTNING, new BooleanFeature(this, "cancel-item-delete-by-lightning", false, "Cancel Item Delete by Lightning", new String[]{"&7&oThe item can't be", "&7&odestroyed by a lightning"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));


        if(!SCore.is1v11Less()) restrictions.put(RestrictionEnum.CANCEL_SWAPHAND, new BooleanFeature(this, "cancel-swap-hand", false, "Cancel Swap Hand", new String[]{"&7&oThe item can't be", "&7&oswaped between the two hands"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.LOCKED_INVENTORY, new BooleanFeature(this, "locked-in-inventory", false, "Locked Inventory", new String[]{"&7&oThe item is locked in the inventory"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if(config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            for (RestrictionEnum restriction : restrictions.keySet()) {
                restrictions.get(restriction).load(plugin, section, isPremiumLoading);
            }
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (RestrictionEnum restriction : restrictions.keySet()) {
            restrictions.get(restriction).save(section);
        }
    }

    @Override
    public Restrictions getValue() {
        return this;
    }

    public boolean is(RestrictionEnum ask){
        for(RestrictionEnum restriction : restrictions.keySet()){
            if(restriction.equals(ask)){
                return restrictions.get(restriction).getValue();
            }
        }
        return false;
    }

    public BooleanFeature get(RestrictionEnum ask){
        for(RestrictionEnum restriction : restrictions.keySet()){
            if(restriction.equals(ask)){
                return restrictions.get(restriction);
            }
        }
        return null;
    }

    public int getRestrictionCount() {
        int count = 0;
        for (RestrictionEnum restriction : restrictions.keySet()) {
            if (restrictions.get(restriction).getValue()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Restrictions initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Restrictions activated: &e" + getRestrictionCount();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public Restrictions clone() {
        Restrictions restrictions = new Restrictions(getParent());
        Map<RestrictionEnum, BooleanFeature> clone = new HashMap<>();
        for (RestrictionEnum restriction : this.restrictions.keySet()) {
            clone.put(restriction, this.restrictions.get(restriction).clone());
        }
        restrictions.setRestrictions(clone);
        return restrictions;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        for (RestrictionEnum restriction : restrictions.keySet()) {
            features.add(restrictions.get(restriction));
        }
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
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof Restrictions) {
                Restrictions restrictions = (Restrictions) feature;
                Map<RestrictionEnum, BooleanFeature> reload = new HashMap<>();
                for (RestrictionEnum restriction : this.restrictions.keySet()) {
                    reload.put(restriction, this.restrictions.get(restriction));
                }
                restrictions.setRestrictions(reload);
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
        RestrictionsEditorManager.getInstance().startEditing(player, this);
    }

}
