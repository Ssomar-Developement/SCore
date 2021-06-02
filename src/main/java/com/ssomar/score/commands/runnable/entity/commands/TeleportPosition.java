package com.ssomar.score.commands.runnable.entity.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandTemplate;

/* TELEPORT POSITION {x} {y} {z} */
public class TeleportPosition extends EntityCommandTemplate{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		if(args.size()==3) {
			try {
				if(!entity.isDead()) entity.teleport(new Location(entity.getWorld(), Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)), Integer.valueOf(args.get(2))));
			}catch(Exception e) {}
		}
	}

	@Override
	public String verify(List<String> args) {
		
		String error = "";
		
		String tppos= "TELEPORT POSITION {x} {y} {z}";
		if(args.size()<3) error = notEnoughArgs+tppos;
		else if(args.size()==3) {
			boolean bError= false;
			try {
				Double.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidMaterial+args.get(0)+" for command: "+tppos;
				bError=true;
			}
			if(!bError) {
				try {
					Integer.valueOf(args.get(1));
				}catch(NumberFormatException e){
					error = invalidQuantity+args.get(1)+" for command: "+tppos;
					bError=true;
				}
			}
		}
		else error= tooManyArgs+tppos;
		
		return error;
	}

}
