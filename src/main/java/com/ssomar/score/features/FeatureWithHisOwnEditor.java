package com.ssomar.score.features;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.menu.GUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public abstract class FeatureWithHisOwnEditor<FINAL_VALUE_CLASS, FEATURE_CLASS, Y extends GUI, T extends NewGUIManager<Y>> extends FeatureAbstract<FINAL_VALUE_CLASS, FEATURE_CLASS> implements FeatureParentInterface<FINAL_VALUE_CLASS, FEATURE_CLASS>, Serializable {

    /**
     * Optional GUI texture character for this feature's editor.
     * When set, the editor will use this char as a custom background texture overlay.
     */
    private char editorGuiTextureChar = '\0';

    public FeatureWithHisOwnEditor(FeatureParentInterface parent, FeatureSettingsInterface featureSettingsSCore) {
        super(parent, featureSettingsSCore);
    }

    public char getEditorGuiTextureChar() {
        return editorGuiTextureChar;
    }

    public void setEditorGuiTextureChar(char textureChar) {
        this.editorGuiTextureChar = textureChar;
    }

    public abstract void openEditor(@NotNull Player player);

    public boolean isPremium() {
        if (getParent() == null || getParent() == this) return true;
        return getParent().isPremium();
    }

    @Nullable
    public FeatureInterface getFeature(FeatureSettingsInterface featureSettings) {
        for(FeatureInterface feature : this.getFeatures()) {
            if(feature.getFeatureSettings().getIdentifier().equals(featureSettings.getIdentifier())) return feature;
        }
        return null;
    }

    public FeatureInterface getFeatureWithName(String name) {
        for(FeatureInterface feature : this.getFeatures()) {
            if(feature.getFeatureSettings().getName().equals(name)) return feature;
        }
        return null;
    }

}
