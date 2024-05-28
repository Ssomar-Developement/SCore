package com.ssomar.score.usedapi;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.logging.Utils;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.stat.provider.EntityStatProvider;
import io.lumine.mythic.lib.damage.AttackMetadata;
import io.lumine.mythic.lib.damage.DamageMetadata;
import io.lumine.mythic.lib.damage.DamageType;
import io.lumine.mythic.lib.element.Element;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class MyhticLibAPI {

    public static void mlib_damage(@Nullable LivingEntity attacker, LivingEntity entity, double damage, String damageType, @Nullable String element, boolean knockback) {

        Element elementEnum = null;
        if (element != null){
            try {
                elementEnum = Element.valueOf(element);
            } catch (IllegalArgumentException e) {
                Utils.sendConsoleMsg(SCore.plugin, "&cInvalid Element specification in MLIB_DAMAGE command ! Element : &7"+element);
                Utils.sendConsoleMsg(SCore.plugin, "&cAvailable elements are : &7"+Element.values());
            }
        }
        DamageMetadata damageMetadata;
        if (elementEnum == null) damageMetadata = new DamageMetadata(damage, DamageType.valueOf(damageType));
        else damageMetadata = new DamageMetadata(damage, elementEnum, DamageType.valueOf(damageType));

        EntityStatProvider entityStatProvider = null;
        if(attacker != null) entityStatProvider = new EntityStatProvider(attacker);

        AttackMetadata attacKMetadata = new AttackMetadata(damageMetadata, entityStatProvider);
        MythicLib.inst().getDamage().damage(attacKMetadata, entity, knockback);
    }
}
