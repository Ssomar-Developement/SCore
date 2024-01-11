package com.ssomar.score.features.custom.activators.group;

import com.ssomar.score.features.custom.activators.activator.SActivator;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ActivatorsFeatureEditor extends FeatureEditorInterface<ActivatorsFeature> {

    public final ActivatorsFeature activatorsGroupFeature;
    private final int perPage;
    private int page;

    public ActivatorsFeatureEditor(ActivatorsFeature activatorsFeature) {
        super("&lActivators feature Editor", 3 * 9);
        this.activatorsGroupFeature = activatorsFeature;
        this.page = 1;
        this.perPage = 18;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        int total = 0;

        for (SActivator activator : activatorsGroupFeature.getActivators().values()) {
            if ((page - 1) * perPage <= total && total < page * perPage) {
                activator.initAndUpdateItemParentEditor(this, i);
                i++;

            }
            total++;
        }

        if (activatorsGroupFeature.getActivators().values().size() > perPage && page * perPage < activatorsGroupFeature.getActivators().values().size()) {
            createItem(NEXT_PAGE_MAT, 1, 26, GUI.NEXT_PAGE, false, false);
        }
        if (page > 1) {
            createItem(PREVIOUS_PAGE_MAT, 1, 19, GUI.PREVIOUS_PAGE, false, false);
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);


        // new activator
        if (!activatorsGroupFeature.isPremium() && activatorsGroupFeature.getActivators().size() >= activatorsGroupFeature.getPremiumLimit()) {
            createItem(PURPLE, 1, 22, GUI.PREMIUM, false, false, "", "&d&oIt requires premium to", "&d&ohave more than " + activatorsGroupFeature.getPremiumLimit() + " activator !");
        } else
            createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new activator");
    }

    @Override
    public ActivatorsFeature getParent() {
        return activatorsGroupFeature;
    }

    public void nextPage() {
        page++;
        clearAndSetBackground();
        load();
    }

    public void prevPage() {
        page--;
        clearAndSetBackground();
        load();
    }
}
