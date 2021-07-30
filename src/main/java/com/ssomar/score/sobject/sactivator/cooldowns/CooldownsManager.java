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

	private Map<String, List<Cooldown>> cooldowns = new HashMap<>();
	private Map<UUID, List<Cooldown>> cooldownsUUID = new HashMap<>();

	public void addCooldown(Cooldown cd) {
		String id = cd.getId();
		if(cooldowns.containsKey(id)) {	
			List<Cooldown> cds = cooldowns.get(id);
			cds.add(cd);
			cooldowns.replace(id, cds);
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
			cooldownsUUID.replace(id2, cds);
		}
		else {
			List<Cooldown> cds = new ArrayList<>();
			cds.add(cd);
			cooldownsUUID.put(id2, cds);
		}
	}
	
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
		String id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		if(cooldowns.containsKey(id) && cooldowns.get(id).size()!= 0){
			List<Cooldown> toRemove = new ArrayList<>();
			for(Cooldown cd : cooldowns.get(id)) {
				if(this.getCooldown(sPlugin, sO, sAct, cd.getEntityUUID())<cd.getCooldown()) {
					cooldowns.get(id).removeAll(toRemove);		
					return true;
				}
				else {
					toRemove.add(cd);
				}
			}	
			cooldowns.get(id).removeAll(toRemove);		
		}
		return false;
	}

	public boolean isGlobalCooldown(SPlugin sPlugin, SObject sO, SActivator sAct) {
		String id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		if(cooldowns.containsKey(id) && cooldowns.get(id).size()!= 0){
			List<Cooldown> toRemove = new ArrayList<>();
			for(Cooldown cd : cooldowns.get(id)) {
				if(!cd.isGlobal()) continue;
				/* if actual cd < registered cd so its in cooldown !*/
				if(this.getCooldown(sPlugin, sO, sAct, cd.getEntityUUID())<cd.getCooldown()) {
					cooldowns.get(id).removeAll(toRemove);		
					return true;
				}
				else {
					toRemove.add(cd);
				}
			}	
			cooldowns.get(id).removeAll(toRemove);		
		}
		return false;
	}

	/**
	 * @param id
	 * @param uuid
	 * @return-
	 */
	public boolean isInCooldownForPlayer(SPlugin sPlugin, SObject sO, SActivator sAct, UUID uuid) {
		String id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		if(cooldowns.containsKey(id) && cooldowns.get(id).size()!= 0){
			List<Cooldown> toRemove = new ArrayList<>();
			for(Cooldown cd : cooldowns.get(id)) {
				if(cd.getEntityUUID().equals(uuid)) {
					if(this.getCooldown(sPlugin, sO, sAct, uuid)<cd.getCooldown()) {
						return true;
					}
					else {
						toRemove.add(cd);
					}
				}
			}	
			cooldowns.get(id).removeAll(toRemove);	
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
	
	public List<Cooldown> getCooldownsOf(UUID uuid){
		if(cooldownsUUID.containsKey(uuid)) {
			return cooldownsUUID.get(uuid);
		}
		else return new ArrayList<>();
	}
	
	public void removeCooldownsOf(UUID uuid){
		cooldownsUUID.remove(uuid);
	}

	public static CooldownsManager getInstance() {
		if(instance == null) instance = new CooldownsManager();
		return instance;
	}

}
