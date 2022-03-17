package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AnimationTeleportEnder extends PlayerCommand{

        @Override
        public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
            receiver.playEffect(EntityEffect.TELEPORT_ENDER);
        }

        @Override
        public String verify(List<String> args) {
            String error = "";

            return error;
        }

        @Override
        public List<String> getNames() {
            List<String> names = new ArrayList<>();
            names.add("~ANIMATION_TELEPORT_ENDER");
            names.add("TELEPORT_ENDER_ANIMATION");
            return names;
        }

        @Override
        public String getTemplate() {
            return "TELEPORT_ENDER_ANIMATION";
        }

    @Override
    public ChatColor getColor() {
        return ChatColor.AQUA;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.GOLD;
    }


}
