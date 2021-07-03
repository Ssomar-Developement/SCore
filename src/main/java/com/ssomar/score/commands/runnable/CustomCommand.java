package com.ssomar.score.commands.runnable;

import com.ssomar.score.utils.SendMessage;

public abstract class CustomCommand {

	protected static String tooManyArgs= "There is too many args for the command: ";
	protected static String notEnoughArgs= "There is not enough args for the command: ";
	protected static String invalidMaterial= "Command contains an invalid material: ";
	protected static String invalidWorld= "Command contains an invalid world: ";
	protected static String invalidEntityType= "Command contains an invalid entityType: ";
	protected static String invalidQuantity= "Command contains an invalid quantity: ";
	protected static String invalidDistance= "Command contains an invalid distance: ";
	protected static String invalidCoordinate= "Command contains an invalid coordinate: ";
	protected static String invalidTime= "Command contains an invalid time: ";
	protected static String invalidRange= "Command contains an invalid range: ";
	protected static String invalidBoolean= "Command contains an invalid boolean: ";
	protected static String invalidExecutableItems= "Command contains an invalid id of ExecutableItems: ";

	public static SendMessage sm = new SendMessage();	
}
