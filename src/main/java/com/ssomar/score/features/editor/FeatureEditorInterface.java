package com.ssomar.score.features.editor;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.GUI;

public abstract class FeatureEditorInterface<T extends FeatureParentInterface> extends GUI {

    public FeatureEditorInterface(String name, int size) {
        super(name, size);
    }

    public abstract T getParent();
}
