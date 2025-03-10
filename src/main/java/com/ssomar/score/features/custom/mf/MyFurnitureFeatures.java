package com.ssomar.score.features.custom.mf;

import com.ssomar.myfurniture.MyFurniture;
import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.MyFurnitureFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.Dependency;
import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.strings.StringConverter;
import com.ssomar.score.utils.writer.NameSpaceKeyWriterReader;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class MyFurnitureFeatures extends FeatureWithHisOwnEditor<MyFurnitureFeatures, MyFurnitureFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItem {

    private BooleanFeature enable;
    private MyFurnitureFeature myFurnitureID;

    public MyFurnitureFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.myFurnitureFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.enable = new BooleanFeature(this, false, FeatureSettingsSCore.enable);
        this.myFurnitureID = new MyFurnitureFeature(this, FeatureSettingsSCore.myfurnitureID);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            error.addAll(this.enable.load(plugin, section, isPremiumLoading));
            error.addAll(this.myFurnitureID.load(plugin, section, isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.enable.save(section);
        this.myFurnitureID.save(section);

        if(isSavingOnlyIfDiffDefault() && section.getKeys(false).isEmpty()){
            config.set(getName(), null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    public MyFurnitureFeatures getValue() {
        return this;
    }

    @Override
    public MyFurnitureFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 3] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        if (enable.getValue())
            finalDescription[finalDescription.length - 2] = "&7Enabled: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Enabled: &c&l✘";

        finalDescription[finalDescription.length - 1] = "&7MyFurniture: &e" + myFurnitureID.getValueID().orElse("&cNONE");


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public MyFurnitureFeatures clone(FeatureParentInterface newParent) {
        MyFurnitureFeatures dropFeatures = new MyFurnitureFeatures(newParent);
        dropFeatures.setEnable(this.enable.clone(dropFeatures));
        dropFeatures.setMyFurnitureID(this.myFurnitureID.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.enable);
        features.add(this.myFurnitureID);
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
            if (feature instanceof MyFurnitureFeatures) {
                MyFurnitureFeatures dropFeatures = (MyFurnitureFeatures) feature;
                dropFeatures.setEnable(this.enable);
                dropFeatures.setMyFurnitureID(this.myFurnitureID);
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

    public String getSimpleLocString(Location loc) {
        return loc.getWorld().getName() + "-" + loc.getBlockX() + "-" + loc.getBlockY() + "-" + loc.getBlockZ();
    }

    @Override
    public boolean isAvailable() {
        //SsomarDev.testMsg("is1v21v4Plus: "+SCore.is1v21v4Plus()+ " Dependency.MY_FURNITURE.isInstalled(): "+Dependency.MY_FURNITURE.isInstalled(), true);
        return SCore.is1v21v2Plus() && Dependency.MY_FURNITURE.isInstalled();
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {
        return true;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {
        if(!enable.getValue() || !myFurnitureID.getValue().isPresent() || !Dependency.MY_FURNITURE.isEnabled()) return;
        PersistentDataContainer persistentDataContainer = args.getMeta().getPersistentDataContainer();
        NameSpaceKeyWriterReader.writeString(MyFurniture.plugin, persistentDataContainer, "MF-ID", myFurnitureID.getValueID().get());
    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {

        if(isAvailable() && isApplicable(args) && Dependency.MY_FURNITURE.isEnabled()){
            PersistentDataContainer persistentDataContainer = args.getMeta().getPersistentDataContainer();
            Optional<String> mfID = NameSpaceKeyWriterReader.readString(MyFurniture.plugin, persistentDataContainer, "MF-ID");
            if(mfID.isPresent()) {
                myFurnitureID.setValue(mfID);
                enable.setValue(true);
            }

        }
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.MYFURNITURE;
    }
}
