package com.ssomar.score.projectiles;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.SProjectileTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.features.SProjectileFeatureInterface;
import com.ssomar.score.projectiles.loader.SProjectileLoader;
import com.ssomar.score.sobject.SObjectWithFileEditable;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class SProjectile extends SObjectWithFileEditable<SProjectile, SProjectileEditor, SProjectileEditorManager> {

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
        super(id, parent, FeatureSettingsSCore.SPROJECTILE, path, SProjectileLoader.getInstance());
        reset();
    }

    public SProjectile(String id, String path) {
        super(id, FeatureSettingsSCore.SPROJECTILE, path, SProjectileLoader.getInstance());
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

        type = new SProjectileTypeFeature(this, Optional.of(SProjectileType.ARROW), FeatureSettingsSCore.type);

        subFeatures = type.getValue().get().getFeatures(this);
    }

    @Override
    public SProjectile clone(FeatureParentInterface newParent) {
        SProjectile clone = new SProjectile(this, getId(), getPath());
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
        return "SProjectile: " + getId();
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
    public ItemStack getIconItem() {
        return new ItemStack(getType().getEditorMaterial());
    }
}
