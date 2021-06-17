package com.ssomar.score.commands.runnable;

import java.util.List;

public interface Command {
	
	public List<String> getNames();
	
	public String getTemplate();

}
