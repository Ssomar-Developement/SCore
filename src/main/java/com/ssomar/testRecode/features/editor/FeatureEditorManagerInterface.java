package com.ssomar.testRecode.features.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.menu.NewGUIManager;
import org.bukkit.entity.Player;

public abstract class FeatureEditorManagerInterface<T extends GUI, Y extends FeatureInterface> extends NewGUIManager<T> {

    public void startEditing(Player editor, Y feature) {
        cache.put(editor, buildEditor(feature));
    }

    public abstract T buildEditor(Y feature);
}
