package com.ssomar.score.features.custom.itemcheckers;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorReloaded;
import com.ssomar.score.features.editor.GenericFeatureParentEditorReloadedManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.enums.EnumFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class ItemCheckers extends FeatureWithHisOwnEditor<ItemCheckers, ItemCheckers, GenericFeatureParentEditorReloaded, GenericFeatureParentEditorReloadedManager> {

    private EnumFeature<ItemCheckerType> itemCheckerType;
    private Map<ItemCheckerEnum, BooleanFeature> checkers;
    private Map<ItemCheckerEnum, Boolean> defaultValues;
    private List<ItemCheckerEnum> notFor1_11_less;
    private List<ItemCheckerEnum> notFor1_13_less;
    private List<ItemCheckerEnum> notFor1_18_less;
    private List<ItemCheckerEnum> notFor1_19_less;

    public ItemCheckers(FeatureParentInterface parent, Map<ItemCheckerEnum, Boolean> defaultValues) {
        super(parent, FeatureSettingsSCore.itemCheckers);
        this.defaultValues = defaultValues;
        this.notFor1_11_less = new ArrayList<>();
        //this.notFor1_11_less.add(ItemCheckerEnum.CANCEL_SWAPHAND);
        this.notFor1_13_less = new ArrayList<>();
        this.notFor1_18_less = new ArrayList<>();
        this.notFor1_19_less = new ArrayList<>();
        reset();
    }

    @Override
    public void reset() {
        itemCheckerType = new EnumFeature<ItemCheckerType>(this, Optional.of(ItemCheckerType.CUSTOM_CHECKS), FeatureSettingsSCore.itemCheckerType, ItemCheckerType.class, ItemCheckerType.CUSTOM_CHECKS, "ItemCheckerType", Arrays.asList(ItemCheckerType.values()));

        checkers = new LinkedHashMap<>();
        for (ItemCheckerEnum restriction : ItemCheckerEnum.values()) {
            if (SCore.is1v11Less() && notFor1_11_less.contains(restriction)) continue;
            if (SCore.is1v13Less() && notFor1_13_less.contains(restriction)) continue;
            if (!SCore.is1v19Plus() && notFor1_18_less.contains(restriction)) continue;
            if (!SCore.is1v20Plus() && notFor1_19_less.contains(restriction)) continue;
            checkers.put(restriction, new BooleanFeature(this, defaultValues.get(restriction), restriction.featureSetting));
        }
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            error.addAll(itemCheckerType.load(plugin, section, isPremiumLoading));
            for (ItemCheckerEnum restriction : checkers.keySet()) {
                checkers.get(restriction).load(plugin, section, isPremiumLoading);
            }
        }

        return error;
    }


    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        itemCheckerType.save(section);
        for (ItemCheckerEnum restriction : checkers.keySet()) {
            checkers.get(restriction).save(section);
        }
        if(isSavingOnlyIfDiffDefault() && section.getKeys(false).isEmpty()){
            config.set(getName(), null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    @Override
    public ItemCheckers getValue() {
        return this;
    }

    public boolean is(ItemCheckerEnum ask) {
        for (ItemCheckerEnum restriction : checkers.keySet()) {
            if (restriction.equals(ask)) {
                return checkers.get(restriction).getValue();
            }
        }
        return false;
    }

    public BooleanFeature get(ItemCheckerEnum ask) {
        for (ItemCheckerEnum restriction : checkers.keySet()) {
            if (restriction.equals(ask)) {
                return checkers.get(restriction);
            }
        }
        return null;
    }

    public int getRestrictionCount() {
        int count = 0;
        for (ItemCheckerEnum restriction : checkers.keySet()) {
            if (checkers.get(restriction).getValue()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public ItemCheckers initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        if(itemCheckerType.getValue().get() == ItemCheckerType.ITEM_MUST_BE_EXACTLY_THE_SAME) finalDescription[finalDescription.length - 1] = "&7The item must be exactly the same";
        else finalDescription[finalDescription.length - 1] = "&7Item checkers activated: &e" + getRestrictionCount();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public ItemCheckers clone(FeatureParentInterface newParent) {
        ItemCheckers restrictions = new ItemCheckers(getParent(), getDefaultValues());
        restrictions.setItemCheckerType(itemCheckerType.clone(restrictions));
        Map<ItemCheckerEnum, BooleanFeature> clone = new LinkedHashMap<>();
        for (ItemCheckerEnum restriction : this.checkers.keySet()) {
            clone.put(restriction, this.checkers.get(restriction).clone(restrictions));
        }
        restrictions.setCheckers(clone);
        return restrictions;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(itemCheckerType);
        if(itemCheckerType.getValue().get() == ItemCheckerType.ITEM_MUST_BE_EXACTLY_THE_SAME) return features;

        for (ItemCheckerEnum restriction : checkers.keySet()) {
            features.add(checkers.get(restriction));
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof ItemCheckers) {
                ItemCheckers restrictions = (ItemCheckers) feature;
                restrictions.setItemCheckerType(itemCheckerType);
                Map<ItemCheckerEnum, BooleanFeature> reload = new LinkedHashMap<>();
                for (ItemCheckerEnum restriction : this.checkers.keySet()) {
                    reload.put(restriction, this.checkers.get(restriction));
                }
                restrictions.setCheckers(reload);
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
        GenericFeatureParentEditorReloadedManager.getInstance().startEditing(player, this);
    }

    public boolean isSimilar(ItemStack item1, ItemStack item2) {
        if(item1 == null || item2 == null) return false;

        if(itemCheckerType.getValue().get() == ItemCheckerType.ITEM_MUST_BE_EXACTLY_THE_SAME) {
            return item1.isSimilar(item2);
        }
        else {
            for (ItemCheckerEnum checker : checkers.keySet()) {
                if (checkers.get(checker).getValue()) {
                    if (!checker.check(item1, item2)) {
                        return false;
                    }
                }
            }
        }

        // TODO: Check if the item is similar with checker settings
        return true;
    }

}
