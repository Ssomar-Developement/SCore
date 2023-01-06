package com.ssomar.score.utils;

import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.Serializable;

public class SendMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    private StringPlaceholder sp = new StringPlaceholder();

    public static void sendMessageNoPlch(Player p, String s) {
        sendMessageNoPlch((CommandSender) p, s);
    }

    public static void sendMessageNoPlch(CommandSender cs, String s) {
        String prepareMsg = s;
        sendMessageFinal(cs, prepareMsg);
    }

    public void sendMessage(Player p, String s) {
        this.sendMessage((CommandSender) p, s);
    }

    public void sendMessage(CommandSender cs, String s) {
        String prepareMsg = s;
        prepareMsg = sp.replacePlaceholder(prepareMsg);
        sendMessageFinal(cs, prepareMsg);
    }

    public static void sendMessageFinal(CommandSender cs, String prepareMsg) {
        if (!(prepareMsg.isEmpty() || StringConverter.decoloredString(prepareMsg).isEmpty())){
            if(prepareMsg.contains("&") || prepareMsg.contains("§")) {
                prepareMsg = StringConverter.coloredString(prepareMsg);
                cs.sendMessage(prepareMsg);
            }
            else {
                try {
                    Audience audience = Audience.audience((Audience) cs);
                    audience.sendMessage(MiniMessage.miniMessage().deserialize(prepareMsg));
                }
                catch (Exception | Error e){
                    prepareMsg = StringConverter.coloredString(prepareMsg);
                    cs.sendMessage(prepareMsg);
                }
            }
        }
    }

    public void resetSp() {
        this.sp = new StringPlaceholder();
    }

}
