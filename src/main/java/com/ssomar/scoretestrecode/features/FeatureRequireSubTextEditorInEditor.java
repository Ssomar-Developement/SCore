package com.ssomar.scoretestrecode.features;

import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.editor.Suggestion;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public interface FeatureRequireSubTextEditorInEditor {

    /**
     * return an error if there is one **/
    Optional<String> verifyMessageReceived(String message);

    List<String> getCurrentCValues();

    List<Suggestion> getSuggestions();

    void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager);

    void finishEditInSubEditor(Player editor, NewGUIManager manager);
}
