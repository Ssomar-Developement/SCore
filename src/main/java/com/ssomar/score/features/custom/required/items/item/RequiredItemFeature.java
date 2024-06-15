package com.ssomar.score.features.custom.required.items.item;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.required.RequiredPlayerInterface;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.MaterialFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RequiredItemFeature extends FeatureWithHisOwnEditor<RequiredItemFeature, RequiredItemFeature, RequiredItemFeatureEditor, RequiredItemFeatureEditorManager> implements RequiredPlayerInterface {

    private static final Boolean DEBUG = false;
    private MaterialFeature material;
    private IntegerFeature amount;
    private BooleanFeature notExecutableItem;
    private String id;

    public RequiredItemFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.requiredItem);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.material = new MaterialFeature(this, Optional.of(Material.STONE), FeatureSettingsSCore.material);
        this.amount = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.amount);
        this.notExecutableItem = new BooleanFeature(this,  false, FeatureSettingsSCore.notExecutableItem, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.material.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.amount.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.notExecutableItem.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Required Item because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.material.save(attributeConfig);
        this.amount.save(attributeConfig);
        this.notExecutableItem.save(attributeConfig);
    }

    @Override
    public RequiredItemFeature getValue() {
        return this;
    }

    @Override
    public RequiredItemFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = "&7Material: &e" + material.getValue().get().name();
        finalDescription[finalDescription.length - 1] = "&7Amount: &e" + amount.getValue().get();

        gui.createItem(material.getValue().get(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RequiredItemFeature clone(FeatureParentInterface newParent) {
        RequiredItemFeature eF = new RequiredItemFeature(newParent, id);
        eF.setMaterial(material.clone(eF));
        eF.setAmount(amount.clone(eF));
        eF.setNotExecutableItem(notExecutableItem.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(material, amount, notExecutableItem));
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
            if (feature instanceof RequiredItemFeature) {
                RequiredItemFeature aFOF = (RequiredItemFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setMaterial(material);
                    aFOF.setAmount(amount);
                    aFOF.setNotExecutableItem(notExecutableItem);
                    break;
                }
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        RequiredItemFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public boolean verify(Player player, Event event) {
        PlayerInventory inventory = player.getInventory();
        return verify(inventory, event);
    }

    public boolean verify(Inventory inventory, Event event) {
        int needed = amount.getValue().get();
        for (ItemStack it : inventory.getContents()) {
            if (it == null || !it.getType().equals(material.getValue().get())) continue;

            if (!notExecutableItem.getValue() || !SCore.hasExecutableItems) {
                needed -= it.getAmount();
            } else {
                Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(it);
                if (eiOpt.isPresent()) {
                    continue;
                } else {
                    needed -= it.getAmount();
                }
            }
            if (needed <= 0) return true;
        }
        if (needed <= 0) return true;
        else {
            return false;
        }
    }

    @Override
    public void take(Player player) {
        PlayerInventory inventory = player.getInventory();
        int needed = amount.getValue().get();
        SsomarDev.testMsg("required item: " + material.getValue().get().name() + " " + amount.getValue().get(), DEBUG);
        ItemStack itemStack =player.getItemOnCursor();
        SsomarDev.testMsg("item on cursor: " + itemStack.getType().name() + " " + itemStack.getAmount(), true);
        for (ItemStack it : inventory.getContents()) {
            if (it == null || !it.getType().equals(material.getValue().get())) continue;

            SsomarDev.testMsg("item: " + it.getType().name() + " " + it.getAmount(), DEBUG);

            if (!notExecutableItem.getValue() || !SCore.hasExecutableItems) {
                if (needed >= it.getAmount()) {
                    needed -= it.getAmount();
                    int slot = inventory.first(it);
                    inventory.clear(slot);
                } else {
                    it.setAmount(it.getAmount() - needed);
                    SsomarDev.testMsg("new amount: " + it.getAmount(), DEBUG);
                    needed = 0;
                }
            } else {
                SsomarDev.testMsg("executable item", DEBUG);
                Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(it);
                if (eiOpt.isPresent()) {
                    continue;
                } else {
                    if (needed >= it.getAmount()) {
                        needed -= it.getAmount();
                        /* .first doesnt check off hand and armor */
                        // int slot = inventory.first(it);
                        int slot = -1;
                        for (ItemStack item : inventory.getContents()) {
                            slot++;
                            if (item == null) continue;
                            if (item.equals(it)) break;
                        }
                        SsomarDev.testMsg("slot: " + slot, DEBUG);
                        inventory.clear(slot);
                    } else {
                        it.setAmount(it.getAmount() - needed);
                        SsomarDev.testMsg("new amount: " + it.getAmount(), DEBUG);
                        needed = 0;
                    }
                }
            }
            if (needed <= 0) break;
        }
        player.updateInventory();
    }
}
