package com.ssomar.score.commands.runnable;

import java.util.List;

import com.ssomar.score.utils.SendMessage;
import org.bukkit.ChatColor;

public abstract class SCommand {

	protected static String tooManyArgs= "There is too many args for the command: ";
	protected static String notEnoughArgs= "There is not enough args for the command: ";
	protected static String invalidMaterial= "SCommand contains an invalid material: ";
	protected static String invalidWorld= "SCommand contains an invalid world: ";
	protected static String invalidEntityType= "SCommand contains an invalid entityType: ";
	protected static String invalidQuantity= "SCommand contains an invalid quantity: ";
	protected static String invalidDistance= "SCommand contains an invalid distance: ";
	protected static String invalidCoordinate= "SCommand contains an invalid coordinate: ";
	protected static String invalidTime= "SCommand contains an invalid time or number: ";
	protected static String invalidRange= "SCommand contains an invalid range: ";
	protected static String invalidBoolean= "SCommand contains an invalid boolean: ";
	protected static String invalidExecutableItems= "SCommand contains an invalid id of ExecutableItems: ";

	public static SendMessage sm = new SendMessage();

	public abstract List<String> getNames();
	
	public abstract String getTemplate();

	public abstract ChatColor getColor();

	public abstract ChatColor getExtraColor();

	public ChatColor getExtraColorNotNull() {
		if(getExtraColor() == null) return ChatColor.DARK_PURPLE;
		return getExtraColor();
	}

	public ChatColor getColorNotNull() {
		if(getColor() == null) return ChatColor.LIGHT_PURPLE;
		return getColor();
	}
}
