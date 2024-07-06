package com.ssomar.score.projectiles;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.particles.group.ParticlesGroupFeature;
import com.ssomar.score.features.custom.potionsettings.PotionSettingsFeature;
import com.ssomar.score.projectiles.features.*;
import com.ssomar.score.projectiles.features.fireworkFeatures.FireworkFeatures;
import com.ssomar.score.projectiles.features.visualItemFeature.VisualItemFeature;
import com.ssomar.score.utils.FixedMaterial;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.*;

import java.util.*;

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
    THROWNEXPBOTTLE(FixedMaterial.getMaterial(Arrays.asList("EXPERIENCE_BOTTLE", "EXP_BOTTLE")), "THROWNEXPBOTTLE"),
    FIREWORK(FixedMaterial.getMaterial(Arrays.asList("FIREWORK_ROCKET")), "FIREWORK"),
    SPECTRAL_ARROW(FixedMaterial.getMaterial(Arrays.asList("SPECTRAL_ARROW")), "SPECTRAL_ARROW"),
    WIND_CHARGE(FixedMaterial.getMaterial(Arrays.asList("WIND_CHARGE")), "WIND_CHARGE"),;

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

        if(SCore.is1v17Plus()){
            features.add(new FireFeature(parent));
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
            case FIREBALL:
            case DRAGON_FIREBALL:
                features.add(new GravityFeature(parent));
                features.add(new RadiusFeature(parent));
                features.add(new IncendiaryFeature(parent));
                break;
            case SPLASH_POTION:
            case LINGERING_POTION:
                features.add(new GravityFeature(parent));
                /* color feature already in PotionSettingsFeature
                 if (!SCore.is1v13Less()) {
                    features.add(new ColorFeature(parent));
                } */
                features.add(new PotionSettingsFeature(parent));
                break;
            case SHULKER_BULLET: case WIND_CHARGE:
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
            case FIREWORK:
                features.add(new FireworkFeatures(parent));
                break;
        }

        if (!SCore.is1v11Less()) {
            features.add(new ParticlesGroupFeature(parent, false));
        }

        return features;
    }

    public static Map<String, Class> getProjectilesClasses() {
        Map<String, Class> projectiles = new HashMap<>();
        try {
            projectiles.put("ARROW", Arrow.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SPECTRALARROW", SpectralArrow.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SPECTRAL_ARROW", SpectralArrow.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("DRAGONFIREBALL", DragonFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("DRAGON_FIREBALL", DragonFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("FIREBALL", Fireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SMALLFIREBALL", SmallFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LARGEFIREBALL", LargeFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LARGE_FIREBALL", LargeFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SIZEDFIREBALL", SizedFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SIZED_FIREBALL", SizedFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SNOWBALL", Snowball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("THROWNEXPBOTTLE", ThrownExpBottle.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("WITHERSKULL", WitherSkull.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("WITHER_SKULL", WitherSkull.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("EGG", Egg.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("ENDERPEARL", EnderPearl.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("ENDER_PEARL", EnderPearl.class);
        } catch (Exception | Error ignored) {
        }

        try {
            projectiles.put("LINGERINGPOTION", LingeringPotion.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LINGERING_POTION", LingeringPotion.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SPLASHPOTION", SplashPotion.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SPLASH_POTION", SplashPotion.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LLAMASPIT", LlamaSpit.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LLAMA_SPIT", LlamaSpit.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SHULKERBULLET", ShulkerBullet.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SHULKER_BULLET", ShulkerBullet.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("TRIDENT", Trident.class);
        } catch (Exception | Error ignored) {
        }

        try {
            projectiles.put("FIREWORK", Firework.class);
        } catch (Exception | Error ignored) {
        }

        try {
            projectiles.put("SPECTRAL_ARROW", SpectralArrow.class);
        } catch (Exception | Error ignored) {
        }

        try {
            projectiles.put("WIND_CHARGE", WindCharge.class);
        } catch (Exception | Error ignored) {
        }

        return projectiles;
    }
}
