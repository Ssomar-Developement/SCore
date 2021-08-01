package com.ssomar.score.sobject.sactivator.cooldowns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;

public class CooldownsManager {

	private static CooldownsManager instance;

	/* CD_ID Cooldown */
	private Map<String, List<Cooldown>> cooldowns = new HashMap<>();
	
	/* Player_UUID Cooldown */
	private Map<UUID, List<Cooldown>> cooldownsUUID = new HashMap<>();

	public void addCooldown(Cooldown cd) {
		
		if(cd.getCooldown() == 0) return;
		
		String id = cd.getId();
		if(cooldowns.containsKey(id)) {	
			List<Cooldown> cds = cooldowns.get(id);
			cds.add(cd);
		}
		else {
			List<Cooldown> cds = new ArrayList<>();
			cds.add(cd);
			cooldowns.put(id, cds);
		}
		
		UUID id2 = cd.getEntityUUID();
		if(cooldownsUUID.containsKey(id2)) {	
			List<Cooldown> cds = cooldownsUUID.get(id2);
			cds.add(cd);
		}
		else {
			List<Cooldown> cds = new ArrayList<>();
			cds.add(cd);
			cooldownsUUID.put(id2, cds);
		}
	}
	
	/* FROM DB */
	public void addCooldowns(List<Cooldown> cds) {
		for(Cooldown cd : cds) {
			long current = System.currentTimeMillis();
			long delay = current - cd.getTime();	
			int div = 1000;
			if(cd.isInTick()) div = 50;
			int delayInt = (int) (delay/div);

			if(delayInt < cd.getCooldown()) this.addCooldown(cd);
		}
	}

	public boolean isInCooldown(SPlugin sPlugin, SObject sO, SActivator sAct) {
		this.clearCooldownsPassed(sPlugin, sO, sAct);
		String id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		if(cooldowns.containsKey(id) && cooldowns.get(id).size()!= 0){
			for(Cooldown cd : cooldowns.get(id)) {
				if(this.getCooldown(sPlugin, sO, sAct, cd.getEntityUUID())<cd.getCooldown()) {	
					return true;
				}
			}	
		}
		return false;
	}

	public boolean isGlobalCooldown(SPlugin sPlugin, SObject sO, SActivator sAct) {
		this.clearCooldownsPassed(sPlugin, sO, sAct);
		String id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		if(cooldowns.containsKey(id) && cooldowns.get(id).size()!= 0){
			for(Cooldown cd : cooldowns.get(id)) {
				if(!cd.isGlobal()) continue;
				/* if actual cd < registered cd so its in cooldown !*/
				if(this.getCooldown(sPlugin, sO, sAct, cd.getEntityUUID())<cd.getCooldown()) {
					return true;
				}
			}			
		}
		return false;
	}

	/**
	 * @param id
	 * @param uuid
	 * @return-
	 */
	public boolean isInCooldownForPlayer(SPlugin sPlugin, SObject sO, SActivator sAct, UUID uuid) {
		this.clearCooldownsPassed(sPlugin, sO, sAct);	
		
		String id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		List<Cooldown> cooldowns;
		if(cooldownsUUID.containsKey(uuid) && (cooldowns = cooldownsUUID.get(uuid)).size()!= 0){
			for(Cooldown cd : cooldowns) {
				if(cd.getId().equals(id)) {
					if(this.getCooldown(sPlugin, sO, sAct, uuid)<cd.getCooldown()) {
						return true;
					}
				}
			}	
		}
		return false;
	}

	public int getMaxGlobalCooldown(SPlugin sPlugin, SObject sO, SActivator sAct) {
		String id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		if(cooldowns.containsKey(id) && cooldowns.get(id).size()!= 0) {
			int max = -1;
			for(Cooldown cd : cooldowns.get(id)) {
				long current = System.currentTimeMillis();
				long delay = current - cd.getTime();	
				int div = 1000;
				if(cd.isInTick()) div = 50;
				int delayInt = (int) (delay/div);
				if(max < delayInt) {
					max = delayInt;
				}
			}
			return max;
		}	
		return -1;
	}

	public int getCooldown(SPlugin sPlugin, SObject sO, SActivator sAct, UUID uuid) {
		String id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		if(cooldowns.containsKey(id) && cooldowns.get(id).size()!= 0) {
			for(Cooldown cd : cooldowns.get(id)) {
				if(!cd.getEntityUUID().equals(uuid)) continue;
				long current = System.currentTimeMillis();
				long delay = current - cd.getTime();	
				int div = 1000;
				if(cd.isInTick()) div = 50;
				int delayInt = (int) (delay/div);
				return delayInt;
			}
			return 0;
		}	
		return 0;
	}
	
	public void clearCooldownsPassed(SPlugin sPlugin, SObject sO, SActivator sAct) {
		
		List<Cooldown> toRemove = new ArrayList<>();
		
		for(String s : cooldowns.keySet()) {
			for(Cooldown cd : cooldowns.get(s)) {
				if(this.getCooldown(sPlugin, sO, sAct, cd.getEntityUUID())>=cd.getCooldown()) {
					toRemove.add(cd);
				}
			}
			if(!toRemove.isEmpty()) {
				cooldowns.get(s).removeAll(toRemove);
			}
		}
		
		for(UUID uuid : cooldownsUUID.keySet()) {
			cooldownsUUID.get(uuid).removeAll(toRemove);
		}
	}
	
	public List<Cooldown> getCooldownsOf(UUID uuid){
		if(cooldownsUUID.containsKey(uuid)) {
			return cooldownsUUID.get(uuid);
		}
		else return new ArrayList<>();
	}
	
	public List<Cooldown> getAllCooldowns(){
		List<Cooldown> result = new ArrayList<>();
		
		for(String id : cooldowns.keySet()) {
			result.addAll(cooldowns.get(id));
		}
		
		return result;
	}
	
	public void clearCooldowns() {
		cooldowns.clear();
		cooldownsUUID.clear();
	}
	
	public void removeCooldownsOf(UUID uuid){
		cooldownsUUID.remove(uuid);
		for(String s : cooldowns.keySet()) {
			Cooldown toRemove = null;
			for(Cooldown c : cooldowns.get(s)) {
				if(c.getEntityUUID().equals(uuid)) {
					toRemove = c;
					break;
				}
			}
			if(toRemove != null) {
				cooldowns.get(s).remove(toRemove);
				break;
			}
		}
	}

	public static CooldownsManager getInstance() {
		if(instance == null) instance = new CooldownsManager();
		return instance;
	}

}

/* Reader */
//System.out.println("--------^^^^^^^^^^^^^^^^^^^^1-------------");
//
//for(String s : cooldowns.keySet()) {
//	for(Cooldown cd2 : cooldowns.get(s)) {
//		System.out.println(cd2.toString());
//	}
//}
//
//System.out.println("----------------------");
//
//for(UUID uuid2 : cooldownsUUID.keySet()) {
//	for(Cooldown cd2 : cooldownsUUID.get(uuid2)) {
//		System.out.println(cd2.toString());
//	}
//}
//
//System.out.println("---------vvvvvvvvvvv1vvvvvv-------------");
