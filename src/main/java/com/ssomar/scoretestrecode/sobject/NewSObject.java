package com.ssomar.scoretestrecode.sobject;


import com.ssomar.executableitems.executableitems.ExecutableItemLoader;
import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.custom.activators.activator.NewSActivator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.File;
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

    public boolean delete() {
        File file = null;

        if((file = new File(getPath())) != null) {
            file.delete();
            return true;
        }
        return false;

    }

    public abstract List<NewSActivator> getActivators();

    public abstract Item dropItem(Location location, int amount);

    public abstract ItemStack buildItem(int quantity, Optional<Player> creatorOpt);

    @Nullable
    public abstract NewSActivator getActivator(String actID);

    public abstract List<String> getDescription();
}
