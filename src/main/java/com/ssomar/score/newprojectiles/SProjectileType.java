package com.ssomar.score.newprojectiles;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.custom.enchantments.group.EnchantmentsGroupFeature;
import com.ssomar.score.features.custom.potioneffects.group.PotionEffectGroupFeature;
import com.ssomar.score.features.types.*;
import com.ssomar.score.newprojectiles.features.BounceFeature;
import com.ssomar.score.newprojectiles.features.ChargedFeature;
import com.ssomar.score.newprojectiles.features.ColorFeature;
import com.ssomar.score.newprojectiles.features.CriticalFeature;

import java.util.ArrayList;
import java.util.List;

public enum SProjectileType {
    ARROW("ARROW"),
    EGG("EGG"),
    FIREBALL("FIREBALL", "SMALL_FIREBALL", "DRAGON_FIREBALL"),
    SPLASH_POTION("SPLASH_POTION"),
    LINGERING_POTION("LINGERING_POTION"),
    ENDER_PEARL("ENDER_PEARL"),
    SHULKER_BULLET("SHULKER_BULLET"),
    SNOWBALL("SNOWBALL"),
    TRIDENT("TRIDENT"),
    WITHER_SKULL("WITHER_SKULL");

    private final String[] validNames;

    private BounceFeature bounce;
    private ChargedFeature charged;
    private CriticalFeature critical;

    private BooleanFeature despawn;
    private BooleanFeature glowing;
    private BooleanFeature gravity;
    private BooleanFeature incendiary;
    private BooleanFeature invisible;
    private BooleanFeature pickup;
    private BooleanFeature removeWhenHitBlock;
    private BooleanFeature silent;

    private ColorFeature color;

    private ColoredStringFeature customName;

    private DoubleFeature damage;
    private DoubleFeature radius;
    private DoubleFeature velocity;

    private IntegerFeature knockbackStrength;
    private IntegerFeature piercingLevel;

    private EnchantmentsGroupFeature enchantments;

    private PotionEffectGroupFeature potionEffects;

    private MaterialFeature visualItem;
    private IntegerFeature visualItemCustomTexture;

    SProjectileType(String... validNames) {
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

    public List<FeatureAbstract> getFeatures() {
        List<FeatureAbstract> features = new ArrayList<>();

        features.add(customName);
        features.add(invisible);
        features.add(glowing);
        features.add(bounce);
        features.add(despawn);
        features.add(velocity);
        features.add(silent);

        if(!SCore.is1v13Less()) {
            features.add(removeWhenHitBlock);
        }

        switch (this){
            case ARROW:
                features.add(gravity);
                if(!SCore.is1v13Less()) {
                    features.add(color);
                    features.add(piercingLevel);
                    features.add(critical);
                    features.add(damage);
                    features.add(knockbackStrength);
                    features.add(pickup);
                }
                break;
            case EGG: case ENDER_PEARL: case SNOWBALL:
                features.add(gravity);
                if(!SCore.is1v13Less()) {
                    features.add(visualItemCustomTexture);
                }
                break;
            case FIREBALL:
                features.add(gravity);
                features.add(radius);
                features.add(incendiary);
                break;
            case SPLASH_POTION: case LINGERING_POTION:
                features.add(gravity);
                if(!SCore.is1v13Less()) {
                    features.add(color);
                }
                features.add(potionEffects);
                break;
            case SHULKER_BULLET:
                features.add(gravity);
                break;
            case TRIDENT:
                features.add(gravity);
                if(!SCore.is1v13Less()) {
                    features.add(piercingLevel);
                    features.add(critical);
                    features.add(knockbackStrength);
                    features.add(pickup);
                    features.add(visualItemCustomTexture);
                }
                if(!SCore.is1v12Less()){
                    features.add(enchantments);
                }
                break;
            case WITHER_SKULL:
                features.add(radius);
                features.add(incendiary);
                features.add(charged);
                break;
        }

        if(!SCore.is1v11Less()) {
            //features.add(particles);
        }

        return features;
    }
}
