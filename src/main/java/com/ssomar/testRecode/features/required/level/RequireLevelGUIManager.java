package com.ssomar.testRecode.features.required.level;


import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireOneMessageInEditor;
import com.ssomar.testRecode.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.testRecode.features.editor.FeatureEditorManagerInterface;
import com.ssomar.testRecode.menu.NewInteractionClickedGUIManager;
import org.bukkit.entity.Player;

import java.util.Optional;

public class RequireLevelGUIManager extends FeatureEditorManagerInterface<RequireLevelGUI, RequiredLevel> {

    private static RequireLevelGUIManager instance;

    @Override
    public boolean allClicked(NewInteractionClickedGUIManager<RequireLevelGUI> i) {

        for(FeatureInterface feature : i.gui.requiredLevel.getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    ((FeatureRequireOnlyClicksInEditor) feature).clickParentEditor(i.player, this);
                }
                else if(feature instanceof FeatureRequireOneMessageInEditor){
                    ((FeatureRequireOneMessageInEditor) feature).askInEditor(i.player, this);
                }
                return true;
            }
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
    public void receiveMessage(Player p, String message, NewInteractionClickedGUIManager<RequireLevelGUI> interact) {
        for(FeatureInterface feature : interact.gui.requiredLevel.getFeatures()){
            if(feature instanceof FeatureRequireOneMessageInEditor){
                Optional<String> potentialError = ((FeatureRequireOneMessageInEditor) feature).verifyMessageReceived(message);
                if(potentialError.isPresent()){
                    p.sendMessage(potentialError.get());
                    ((FeatureRequireOneMessageInEditor) feature).askInEditor(p, this);
                }
                else{
                    ((FeatureRequireOneMessageInEditor) feature).finishEditInEditor(p, this);
                    interact.gui.openGUISync(interact.player);
                }
            }
        }
    }

    @Override
    public void reset(NewInteractionClickedGUIManager<RequireLevelGUI> interact) {
        interact.gui.requiredLevel.reset();
    }

    @Override
    public void save(NewInteractionClickedGUIManager<RequireLevelGUI> interact) {
        RequiredLevel requiredLevel = interact.gui.requiredLevel;
        FeatureParentInterface parentInterface = requiredLevel.getParent();
        requiredLevel.save(parentInterface.getConfigurationSection());
        parentInterface.reload();
    }

    public static RequireLevelGUIManager getInstance(){
        if(instance == null){
            instance = new RequireLevelGUIManager();
        }
        return instance;
    }

    @Override
    public RequireLevelGUI buildEditor(RequiredLevel feature) {
        return new RequireLevelGUI(feature.clone());
    }
}
