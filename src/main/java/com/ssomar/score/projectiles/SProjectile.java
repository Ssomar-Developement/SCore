package com.ssomar.score.projectiles;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.custom.activators.group.ActivatorsFeature;
import com.ssomar.score.features.types.SProjectileTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.features.SProjectileFeatureInterface;
import com.ssomar.score.projectiles.loader.SProjectileLoader;
import com.ssomar.score.sobject.NewSObject;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class SProjectile extends NewSObject<SProjectile, SProjectileEditor, SProjectileEditorManager> {

    private String id;
    private String path;

    private SProjectileTypeFeature type;

    private List<FeatureAbstract> subFeatures;

    public void transformTheProjectile(Entity e, Player launcher, Material material) {
        if (e instanceof Projectile) {
            for (FeatureAbstract f : subFeatures) {
                if (f instanceof SProjectileFeatureInterface) {
                    ((SProjectileFeatureInterface) f).transformTheProjectile(e, launcher, material);
                }
            }
        }
    }


    public SProjectile(FeatureParentInterface parent, String id, String path) {
        super(parent, "SPROJ", "SPROJ", new String[]{}, Material.ARROW);
        this.id = id;
        this.path = path;
        reset();
    }

    public SProjectile(String id, String path) {
        super("SPROJ", "SPROJ", new String[]{}, Material.ARROW);
        this.id = id;
        this.path = path;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();

        errors.addAll(type.load(plugin, config, isPremiumLoading));

        //SsomarDev.testMsg("type: "+type.getValue().get().name()+ " for "+id, true);

        subFeatures = type.getValue().get().getFeatures(this);
        for (FeatureAbstract f : subFeatures) {
            //SsomarDev.testMsg("feature: "+f.getName(), true);
            errors.addAll(f.load(plugin, config, isPremiumLoading));
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        for(String s : config.getKeys(false)){
            config.set(s, null);
        }
        for (FeatureInterface feature : getFeatures()) {
            feature.save(config);
        }
    }

    @Override
    public SProjectile getValue() {
        return this;
    }

    @Override
    public SProjectile initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public void reset() {

        type = new SProjectileTypeFeature(this, "type", Optional.of(SProjectileType.ARROW), "Type", new String[]{}, Material.ARROW, false);

        subFeatures = type.getValue().get().getFeatures(this);
    }

    @Override
    public SProjectile clone(FeatureParentInterface newParent) {
        SProjectile clone = new SProjectile(this, id, path);
        clone.setType(type.clone(clone));
        List<FeatureAbstract> newFeatures = new ArrayList<>();
        for (FeatureAbstract f : subFeatures) {
            newFeatures.add((FeatureAbstract) f.clone(clone));
        }
        clone.setSubFeatures(newFeatures);
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<FeatureInterface>();
        features.add(type);
        features.addAll(this.subFeatures);
        return features;
    }

    @Override
    public String getParentInfo() {
        return "SProjectile: " + id;
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        File file = getFile();

        FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        return config;
    }

    @Override
    public File getFile() {
        File file = new File(path);
        if (!file.exists()) {
            try {
                new File(path).createNewFile();
                file = SProjectileLoader.getInstance().searchFileOfObject(id);
            } catch (IOException ignored) {
                return null;
            }
        }
        return file;
    }

    @Override
    public void reload() {
        if (getParent() instanceof SProjectile) {
            SProjectile sProjectile = (SProjectile) getParent();
            sProjectile.setType(type);
            sProjectile.setSubFeatures(subFeatures);
            //SsomarDev.testMsg("RELOAD INTO "+sProjectile.hashCode());
        }
    }

    public void update() {

        List<FeatureAbstract> newFeatures = new ArrayList<>();
        for (FeatureAbstract f : type.getValue().get().getFeatures(this)) {
            boolean found = false;
            for (FeatureAbstract f2 : subFeatures) {
                if (f2.getName().equals(f.getName())) {
                    //SsomarDev.testMsg("found " + f2.getName());
                    newFeatures.add(f2);
                    found = true;
                    break;
                }
            }
            if (!found) {
                newFeatures.add(f);
            }
        }
        setSubFeatures(newFeatures);
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        NewSObjectsManagerEditor.getInstance().startEditing(player, new SProjectilesEditor());
    }

    @Override
    public void openEditor(@NotNull Player player) {
        SProjectileEditorManager.getInstance().startEditing(player, this);
    }


    @Override
    public ActivatorsFeature getActivators() {
        return null;
    }

    @Override
    public Item dropItem(Location location, int amount) {
        return null;
    }

    @Override
    public ItemStack buildItem(int quantity, Optional<Player> creatorOpt) {
        return new ItemStack(type.getValue().get().getMaterial());
    }

    @Nullable
    @Override
    public NewSActivator getActivator(String actID) {
        return null;
    }

    @Override
    public List<String> getDescription() {
        List<String> description = new ArrayList<>();
        description.add("§7ID: §f" + id);
        description.add("§7Path: §f" + path);
        return description;
    }
}
