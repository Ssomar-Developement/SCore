package com.ssomar.testRecode.features.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.*;

public abstract class FeatureEditorInterface<T extends FeatureWithHisOwnEditor> extends GUI {

    public FeatureEditorInterface(String name, int size) {
        super(name, size);
    }

    public abstract T getFeature();
}
