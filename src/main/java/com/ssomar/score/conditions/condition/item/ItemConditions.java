package com.ssomar.score.conditions.condition.item;

import java.util.*;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public class ItemConditions extends NewConditions {

	public boolean verifConditions(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			ItemCondition itemCondition = (ItemCondition)object;
			if(!itemCondition.verifCondition(itemStack, playerOpt, messageSender)) return false;
		}
		return true;
	}

	@Override
	public NewConditions createNewInstance() {
		return new ItemConditions();
	}
}
