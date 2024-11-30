package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.magic.MagicType;
import com.willfp.ecoskills.magic.MagicTypes;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RegainMagic extends PlayerCommand {

    public RegainMagic() {
        CommandSetting ecoSkillsMagicID = new CommandSetting("ecoSkillsMagicID", 0, String.class, "");
        CommandSetting amount = new CommandSetting("amount", 1, Integer.class, 5);
        List<CommandSetting> settings = getSettings();
        settings.add(ecoSkillsMagicID);
        settings.add(amount);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        String magicId = (String) sCommandToExec.getSettingValue("ecoSkillsMagicID");
        int regain = (int) sCommandToExec.getSettingValue("amount");

        if (!SCore.hasEcoSkills) return;

        MagicType type = MagicTypes.INSTANCE.getByID(magicId);

        int magicAmount = EcoSkillsAPI.getMagic(receiver, type);
        EcoSkillsAPI.setMagic(receiver, type, magicAmount + regain);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REGAIN_MAGIC");
        names.add("REGAIN MAGIC");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REGAIN_MAGIC ecoSkillsMagicID:MagicId amount:5";
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
