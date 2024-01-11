package com.ssomar.score.sobject;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.menu.GUI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public abstract class SObject<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends FeatureWithHisOwnEditor<X, X, Y, Z> {

    private String id;

    public SObject(String id, String name, String editorName, String[] editorDescription, Material editorMaterial) {
        super(null, name, editorName, editorDescription, editorMaterial, false);
        this.id = id;
    }

    /**
     * Useful to have an option to set a parent for the clone option
     **/
    public SObject(String id, FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial) {
        super(parent, name, editorName, editorDescription, editorMaterial, false);
        this.id = id;
    }

    public abstract boolean delete();

    public abstract ItemStack buildItem(int quantity, Optional<Player> creatorOpt);

    public abstract List<String> getDescription();
}