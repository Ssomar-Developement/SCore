package com.ssomar.score.newprojectiles;

/* public class SProjectileEditorManager extends FeatureEditorManagerAbstract<com.ssomar.testRecode.executableItems.NewExecutableItemEditor, NewExecutableItem> {

    private static SProjectileEditorManager instance;

    @Override
    public com.ssomar.testRecode.executableItems.NewExecutableItemEditor buildEditor(NewExecutableItem featureParentInterface) {
        return new com.ssomar.testRecode.executableItems.NewExecutableItemEditor(featureParentInterface.clone(featureParentInterface.getParent()));
    }

    public static SProjectileEditorManager getInstance() {
        if (instance == null) {
            instance = new SProjectileEditorManager();
        }
        return instance;
    }

    public void reloadEditor(NewInteractionClickedGUIManager<com.ssomar.testRecode.executableItems.NewExecutableItemEditor> i) {
        i.gui.load();
        i.player.updateInventory();
    }


    @Override
    public void receiveMessage(NewInteractionClickedGUIManager<com.ssomar.testRecode.executableItems.NewExecutableItemEditor> i) {
        super.receiveMessage(i);
        reloadEditor(i);
    }

    @Override
    public void clicked(ItemStack item, NewInteractionClickedGUIManager<NewExecutableItemEditor> interact, ClickType click) {
        super.clicked(item, interact, click);
        reloadEditor(interact);
    }

}*/
