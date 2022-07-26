package com.ssomar.score.features.custom.activators.activator;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.HigherFormSObject;
import com.ssomar.score.sobject.sactivator.EventInfo;
import com.ssomar.score.sobject.sactivator.SOption;
import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

public abstract class NewSActivator<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends FeatureWithHisOwnEditor<X, X, Y, Z> {

    @Getter
    private String id;

    public NewSActivator(FeatureParentInterface parent, String id) {
        super(parent, "activator", "Activator", new String[]{"&7&oAn activator"}, Material.BEACON, false);
        this.id = id;
    }

    public abstract String getParentObjectId();

    public abstract SOption getOption();

    public abstract List<FeatureInterface> getFeatures();

    public abstract void run(HigherFormSObject parentObject, EventInfo eventInfo);

    public abstract List<String> getMenuDescription();

    /**
     * Return a new instance of the NewActivator
     **/
    public abstract NewSActivator getBuilderInstance(FeatureParentInterface parent, String id);

    public boolean isEqualsOrAClone(NewSActivator activator) {
        return this.getClass().equals(activator.getClass()) && this.id.equals(activator.id) && this.getParentObjectId().equals(activator.getParentObjectId());
    }
}
