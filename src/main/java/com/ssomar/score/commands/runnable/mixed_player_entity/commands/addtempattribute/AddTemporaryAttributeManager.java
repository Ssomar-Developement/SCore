package com.ssomar.score.commands.runnable.mixed_player_entity.commands.addtempattribute;

import com.ssomar.score.SCore;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.TemporaryAttributeQuery;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class AddTemporaryAttributeManager {
    /**
     * It will perform a query at TemporaryAttributeQuery for expired temporary attributes and
     * remove them
     * @param player
     */
    public static void removeExpiredAttributes(Player player) {
        UUID playerUUID = player.getUniqueId();

        Runnable runnableAsync = new Runnable() {
            @Override
            public void run() {
                // Fetch expired attributes from database (don't delete yet)
                ArrayList<AddTemporaryAttributeObject> tempAttrlist = TemporaryAttributeQuery.fetchExpiredTemporaryAttributes(Database.getInstance().connect(), playerUUID.toString());

                // Sync back to main thread to modify player
                Runnable runnableSync = new Runnable() {
                    @Override
                    public void run() {
                        Player getBackPlayer = Bukkit.getPlayer(playerUUID);
                        if (getBackPlayer == null || !getBackPlayer.isOnline()) return;

                        for (AddTemporaryAttributeObject tempAttr : tempAttrlist) {
                            AttributeUtils.removeSpecificAttribute(getBackPlayer, tempAttr.getAttribute_type(), tempAttr.getAttribute_key());
                        }

                        // Delete AFTER successful removal from player - run async in the method
                        SCore.schedulerHook.runAsyncTask(() -> {
                            for (AddTemporaryAttributeObject tempAttr : tempAttrlist) {
                                TemporaryAttributeQuery.removeFromRecords(Database.getInstance().connect(), tempAttr.getAttribute_key());
                            }
                        },0);
                    }
                };
                SCore.schedulerHook.runTask(runnableSync, 0);
            }
        };
        SCore.schedulerHook.runAsyncTask(runnableAsync, 0);
    }

}
