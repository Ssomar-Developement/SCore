package com.ssomar.score.sobject;


import com.ssomar.score.features.custom.activators.activator.SActivator;
import com.ssomar.score.features.custom.activators.group.ActivatorsFeature;
import org.jetbrains.annotations.Nullable;

public interface SObjectWithActivators{

    /**
     * @return the activators
     */
    ActivatorsFeature getActivators();

    @Nullable
    SActivator getActivator(String actID);

}
