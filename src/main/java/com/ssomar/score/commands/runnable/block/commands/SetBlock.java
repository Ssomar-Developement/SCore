package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SetBlock extends BlockCommand {

    private static final Boolean DEBUG = false;

    @Override
    public void run(@Nullable Player p, @NotNull Block block, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        ActionInfo aInfo = sCommandToExec.getActionInfo();

        String mat = args.get(0).toUpperCase();
        UUID uuid = null;
        if (p != null) uuid = p.getUniqueId();
        if (Material.matchMaterial(mat) != null) {
            SafePlace.placeBlockWithEvent(block, Material.matchMaterial(mat), Optional.empty(), uuid, false, true);
        } else {
            World w = block.getWorld();
            List<Entity> entities = w.getEntities();
            SsomarDev.testMsg("entities size: "+entities.size(), DEBUG);

            if (entities.size() > 0) {
                SsomarDev.testMsg("entities size PASSE2: "+entities.size(), DEBUG);
                if (uuid != null && !SafePlace.verifSafePlace(uuid, block)) return;
                String command = "execute at " + entities.get(0).getUniqueId() + " run setblock " + block.getX() + " " + block.getY() + " " + block.getZ() + " " + args.get(0) + " replace";
                SsomarDev.testMsg("RUN: "+ command, DEBUG);
                RunConsoleCommand.runConsoleCommand(command, aInfo.isSilenceOutput());
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkMaterial(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETBLOCK {material}";
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
