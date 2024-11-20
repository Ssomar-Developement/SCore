package com.ssomar.score.commands.runnable.player.commands.absorption;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Absorption extends PlayerCommand {

    public Absorption() {
        CommandSetting amount = new CommandSetting("amount", 0, Double.class, 5.0);
        CommandSetting time = new CommandSetting("time", 1, Integer.class, 200);
        List<CommandSetting> settings = getSettings();
        settings.add(amount);
        settings.add(time);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        double absorption = (double) sCommandToExec.getSettingValue("amount");
        int time = (int) sCommandToExec.getSettingValue("time");
        double currentabsorption = receiver.getAbsorptionAmount();

        try {
            if(time <= 0) {
                receiver.setAbsorptionAmount(currentabsorption + absorption);
            }else{
                long timestamp = System.currentTimeMillis();
                /* convert time ticks to time secs */
                time = time * 50;
                long finalTime = time+timestamp;
                AbsorptionObject absorptionObject = new AbsorptionObject(receiver.getUniqueId(), absorption, finalTime);
                AbsorptionManager.getInstance().addAbsorption(AbsorptionManager.getInstance().applyAbsorption(absorptionObject));
            }
        }catch(IllegalArgumentException e){
            SsomarDev.testMsg("ABSORPTION Error: "+e.getMessage(), true);
            //I don't know how to add a debug message, but this happens if the player tries to remove ABSORPTION
            //like ABSORPTION -5, if the player has ABSORPTION 5, it will work, but once it worked now the player
            //has ABSORPTION 0, if he tries again, error
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ABSORPTION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ABSORPTION amount:5.0 time:200";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }
}
