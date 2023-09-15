package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.commands.runnable.player.commands.MobAround.mobAroundExecution;

/* MOB_AROUND {distance} {Your commands here} */
public class MobAround extends BlockCommand {

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
       return com.ssomar.score.commands.runnable.player.commands.MobAround.staticVerify(args, isFinalVerification, getTemplate());
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MOB_AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MOB_AROUND {distance} {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        mobAroundExecution(block.getLocation(), p, null, true, args, aInfo);
    }
}
