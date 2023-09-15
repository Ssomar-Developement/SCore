package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.magic.MagicType;
import com.willfp.ecoskills.magic.MagicTypes;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegainMagic extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        String magicId = args.get(0);
        int regain = Integer.valueOf(args.get(1));

        if (!SCore.hasEcoSkills) return;

        MagicType type = MagicTypes.INSTANCE.getByID(magicId);

        int magicAmount = EcoSkillsAPI.getMagic(receiver, type);
        EcoSkillsAPI.setMagic(receiver, type, magicAmount + regain);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REGAIN MAGIC");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REGAIN MAGIC {ecoskills magic OD} {amount}";
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
