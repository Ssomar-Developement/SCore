package com.ssomar.score.conditions.condition.playercondition;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Optional;

@Getter @Setter
public class PlayerConditions extends NewConditions {


    public boolean verifConditions(Player player, Optional<Player> playerOpt, SendMessage messageSender) {

        for(Object object : getConditions().values()){
            PlayerCondition playerCondition = (PlayerCondition)object;
            if(!playerCondition.verifCondition(player, playerOpt, messageSender)) return false;
        }
        return true;
    }

    @Override
    public NewConditions createNewInstance() {
        return new PlayerConditions();
    }
}
