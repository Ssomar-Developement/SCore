package com.ssomar.score.usedapi;

import com.gamingmesh.jobs.api.JobsPaymentEvent;
import com.gamingmesh.jobs.container.CurrencyType;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.JobsMoneyBoost;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class JobsAPI implements Listener {

    private static final Boolean DEBUG = false;

    @EventHandler
    public void onJobsPayment(JobsPaymentEvent event) {
        OfflinePlayer player = event.getPlayer();
        SsomarDev.testMsg("JobsEarningsBoostEvent", DEBUG);
        if (JobsMoneyBoost.getInstance().getActiveBoosts().containsKey(player.getUniqueId())) {

            double multiplier = 1;
            for (double m : JobsMoneyBoost.getInstance().getActiveBoosts().get(player.getUniqueId())) {
                multiplier *= m;
            }

            Map<CurrencyType, Double> payments  =event.getPayment();
            for (CurrencyType currencyType : payments.keySet()) {
                if(currencyType.equals(CurrencyType.MONEY)) payments.put(currencyType, payments.get(currencyType) * multiplier);
            }
        }
    }
}
