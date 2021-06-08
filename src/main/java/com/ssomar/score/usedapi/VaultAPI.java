package com.ssomar.score.usedapi;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.ssomar.score.SCore;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;

import net.milkbowl.vault.economy.Economy;

public class VaultAPI {
	
	private boolean error=false;
	
	private static RegisteredServiceProvider<Economy> rsp= null;
	
	public SendMessage sm = new SendMessage();
	
	private static Economy econ = null;
	
	public VaultAPI() {}
	
	
	public boolean verifEconomy(Player p) {
		if(SCore.hasVault) {
			rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	        	p.sendMessage(StringConverter.coloredString("&4&l[SCore] &cPlease contact your administrator, Vault dont find an Economy plugin !"));
				SCore.plugin.getServer().getLogger().severe("[SCore] Vault dont find and Economy plugin !");
				error=true;
	            return false;
	        }
	        econ = rsp.getProvider();
	        if(econ == null) {
	        	p.sendMessage(StringConverter.coloredString("&4&l[SCore] &cPlease contact your administrator, Vault has problem with the Economy plugin !"));
				SCore.plugin.getServer().getLogger().severe("[SCore] Vault has problem with the Economy plugin !");
				error=true;
	        	return false;
	        }
	        return true;
		}
		else {
			p.sendMessage(StringConverter.coloredString("&4&l[SCore] &cPlease contact your administrator, Vault is not detected !"));
			SCore.plugin.getServer().getLogger().severe("[SCore] Vault is not detected !");
			error=true;
			return false;
		}
	}
	
	public boolean hasMoney(Player p, double amount, String customMessage) {
		if(error) return false;
		
		if(!econ.has(p, amount)) {
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);
			if(customMessage.length()== 0) sm.sendMessage(p, StringConverter.coloredString(MessageMain.getInstance().getMessage(SCore.getPlugin(), Message.ERROR_MONEY).replaceAll("%amount%", amount+"").replaceAll("%balance%", df.format(econ.getBalance(p))+"")));
			else sm.sendMessage(p, StringConverter.coloredString(customMessage.replaceAll("%amount%", amount+"").replaceAll("%balance%", df.format(econ.getBalance(p))+"")));
		}
		return econ.has(p, amount);
	}
	
	public void takeMoney(Player p, double amount) {
		
		if(error) return;
		else econ.withdrawPlayer(p, amount);
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		sm.sendMessage(p, StringConverter.coloredString(MessageMain.getInstance().getMessage(SCore.getPlugin(), Message.NEW_BALANCE).replaceAll("%amount%", amount+"").replaceAll("%balance%", df.format(econ.getBalance(p))+"")));
	}

}
