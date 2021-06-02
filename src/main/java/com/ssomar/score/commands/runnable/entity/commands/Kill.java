package com.ssomar.score.commands.runnable.entity.commands;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandTemplate;

/* KILL */
public class Kill extends EntityCommandTemplate{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		if(!entity.isDead()) entity.remove();
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}

}
