package com.ssomar.testRecode.features;

import com.ssomar.testRecode.menu.NewGUIManager;
import org.bukkit.entity.Player;

import java.util.Optional;

public interface FeatureRequireOneMessageInEditor {

    void askInEditor(Player editor, NewGUIManager guiManager);

    /**
     * return an error if there is one **/
    Optional<String> verifyMessageReceived(String message);

    void finishEditInEditor(Player editor, NewGUIManager guiManager);
}
