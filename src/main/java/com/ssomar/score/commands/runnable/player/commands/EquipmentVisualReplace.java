package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.ProtocolibAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* TOTEM_ANIMATION */
public class EquipmentVisualReplace extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        EquipmentSlot slot = EquipmentSlot.valueOf(args.get(0).toUpperCase());
        Material mat = Material.valueOf(args.get(1).toUpperCase());
        int amount = Integer.parseInt(args.get(2));
        int time = Integer.parseInt(args.get(3));
        ProtocolibAPI.sendPumpkinHeadPacket(receiver, slot, mat, amount, time);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 4) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkEquipmentSlot(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkMaterial(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        ArgumentChecker ac3 = checkInteger(args.get(2), isFinalVerification, getTemplate());
        if (!ac3.isValid()) return Optional.of(ac3.getError());

        ArgumentChecker ac4 = checkInteger(args.get(3), isFinalVerification, getTemplate());
        if (!ac4.isValid()) return Optional.of(ac4.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("EQUIPMENT_VISUAL_REPLACE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "EQUIPMENT_VISUAL_REPLACE {EquipmentSlot} {material} {amount} {timeinticks}";
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
