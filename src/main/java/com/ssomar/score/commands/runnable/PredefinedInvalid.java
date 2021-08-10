package com.ssomar.score.commands.runnable;

import com.ssomar.score.utils.SendMessage;

public abstract class PredefinedInvalid {

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
}
