package com.ssomar.score.features.custom.bannersettings;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.patterns.group.PatternsGroupFeature;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.ColorIntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Banner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class BannerSettingsFeature extends FeatureWithHisOwnEditor<BannerSettingsFeature, BannerSettingsFeature, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItem {

    private ColorIntegerFeature color;
    private PatternsGroupFeature patterns;

    public BannerSettingsFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.bannerSettings);
        reset();
    }

    @Override
    public void reset() {
        this.color = new ColorIntegerFeature(this, Optional.empty(), FeatureSettingsSCore.color);
        this.patterns = new PatternsGroupFeature(this);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection potionSettings = config.getConfigurationSection(getName());
            errors.addAll(color.load(plugin, potionSettings, isPremiumLoading));
            errors.addAll(this.patterns.load(plugin, potionSettings, isPremiumLoading));
        }

        return errors;
    }

    public void load(SPlugin plugin, ItemStack item, boolean isPremiumLoading) {
        if (item != null && item.hasItemMeta()) {
            patterns.load(plugin, item, isPremiumLoading);
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof BlockStateMeta) {
                BlockStateMeta bmeta = (BlockStateMeta) meta;
                Banner banner = (Banner) bmeta.getBlockState();
                color.setValue(Optional.of(banner.getBaseColor().getColor().asRGB()));
            }
        }
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection potionSettings = config.createSection(getName());
        color.save(potionSettings);
        patterns.save(potionSettings);
    }

    @Override
    public BannerSettingsFeature getValue() {
        return this;
    }

    @Override
    public BannerSettingsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        if (color.getValue().isPresent())
            finalDescription[finalDescription.length - 2] = "&7Color : &e" + color.getValue().get();
        else finalDescription[finalDescription.length - 2] = "&7Color : &cNO VALUE";
        finalDescription[finalDescription.length - 1] = "&7Pattern(s) : &e" + patterns.getMCPatterns().size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public BannerSettingsFeature clone(FeatureParentInterface newParent) {
        BannerSettingsFeature dropFeatures = new BannerSettingsFeature(newParent);
        dropFeatures.color = color.clone(dropFeatures);
        dropFeatures.setPatterns(this.patterns.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(color, patterns));
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
            if (feature instanceof BannerSettingsFeature) {
                BannerSettingsFeature hiders = (BannerSettingsFeature) feature;
                hiders.setColor(color);
                hiders.setPatterns(patterns);
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

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {
        return args.getMeta() instanceof BannerMeta;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {
        ItemMeta meta = args.getMeta();
        try {
            if (meta instanceof BannerMeta) {
                BannerMeta bmeta = (BannerMeta) meta;
                // The color is in DATA MATERIAL example >> BLACK_BANNER
                /* DyeColor color;
                 if (config.getBannerSettings().getColor().getValue().isPresent()) {
                    if ((color = DyeColor.getByColor(Color.fromRGB(config.getBannerSettings().getColor().getValue().get()))) != null) {
                        bmeta.setBaseColor(color);
                    }
                    bmeta.setBaseColor(DyeColor.getByColor(Color.fromRGB(config.getBannerSettings().getColor().getValue().get())));
                } */
                if (!getPatterns().getMCPatterns().isEmpty())
                    bmeta.setPatterns(getPatterns().getMCPatterns());
            }
            /* if (meta instanceof BlockStateMeta && (getColor().getValue().isPresent() || !getPatterns().getMCPatterns().isEmpty())) {
                BlockStateMeta bmeta = (BlockStateMeta) meta;
                Banner banner = (Banner) bmeta.getBlockState();
                if (getColor().getValue().isPresent()) {
                    DyeColor color;
                    if ((color = DyeColor.getByColor(Color.fromRGB(getColor().getValue().get()))) != null) {
                        banner.setBaseColor(color);
                    }
                }
                if (!getPatterns().getMCPatterns().isEmpty())
                    banner.setPatterns(getPatterns().getMCPatterns());
                banner.update();
                bmeta.setBlockState(banner);
            }*/
        } catch (Exception ignored) {
            SsomarDev.testMsg("Error while applying BannerSettingsFeature on item meta", true);
        }
    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        SsomarDev.testMsg("banner 1", true);
        if (meta instanceof BannerMeta) {
            BannerMeta bannerMeta = (BannerMeta) meta;
            patterns.load(SCore.plugin,bannerMeta, true);



            /*SsomarDev.testMsg("banner 2", true);
            BlockStateMeta bmeta = (BlockStateMeta) meta;
            if (bmeta.getBlockState() instanceof Banner) {
                SsomarDev.testMsg("banner 3", true);
                Banner banner = (Banner) bmeta.getBlockState();
                color.setValue(Optional.of(banner.getBaseColor().getColor().asRGB()));
                int i = 0;
                for (org.bukkit.block.banner.Pattern pattern : banner.getPatterns()) {
                    PatternFeature patternFeature = new PatternFeature(this, "pattern" + i);
                    Map<String, Object> map = pattern.serialize();
                    int j = 0;
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String id = "subpattern" + j;
                        SubPatternFeature subPatternFeature = new SubPatternFeature(patternFeature, id);
                        subPatternFeature.getObject().setValue(Optional.ofNullable(entry.getValue()));
                        subPatternFeature.getString().setValue(Optional.ofNullable(entry.getKey()));
                        patternFeature.getSubPattern().put(id, subPatternFeature);
                        j++;
                    }
                    i++;
                }
            }*/
        }
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.BANNER;
    }
}
