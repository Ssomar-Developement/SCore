package com.ssomar.score.newprojectiles;

/* import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.newprojectiles.features.SProjectileFeatureInterface;
import com.ssomar.score.sobject.NewSObject;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SProjectile extends NewSObject<SProjectile, SProjectileEditor, SProjectileEditorManager> {

    private String id;
    private String path;

    private SProjectileType type;

    private List<FeatureAbstract> features;

    public void transformTheProjectile(Entity e, Player launcher) {
        if(e instanceof Projectile){
            for(FeatureAbstract f : features){
                if(f instanceof SProjectileFeatureInterface){
                    ((SProjectileFeatureInterface) f).transformTheProjectile(e, launcher, Material.RABBIT);
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

        features = type.getFeatures();
        for(FeatureAbstract f : features){
            errors.addAll(f.load(plugin, config, isPremiumLoading));
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {

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

    }

    @Override
    public SProjectile clone(FeatureParentInterface newParent) {
        return null;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return null;
    }

    @Override
    public String getParentInfo() {
        return null;
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return null;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public void reload() {

    }

    @Override
    public void openBackEditor(@NotNull Player player) {

    }

    @Override
    public void openEditor(@NotNull Player player) {

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
        return null;
    }

    @Nullable
    @Override
    public NewSActivator getActivator(String actID) {
        return null;
    }

    @Override
    public List<String> getDescription() {
        return null;
    }
}*/
