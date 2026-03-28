package com.ssomar.score.features.editor;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import net.kyori.adventure.text.Component;

public abstract class FeatureEditorInterface<T extends FeatureParentInterface> extends GUI {

    public FeatureEditorInterface(FeatureSettingsInterface settings, int size) {
        super(settings, size);
    }

    public FeatureEditorInterface(String name, int size) {
        super(name, size);
    }

    /**
     * Constructor accepting an Adventure Component title for custom GUI textures.
     * Uses initInventory(Component, size) so that `this` is properly set as the inventory holder.
     */
    public FeatureEditorInterface(Component title, int size) {
        super(title, size);
    }

    public abstract T getParent();
}
