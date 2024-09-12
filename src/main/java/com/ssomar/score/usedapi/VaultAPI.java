package com.ssomar.score.usedapi;

import com.ssomar.score.SCore;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.strings.StringConverter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class VaultAPI {

    private static Economy econ = null;
    public final SendMessage sm = new SendMessage();

    public VaultAPI() {
    }


    public boolean verifEconomy(Player p) {
        if (SCore.hasVault) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                p.sendMessage(StringConverter.coloredString("&4&l[SCore] &cPlease contact your administrator, Vault dont find an Economy plugin !"));
                SCore.plugin.getServer().getLogger().severe("[SCore] Vault dont find an Economy plugin !");
                return false;
            }
            econ = rsp.getProvider();
            if (econ == null) {
                p.sendMessage(StringConverter.coloredString("&4&l[SCore] &cPlease contact your administrator, Vault has problem with the Economy plugin !"));
                SCore.plugin.getServer().getLogger().severe("[SCore] Vault has problem with the Economy plugin !");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean hasMoney(Player p, double amount, String customMessage) {
        if (!SCore.hasVault) return false;

        if (!econ.has(p, amount)) {
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            if (customMessage.length() == 0)
                sm.sendMessage(p, StringConverter.coloredString(MessageMain.getInstance().getMessage(SCore.plugin, Message.ERROR_MONEY).replaceAll("%amount%", amount + "").replaceAll("%balance%", df.format(econ.getBalance(p)) + "")));
            else
                sm.sendMessage(p, StringConverter.coloredString(customMessage.replaceAll("%amount%", amount + "").replaceAll("%balance%", df.format(econ.getBalance(p)) + "")));
        }
        return econ.has(p, amount);
    }

    public void takeMoney(Player p, double amount) {

        if (!SCore.hasVault) return;

        else econ.withdrawPlayer(p, amount);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        sm.sendMessage(p, StringConverter.coloredString(MessageMain.getInstance().getMessage(SCore.plugin, Message.NEW_BALANCE_NEGATIVE).replaceAll("%amount%", amount + "").replaceAll("%balance%", df.format(econ.getBalance(p)) + "")));
    }

    public void addMoney(Player p, double amount) {

        if (!SCore.hasVault) return;

        else econ.depositPlayer(p, amount);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        sm.sendMessage(p, StringConverter.coloredString(MessageMain.getInstance().getMessage(SCore.plugin, Message.NEW_BALANCE_POSITIVE).replaceAll("%amount%", amount + "").replaceAll("%balance%", df.format(econ.getBalance(p)) + "")));
    }

}
