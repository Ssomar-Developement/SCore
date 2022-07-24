package com.ssomar.score.commands.runnable.player.events;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.XpBoost;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class XPBoostEvent implements Listener {

    private static final Boolean DEBUG = false;

    @EventHandler(priority = EventPriority.HIGH)
    public void playerExpChangeEvent(PlayerExpChangeEvent e) {
        Player player = e.getPlayer();
        if (DEBUG) SsomarDev.testMsg("XPBoostEvent");
        if (XpBoost.getInstance().getActiveBoosts().containsKey(player.getUniqueId())) {

            double multiplier = 1;
            for (double m : XpBoost.getInstance().getActiveBoosts().get(player.getUniqueId())) {
                multiplier *= m;
            }

            if (DEBUG) SsomarDev.testMsg("XPBoostEvent base: " + e.getAmount());
            if (DEBUG) SsomarDev.testMsg("XPBoostEvent modified " + (int) (e.getAmount() * multiplier));
            e.setAmount((int) (e.getAmount() * multiplier));
        }
    }
}
