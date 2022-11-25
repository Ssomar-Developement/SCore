package com.ssomar.score.utils;

import com.ssomar.score.SCore;

public class Utils {

    public static void sendConsoleMsg(String msg) {
        if (SCore.is1v16Plus())
            SCore.plugin.getServer().getConsoleSender().sendMessage(StringConverter.coloredString(msg));
        else
            SCore.plugin.getServer().getConsoleSender().sendMessage(StringConverter.decoloredString(msg));
    }

}
