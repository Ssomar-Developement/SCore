package com.ssomar.score.features.custom.givefirstjoin;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.sobject.NewSObject;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class GiveFirstJoinFeatures extends FeatureWithHisOwnEditor<GiveFirstJoinFeatures, GiveFirstJoinFeatures, GiveFirstJoinFeaturesEditor, GiveFirstJoinFeaturesEditorManager> {

    private BooleanFeature giveFirstJoin;
    private IntegerFeature giveFirstJoinAmount;
    private IntegerFeature giveFirstJoinSlot;

    public GiveFirstJoinFeatures(FeatureParentInterface parent) {
        super(parent, "giveFirstJoin", "Give first join features", new String[]{"&7&oGive the item for", "&7&othe first join of the player"}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        this.giveFirstJoin = new BooleanFeature(getParent(), "giveFirstJoin", false, "Give first join", new String[]{"&7&oEnable the feature"}, Material.LEVER, false, false);
        this.giveFirstJoinAmount = new IntegerFeature(getParent(), "giveFirstJoinAmount", Optional.of(1), "Amount", new String[]{"&7&oThe amount to give"}, GUI.CLOCK, false);
        this.giveFirstJoinSlot = new IntegerFeature(getParent(), "giveFirstJoinSlot", Optional.of(0), "Slot", new String[]{"&7&oSlot between 0 and 8 includes"}, GUI.CLOCK, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            giveFirstJoin.load(plugin, section, isPremiumLoading);
            giveFirstJoinAmount.load(plugin, section, isPremiumLoading);
            giveFirstJoinSlot.load(plugin, section, isPremiumLoading);
        }

        return error;
    }

    public void giveFirstJoin(@NotNull Player player) {
        if (giveFirstJoin.getValue()) {
            if (getParent() instanceof NewSObject) {
                NewSObject object = (NewSObject) getParent();
                ItemStack item = object.buildItem(giveFirstJoinAmount.getValue().get(), Optional.of(player));
                player.getInventory().setItem(giveFirstJoinSlot.getValue().get(), item);
            }
        }
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        giveFirstJoin.save(section);
        giveFirstJoinAmount.save(section);
        giveFirstJoinSlot.save(section);
    }

    @Override
    public GiveFirstJoinFeatures getValue() {
        return this;
    }

    @Override
    public GiveFirstJoinFeatures initItemParentEditor(GUI gui, int slot) {
        int toAdd = 2;
        if (getGiveFirstJoin().getValue()) {
            toAdd += 2;
        }
        String[] finalDescription = new String[getEditorDescription().length + toAdd];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (getGiveFirstJoin().getValue()) {
            finalDescription[finalDescription.length - 4] = gui.CLICK_HERE_TO_CHANGE;
            finalDescription[finalDescription.length - 3] = "&7Enable: &a&l✔";
            finalDescription[finalDescription.length - 2] = "&7Amount: &e" + getGiveFirstJoinAmount().getValue().get();
            finalDescription[finalDescription.length - 1] = "&7Slot: &e" + getGiveFirstJoinSlot().getValue().get();
        } else {
            finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
            finalDescription[finalDescription.length - 1] = "&7Enable: &c&l✘";
        }

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public GiveFirstJoinFeatures clone(FeatureParentInterface newParent) {
        GiveFirstJoinFeatures giveFirstJoinFeatures = new GiveFirstJoinFeatures(newParent);
        giveFirstJoinFeatures.setGiveFirstJoin(giveFirstJoin.clone(giveFirstJoinFeatures));
        giveFirstJoinFeatures.setGiveFirstJoinAmount(giveFirstJoinAmount.clone(giveFirstJoinFeatures));
        giveFirstJoinFeatures.setGiveFirstJoinSlot(giveFirstJoinSlot.clone(giveFirstJoinFeatures));
        return giveFirstJoinFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(giveFirstJoin, giveFirstJoinAmount, giveFirstJoinSlot));
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
            if (feature instanceof GiveFirstJoinFeatures) {
                GiveFirstJoinFeatures giveFirstJoinFeatures = (GiveFirstJoinFeatures) feature;
                giveFirstJoinFeatures.setGiveFirstJoin(giveFirstJoin);
                giveFirstJoinFeatures.setGiveFirstJoinAmount(giveFirstJoinAmount);
                giveFirstJoinFeatures.setGiveFirstJoinSlot(giveFirstJoinSlot);
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
        GiveFirstJoinFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
