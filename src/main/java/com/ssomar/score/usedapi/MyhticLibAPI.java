package com.ssomar.score.usedapi;

import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.damage.AttackMetadata;
import io.lumine.mythic.lib.damage.DamageMetadata;
import io.lumine.mythic.lib.damage.DamageType;
import io.lumine.mythic.lib.element.Element;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class MyhticLibAPI {

    public static void mlib_damage(Player player, double damage, String damageType, @Nullable String element){

        Element elementEnum = Element.valueOf(element);
        DamageMetadata damageMetadata;
        if(elementEnum == null) damageMetadata = new DamageMetadata(damage, DamageType.valueOf(damageType));
        else damageMetadata = new DamageMetadata(damage, elementEnum, DamageType.valueOf(damageType));

        AttackMetadata attacKMetadata = new AttackMetadata(damageMetadata, null);
        MythicLib.inst().getDamage().registerAttack(attacKMetadata);
    }
}
