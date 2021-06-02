package com.ssomar.score.commands.runnable.item;

import java.util.ArrayList;
import java.util.List;

public enum ItemCommand {

	ADDUSAGE ("ADDUSAGE");

	private String[] names;

	ItemCommand(String... names) {
		this.names=names;
	}

	@SuppressWarnings("unused")
	public static String verifArgs(ItemCommand iC, List<String> args) {

		/* ""> No error */
		String error="";

		String tooManyArgs= "There is too many args for the command: ";
		String notEnoughArgs= "There is not enough args for the command: ";
		String invalidMaterial= "Command contains an invalid material: ";
		String invalidEntityType= "Command contains an invalid entityType: ";
		String invalidQuantity= "Command contains an invalid quantity: ";
		String invalidDistance= "Command contains an invalid distance: ";
		String invalidTime= "Command contains an invalid time: ";
		String invalidRange= "Command contains an invalid range: ";
		String invalidBoolean= "Command contains an invalid boolean: ";
		String invalidExecutableItems= "Command contains an invalid id of ExecutableItems: ";

		/* ADDUSAGE {amount} */
		if(iC==ItemCommand.ADDUSAGE) {
			String addusage= "ADDUSAGE {amount}";
			if(args.size()!=1) error = notEnoughArgs+addusage;
			else { 
				try {
					Integer.valueOf(args.get(0));
				}
				catch(Exception e) {
					error = invalidQuantity+args.get(0)+" "+addusage;
				}
			}
		}

		return error;
	}

	public static boolean isValidItemCommands(String entry) {
		for(ItemCommand itemCommands : values()) {
			for(String name: itemCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}


	public static ItemCommand getItemCommand(String entry) {
		for(ItemCommand itemCommands : values()) {
			for(String name: itemCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return itemCommands;
				}
			}
		}
		return null;
	}

	public static List<String> getICArgs(String entry) {
		List<String> args = new ArrayList<>();
		boolean first= true;
		boolean second= false;
		for(String s : entry.split(" ")) {
			if(first) {
				first=false;
				continue;
			}
			if(second) {
				second=false;
				continue;
			}
			args.add(s);
		}
		return args;
	}

	public boolean containsThisName(String entry) {
		for(String name : getNames()) {
			if(name.equalsIgnoreCase(entry)) return true;
		}
		return false;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
}

