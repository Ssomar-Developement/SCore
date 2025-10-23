package com.ssomar.score.commands.runnable.player.events;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.McMMOXpBoost;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class McMMOXPBoostEvent implements Listener {

    private static final Boolean DEBUG = true;

    @EventHandler(priority = EventPriority.HIGH)
    public void onMcMMOPlayerXpGain(McMMOPlayerXpGainEvent e) {
        if (!SCore.hasMcMMO) return;

        Player player = e.getPlayer();
        SsomarDev.testMsg("McMMOXPBoostEvent", DEBUG);

        if (McMMOXpBoost.getInstance().getActiveBoosts().containsKey(player.getUniqueId())) {
            double multiplier = 1;
            for (double m : McMMOXpBoost.getInstance().getActiveBoosts().get(player.getUniqueId())) {
                multiplier *= m;
            }

            SsomarDev.testMsg("McMMOXPBoostEvent base: " + e.getRawXpGained(), DEBUG);
            SsomarDev.testMsg("McMMOXPBoostEvent modified " + (float) (e.getRawXpGained() * multiplier), DEBUG);
            e.setRawXpGained((float) (e.getRawXpGained() * multiplier));
        }
    }
}
