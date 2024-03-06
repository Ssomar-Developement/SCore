package com.ssomar.score.features.editor;

import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public abstract class FeatureEditorManagerAbstract<T extends FeatureEditorInterface<Y>, Y extends FeatureParentInterface> extends NewGUIManager<T> {

    public void startEditing(Player editor, Y feature) {
        cache.put(editor, buildEditor(feature));
        cache.get(editor).openGUISync(editor);
    }

    public abstract T buildEditor(Y parent);

    @Override
    public boolean allClicked(NewInteractionClickedGUIManager<T> i) {

        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {

                //SsomarDev.testMsg("Feature clicked: " + feature.getName());
                if ((feature instanceof FeatureRequireOnlyClicksInEditor && !(feature instanceof FeatureRequireOneMessageInEditor)) || feature instanceof FeatureRequireClicksOrOneMessageInEditor) {
                    ((FeatureRequireOnlyClicksInEditor) feature).clickParentEditor(i.player, this);
                } else if (feature instanceof FeatureRequireOneMessageInEditor && !(feature instanceof FeatureRequireOnlyClicksInEditor)) {
                    //SsomarDev.testMsg("FeatureRequireOneMessageInEditor");
                    ((FeatureRequireOneMessageInEditor) feature).askInEditor(i.player, this);
                } else if (feature instanceof FeatureRequireMultipleMessageInEditor) {
                    //SsomarDev.testMsg("FeatureRequireOneMessageInEditor");
                    ((FeatureRequireMultipleMessageInEditor) feature).askInEditorFirstTime(i.player, this);
                } else if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                    FeatureRequireSubTextEditorInEditor featureRequireSubTextEditorInEditor = (FeatureRequireSubTextEditorInEditor) feature;
                    requestWriting.put(i.player, feature.getEditorName());
                    currentWriting.put(i.player, featureRequireSubTextEditorInEditor.getCurrentValues());
                    suggestions.put(i.player, featureRequireSubTextEditorInEditor.getSuggestions());
                    moreInfo = featureRequireSubTextEditorInEditor.getMoreInfo();
                    enableTextEditor(i.player);
                    i.player.closeInventory();
                    ((FeatureRequireSubTextEditorInEditor) feature).sendBeforeTextEditor(i.player, this);
                    sendEditor(i.player, featureRequireSubTextEditorInEditor.getTips());
                } else if (feature instanceof FeatureParentInterface) {
                    /* Save the parent if there is one */
                    i.gui.getParent().reload();
                    i.gui.getParent().save();
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
        for (FeatureInterface feature : interact.gui.getParent().getFeatures()) {
            feature.reset();
        }
        interact.gui.load();
    }

    @Override
    public void newObject(NewInteractionClickedGUIManager<T> i) {
        if (i.gui.getParent() instanceof FeaturesGroup) {
            ((FeaturesGroup) i.gui.getParent()).createNewFeature(i.player);
        }

    }

    @Override
    public void back(NewInteractionClickedGUIManager<T> interact) {
        Y parent = interact.gui.getParent();
        parent.openBackEditor(interact.player);
    }

    @Override
    public boolean noShiftclicked(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                if (feature instanceof FeatureRequireOnlyClicksInEditor) {
                    return ((FeatureRequireOnlyClicksInEditor) feature).noShiftclicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                if (feature instanceof FeatureRequireOnlyClicksInEditor) {
                    return ((FeatureRequireOnlyClicksInEditor) feature).noShiftLeftclicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean noShiftRightclicked(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                if (feature instanceof FeatureRequireOnlyClicksInEditor) {
                    return ((FeatureRequireOnlyClicksInEditor) feature).noShiftRightclicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean shiftClicked(NewInteractionClickedGUIManager<T> i) {
        if (i.gui.getParent() instanceof FeaturesGroup) {
            FeatureInterface feature = ((FeaturesGroup) (i.gui.getParent())).getTheChildFeatureClickedParentEditor(i.decoloredName);
            if (feature == null) {
                return false;
            }
            ((FeaturesGroup) i.gui.getParent()).deleteFeature(i.player, feature);
            i.gui.getParent().save();
            i.gui.getParent().openEditor(i.player);
            return true;
        } else {
            for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
                if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                    if (feature instanceof FeatureRequireOnlyClicksInEditor) {
                        return ((FeatureRequireOnlyClicksInEditor) feature).shiftClicked(i.player, this);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                if (feature instanceof FeatureRequireOnlyClicksInEditor) {
                    return ((FeatureRequireOnlyClicksInEditor) feature).shiftLeftClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                if (feature instanceof FeatureRequireOnlyClicksInEditor) {
                    return ((FeatureRequireOnlyClicksInEditor) feature).shiftRightClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                if (feature instanceof FeatureRequireOnlyClicksInEditor) {
                    return ((FeatureRequireOnlyClicksInEditor) feature).leftClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                if (feature instanceof FeatureRequireOnlyClicksInEditor) {
                    return ((FeatureRequireOnlyClicksInEditor) feature).rightClicked(i.player, this);
                }
            }
        }
        return false;
    }

    @Override
    public boolean middleClicked(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature.isTheFeatureClickedParentEditor(i.decoloredName)) {
                if (feature instanceof FeatureRequireOneMessageInEditor) {
                    ((FeatureRequireOneMessageInEditor) feature).askInEditor(i.player, this);
                    return true;
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

    public void receiveMessageDeleteline(NewInteractionClickedGUIManager<T> i) {
        super.receiveMessageDeleteline(i);
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (requestWriting.get(i.player).equals(feature.getEditorName())) {
                if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                    FeatureRequireSubTextEditorInEditor featureRequireSubTextEditorInEditor = (FeatureRequireSubTextEditorInEditor) feature;
                    ((FeatureRequireSubTextEditorInEditor) feature).sendBeforeTextEditor(i.player, this);
                    sendEditor(i.player, featureRequireSubTextEditorInEditor.getTips());
                }
            }
        }
    }

    public void receiveMessageUpLine(NewInteractionClickedGUIManager<T> i) {
        super.receiveMessageUpLine(i);
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (requestWriting.get(i.player).equals(feature.getEditorName())) {
                if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                    FeatureRequireSubTextEditorInEditor featureRequireSubTextEditorInEditor = (FeatureRequireSubTextEditorInEditor) feature;
                    ((FeatureRequireSubTextEditorInEditor) feature).sendBeforeTextEditor(i.player, this);
                    sendEditor(i.player, featureRequireSubTextEditorInEditor.getTips());
                }
            }
        }
    }

    public void receiveMessageDownLine(NewInteractionClickedGUIManager<T> i) {
        super.receiveMessageDownLine(i);
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (requestWriting.get(i.player).equals(feature.getEditorName())) {
                if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                    FeatureRequireSubTextEditorInEditor featureRequireSubTextEditorInEditor = (FeatureRequireSubTextEditorInEditor) feature;
                    ((FeatureRequireSubTextEditorInEditor) feature).sendBeforeTextEditor(i.player, this);
                    sendEditor(i.player, featureRequireSubTextEditorInEditor.getTips());
                }
            }
        }
    }

    public void receiveMessageEditLine(NewInteractionClickedGUIManager<T> i) {
        super.receiveMessageEditLine(i);
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (requestWriting.get(i.player).equals(feature.getEditorName())) {
                if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                    FeatureRequireSubTextEditorInEditor featureRequireSubTextEditorInEditor = (FeatureRequireSubTextEditorInEditor) feature;
                    ((FeatureRequireSubTextEditorInEditor) feature).sendBeforeTextEditor(i.player, this);
                    sendEditor(i.player, featureRequireSubTextEditorInEditor.getTips());
                }
            }
        }
    }

    @Override
    public void receiveMessagePreviousPage(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (requestWriting.get(i.player).equals(feature.getEditorName())) {
                if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                    FeatureRequireSubTextEditorInEditor featureRequireSubTextEditorInEditor = (FeatureRequireSubTextEditorInEditor) feature;
                    if (suggestionPage.get(i.player) != 0)
                        suggestionPage.put(i.player, suggestionPage.get(i.player) - 1);
                    ((FeatureRequireSubTextEditorInEditor) feature).sendBeforeTextEditor(i.player, this);
                    sendEditor(i.player, featureRequireSubTextEditorInEditor.getTips());
                }
            }
        }
    }

    @Override
    public void receiveMessageNextPage(NewInteractionClickedGUIManager<T> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (requestWriting.get(i.player).equals(feature.getEditorName())) {
                if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                    FeatureRequireSubTextEditorInEditor featureRequireSubTextEditorInEditor = (FeatureRequireSubTextEditorInEditor) feature;
                    suggestionPage.put(i.player, suggestionPage.get(i.player) + 1);
                    ((FeatureRequireSubTextEditorInEditor) feature).sendBeforeTextEditor(i.player, this);
                    sendEditor(i.player, featureRequireSubTextEditorInEditor.getTips());
                }
            }
        }
    }

    public void receiveMessageFinish(NewInteractionClickedGUIManager<T> interact) {
        for (FeatureInterface feature : interact.gui.getParent().getFeatures()) {
            if (feature instanceof FeatureRequireMultipleMessageInEditor) {
                if (feature.getEditorName().equals(requestWriting.get(interact.player))) {
                    ((FeatureRequireMultipleMessageInEditor) feature).finishEditInEditor(interact.player, this, null);
                    interact.gui.openGUISync(interact.player);
                }
            } else if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                if (feature.getEditorName().equals(requestWriting.get(interact.player))) {
                    FeatureRequireSubTextEditorInEditor f = (FeatureRequireSubTextEditorInEditor) feature;
                    f.finishEditInSubEditor(interact.player, this);
                    currentWriting.remove(interact.player);
                    requestWriting.remove(interact.player);
                    disableTextEditor(interact.player);
                    interact.gui.openGUISync(interact.player);
                }
            }
        }
    }

    public void receiveMessageNoValue(NewInteractionClickedGUIManager<T> interact) {
        for (FeatureInterface feature : interact.gui.getParent().getFeatures()) {
            if (feature.getEditorName().equals(requestWriting.get(interact.player))) {
                if (feature instanceof FeatureRequireOneMessageInEditor) {
                    ((FeatureRequireOneMessageInEditor) feature).finishEditInEditorNoValue(interact.player, this);
                    interact.gui.openGUISync(interact.player);
                }
            }
        }
    }


    @Override
    public void receiveMessageValue(NewInteractionClickedGUIManager<T> interact) {
        for (FeatureInterface feature : interact.gui.getParent().getFeatures()) {
            if (feature.getEditorName().equals(requestWriting.get(interact.player))) {
                if (feature instanceof FeatureRequireOneMessageInEditor) {
                    Optional<String> potentialError = ((FeatureRequireOneMessageInEditor) feature).verifyMessageReceived(interact.message);
                    if (potentialError.isPresent()) {
                        interact.player.sendMessage(potentialError.get());
                        // AsyncChat -> Sync close inv / Message
                        NewGUIManager newGUIManager = this;
                        BukkitRunnable runnable = new BukkitRunnable() {

                            public void run() {
                                ((FeatureRequireOneMessageInEditor) feature).askInEditor(interact.player, newGUIManager);
                            }
                        };
                        SCore.schedulerHook.runTask(runnable, 0);
                    } else {
                        ((FeatureRequireOneMessageInEditor) feature).finishEditInEditor(interact.player, this, interact.message);
                        interact.gui.openGUISync(interact.player);
                    }
                } else if (feature instanceof FeatureRequireMultipleMessageInEditor) {
                    Optional<String> potentialError = ((FeatureRequireMultipleMessageInEditor) feature).verifyMessageReceived(interact.message);
                    if (potentialError.isPresent()) {
                        interact.player.sendMessage(potentialError.get());
                        // AsyncChat -> Sync close inv / Message
                        NewGUIManager newGUIManager = this;
                        BukkitRunnable runnable = new BukkitRunnable() {

                            public void run() {
                                ((FeatureRequireOneMessageInEditor) feature).askInEditor(interact.player, newGUIManager);
                            }
                        };
                        SCore.schedulerHook.runTask(runnable, 0);
                    } else {
                        ((FeatureRequireMultipleMessageInEditor) feature).addMessageValue(interact.player, this, interact.message);
                    }
                } else if (feature instanceof FeatureRequireSubTextEditorInEditor) {
                    FeatureRequireSubTextEditorInEditor f = (FeatureRequireSubTextEditorInEditor) feature;
                    Optional<String> pErrors = f.verifyMessageReceived(interact.message);
                    if (pErrors.isPresent()) {
                        interact.player.sendMessage(StringConverter.coloredString(pErrors.get()));
                        return;
                    } else {
                        currentWriting.get(interact.player).add(interact.message);
                        f.sendBeforeTextEditor(interact.player, this);
                        sendEditor(interact.player, f.getTips());
                    }
                }
            }
        }
    }

    @Override
    public void save(NewInteractionClickedGUIManager<T> interact) {
        FeatureParentInterface parent = interact.gui.getParent();
        parent.save();
        parent.reload();
        while (parent instanceof FeatureInterface && ((FeatureAbstract) parent).getParent() != parent) {
            parent = ((FeatureAbstract) parent).getParent();
            parent.reload();
        }
        back(interact);
    }
}
