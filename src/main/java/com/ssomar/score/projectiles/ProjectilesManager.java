package com.ssomar.score.projectiles;

import com.ssomar.executableitems.items.ItemLoader;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectilesManager {
	
	private static ProjectilesManager instance;
	
	private List<SProjectiles> projectiles = new ArrayList<>();
	
	public boolean containsProjectileWithID(String id) {
		for(SProjectiles projectile : projectiles) {
			if(projectile.getId().equals(id)) return true;
		}
		return false;
	}
	
	public SProjectiles getProjectileWithID(String id) {
		for(SProjectiles projectile : projectiles) {
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

	public List<SProjectiles> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<SProjectiles> projectiles) {
		this.projectiles = projectiles;
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
