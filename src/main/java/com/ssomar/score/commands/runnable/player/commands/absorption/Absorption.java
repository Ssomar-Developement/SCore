package com.ssomar.score.commands.runnable.player.commands.absorption;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* ABSORPTION {amount} [timeinticks] */
public class Absorption extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        double absorption = Double.parseDouble(args.get(0));
        double currentabsorption = receiver.getAbsorptionAmount();
        long time;

        try {
            time = Integer.valueOf(args.get(1));
            SsomarDev.testMsg("time: "+time, true);
        }catch(ArrayIndexOutOfBoundsException e){
            time = 0;
        }

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
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        /* if(SCore.is1v20v4Plus()){
            return Optional.of("This command is not available for 1.20.4+, you should now use the vanilla command /attribute ( https://discord.com/channels/701066025516531753/1184977479467794572/1188938447474409593 )");
        } */

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        if(args.size() >= 2){
            ArgumentChecker ac2 = checkDouble(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ABSORPTION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ABSORPTION {amount} [time in ticks]";
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
