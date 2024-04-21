package com.ssomar.score.sobject;


import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.GUI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class SObjectWithFileEditable<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>>extends SObjectWithFile<X,Y,Z> implements SObjectEditable {


    public SObjectWithFileEditable(String id, String name, String editorName, String[] editorDescription, Material editorMaterial, String path, SObjectWithFileLoader sObjectWithFileLoader) {
        super(id, name, editorName, editorDescription, editorMaterial, path, sObjectWithFileLoader);
    }

    public SObjectWithFileEditable(String id, FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, String path, SObjectWithFileLoader sObjectWithFileLoader) {
        super(id, parent, name, editorName, editorDescription, editorMaterial, path, sObjectWithFileLoader);
    }

    @Override
    public List<String> getDescription() {
        List<String> lore = new ArrayList<>();
        lore.add("§7ID: §6" + this.getId());
        lore.add("§7Path: §6" + this.getPath());
        return lore;
    }
}
