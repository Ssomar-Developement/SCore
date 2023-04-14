package com.ssomar.score.utils.logging;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.strings.StringConverter;

public class Utils {

    public static void sendConsoleMsg(String msg) {
        if (SCore.is1v16Plus())
            SCore.plugin.getServer().getConsoleSender().sendMessage(StringConverter.coloredString(msg));
        else
            SCore.plugin.getServer().getConsoleSender().sendMessage(StringConverter.decoloredString(msg));
    }

}
