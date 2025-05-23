package com.ssomar.score.features.editor;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GenericFeatureParentEditorReloaded extends FeatureEditorInterface<FeatureParentInterface> {

    private FeatureParentInterface featureParent;

    public GenericFeatureParentEditorReloaded(FeatureParentInterface featureParent) {
        super("&l"+featureParent.getFeatureSettings().getEditorName()+" Editor", Math.max(3*9, (int) Math.ceil(((double) featureParent.getFeatures().size()+9.0) / 9.0) * 9));
        this.featureParent = featureParent;
        load();
    }

    @Override
    public void load() {
        clearAndSetBackground();
        int i = 0;
        for(FeatureInterface feature : (List<FeatureInterface>) featureParent.getFeatures()) {
            feature.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, getSize()-9, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, getSize()-8, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, getSize()-1, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public FeatureParentInterface getParent() {
        return featureParent;
    }

    public void click(Player p, ItemStack item, ClickType click){
        GenericFeatureParentEditorReloadedManager.getInstance().clicked(p, item, click);
    }
}
