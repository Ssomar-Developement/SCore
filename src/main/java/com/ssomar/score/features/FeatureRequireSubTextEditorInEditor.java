package com.ssomar.score.features;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public interface FeatureRequireSubTextEditorInEditor {

    /**
     * return an error if there is one
     **/
    Optional<String> verifyMessageReceived(String message);

    List<String> getCurrentValues();

    List<TextComponent> getMoreInfo();

    List<Suggestion> getSuggestions();

    String getTips();

    void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager);

    void finishEditInSubEditor(Player editor, NewGUIManager manager);
}
