package com.ssomar.score.projectiles;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.particles.group.ParticlesGroupFeature;
import com.ssomar.score.features.custom.potionsettings.PotionSettingsFeature;
import com.ssomar.score.projectiles.features.*;
import com.ssomar.score.projectiles.features.visualItemFeature.VisualItemFeature;
import com.ssomar.score.utils.FixedMaterial;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum SProjectileType {
    ARROW(FixedMaterial.getMaterial(Arrays.asList("ARROW")), "ARROW"),
    EGG(FixedMaterial.getMaterial(Arrays.asList("EGG")), "EGG"),
    FIREBALL(FixedMaterial.getMaterial(Arrays.asList("FIRE_CHARGE")), "FIREBALL", "SMALL_FIREBALL"),
    SPLASH_POTION(FixedMaterial.getMaterial(Arrays.asList("SPLASH_POTION")), "SPLASH_POTION"),
    LINGERING_POTION(FixedMaterial.getMaterial(Arrays.asList("LINGERING_POTION")), "LINGERING_POTION"),
    ENDER_PEARL(FixedMaterial.getMaterial(Arrays.asList("ENDER_PEARL")), "ENDER_PEARL"),
    SHULKER_BULLET(FixedMaterial.getMaterial(Arrays.asList("PURPLE_SHULKER_BOX")), "SHULKER_BULLET"),
    SNOWBALL(FixedMaterial.getMaterial(Arrays.asList("SNOWBALL", "SNOW_BALL")), "SNOWBALL"),
    TRIDENT(FixedMaterial.getMaterial(Arrays.asList("TRIDENT")), "TRIDENT"),
    WITHER_SKULL(FixedMaterial.getMaterial(Arrays.asList("WITHER_SKELETON_SKULL")), "WITHER_SKULL"),
    DRAGON_FIREBALL(FixedMaterial.getMaterial(Arrays.asList("DRAGON_HEAD")), "DRAGON_FIREBALL"),
    THROWNEXPBOTTLE(FixedMaterial.getMaterial(Arrays.asList("EXPERIENCE_BOTTLE", "EXP_BOTTLE")), "THROWNEXPBOTTLE");

    private Material material;
    private final String[] validNames;


    SProjectileType(Material material, String... validNames) {
        this.material = material;
        this.validNames = validNames;

    }


    public static SProjectileType valueOfCustom(String str) {
        for (SProjectileType type : values()) {
            for (String name : type.validNames) {
                if (name.equalsIgnoreCase(str)) {
                    return type;
                }
            }
        }
        return null;
    }

    public List<FeatureAbstract> getFeatures(FeatureParentInterface parent) {
        List<FeatureAbstract> features = new ArrayList<>();

        features.add(new CustomNameVisisbleFeature(parent));
        features.add(new CustomNameFeature(parent));
        features.add(new InvisibleFeature(parent));
        features.add(new GlowingFeature(parent));
        features.add(new BounceFeature(parent));
        features.add(new DespawnFeature(parent));
        features.add(new VelocityFeature(parent));
        features.add(new SilentFeature(parent));

        if (!SCore.is1v13Less()) {
            features.add(new RemoveWhenHitBlockFeature(parent));
        }

        switch (this) {
            case ARROW:
                features.add(new GravityFeature(parent));
                if (!SCore.is1v13Less()) {
                    features.add(new ColorFeature(parent));
                    features.add(new PierceLevelFeature(parent));
                    features.add(new CriticalFeature(parent));
                    features.add(new DamageFeature(parent));
                    features.add(new KnockbackStrengthFeature(parent));
                    features.add(new PickupFeature(parent));
                }
                break;
            case EGG:
            case ENDER_PEARL:
            case SNOWBALL:
            case THROWNEXPBOTTLE:
                features.add(new GravityFeature(parent));
                if (!SCore.is1v13Less()) {
                    features.add(new VisualItemFeature(parent));
                    features.add(new CustomModelDataFeature(parent));
                }
                break;
            case FIREBALL: case DRAGON_FIREBALL:
                features.add(new GravityFeature(parent));
                features.add(new RadiusFeature(parent));
                features.add(new IncendiaryFeature(parent));
                break;
            case SPLASH_POTION:
            case LINGERING_POTION:
                features.add(new GravityFeature(parent));
                if (!SCore.is1v13Less()) {
                    features.add(new ColorFeature(parent));
                }
                features.add(new PotionSettingsFeature(parent));
                break;
            case SHULKER_BULLET:
                features.add(new GravityFeature(parent));
                break;
            case TRIDENT:
                features.add(new GravityFeature(parent));
                if (!SCore.is1v13Less()) {
                    features.add(new PierceLevelFeature(parent));
                    features.add(new CriticalFeature(parent));
                    features.add(new KnockbackStrengthFeature(parent));
                    features.add(new PickupFeature(parent));
                    features.add(new CustomModelDataFeature(parent));
                }
                if (!SCore.is1v12Less()) {
                    features.add(new EnchantmentsFeature(parent, false));
                }
                break;
            case WITHER_SKULL:
                features.add(new GravityFeature(parent));
                features.add(new RadiusFeature(parent));
                features.add(new IncendiaryFeature(parent));
                features.add(new ChargedFeature(parent));
                break;
        }

        if (!SCore.is1v11Less()) {
            features.add(new ParticlesGroupFeature(parent, false));
        }

        return features;
    }
}
