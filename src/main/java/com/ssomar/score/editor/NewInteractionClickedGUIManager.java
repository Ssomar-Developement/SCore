package com.ssomar.score.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class NewInteractionClickedGUIManager<T extends GUI> {

    public HashMap<Player, T> cache;
    public T gui;
    /* Item clicked name */
    public String name;
    public String decoloredName;
    public String coloredDeconvertName;
    public String message;
    public String decoloredMessage;
    public String coloredDeconvertMessage;
    public String localizedName;
    public Player player;

    public void setName(String name) {
        this.name = name;
        this.decoloredName = StringConverter.decoloredString(name);
        this.coloredDeconvertName = StringConverter.deconvertColor(name);
    }

    public void setMessage(String message) {
        this.message = message;
        this.decoloredMessage = StringConverter.decoloredString(message);
        this.coloredDeconvertMessage = StringConverter.deconvertColor(message);
    }
}
