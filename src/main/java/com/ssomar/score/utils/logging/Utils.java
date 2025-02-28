package com.ssomar.score.utils.logging;

import com.ssomar.score.SCore;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Utils {

    public static void sendConsoleMsg(String msg) {
        // SCore shading
        if(SCore.plugin == null) sendConsoleMsg(Bukkit.getPluginManager().getPlugins()[0], msg);
        else sendConsoleMsg((SPlugin) SCore.plugin, msg);
    }

    public static void sendConsoleMsg(SPlugin sPlugin, String msg) {
        sendConsoleMsg(sPlugin.getPlugin(), msg);
    }

    public static void sendConsoleMsg(Plugin plugin, String msg) {
        if (SCore.is1v16Plus())
            plugin.getServer().getConsoleSender().sendMessage(StringConverter.coloredString(msg));
        else
            plugin.getServer().getConsoleSender().sendMessage(StringConverter.decoloredString(msg));
    }

    public static void sendConsoleFlatMsg(SPlugin sPlugin, String msg) {
        sPlugin.getPlugin().getServer().getConsoleSender().sendMessage(msg);
    }

}
