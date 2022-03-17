package com.ssomar.score.projectiles;

import com.ssomar.score.projectiles.types.SProjectiles;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProjectilesManager {
	
	private static ProjectilesManager instance;
	
	private List<SProjectiles> projectiles = new ArrayList<>();
	private List<SProjectiles> projectilesOfDefaultItems = new ArrayList<>();
	
	public boolean containsProjectileWithID(String id) {
		List<SProjectiles> all = new ArrayList<>(projectiles);
		all.addAll(projectilesOfDefaultItems);
		for(SProjectiles projectile : all) {
			if(projectile.getId().equals(id)) return true;
		}
		return false;
	}
	
	public SProjectiles getProjectileWithID(String id) {
		List<SProjectiles> all = new ArrayList<>(projectiles);
		all.addAll(projectilesOfDefaultItems);
		for(SProjectiles projectile : all) {
			if(projectile.getId().equals(id)) return projectile;
		}
		return null;
	}

	public void replaceProjectileWithID(String id, SProjectiles newProj){
		for(int i = 0; i < projectiles.size(); i++){
			if(projectiles.get(i).getId().equals(id)){
				projectiles.remove(i);
				break;
			}
		}
		projectiles.add(newProj);
	}
	
	public void addProjectile(SProjectiles projectile) {
		projectiles.add(projectile);
	}

	public void removeProjectile(String id) {
		for(int i = 0; i < projectiles.size(); i++){
			if(projectiles.get(i).getId().equals(id)){
				projectiles.remove(i);
				break;
			}
		}

	}

	public static ProjectilesManager getInstance() {
		if(instance == null) instance = new ProjectilesManager();
		return instance;
	}

	public boolean deleteProjectile(String id) {
		File file = null;
		if ((file = ProjectilesLoader.searchFileOfProjectile(id)) != null) {
			file.delete();
		} else {
			return false;
		}
		this.removeProjectile(id);
		return true;
	}


}
