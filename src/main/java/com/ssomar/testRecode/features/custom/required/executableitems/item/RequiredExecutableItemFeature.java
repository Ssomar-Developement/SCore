package com.ssomar.testRecode.features.custom.required.executableitems.item;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.custom.required.RequiredPlayerInterface;
import com.ssomar.testRecode.features.types.ExecutableItemFeature;
import com.ssomar.testRecode.features.types.IntegerFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
public class RequiredExecutableItemFeature extends FeatureWithHisOwnEditor<RequiredExecutableItemFeature, RequiredExecutableItemFeature, RequiredExecutableItemFeatureEditor, RequiredExecutableItemFeatureEditorManager> implements RequiredPlayerInterface {

    private ExecutableItemFeature executableItem;
    private IntegerFeature amount;
    private String id;

    public RequiredExecutableItemFeature(FeatureParentInterface parent, String id) {
        super(parent, "RequiredExecutableItem", "Required ExecutableItem", new String[]{"&7&oA required ExecutableItem"}, Material.STONE, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.executableItem = new ExecutableItemFeature(this, "executableItem", "ExecutableItem", new String[]{"&7&oThe ExecutableItem"}, Material.STONE, false);
        this.amount = new IntegerFeature(this, "amount", Optional.of(1), "Amount", new String[]{"&7&oThe amount"}, GUI.CLOCK, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.executableItem.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.amount.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Required ExecutableItem because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
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
        this.executableItem.save(attributeConfig);
        this.amount.save(attributeConfig);
    }

    @Override
    public RequiredExecutableItemFeature getValue() {
        return this;
    }

    @Override
    public RequiredExecutableItemFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = gui.CLICK_HERE_TO_CHANGE;
        ItemStack item = new ItemStack(Material.STONE);
        if (executableItem.getValue().isPresent()) {
            finalDescription[finalDescription.length - 2] = "&7&oExecutableItem: &a" + executableItem.getValue().get().getId();
            item = executableItem.getValue().get().buildItem(1, Optional.empty());
        } else {
            finalDescription[finalDescription.length - 2] = "&7&oExecutableItem: &cNone";
        }
        finalDescription[finalDescription.length - 1] = "&7Amount: &e" + amount.getValue().get();

        gui.createItem(item, 1, slot, gui.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public RequiredExecutableItemFeature clone() {
        RequiredExecutableItemFeature eF = new RequiredExecutableItemFeature(getParent(), id);
        eF.setExecutableItem(executableItem.clone());
        eF.setAmount(amount.clone());
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(executableItem, amount));
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
            if (feature instanceof RequiredExecutableItemFeature) {
                RequiredExecutableItemFeature aFOF = (RequiredExecutableItemFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setExecutableItem(executableItem);
                    aFOF.setAmount(amount);
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
        RequiredExecutableItemFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public boolean verify(Player player, Event event) {
        PlayerInventory inventory = player.getInventory();
        int needed = amount.getValue().get();
        if (!SCore.hasExecutableItems || !getExecutableItem().getValue().isPresent()) return true;

        for (ItemStack it : inventory.getContents()) {
            Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(it);
            if (eiOpt.isPresent() && eiOpt.get().getId().equals(getExecutableItem().getValue().get().getId())) {
                needed -= it.getAmount();
            } else {
                continue;
            }
            if (needed <= 0) return true;
        }
        if (needed <= 0) return true;
        else return false;
    }

    @Override
    public void take(Player player) {
        PlayerInventory inventory = player.getInventory();
        int needed = amount.getValue().get();
        if (!SCore.hasExecutableItems || !getExecutableItem().getValue().isPresent()) return;

        for (ItemStack it : inventory.getContents()) {
            Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(it);
            if (eiOpt.isPresent() && eiOpt.get().getId().equals(getExecutableItem().getValue().get().getId())) {
                if (needed >= it.getAmount()) {
                    needed -= it.getAmount();
                    inventory.remove(it);
                } else {
                    it.setAmount(it.getAmount() - needed);
                    needed = 0;
                }
            } else {
                continue;
            }
            if (needed <= 0) break;
        }
    }
}
