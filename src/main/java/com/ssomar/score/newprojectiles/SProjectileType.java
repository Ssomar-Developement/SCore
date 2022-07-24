package com.ssomar.score.newprojectiles;

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

    private String [] validNames;

    SProjectileType(String ... validNames) {
        this.validNames = validNames;
    }


    public static SProjectileType valueOfCustom(String str){
        for(SProjectileType type : values()){
            for(String name : type.validNames){
                if(name.equalsIgnoreCase(str)){
                    return type;
                }
            }
        }
        return null;
    }
}
