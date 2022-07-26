package com.ssomar.score.features.custom.detailedslots;


import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class DetailedSlotsEditorManager extends FeatureEditorManagerAbstract<DetailedSlotsEditor, DetailedSlots> {

    private static DetailedSlotsEditorManager instance;

    public static DetailedSlotsEditorManager getInstance() {
        if (instance == null) {
            instance = new DetailedSlotsEditorManager();
        }
        return instance;
    }

    @Override
    public DetailedSlotsEditor buildEditor(DetailedSlots parent) {
        return new DetailedSlotsEditor(parent.clone(parent.getParent()));
    }

    @Override
    public boolean allClicked(NewInteractionClickedGUIManager<DetailedSlotsEditor> i) {

        if (i.decoloredName.contains("Slot: ")) {
            String[] split = i.decoloredName.split("Slot: ");
            if (split.length == 2) {
                try {
                    int slot = Integer.valueOf(split[1]);
                    cache.get(i.player).changeSlot(slot);
                } catch (Exception e) {
                    switch (split[1]) {
                        case "mainHand":
                            i.gui.changeSlotMainHand();
                            break;
                        case "offHand":
                            i.gui.changeSlotOffHand();
                            break;
                        case "boots":
                            i.gui.changeSlotBoots();
                            break;
                        case "leggings":
                            i.gui.changeSlotLeggings();
                            break;
                        case "chestplate":
                            i.gui.changeSlotChestplate();
                            break;
                        case "helmet":
                            i.gui.changeSlotHelmet();
                            break;
                        default:
                            break;
                    }
                }
            }
        } else if (i.decoloredName.contains("Disable all slots")) {
            i.gui.disableAllSlots();
        } else if (i.decoloredName.contains("Enable all slots")) {
            i.gui.enableAllSlots();
        }
        return false;
    }
}
