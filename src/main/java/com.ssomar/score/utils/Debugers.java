package com.ssomar.score.utils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class Debugers extends ArrayList<CommandSender> {

    public void sendDebug(String message) {
        for (CommandSender sender : this) {
            sender.sendMessage(StringConverter.coloredString("&c[DEBUG] "+message));
        }
    }
}
