package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.api.executableblocks.config.ExecutableBlockInterface;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DropExecutableBlock extends EntityCommand {

    public static final Boolean DEBUG = false;

    //
    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        //SsomarDev.testMsg("DropExecutableItem.run()", DEBUG);
        if (SCore.hasExecutableBlocks && ExecutableBlocksAPI.getExecutableBlocksManager().isValidID(args.get(0))) {
            //SsomarDev.testMsg("DropExecutableItem.run() - hasExecutableItems", DEBUG);
            int amount = Double.valueOf(args.get(1)).intValue();
            if (amount > 0) {
                //SsomarDev.testMsg("DropExecutableItem.run() - amount > 0", DEBUG);
                Optional<ExecutableBlockInterface> eiOpt = ExecutableBlocksAPI.getExecutableBlocksManager().getExecutableBlock(args.get(0));
                if (eiOpt.isPresent()) {
                    //SsomarDev.testMsg(">> loc: " + entity.getLocation());
                    entity.getWorld().dropItem(entity.getLocation(), eiOpt.get().buildItem(amount, Optional.ofNullable(p)));
                }
            }
        }
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
       return staticVerif(args, isFinalVerification, getTemplate());
    }

    public static Optional<String> staticVerif(List<String> args, boolean isFinalVerification, String template) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + template);

        ArgumentChecker ac = checkExecutableBlockID(args.get(0), isFinalVerification, template);
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkDouble(args.get(1), isFinalVerification, template);
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DROPEXECUTABLEBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DROPEXECUTABLEBLOCK {id} {quantity}";
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
