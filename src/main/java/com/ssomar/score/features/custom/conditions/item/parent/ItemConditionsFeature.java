package com.ssomar.score.features.custom.conditions.item.parent;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.custom.conditions.item.ItemConditionRequest;
import com.ssomar.score.features.custom.conditions.item.condition.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ItemConditionsFeature extends FeatureWithHisOwnEditor<ItemConditionsFeature, ItemConditionsFeature, ItemConditionsFeatureEditor, ItemConditionsFeatureEditorManager> {

    private List<ItemConditionFeature> conditions;

    public ItemConditionsFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        reset();
    }

    @Override
    public void reset() {
        conditions = new ArrayList<>();
        /* Boolean features */
        if (!SCore.is1v12Less()) {
            conditions.add(new IfCrossbowMustBeCharged(this));
            conditions.add(new IfCrossbowMustNotBeCharged(this));
        }

        /* Number condition */
        conditions.add(new IfDurability(this));
        conditions.add(new IfUsage(this));

        /* ListEnchantWithLevel */
        conditions.add(new IfHasEnchant(this));
        conditions.add(new IfHasNotEnchant(this));

    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            for (ItemConditionFeature condition : conditions) {
                error.addAll(condition.load(plugin, section, isPremiumLoading));
            }
        }

        return error;
    }

    public boolean verifConditions(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event) {
        ItemConditionRequest request = new ItemConditionRequest(itemStack, playerOpt, new StringPlaceholder(), event);
        for (ItemConditionFeature condition : conditions) {
            if (!condition.verifCondition(request)) {
                if (messageSender != null && playerOpt.isPresent()) {
                    for (String error : request.getErrorsFinal()) {
                        messageSender.sendMessage(playerOpt.get(), error);
                    }
                }
                return false;
            }
        }
        return true;
    }

    public boolean verifConditions(ItemStack itemStack, List<String> errors, @Nullable StringPlaceholder stringPlaceholder) {
        if(stringPlaceholder == null) stringPlaceholder = new StringPlaceholder();
        ItemConditionRequest request = new ItemConditionRequest(itemStack, Optional.empty(), stringPlaceholder, null);
        for (ItemConditionFeature condition : conditions) {
            if (!condition.verifCondition(request)) {
                errors.addAll(request.getErrorsFinal());
                return false;
            }
        }
        return true;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (ItemConditionFeature condition : conditions) {
            condition.save(section);
        }
    }

    @Override
    public ItemConditionsFeature getValue() {
        return this;
    }

    @Override
    public ItemConditionsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7Item condition(s) enabled: &e" + getItemConditionEnabledCount();
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    public int getItemConditionEnabledCount() {
        int i = 0;
        for (ItemConditionFeature condition : conditions) {
            if (condition.hasCondition()) {
                i++;
            }
        }
        return i;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public ItemConditionsFeature clone(FeatureParentInterface newParent) {
        ItemConditionsFeature clone = new ItemConditionsFeature(newParent, getFeatureSettings());
        List<ItemConditionFeature> clones = new ArrayList<>();
        for (ItemConditionFeature condition : conditions) {
            clones.add((ItemConditionFeature) condition.clone(clone));
        }
        clone.setConditions(clones);
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(conditions);
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if (section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        } else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof ItemConditionsFeature && feature.getName().equals(getName())) {
                ItemConditionsFeature bCF = (ItemConditionsFeature) feature;
                List<ItemConditionFeature> clones = new ArrayList<>();
                for (ItemConditionFeature condition : conditions) {
                    clones.add((ItemConditionFeature) condition);
                }
                bCF.setConditions(clones);
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
        ItemConditionsFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
