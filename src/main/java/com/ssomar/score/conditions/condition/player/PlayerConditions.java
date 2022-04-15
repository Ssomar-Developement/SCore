package com.ssomar.score.conditions.condition.player;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.ConditionsVerification;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Optional;

@Getter @Setter
public class PlayerConditions extends Conditions {


    public ConditionsVerification verifConditions(Player player, Optional<Player> playerOpt, SendMessage messageSender) {

        for(Object object : getConditions().values()){
            PlayerCondition playerCondition = (PlayerCondition)object;
            if(!playerCondition.verifCondition(player, playerOpt, messageSender)) return new ConditionsVerification(false, playerCondition.isErrorCancelEvent());
        }
        return new ConditionsVerification(true, false);
    }

    @Override
    public Conditions createNewInstance() {
        return new PlayerConditions();
    }
}
