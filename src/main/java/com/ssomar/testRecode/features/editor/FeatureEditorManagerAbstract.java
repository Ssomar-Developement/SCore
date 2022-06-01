package com.ssomar.testRecode.features.editor;

import com.ssomar.testRecode.features.*;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.editor.NewInteractionClickedGUIManager;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class FeatureEditorManagerAbstract<T extends FeatureEditorInterface<Y>, Y extends FeatureParentInterface> extends NewGUIManager<T> {

    public void startEditing(Player editor, Y feature) {
        cache.put(editor, buildEditor(feature));
        cache.get(editor).openGUISync(editor);
    }

    public abstract T buildEditor(Y parent);

    @Override
    public boolean allClicked(NewInteractionClickedGUIManager<T> i) {

        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                //SsomarDev.testMsg("Feature clicked: " + feature.getName());
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    ((FeatureRequireOnlyClicksInEditor) feature).clickParentEditor(i.player, this);
                }
                else if(feature instanceof FeatureRequireOneMessageInEditor){
                    //SsomarDev.testMsg("FeatureRequireOneMessageInEditor");
                    ((FeatureRequireOneMessageInEditor) feature).askInEditor(i.player, this);
                }
                else if(feature instanceof FeatureParentInterface){
                    FeatureParentInterface parent = (FeatureParentInterface) feature;
                    parent.openEditor(i.player);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset(NewInteractionClickedGUIManager<T> interact) {
        for(FeatureInterface feature : interact.gui.getParent().getFeatures()){
           feature.reset();
        }
        interact.gui.load();
    }

    @Override
    public void newObject(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                //SsomarDev.testMsg("Feature clicked: " + feature.getName());
                if(feature instanceof FeaturesGroup){
                    ((FeaturesGroup) feature).createNewFeature(i.player);
                }
            }
        }
    }

    @Override
    public void back(NewInteractionClickedGUIManager<T> interact) {
        Y parent = interact.gui.getParent();
        parent.openBackEditor(interact.player);
    }

    @Override
    public boolean noShiftclicked(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    return ((FeatureRequireOnlyClicksInEditor) feature).noShiftclicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    return ((FeatureRequireOnlyClicksInEditor) feature).noShiftLeftclicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean noShiftRightclicked(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    return ((FeatureRequireOnlyClicksInEditor) feature).noShiftRightclicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean shiftClicked(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    return ((FeatureRequireOnlyClicksInEditor) feature).shiftClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    return ((FeatureRequireOnlyClicksInEditor) feature).shiftLeftClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    return ((FeatureRequireOnlyClicksInEditor) feature).shiftRightClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    return ((FeatureRequireOnlyClicksInEditor) feature).leftClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<T> i) {
        for(FeatureInterface feature : i.gui.getParent().getFeatures()){
            if(feature.isTheFeatureClickedParentEditor(i.name)){
                if(feature instanceof FeatureRequireOnlyClicksInEditor){
                    return ((FeatureRequireOnlyClicksInEditor) feature).rightClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public void nextPage(NewInteractionClickedGUIManager<T> interact) {

    }

    @Override
    public void previousPage(NewInteractionClickedGUIManager<T> interact) {

    }

    @Override
    public void receiveMessage(NewInteractionClickedGUIManager<T> interact) {
        for(FeatureInterface feature : interact.gui.getParent().getFeatures()){
            if(feature instanceof FeatureRequireOneMessageInEditor){
                if(feature.getEditorName().equals(requestWriting.get(interact.player))) {
                    Optional<String> potentialError = ((FeatureRequireOneMessageInEditor) feature).verifyMessageReceived(interact.message);
                    if (potentialError.isPresent()) {
                        interact.player.sendMessage(potentialError.get());
                        ((FeatureRequireOneMessageInEditor) feature).askInEditor(interact.player, this);
                    } else {
                        ((FeatureRequireOneMessageInEditor) feature).finishEditInEditor(interact.player, this, interact.message);
                        interact.gui.openGUISync(interact.player);
                    }
                }
            }
        }
    }

    @Override
    public void save(NewInteractionClickedGUIManager<T> interact) {
        for(FeatureInterface feature : interact.gui.getParent().getFeatures()){
            if(!(feature instanceof FeatureParentInterface)){
                feature.extractInfoFromParentEditor(this, interact.player);
            }
        }
        interact.gui.getParent().save();
        interact.gui.getParent().reload();
        back(interact);
    }
}
