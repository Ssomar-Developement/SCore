package com.ssomar.score.sobject;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.menu.GUI;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SObject<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends FeatureWithHisOwnEditor<X, X, Y, Z> {

    private String id;

    public SObject(String id, FeatureSettingsInterface featureSettings) {
        super(null, featureSettings);
        this.id = id;
    }

    /**
     * Useful to have an option to set a parent for the clone option
     **/
    public SObject(String id, FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.id = id;
    }

    public abstract boolean delete();

}