package com.ssomar.testRecode.features.custom.activators.activator;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.HigherFormSObject;
import com.ssomar.score.sobject.sactivator.EventInfo;
import com.ssomar.score.sobject.sactivator.SOption;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import org.bukkit.Material;

import java.util.List;

public abstract class NewSActivator<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends FeatureWithHisOwnEditor<X, X, Y, Z> {

    public NewSActivator(FeatureParentInterface parent, String id) {
        super(parent, "activator", "Activator", new String[]{"&7&oAn activator"}, Material.BEACON, false);
    }

    public abstract String getParentObjectId();

    public abstract String getId();

    public abstract SOption getOption();

    public abstract void run(HigherFormSObject parentObject, EventInfo eventInfo);

    public abstract List<String> getMenuDescription();

    /** Return a new instance of the NewActivator **/
    public abstract NewSActivator getBuilderInstance(FeatureParentInterface parent, String id);
}
