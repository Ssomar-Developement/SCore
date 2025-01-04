package com.ssomar.score.scheduler;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ScheduleFeatures extends FeatureWithHisOwnEditor<ScheduleFeatures, ScheduleFeatures, ScheduleFeaturesEditor, ScheduleFeaturesEditorManager> {

    private UncoloredStringFeature startDateFeature;
    private UncoloredStringFeature endDateFeature;

    private ListUncoloredStringFeature when;

    public ScheduleFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.scheduleFeatures);
        reset();
    }


    public Date getStartDate() {
        try {
            return StringConverter.stringToDate(startDateFeature.getValue().get());
        } catch (Exception e) {
            e.printStackTrace();
            return StringConverter.stringToDate("1700-01-01 00:00:00");
        }
    }

    public Date getEndDate() {
        try {
            return StringConverter.stringToDate(endDateFeature.getValue().get());
        } catch (Exception e) {
            e.printStackTrace();
            return StringConverter.stringToDate("3000-01-01 00:00:00");
        }
    }

    public List<Long> getNextTimestamp(long calculationTime) {
        return DateGenerator.generateNextValidTimestamps(new Date(), when.getValues(), calculationTime, getStartDate(), getEndDate());
    }

    @Override
    public void reset() {
        this.startDateFeature = new UncoloredStringFeature(this, Optional.of("1700-01-01 00:00:00"), FeatureSettingsSCore.startDate, false);
        this.endDateFeature = new UncoloredStringFeature(this, Optional.of("3000-01-01 00:00:00"), FeatureSettingsSCore.endDate, false);
        this.when = new ListUncoloredStringFeature(this, new ArrayList<>(), FeatureSettingsSCore.when, false, Optional.empty());
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            error.addAll(this.startDateFeature.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
            error.addAll(this.endDateFeature.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
            error.addAll(this.when.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.startDateFeature.save(section);
        this.endDateFeature.save(section);
        this.when.save(section);
    }

    public ScheduleFeatures getValue() {
        return this;
    }

    @Override
    public ScheduleFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 4] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 3] = "§6Start Date: §e" + startDateFeature.getValue().get();
        finalDescription[finalDescription.length - 2] = "§6End Date: §e" + endDateFeature.getValue().get();
        finalDescription[finalDescription.length - 1] = "§6When: §e" + when.getValues().toString();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public ScheduleFeatures clone(FeatureParentInterface newParent) {
        ScheduleFeatures dropFeatures = new ScheduleFeatures(newParent);
        dropFeatures.setStartDateFeature(this.startDateFeature.clone(dropFeatures));
        dropFeatures.setEndDateFeature(this.endDateFeature.clone(dropFeatures));
        dropFeatures.setWhen(this.when.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.startDateFeature);
        features.add(this.endDateFeature);
        features.add(this.when);
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
            if (feature instanceof ScheduleFeatures) {
                ScheduleFeatures dropFeatures = (ScheduleFeatures) feature;
                dropFeatures.setStartDateFeature(this.startDateFeature);
                dropFeatures.setEndDateFeature(this.endDateFeature);
                dropFeatures.setWhen(this.when);
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
        ScheduleFeaturesEditorManager.getInstance().startEditing(player, this);
    }

    public String getSimpleLocString(Location loc){
        return loc.getWorld().getName() + "-" + loc.getBlockX() + "-" + loc.getBlockY() + "-" + loc.getBlockZ();
    }
}
