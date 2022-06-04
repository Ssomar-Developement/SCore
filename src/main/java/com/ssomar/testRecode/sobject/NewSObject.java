package com.ssomar.testRecode.sobject;


import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;
import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;
import com.ssomar.testRecode.sobject.sactivator.NewSActivator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class NewSObject<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends FeatureWithHisOwnEditor<X, X, Y, Z> {

    public NewSObject(String name, String editorName, String[] editorDescription, Material editorMaterial) {
        super(null, name, editorName, editorDescription, editorMaterial, false);
    }

    /** Useful to have an option to set a parent for the clone option **/
    public NewSObject(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial) {
        super(parent, name, editorName, editorDescription, editorMaterial, false);
    }

    public abstract String getId();

    public abstract void setId(String id);

    public abstract String getPath();

    public abstract List<NewSActivator> getActivators();

    public abstract Item dropItem(Location location, int amount);

    public abstract ItemStack buildItem(int quantity, Optional<Player> creatorOpt);

    @Nullable
    public abstract NewSActivator getActivator(String actID);

    public abstract List<String> getDescription();
}
