package com.ssomar.score.utils.logging;

import com.ssomar.score.SCore;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;

public class Utils {

    public static void sendConsoleMsg(String msg) {
       sendConsoleMsg(SCore.plugin, msg);
    }

    public static void sendConsoleMsg(SPlugin sPlugin, String msg) {
        if (SCore.is1v16Plus())
            sPlugin.getPlugin().getServer().getConsoleSender().sendMessage(StringConverter.coloredString(msg));
        else
            sPlugin.getPlugin().getServer().getConsoleSender().sendMessage(StringConverter.decoloredString(msg));
    }

}
