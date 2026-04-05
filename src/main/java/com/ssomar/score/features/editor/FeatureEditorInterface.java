package com.ssomar.score.features.editor;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import org.bukkit.inventory.Inventory;

public abstract class FeatureEditorInterface<T extends FeatureParentInterface> extends GUI {

    public FeatureEditorInterface(FeatureSettingsInterface settings, int size) {
        super(settings, size);
    }

    public FeatureEditorInterface(String name, int size) {
        super(name, size);
    }

    /**
     * Constructor accepting a pre-built Inventory (e.g. with a Component title for custom GUI textures).
     * Avoids classloader issues with Adventure types across plugin boundaries.
     */
    public FeatureEditorInterface(Inventory preBuiltInventory, int size) {
        super(preBuiltInventory, size);
    }

    public abstract T getParent();
}
