package com.ssomar.score.projectiles.features.Particles;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ParticleGUI extends SimpleGUI {

	private boolean newRequiredEI = false;	
	public static final String ID = "ID:";
	public static final String TYPE = "Particles type";
	public static final String AMOUNT = "Amount";
	public static final String OFFSET = "Particles offset (radius)";
	public static final String SPEED = "Particles speed";
	public static final String DELAY = "Particles delay";
	public static final String COLOR = "Redstone color";

	public ParticleGUI(ParticlesFeature.CustomParticle particle, int index, String projID) {
		super("&8&lEditor - Particle", 4*9);
		newRequiredEI = true;

		this.fillTheGUI(particle, index, projID);
	}

	public void fillTheGUI(ParticlesFeature.CustomParticle particle, int index, String projID) {
		//Main Options
		createItem(Material.NAME_TAG,						1 , 0, 	TITLE_COLOR+TYPE, 	false,	false, "", "&a✎ Click here to change");
		// this.updateType(); TODO UPDATE TYPE
		
		createItem(Material.CHEST,							1 , 1, 	TITLE_COLOR+AMOUNT, 	false,	false, "", "&a✎ Click here to change", "&7actually:");
		this.updateInt(AMOUNT, particle.getParticlesAmount());
		
		createItem(Material.BEACON,							1 , 2, 	TITLE_COLOR+OFFSET, 	false,	false, "", "&a✎ Click here to change", "&7actually:" );
		this.updateDouble(TITLE_COLOR+OFFSET, particle.getParticlesOffSet());

		createItem(Material.BEACON,							1 , 3, 	TITLE_COLOR+SPEED, 	false,	false, "", "&a✎ Click here to change", "&7actually:" );
		this.updateDouble(TITLE_COLOR+SPEED, particle.getParticlesSpeed());

		createItem(Material.BEACON,							1 , 4, 	TITLE_COLOR+DELAY, 	false,	false, "", "&a✎ Click here to change", "&7actually:" );
		this.updateDouble(TITLE_COLOR+DELAY, particle.getParticlesDelay());
		

		createItem(Material.BOOK,							1 , 8, 	"&a&l"+ID, 	false,	false, "", "&7actually: &e"+index);
		createItem(Material.BOOK, 							1 , 33, "&a&lProjectile ID:", 	false, false, "", "&7actually: &e"+projID);

		//Reset menu
		createItem(ORANGE, 					1 , 28, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of this projectile" );
		// exit
		createItem(RED, 					1 , 27, "&4&l▶&c Back to the list of projectiles", 		false,	false);
		//Save menu
		createItem(GREEN, 					1 , 35, "&2&l✔ &aSave this projectile", 		false,	false, 	"", "&a&oClick here to save this" , "&a&oprojectile" );
	}


}
