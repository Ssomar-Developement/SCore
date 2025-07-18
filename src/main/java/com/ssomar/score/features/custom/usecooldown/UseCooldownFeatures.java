package com.ssomar.score.features.custom.usecooldown;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class UseCooldownFeatures extends FeatureWithHisOwnEditor<UseCooldownFeatures, UseCooldownFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> {

    // Must be in lowercase to match the NamespacedKey format
    private UncoloredStringFeature cooldownGroup;
    private IntegerFeature vanillaUseCooldown;

    public UseCooldownFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.useCooldownFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.cooldownGroup = new UncoloredStringFeature(this, Optional.empty(), FeatureSettingsSCore.cooldownGroup, true);
        this.vanillaUseCooldown = new IntegerFeature(this, Optional.empty(), FeatureSettingsSCore.vanillaUseCooldown);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (isPremiumLoading) {
            error.addAll(this.cooldownGroup.load(plugin, config, isPremiumLoading));
            if(cooldownGroup.getValue().isPresent()) {
                String group = cooldownGroup.getValue().get().toLowerCase();
                cooldownGroup.setValue(Optional.of(group));
            }
            error.addAll(this.vanillaUseCooldown.load(plugin, config, isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        this.cooldownGroup.save(config);
        this.vanillaUseCooldown.save(config);
    }

    public UseCooldownFeatures getValue() {
        return this;
    }

    @Override
    public UseCooldownFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 3] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;

        finalDescription[finalDescription.length - 2] = "&7CooldownGroup: &e" + cooldownGroup.getValue().orElse("&cNONE");

        finalDescription[finalDescription.length - 1] = "&7VanillaUseCooldown: &e" + vanillaUseCooldown.getValue().orElse(0);

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public UseCooldownFeatures clone(FeatureParentInterface newParent) {
        UseCooldownFeatures dropFeatures = new UseCooldownFeatures(newParent);
        dropFeatures.setCooldownGroup(this.cooldownGroup.clone(dropFeatures));
        dropFeatures.setVanillaUseCooldown(this.vanillaUseCooldown.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.cooldownGroup);
        features.add(this.vanillaUseCooldown);
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
            if (feature instanceof UseCooldownFeatures) {
                UseCooldownFeatures dropFeatures = (UseCooldownFeatures) feature;
                dropFeatures.setCooldownGroup(this.cooldownGroup);
                dropFeatures.setVanillaUseCooldown(this.vanillaUseCooldown);
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
        GenericFeatureParentEditorManager.getInstance().startEditing(player, this);
    }

    public void applyOnItemMeta(ItemMeta meta){
        /* Added in 1.21.2 */
        if (SCore.is1v21v2Plus()) {
            boolean oneEdit = false;
            UseCooldownComponent useCooldown = meta.getUseCooldown();
            boolean fixSpigotIssueVanillaUseCooldownMustBeAtLeastOne = false;
            if (cooldownGroup.getValue().isPresent()) {
                useCooldown.setCooldownGroup(NamespacedKey.fromString(cooldownGroup.getValue().get().toLowerCase()));
                fixSpigotIssueVanillaUseCooldownMustBeAtLeastOne = true;
                oneEdit = true;
            }
            if (vanillaUseCooldown.getValue().isPresent() && vanillaUseCooldown.getValue().get() > 0) {
                useCooldown.setCooldownSeconds(vanillaUseCooldown.getValue().get());
                oneEdit = true;
            } else if(fixSpigotIssueVanillaUseCooldownMustBeAtLeastOne){
                useCooldown.setCooldownSeconds(1);
                oneEdit = true;
            }
            if(oneEdit) meta.setUseCooldown(useCooldown);
        }
    }
}
