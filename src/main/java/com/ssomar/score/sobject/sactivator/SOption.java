package com.ssomar.score.sobject.sactivator;

import java.util.List;

public interface SOption {
	
	public List<SOption> getOptionWithPlayer();
	
	public List<SOption> getOptionWithWorld();
	
	public List<SOption> getOptionWithItem();
	
	public List<SOption> getOptionWithOwner();
	
	public List<SOption> getOptionWithBlock();
	
	public List<SOption> getOptionWithTargetBlock();
	
	public List<SOption> getOptionWithTargetEntity();
	
	public List<SOption> getOptionWithTargetPlayer();

}
