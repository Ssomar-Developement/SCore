package com.ssomar.score.features.custom.activators.activator;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.loop.LoopFeatures;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.sactivator.EventInfo;
import com.ssomar.score.sobject.sactivator.SOption;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;

import java.util.List;

public abstract class SActivator<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends FeatureWithHisOwnEditor<X, X, Y, Z> {

    @Getter
    private final String id;

    @Getter
    private SPlugin sPlugin;

    public SActivator(SPlugin sPlugin, FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.activator);
        this.id = id;
        this.sPlugin = sPlugin;
    }

    public abstract String getParentObjectId();

    public abstract SOption getOption();

    public abstract List<FeatureInterface> getFeatures();

    public abstract LoopFeatures getLoopFeatures();

    public abstract void run(Object parentObject, EventInfo eventInfo);

    public abstract List<String> getMenuDescription();

    /**
     * Return a new instance of the NewActivator
     **/
    public abstract SActivator getBuilderInstance(FeatureParentInterface parent, String id);

    public boolean isEqualsOrAClone(SActivator activator) {
        return this.getClass().equals(activator.getClass()) && this.id.equals(activator.id) && this.getParentObjectId().equals(activator.getParentObjectId());
    }

    public abstract List<X> extractActivatorsSameClass(List<SActivator> toActiv);
    public abstract void activateOptionGlobal(SOption sOption, EventInfo eventInfo, List<SActivator> toActiv);
}
