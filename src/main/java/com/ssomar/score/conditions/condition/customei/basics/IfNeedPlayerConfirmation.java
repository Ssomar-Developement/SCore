package com.ssomar.score.conditions.condition.customei.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.customei.CustomEICondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class IfNeedPlayerConfirmation extends CustomEICondition<Boolean, String> {


    public IfNeedPlayerConfirmation() {
        super(ConditionType.BOOLEAN, "ifNeedPlayerConfirmation", "If need player confirmation", new String[]{}, false, " &7➤ Click again to confirm the use of this item");
    }

    @Override
    public boolean verifCondition(Player player, ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {
        return true;
    }
}
