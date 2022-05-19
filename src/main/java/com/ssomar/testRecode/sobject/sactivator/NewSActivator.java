package com.ssomar.testRecode.sobject.sactivator;

import com.ssomar.score.sobject.HigherFormSObject;
import com.ssomar.score.sobject.sactivator.EventInfo;
import com.ssomar.score.sobject.sactivator.SOption;
import com.ssomar.testRecode.features.FeatureParentInterface;

import java.util.List;

public interface NewSActivator extends FeatureParentInterface {

    String getParentObjectId();

    String getId();

    SOption getOption();

    void run(HigherFormSObject parentObject, EventInfo eventInfo);

    List<String> getMenuDescription();
}
