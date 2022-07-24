package com.ssomar.scoretestrecode.features.custom.restrictions;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.FixedMaterial;
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

@Getter
@Setter
public class Restrictions extends FeatureWithHisOwnEditor<Restrictions, Restrictions, RestrictionsEditor, RestrictionsEditorManager> {

    private final static boolean NOT_SAVE_RESTRICTIONS = true;
    private Map<RestrictionEnum, BooleanFeature> restrictions;
    private Map<RestrictionEnum, Boolean> defaultValues;

    public Restrictions(FeatureParentInterface parent, Map<RestrictionEnum, Boolean> defaultValues) {
        super(parent, "restrictions", "Restrictions", new String[]{"&7&oThe restrictions features"}, Material.ANVIL, false);
        this.defaultValues = defaultValues;
        reset();
    }

    @Override
    public void reset() {
        restrictions = new HashMap<>();
        restrictions.put(RestrictionEnum.CANCEL_DROP, new BooleanFeature(this, RestrictionEnum.CANCEL_DROP.editName, defaultValues.get(RestrictionEnum.CANCEL_DROP), "Cancel Item Drop", new String[]{"&7&oCancel the drop of the item"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_PLACE, new BooleanFeature(this, RestrictionEnum.CANCEL_PLACE.editName, defaultValues.get(RestrictionEnum.CANCEL_PLACE), "Cancel Item Placement", new String[]{"&7&oCancel the placement of the item"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_TOOL_INTERACTIONS, new BooleanFeature(this, RestrictionEnum.CANCEL_TOOL_INTERACTIONS.editName, defaultValues.get(RestrictionEnum.CANCEL_TOOL_INTERACTIONS), "Cancel Tool Interactions", new String[]{"&7&oCancel the interactions of the tool"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_CONSUMPTION, new BooleanFeature(this, RestrictionEnum.CANCEL_CONSUMPTION.editName, defaultValues.get(RestrictionEnum.CANCEL_CONSUMPTION), "Cancel Consumption", new String[]{"&7&oThe item can't be", "&7&oconsumed"}, Material.POTION, false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_CRAFT, new BooleanFeature(this, RestrictionEnum.CANCEL_CRAFT.editName, defaultValues.get(RestrictionEnum.CANCEL_CRAFT), "Cancel Craft", new String[]{"&7&oThe item can't be used", "&7&oto craft vanilla item"}, FixedMaterial.getMaterial(Arrays.asList("CRAFTING_TABLE", "WORKBENCH")), false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ALL_CRAFT, new BooleanFeature(this, RestrictionEnum.CANCEL_ALL_CRAFT.editName, defaultValues.get(RestrictionEnum.CANCEL_ALL_CRAFT), "Cancel All Craft", new String[]{"&7&oThe item can't be used", "&7&oto craft any item", "&7&o(Even custom items)"}, FixedMaterial.getMaterial(Arrays.asList("CRAFTING_TABLE", "WORKBENCH")), false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_DEPOSIT_IN_CHEST, new BooleanFeature(this, RestrictionEnum.CANCEL_DEPOSIT_IN_CHEST.editName, defaultValues.get(RestrictionEnum.CANCEL_DEPOSIT_IN_CHEST), "Cancel Deposit in Chest", new String[]{"&7&oThe item can't be", "&7&oto deposit in a chest"}, Material.CHEST, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_DEPOSIT_IN_FURNACE, new BooleanFeature(this, RestrictionEnum.CANCEL_DEPOSIT_IN_FURNACE.editName, defaultValues.get(RestrictionEnum.CANCEL_DEPOSIT_IN_FURNACE), "Cancel Deposit in Furnace", new String[]{"&7&oThe item can't be", "&7&oto deposit in a furnace"}, Material.FURNACE, false, NOT_SAVE_RESTRICTIONS));
        if (!SCore.is1v13Less())
            restrictions.put(RestrictionEnum.CANCEL_STONE_CUTTER, new BooleanFeature(this, RestrictionEnum.CANCEL_STONE_CUTTER.editName, defaultValues.get(RestrictionEnum.CANCEL_STONE_CUTTER), "Cancel Stone Cutter", new String[]{"&7&oThe item can't be", "&7&oplaced in stone cutter"}, FixedMaterial.getMaterial(Arrays.asList("STONECUTTER")), false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ENCHANT, new BooleanFeature(this, RestrictionEnum.CANCEL_ENCHANT.editName, defaultValues.get(RestrictionEnum.CANCEL_ENCHANT), "Cancel Enchant", new String[]{"&7&oThe item can't be", "&7&oenchanted"}, FixedMaterial.getMaterial(Arrays.asList("ENCHANTING_TABLE", "ENCHANTMENT_TABLE")), false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_BREWING, new BooleanFeature(this, RestrictionEnum.CANCEL_BREWING.editName, defaultValues.get(RestrictionEnum.CANCEL_BREWING), "Cancel Brewing", new String[]{"&7&oThe item can't be", "&7&oplaced in a brewing stand"}, FixedMaterial.getBrewingStand(), false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ANVIL, new BooleanFeature(this, RestrictionEnum.CANCEL_ANVIL.editName, defaultValues.get(RestrictionEnum.CANCEL_ANVIL), "Cancel Anvil", new String[]{"&7&oThe item can't be", "&7&oplaced in an anvil"}, Material.ANVIL, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ACTION_ENCHANT_IN_ANVIL, new BooleanFeature(this, RestrictionEnum.CANCEL_ACTION_ENCHANT_IN_ANVIL.editName, defaultValues.get(RestrictionEnum.CANCEL_ACTION_ENCHANT_IN_ANVIL), "Cancel Action Enchant in Anvil", new String[]{"&7&oThe item can't be", "&7&oenchanted in an anvil"}, Material.ANVIL, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ACTION_RENAME_IN_ANVIL, new BooleanFeature(this, RestrictionEnum.CANCEL_ACTION_RENAME_IN_ANVIL.editName, defaultValues.get(RestrictionEnum.CANCEL_ACTION_RENAME_IN_ANVIL), "Cancel Action Rename in Anvil", new String[]{"&7&oThe item can't be", "&7&orenamed in an anvil"}, Material.ANVIL, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_BEACON, new BooleanFeature(this, RestrictionEnum.CANCEL_BEACON.editName, defaultValues.get(RestrictionEnum.CANCEL_BEACON), "Cancel Beacon", new String[]{"&7&oThe item can't be", "&7&oplaced in a beacon"}, Material.BEACON, false, NOT_SAVE_RESTRICTIONS));
        if (!SCore.is1v13Less())
            restrictions.put(RestrictionEnum.CANCEL_CARTOGRAPHY, new BooleanFeature(this, RestrictionEnum.CANCEL_CARTOGRAPHY.editName, defaultValues.get(RestrictionEnum.CANCEL_CARTOGRAPHY), "Cancel Cartography", new String[]{"&7&oThe item can't be", "&7&oplaced in a cartography table"}, FixedMaterial.getMaterial(Arrays.asList("CARTOGRAPHY_TABLE")), false, NOT_SAVE_RESTRICTIONS));
        if (!SCore.is1v13Less())
            restrictions.put(RestrictionEnum.CANCEL_COMPOSTER, new BooleanFeature(this, RestrictionEnum.CANCEL_COMPOSTER.editName, defaultValues.get(RestrictionEnum.CANCEL_COMPOSTER), "Cancel Composter", new String[]{"&7&oThe item can't be", "&7&oplaced in a composter"}, FixedMaterial.getMaterial(Arrays.asList("COMPOSTER")), false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_DISPENSER, new BooleanFeature(this, RestrictionEnum.CANCEL_DISPENSER.editName, defaultValues.get(RestrictionEnum.CANCEL_DISPENSER), "Cancel Dispenser", new String[]{"&7&oThe item can't be", "&7&oplaced in a dispenser"}, Material.DISPENSER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_DROPPER, new BooleanFeature(this, RestrictionEnum.CANCEL_DROPPER.editName, defaultValues.get(RestrictionEnum.CANCEL_DROPPER), "Cancel Dropper", new String[]{"&7&oThe item can't be", "&7&oplaced in a dropper"}, Material.DROPPER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_HOPPER, new BooleanFeature(this, RestrictionEnum.CANCEL_HOPPER.editName, defaultValues.get(RestrictionEnum.CANCEL_HOPPER), "Cancel Hopper", new String[]{"&7&oThe item can't be", "&7&oplaced in a hopper"}, Material.HOPPER, false, NOT_SAVE_RESTRICTIONS));
        if (!SCore.is1v13Less()) {
            restrictions.put(RestrictionEnum.CANCEL_LECTERN, new BooleanFeature(this, RestrictionEnum.CANCEL_LECTERN.editName, defaultValues.get(RestrictionEnum.CANCEL_LECTERN), "Cancel Lectern", new String[]{"&7&oThe item can't be", "&7&oplaced in a lectern"}, FixedMaterial.getMaterial(Arrays.asList("LECTERN")), false, NOT_SAVE_RESTRICTIONS));
            restrictions.put(RestrictionEnum.CANCEL_LOOM, new BooleanFeature(this, RestrictionEnum.CANCEL_LOOM.editName, defaultValues.get(RestrictionEnum.CANCEL_LOOM), "Cancel Loom", new String[]{"&7&oThe item can't be", "&7&oplaced in a loom"}, FixedMaterial.getMaterial(Arrays.asList("LOOM")), false, NOT_SAVE_RESTRICTIONS));
        }
        if (!SCore.is1v13Less())
            restrictions.put(RestrictionEnum.CANCEL_GRIND_STONE, new BooleanFeature(this, RestrictionEnum.CANCEL_GRIND_STONE.editName, defaultValues.get(RestrictionEnum.CANCEL_GRIND_STONE), "Cancel Grind Stone", new String[]{"&7&oThe item can't be", "&7&oplaced in a grind stone"}, FixedMaterial.getMaterial(Arrays.asList("GRINDSTONE")), false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ITEM_FRAME, new BooleanFeature(this, RestrictionEnum.CANCEL_ITEM_FRAME.editName, defaultValues.get(RestrictionEnum.CANCEL_ITEM_FRAME), "Cancel Item Frame", new String[]{"&7&oThe item can't be", "&7&oplaced in an item frame"}, Material.ITEM_FRAME, false, NOT_SAVE_RESTRICTIONS));
        if (!SCore.is1v13Less())
            restrictions.put(RestrictionEnum.CANCEL_SMITHING_TABLE, new BooleanFeature(this, RestrictionEnum.CANCEL_SMITHING_TABLE.editName, defaultValues.get(RestrictionEnum.CANCEL_SMITHING_TABLE), "Cancel Smithing Table", new String[]{"&7&oThe item can't be", "&7&oplaced in a smithing table"}, FixedMaterial.getMaterial(Arrays.asList("SMITHING_TABLE")), false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_MERCHANT, new BooleanFeature(this, RestrictionEnum.CANCEL_MERCHANT.editName, defaultValues.get(RestrictionEnum.CANCEL_MERCHANT), "Cancel Merchant", new String[]{"&7&oThe item can't be", "&7&otraded in a merchant"}, FixedMaterial.getMaterial(Arrays.asList("VILLAGER_SPAWN_EGG", "EMERALD")), false, NOT_SAVE_RESTRICTIONS));

        restrictions.put(RestrictionEnum.CANCEL_ITEM_BURN, new BooleanFeature(this, RestrictionEnum.CANCEL_ITEM_BURN.editName, defaultValues.get(RestrictionEnum.CANCEL_ITEM_BURN), "Cancel Item Burn", new String[]{"&7&oThe item can't burn"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ITEM_DELETE_BY_CACTUS, new BooleanFeature(this, RestrictionEnum.CANCEL_ITEM_DELETE_BY_CACTUS.editName, defaultValues.get(RestrictionEnum.CANCEL_ITEM_DELETE_BY_CACTUS), "Cancel Item Delete by Cactus", new String[]{"&7&oThe item can't be", "&7&odestroyed by a cactus"}, Material.CACTUS, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.CANCEL_ITEM_DELETE_BY_LIGHTNING, new BooleanFeature(this, RestrictionEnum.CANCEL_ITEM_DELETE_BY_LIGHTNING.editName, defaultValues.get(RestrictionEnum.CANCEL_ITEM_DELETE_BY_LIGHTNING), "Cancel Item Delete by Lightning", new String[]{"&7&oThe item can't be", "&7&odestroyed by a lightning"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));


        if (!SCore.is1v11Less())
            restrictions.put(RestrictionEnum.CANCEL_SWAPHAND, new BooleanFeature(this, RestrictionEnum.CANCEL_SWAPHAND.editName, defaultValues.get(RestrictionEnum.CANCEL_SWAPHAND), "Cancel Swap Hand", new String[]{"&7&oThe item can't be", "&7&oswaped between the two hands"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
        restrictions.put(RestrictionEnum.LOCKED_INVENTORY, new BooleanFeature(this, RestrictionEnum.LOCKED_INVENTORY.editName, defaultValues.get(RestrictionEnum.LOCKED_INVENTORY), "Locked Inventory", new String[]{"&7&oThe item is locked in the inventory"}, Material.LEVER, false, NOT_SAVE_RESTRICTIONS));
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
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

    public boolean is(RestrictionEnum ask) {
        for (RestrictionEnum restriction : restrictions.keySet()) {
            if (restriction.equals(ask)) {
                return restrictions.get(restriction).getValue();
            }
        }
        return false;
    }

    public BooleanFeature get(RestrictionEnum ask) {
        for (RestrictionEnum restriction : restrictions.keySet()) {
            if (restriction.equals(ask)) {
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
    public Restrictions clone(FeatureParentInterface newParent) {
        Restrictions restrictions = new Restrictions(getParent(), getDefaultValues());
        Map<RestrictionEnum, BooleanFeature> clone = new HashMap<>();
        for (RestrictionEnum restriction : this.restrictions.keySet()) {
            clone.put(restriction, this.restrictions.get(restriction).clone(restrictions));
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
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof Restrictions) {
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
