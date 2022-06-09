package com.ssomar.scoretestrecode.features.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;

public abstract class FeatureEditorInterface<T extends FeatureParentInterface> extends GUI {

    public FeatureEditorInterface(String name, int size) {
        super(name, size);
    }

    public abstract T getParent();
}
