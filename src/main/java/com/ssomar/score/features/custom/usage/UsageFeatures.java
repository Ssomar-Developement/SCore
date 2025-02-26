package com.ssomar.score.features.custom.usage;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.usage.useperday.UsePerDayFeature;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class UsageFeatures extends FeatureWithHisOwnEditor<UsageFeatures, UsageFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> {

    private IntegerFeature usage;
    private BooleanFeature isRefreshableClean;
    private IntegerFeature usageLimit;

    private UsePerDayFeature usePerDay;
    private String objectId;

    public UsageFeatures(FeatureParentInterface parent, String objectId) {
        super(parent, FeatureSettingsSCore.usageFeatures);
        this.objectId = objectId;
        reset();
    }

    @Override
    public void reset() {
        this.usage = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.usage);
        this.isRefreshableClean = new BooleanFeature(this, true, FeatureSettingsSCore.isRefreshableClean);
        this.usageLimit = new IntegerFeature(this, Optional.of(-1), FeatureSettingsSCore.usageLimit);
        this.usePerDay = new UsePerDayFeature(this, getObjectId());
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            error.addAll(this.usage.load(plugin, section, isPremiumLoading));
            error.addAll(this.isRefreshableClean.load(plugin, section, isPremiumLoading));
            error.addAll(this.usageLimit.load(plugin, section, isPremiumLoading));
            error.addAll(this.usePerDay.load(plugin, section, isPremiumLoading));
        }
        // Old version
        else {
            error.addAll(this.usage.load(plugin, config, isPremiumLoading));
            error.addAll(this.isRefreshableClean.load(plugin, config, isPremiumLoading));
            error.addAll(this.usageLimit.load(plugin, config, isPremiumLoading));
            error.addAll(this.usePerDay.load(plugin, config, isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.usage.save(section);
        this.isRefreshableClean.save(section);
        this.usageLimit.save(section);
        this.usePerDay.save(section);

        if(isSavingOnlyIfDiffDefault() && section.getKeys(false).isEmpty()){
            config.set(getName(), null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    public UsageFeatures getValue() {
        return this;
    }

    @Override
    public UsageFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 5];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 5] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 5] = GUI.CLICK_HERE_TO_CHANGE;

        finalDescription[finalDescription.length - 4] = "&7Usage: &e" + usage.getValue().orElse(0);

        finalDescription[finalDescription.length - 3] = "&7Is Refreshable Clean: &e" + isRefreshableClean.getValue();

        finalDescription[finalDescription.length - 2] = "&7Usage Limit: &e" + usageLimit.getValue().orElse(-1);

        finalDescription[finalDescription.length - 1] = "&7Use Per Day: &e" + usePerDay.getValue().getMaxUsePerDay().getValue().orElse(-1);

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public UsageFeatures clone(FeatureParentInterface newParent) {
        UsageFeatures dropFeatures = new UsageFeatures(newParent, this.objectId);
        dropFeatures.setUsage(this.usage.clone(dropFeatures));
        dropFeatures.setIsRefreshableClean(this.isRefreshableClean.clone(dropFeatures));
        dropFeatures.setUsageLimit(this.usageLimit.clone(dropFeatures));
        dropFeatures.setUsePerDay(this.usePerDay.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(usage);
        features.add(isRefreshableClean);
        features.add(usageLimit);
        features.add(usePerDay);
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
            if (feature instanceof UsageFeatures) {
                UsageFeatures dropFeatures = (UsageFeatures) feature;
                dropFeatures.setUsage(usage);
                dropFeatures.setIsRefreshableClean(isRefreshableClean);
                dropFeatures.setUsageLimit(usageLimit);
                dropFeatures.setUsePerDay(usePerDay);
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
}
