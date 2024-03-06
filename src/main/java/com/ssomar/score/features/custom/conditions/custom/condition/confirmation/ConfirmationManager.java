package com.ssomar.score.features.custom.conditions.custom.condition.confirmation;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter @Setter
public class ConfirmationManager {

    private static ConfirmationManager instance;

    private HashMap<Player, String> needConfirm = new HashMap<>();


    public static ConfirmationManager getInstance() {
        if(instance == null) instance = new ConfirmationManager();
        return instance;
    }
}
