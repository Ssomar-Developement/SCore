package com.ssomar.score.features.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.FeatureParentInterface;

public abstract class FeatureEditorInterface<T extends FeatureParentInterface> extends GUI {

    public FeatureEditorInterface(String name, int size) {
        super(name, size);
    }

    public abstract T getParent();
}
