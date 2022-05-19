package com.ssomar.testRecode.features.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.*;
import com.ssomar.testRecode.features.required.level.RequireLevelGUI;
import com.ssomar.testRecode.features.required.level.RequiredLevel;
import com.ssomar.testRecode.menu.NewGUIManager;
import com.ssomar.testRecode.menu.NewInteractionClickedGUIManager;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class FeatureEditorManagerInterface<T extends FeatureEditorInterface<Y>, Y extends FeatureWithHisOwnEditor> extends NewGUIManager<T> {

    public void startEditing(Player editor, Y feature) {
        cache.put(editor, buildEditor(feature));
    }

    public abstract T buildEditor(Y feature);

    @Override
    public boolean allClicked(NewInteractionClickedGUIManager<T> i) {

        for(FeatureInterface feature : i.gui.getFeature().getFeatures()){
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
    public void reset(NewInteractionClickedGUIManager<T> interact) {
        interact.gui.getFeature().reset();
    }

    @Override
    public boolean noShiftclicked(NewInteractionClickedGUIManager<T> i) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(NewInteractionClickedGUIManager<T> i) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(NewInteractionClickedGUIManager<T> i) {
        return false;
    }

    @Override
    public boolean shiftClicked(NewInteractionClickedGUIManager<T> i) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<T> i) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<T> i) {
        return false;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<T> i) {
        return false;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<T> interact) {
        return false;
    }

    @Override
    public void nextPage(NewInteractionClickedGUIManager<T> interact) {

    }

    @Override
    public void previousPage(NewInteractionClickedGUIManager<T> interact) {

    }

    @Override
    public void receiveMessage(Player p, String message, NewInteractionClickedGUIManager<T> interact) {
        for(FeatureInterface feature : interact.gui.getFeature().getFeatures()){
            if(feature instanceof FeatureRequireOneMessageInEditor){
                Optional<String> potentialError = ((FeatureRequireOneMessageInEditor) feature).verifyMessageReceived(message);
                if(potentialError.isPresent()){
                    p.sendMessage(potentialError.get());
                    ((FeatureRequireOneMessageInEditor) feature).askInEditor(p, this);
                }
                else{
                    ((FeatureRequireOneMessageInEditor) feature).finishEditInEditor(p, this, message);
                    interact.gui.openGUISync(interact.player);
                }
            }
        }
    }

    @Override
    public void save(NewInteractionClickedGUIManager<T> interact) {
        Y featureInterface = interact.gui.getFeature();
        FeatureParentInterface parentInterface = featureInterface.getParent();
        featureInterface.save(parentInterface.getConfigurationSection());
        parentInterface.reload();
    }
}
