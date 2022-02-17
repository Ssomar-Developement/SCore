package com.ssomar.score.sobject.sactivator;

import java.util.List;

public interface SOption {
	
	List<SOption> getOptionWithPlayer();
	
	List<SOption> getOptionWithWorld();
	
	List<SOption> getOptionWithItem();
	
	List<SOption> getOptionWithOwner();
	
	List<SOption> getOptionWithBlock();
	
	List<SOption> getOptionWithTargetBlock();
	
	List<SOption> getOptionWithTargetEntity();
	
	List<SOption> getOptionWithTargetPlayer();

	static boolean isValidOption(String optionStr){
		return false;
	}

	static SOption getOption(String optionStr){
		return null;
	}

}
