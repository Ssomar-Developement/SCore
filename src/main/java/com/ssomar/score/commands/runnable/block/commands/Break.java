package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.safebreak.SafeBreak;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/* BREAK */
public class Break extends BlockCommand {

    @Override
    public void run(Player p, @NotNull Block block, SCommandToExec sCommandToExec) {

        /* Cancel a Loop of blockBreakEvent that MineInCbe can create */
        if (sCommandToExec.getActionInfo().isEventFromCustomBreakCommand()) return;

        ActionInfo aInfo = sCommandToExec.getActionInfo();
        UUID pUUID = null;
        if (p != null) pUUID = p.getUniqueId();

        SafeBreak.breakBlockWithEvent(block, pUUID, aInfo.getSlot(), true, true, !aInfo.isNoPlayerTriggeredTheAction());
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
      return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("BREAK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BREAK";
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
