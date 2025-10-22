package com.ssomar.score.commands.runnable.mixed_player_entity.commands.addtempattribute;

import com.ssomar.score.SCore;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.TemporaryAttributeQuery;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class AddTemporaryAttributeManager {
    /**
     * It will perform a query at TemporaryAttributeQuery for expired temporary attributes and
     * remove them
     * @param player
     */
    public static void removeExpiredAttributes(Player player) {
        ArrayList<AddTemporaryAttributeObject> tempAttrlist = TemporaryAttributeQuery.getTemporaryAttributesToRemove(Database.getInstance().connect(), String.valueOf(player.getUniqueId()));

        for (AddTemporaryAttributeObject tempAttr : tempAttrlist) {
            AttributeUtils.removeSpecificAttribute(player, tempAttr.getAttribute_type(), tempAttr.getAttribute_key());
        }
    }

}
