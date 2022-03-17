package com.ssomar.score.menu.particles;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sparticles.SParticle;
import com.ssomar.score.sparticles.SParticles;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.CustomColor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SParticlesGUI extends GUIAbstract {

	static int index;
	private SParticles sParticles;
	private GUI guiFrom;

	//Page 1
	public SParticlesGUI(Player p, SPlugin sPlugin, SParticles sParticles, GUI guiFrom) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Particles - Page 1", 5*9, sPlugin, null, null);
		this.sParticles = sParticles;
		this.guiFrom = guiFrom;
		setIndex(1);
		loadItems(p);
	}

	// other pages
	public SParticlesGUI(int index, Player p, SPlugin sPlugin, SParticles sParticles, GUI guiFrom) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Particles - Page "+index, 5*9, sPlugin, null, null);
		this.sParticles = sParticles;
		this.guiFrom = guiFrom;
		setIndex(index);
		loadItems(p);
	}

	public void loadItems(Player p) {
		List<SParticle> particles = sParticles.getParticles();
		int i = 0;
		int total = 0;
		for(SParticle sParticle : particles) {
			if((index-1)*27 <= total && total < index*27) {
				ItemStack itemS = new ItemStack(Material.BOOK);
				List<String> desc = new ArrayList<>();
				desc.add("");
				desc.add("&4(shift + left click to delete)");
				desc.add("&7(click to edit)");
				desc.add("&7• ID: &e" + sParticle.getId());
				desc.add("&7• Particle Type: &e" + sParticle.getParticlesType().name());
				desc.add("&7• Particle Amount: &e" + sParticle.getParticlesAmount());
				desc.add("&7• Particle OffSet: &e" + sParticle.getParticlesOffSet());
				desc.add("&7• Particle Speed: &e" + sParticle.getParticlesSpeed());
				desc.add("&7• Particle Delay: &e" + sParticle.getParticlesDelay());
				if(sParticle.getParticlesType().equals(Particle.REDSTONE))
					desc.add("&7• Redstone color: &e" + CustomColor.getName(sParticle.getRedstoneColor()));

				String[]descArray = new String[desc.size()];
				for(int j = 0; j < desc.size(); j++) {
					if(desc.get(j).length() > 40) {
						descArray[j] = desc.get(j).substring(0, 39)+"...";
					}
					else {
						descArray[j] = desc.get(j);
					}
				}
				createItem(itemS, 	1 , i, 	"&2&l✦ ID: &a"+sParticle.getId(), true, false, descArray);
				i++;
			}
			total++;

		}
	
		//other button
		if(total>27 && index*27<total) createItem(PURPLE, 	1 , 44, 	"&5&l▶ &dNext page ", 	false, false);

		if(index>1) createItem(PURPLE, 	1 , 37, 	"&dPrevious page &5&l◀", 	false, false);

		createItem(RED, 	1 , 36, 	"&4&l▶ &cBack", 	false, false);

		createItem(GREEN, 	1 , 40, 	"&2&l✚ &aNew Particle", 	false, false);

		//Last Edit
		if(p != null && SParticlesGUIManager.getInstance().getCache().containsKey(p)) {
			createItem(BLUE, 							1 , 39, "&3&l✦ &bReturn to your last edit", 		false, false, 	"", "&7&oClick here to continue" , "&7&oyour last edit" );
		}
	}

	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		SParticlesGUI.index = index;
	}

	@Override
	public void reloadGUI() {
		this.loadItems(null);
	}
}
