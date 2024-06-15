package com.ssomar.score.features.custom.required.magic.magic;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.required.RequiredPlayerInterface;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.magic.MagicType;
import com.willfp.ecoskills.magic.MagicTypes;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RequiredMagicFeature extends FeatureWithHisOwnEditor<RequiredMagicFeature, RequiredMagicFeature, RequiredMagicFeatureEditor, RequiredMagicFeatureEditorManager> implements RequiredPlayerInterface {

    private UncoloredStringFeature magic;
    private IntegerFeature amount;
    private String id;

    public RequiredMagicFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.requiredMagic);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.magic = new UncoloredStringFeature(this, Optional.empty(), FeatureSettingsSCore.magicID, false);
        this.amount = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.amount);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.magic.load(plugin, enchantmentConfig, isPremiumLoading));
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
        this.magic.save(attributeConfig);
        this.amount.save(attributeConfig);
    }

    @Override
    public RequiredMagicFeature getValue() {
        return this;
    }

    @Override
    public RequiredMagicFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        ItemStack item = new ItemStack(Material.STONE);
        if (magic.getValue().isPresent()) {
            finalDescription[finalDescription.length - 2] = "&7&oMagic ID: &a" + magic.getValue().get();
        } else {
            finalDescription[finalDescription.length - 2] = "&7&oMagic ID: &cNone";
        }
        finalDescription[finalDescription.length - 1] = "&7Amount: &e" + amount.getValue().get();

        gui.createItem(item, 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RequiredMagicFeature clone(FeatureParentInterface newParent) {
        RequiredMagicFeature eF = new RequiredMagicFeature(newParent, id);
        eF.setMagic(magic.clone(eF));
        eF.setAmount(amount.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(magic, amount));
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
            if (feature instanceof RequiredMagicFeature) {
                RequiredMagicFeature aFOF = (RequiredMagicFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setMagic(magic);
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
        RequiredMagicFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public boolean verify(Player player, Event event) {
        int needed = amount.getValue().get();
        if (!SCore.hasEcoSkills) return true;

        if(!magic.getValue().isPresent()) return true;
        String magicId = magic.getValue().get();

        MagicType type = MagicTypes.INSTANCE.getByID(magicId);

        int magicAmount = EcoSkillsAPI.getMagic(player, type);
        if (magicAmount < needed) return false;
        return true;
    }

    @Override
    public void take(Player player) {
        int needed = amount.getValue().get();
        if (!SCore.hasEcoSkills) return;

        if(!magic.getValue().isPresent()) return;
        String magicId = magic.getValue().get();

        MagicType type = MagicTypes.INSTANCE.getByID(magicId);

        int magicAmount = EcoSkillsAPI.getMagic(player, type);
        if (magicAmount < needed) return;
        EcoSkillsAPI.setMagic(player, type, magicAmount - needed);
    }
}
