package com.ssomar.testRecode.features.required.level;


import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.types.BooleanFeature;
import com.ssomar.testRecode.menu.NewGUIManager;
import com.ssomar.testRecode.menu.NewInteractionClickedGUIManager;

public class RequireLevelGUIManager extends NewGUIManager<RequireLevelGUI> {

    @Override
    public boolean allClicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {
        BooleanFeature feature = i.gui.requiredLevel.getCancelEventIfError();
        if(feature.isTheFeatureClicked(i.name)){
            feature.clickEditor(this, i.player);
        }
        return false;
    }

    @Override
    public boolean noShiftclicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {
        return false;
    }

    @Override
    public boolean shiftClicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {
        return false;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {
        return false;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<RequireLevelGUI> interact) {
        return false;
    }

    @Override
    public void reset(NewInteractionClickedGUIManager<RequireLevelGUI> interact) {
        interact.gui.requiredLevel.reset();
    }

    @Override
    public void save(NewInteractionClickedGUIManager<RequireLevelGUI> interact) {
        RequiredLevel requiredLevel = interact.gui.requiredLevel;
        FeatureParentInterface parentInterface = requiredLevel.getParent();
        parentInterface.reload();
        requiredLevel.save(parentInterface.getConfigurationSection());
    }
}
