package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.sobject.sactivator.conditions.condition.worldcondition.WorldCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.messages.MessageDesign;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;

@Getter @Setter
public class WorldConditions extends NewConditions{

	@Override
	public NewConditions createNewInstance() {
		return new WorldConditions();
	}

	public boolean verifCondition(World w, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			WorldCondition worldCondition = (WorldCondition)object;
			if(!worldCondition.verifCondition(w, playerOpt, messageSender)) return false;
		}
		return true;
	}
}
