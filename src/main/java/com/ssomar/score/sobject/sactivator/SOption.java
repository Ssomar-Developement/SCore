package com.ssomar.score.sobject.sactivator;

import java.util.List;

public interface SOption {


	List<SOption> getOptionWithPlayer();

	List<SOption> getOptionWithEntity();
	
	List<SOption> getOptionWithWorld();
	
	List<SOption> getOptionWithItem();
	
	List<SOption> getOptionWithOwner();
	
	List<SOption> getOptionWithBlock();
	
	List<SOption> getOptionWithTargetBlock();
	
	List<SOption> getOptionWithTargetEntity();
	
	List<SOption> getOptionWithTargetPlayer();

	List<SOption> getPremiumOption();

	boolean isValidOption(String optionStr);

	SOption getOption(String optionStr);

	List<SOption> getValues();

	SOption getDefaultValue();

}
