package com.ssomar.score.scheduler;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class ScheduleFeaturesEditorManager extends FeatureEditorManagerAbstract<ScheduleFeaturesEditor, ScheduleFeatures> {

    private static ScheduleFeaturesEditorManager instance;

    public static ScheduleFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new ScheduleFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public ScheduleFeaturesEditor buildEditor(ScheduleFeatures parent) {
        return new ScheduleFeaturesEditor(parent.clone(parent.getParent()));
    }

}
