package com.ssomar.score.sobject;

import java.util.List;

import javax.annotation.Nullable;

import com.ssomar.score.sobject.sactivator.SActivator;

public interface SObject {
	
	public String getID();
	
	public String getPath();
	
	public List<SActivator> getActivators();
	
	@Nullable
	public SActivator getActivator(String actID);

}
