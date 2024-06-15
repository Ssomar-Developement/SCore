package com.ssomar.score.features.custom.detailedslots;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class DetailedSlots extends FeatureWithHisOwnEditor<DetailedSlots, DetailedSlots, DetailedSlotsEditor, DetailedSlotsEditorManager> {

    private List<Integer> slots;

    public DetailedSlots(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.detailedSlots);
        reset();
    }

    @Override
    public void reset() {
        this.slots = new ArrayList<>();
        slots.add(-1);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        slots = new ArrayList<>();
        for (String s : config.getStringList(getName())) {
            try {
                slots.add(Integer.valueOf(s));
            } catch (Exception ignored) {
            }
        }
        if (slots.isEmpty()) {
            for (int i = -1; i <= 40; i++) {
                slots.add(i);
            }
        }

        return error;
    }

    public boolean verifSlot(int slot, boolean mainHand) {
        if (!slots.contains(slot)) {
            if (mainHand) return slots.contains(-1);
            return false;
        }

        return true;
    }

    @Override
    public void save(ConfigurationSection config) {
        /* Empty list = all slots */
        if (slots.size() == 42) config.set(getName(), new ArrayList<>());
        else config.set(getName(), slots);
    }

    @Override
    public DetailedSlots getValue() {
        return this;
    }

    @Override
    public DetailedSlots initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Slot(s) enabled: &e" + slots.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public DetailedSlots clone(FeatureParentInterface newParent) {
        DetailedSlots dropFeatures = new DetailedSlots(newParent);
        dropFeatures.setSlots(new ArrayList<>(slots));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Collections.emptyList());
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
            if (feature instanceof DetailedSlots) {
                DetailedSlots hiders = (DetailedSlots) feature;
                hiders.setSlots(slots);
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
        DetailedSlotsEditorManager.getInstance().startEditing(player, this);
    }

}
