package com.ssomar.scoretestrecode.features.custom.blocktitle;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import com.ssomar.scoretestrecode.features.types.DoubleFeature;
import com.ssomar.scoretestrecode.features.types.list.ListColoredStringFeature;
import lombok.Getter;
import lombok.Setter;
import me.filoghost.holographicdisplays.api.beta.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.beta.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class BlockTitleFeatures extends FeatureWithHisOwnEditor<BlockTitleFeatures, BlockTitleFeatures, BlockTitleFeaturesEditor, BlockTitleFeaturesEditorManager> {

    private ListColoredStringFeature title;
    private DoubleFeature titleAjustement;
    private BooleanFeature activeTitle;

    public BlockTitleFeatures(FeatureParentInterface parent) {
        super(parent, "titleOptions", "Title features", new String[]{"&7&oThe title features"}, Material.ANVIL, true);
        reset();
    }

    @Override
    public void reset() {
        this.activeTitle = new BooleanFeature(this, "activeTitle", false, "Active title", new String[]{"&7&oActive title"}, Material.LEVER, true, false);
        this.title = new ListColoredStringFeature(this, "title", new ArrayList<>(), "Title", new String[]{"&7&oTitle"}, Material.NAME_TAG, true, false, Optional.empty());
        this.titleAjustement = new DoubleFeature(this, "titleAdjustement", Optional.of(0.5), "Title adjustement", new String[]{"&7&oTitle adjustement"}, Material.PISTON, true);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (isPremiumLoading && config.isConfigurationSection(getName())) {
            error.addAll(this.activeTitle.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
            error.addAll(this.title.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
            error.addAll(this.titleAjustement.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.activeTitle.save(section);
        this.title.save(section);
        this.titleAjustement.save(section);
    }

    @Override
    public BlockTitleFeatures getValue() {
        return this;
    }

    @Override
    public BlockTitleFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if(isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 4] = gui.PREMIUM;
        else finalDescription[finalDescription.length - 4] = gui.CLICK_HERE_TO_CHANGE;
        if (activeTitle.getValue())
            finalDescription[finalDescription.length - 3] = "&7Active title: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Active title: &c&l✘";

        finalDescription[finalDescription.length - 2] = "&7Title lines: &e&l" + title.getValue().size();

        finalDescription[finalDescription.length - 1] = "&7Title Ajustement: &e"+titleAjustement.getValue().get();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public BlockTitleFeatures clone(FeatureParentInterface newParent) {
        BlockTitleFeatures dropFeatures = new BlockTitleFeatures(newParent);
        dropFeatures.setActiveTitle(this.activeTitle.clone(dropFeatures));
        dropFeatures.setTitle(this.title.clone(dropFeatures));
        dropFeatures.setTitleAjustement(this.titleAjustement.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.activeTitle);
        features.add(this.title);
        features.add(this.titleAjustement);
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
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof BlockTitleFeatures) {
                BlockTitleFeatures dropFeatures = (BlockTitleFeatures) feature;
                dropFeatures.setActiveTitle(this.activeTitle);
                dropFeatures.setTitle(this.title);
                dropFeatures.setTitleAjustement(this.titleAjustement);
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
        BlockTitleFeaturesEditorManager.getInstance().startEditing(player, this);
    }

    /** Return the location of the Holo **/
    public Location spawn(@NotNull Location location, StringPlaceholder sp){
        if(!activeTitle.getValue()) return null;
        if(SCore.hasCMI){
            CMIHologram holo = new CMIHologram(UUID.randomUUID().toString(), location.clone().add(0, 0.5+getTitleAjustement().getValue().get(),0));
            List<String> lines = new ArrayList<>();
            for(String s : title.getValue()){
                s = StringConverter.coloredString(s);
                s = sp.replacePlaceholder(s);
                lines.add(s);
            }
            holo.setLines(lines);
            CMI.getInstance().getHologramManager().addHologram(holo);
            holo.update();
            //SsomarDev.testMsg("Hologram spawned >> "+holo.getCenterLocation());
            return holo.getCenterLocation();
        }
        else if(SCore.hasHolographicDisplays){
            Hologram holo = HolographicDisplaysAPI.get(SCore.plugin).createHologram(location.clone().add(0, 0.5+getTitleAjustement().getValue().get(),0));
            for(String s : getTitle().getValue()){
                s = StringConverter.coloredString(s);
                s = sp.replacePlaceholder(s);
                if(s.contains("ITEM::")){
                    Material material = Material.STONE;
                    try{
                        material = Material.valueOf(s.split("ITEM::")[1].trim().toUpperCase());
                    }
                    catch (Exception e){}
                    holo.getLines().appendItem(new ItemStack(material));
                }
                else holo.getLines().appendText(s);
            }
            return holo.getPosition().toLocation();
        }
        return null;
    }

    /** location is the location of the Holo **/
    public void remove(@NotNull Location location){
        if(SCore.hasCMI){
            List<CMIHologram> toRemove = new ArrayList<>();
            for(CMIHologram holo : CMI.getInstance().getHologramManager().getHolograms().values()){
               //SsomarDev.testMsg("holo locccccc> "+holo.getCenterLocation());
               if(holo.getCenterLocation().equals(location)){
                   toRemove.add(holo);
               }
            }
            if(toRemove.isEmpty());// SsomarDev.testMsg("HOLO NOT FOUND "+location.toString());
            else {
                for(CMIHologram holo : toRemove){
                    holo.remove();
                }
            }
        }
        else if(SCore.hasHolographicDisplays){
            for(Hologram holo : HolographicDisplaysAPI.get(ExecutableBlocks.plugin).getHolograms()){
                if(holo.getPosition().toLocation().equals(location)){
                    holo.delete();
                }
            }
        }
    }

    /** location is the location of the block / player **/
    public void update(@NotNull Location location, StringPlaceholder sp){
        if(SCore.hasHolographicDisplays || SCore.hasCMI){
            Location loc = spawn(location, sp);
            if(loc != null) remove(loc);
            spawn(location, sp);
        }
    }
}
