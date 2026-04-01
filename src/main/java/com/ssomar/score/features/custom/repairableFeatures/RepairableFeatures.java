package com.ssomar.score.features.custom.repairableFeatures;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.list.ListMaterialFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.strings.StringConverter;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RepairableFeatures extends FeatureWithHisOwnEditor<RepairableFeatures, RepairableFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItemNewPaperComponents {

    private BooleanFeature enable;
    private IntegerFeature repairCost;
    private ListMaterialFeature repairableItems;

    public RepairableFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.repairableFeatures);
        reset();
    }

    @Override
    public void reset() {
        enable = new BooleanFeature(this, false, FeatureSettingsSCore.enable);
        repairCost = new IntegerFeature(this, Optional.of(2), FeatureSettingsSCore.repairCost);
        repairableItems = new ListMaterialFeature(this, new ArrayList<>(), FeatureSettingsSCore.repairableItems);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            for (FeatureInterface feature : getFeatures()) {
                errors.addAll(feature.load(plugin, section, isPremiumLoading));
            }

        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (FeatureInterface feature : getFeatures()) {
            feature.save(section);
        }
        if (isSavingOnlyIfDiffDefault() && section.getKeys(false).isEmpty()) {
            config.set(getName(), null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    @Override
    public RepairableFeatures getValue() {
        return this;
    }

    @Override
    public RepairableFeatures initItemParentEditor(GUI gui, int slot) {
        int len = 4;
        String[] finalDescription = new String[getEditorDescription().length + len];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - len] = GUI.CLICK_HERE_TO_CHANGE;
        len--;

        finalDescription[finalDescription.length - len] = "&7Enable: &e" + (enable.getValue() ? "&a&l✔" : "&c&l✘");
        len--;
        finalDescription[finalDescription.length - len] = "&7Repair Cost: &e" + repairCost.getValue().get();
        len--;
        finalDescription[finalDescription.length - len] = "&7Repairable Items: &e" + repairableItems.getValues().size() + " item(s)";
        len--;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }


    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RepairableFeatures clone(FeatureParentInterface newParent) {
        RepairableFeatures clone = new RepairableFeatures(newParent);
        clone.setEnable(enable.clone(clone));
        clone.setRepairCost(repairCost.clone(clone));
        clone.setRepairableItems(repairableItems.clone(clone));
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(enable);
        features.add(repairCost);
        features.add(repairableItems);
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
            if (feature instanceof RepairableFeatures) {
                RepairableFeatures hiders = (RepairableFeatures) feature;
                hiders.setEnable(enable);
                hiders.setRepairCost(repairCost);
                hiders.setRepairableItems(repairableItems);
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

    @Override
    public boolean isAvailable() {
        return SCore.is1v21v2Plus() && SCore.isPaperOrFork();
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {
        return args.getMeta() instanceof org.bukkit.inventory.meta.Repairable;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {
        if (!isAvailable() || !isApplicable(args)) return;
        if (enable.getValue()) {
            org.bukkit.inventory.meta.Repairable repairable = (org.bukkit.inventory.meta.Repairable) args.getMeta();
            repairable.setRepairCost(repairCost.getValue().get());
        }
    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {
        if (!isAvailable() || !isApplicable(args)) return;
        org.bukkit.inventory.meta.Repairable repairable = (org.bukkit.inventory.meta.Repairable) args.getMeta();
        if (repairable.hasRepairCost()) {
            enable.setValue(true);
            repairCost.setValue(Optional.of(repairable.getRepairCost()));
        }
    }

    @Override
    public void applyOnItem(@NotNull FeatureForItemArgs args) {
        if (!isAvailableForNewComponents()) return;
        if (!enable.getValue() || repairableItems.getValues().isEmpty()) return;
        ItemStack item = args.getItem();
        try {
            List<ItemType> itemTypes = new ArrayList<>();
            for (Material mat : repairableItems.getValues()) {
                ItemType itemType = mat.asItemType();
                if (itemType != null) itemTypes.add(itemType);
            }
            if (itemTypes.isEmpty()) return;
            RegistryKeySet<ItemType> keySet = RegistrySet.keySetFromValues(RegistryKey.ITEM, itemTypes);
            item.setData(DataComponentTypes.REPAIRABLE, io.papermc.paper.datacomponent.item.Repairable.repairable(keySet));
        } catch (Exception e) {
            Utils.sendConsoleMsg(SCore.plugin, "&cError while applying repairable items on an item");
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromItem(@NotNull FeatureForItemArgs args) {
        if (!isAvailableForNewComponents()) return;
        ItemStack item = args.getItem();
        try {
            if (item.hasData(DataComponentTypes.REPAIRABLE)) {
                io.papermc.paper.datacomponent.item.Repairable repairableData = item.getData(DataComponentTypes.REPAIRABLE);
                RegistryKeySet<ItemType> repairWith = repairableData.types();
                for (ItemType itemType : repairWith.resolve(RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM))) {
                    Material mat = Material.matchMaterial(itemType.key().asString());
                    if (mat != null) {
                        repairableItems.getValues().add(mat);
                        enable.setValue(true);
                    }
                }
            }
        } catch (Exception e) {
            Utils.sendConsoleMsg(SCore.plugin, "&cError while loading repairable items from an item");
            e.printStackTrace();
        }
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.REPAIRABLE;
    }
}
