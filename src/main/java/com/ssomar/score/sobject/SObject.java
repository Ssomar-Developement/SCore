package com.ssomar.score.sobject;

import java.util.List;

import com.ssomar.score.sobject.sactivator.SActivator;

public interface SObject {
	
	public String getPath();
	
	public List<SActivator> getActivators();

}
