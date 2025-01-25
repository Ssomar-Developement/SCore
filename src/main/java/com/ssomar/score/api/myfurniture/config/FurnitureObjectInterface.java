package com.ssomar.score.api.myfurniture.config;

import com.ssomar.score.sobject.InternalData;

public interface FurnitureObjectInterface {

    boolean isValid();

    InternalData getInternalData();

    FurnitureInterface getFurnitureConfig();
}
