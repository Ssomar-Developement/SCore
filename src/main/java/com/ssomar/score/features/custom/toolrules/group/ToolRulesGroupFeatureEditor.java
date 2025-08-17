package com.ssomar.score.features.custom.toolrules.group;

import com.ssomar.score.features.custom.toolrules.toolrule.ToolRuleFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;

public class ToolRulesGroupFeatureEditor extends FeatureEditorInterface<ToolRulesGroupFeature> {

    public final ToolRulesGroupFeature attributesGroupFeature;

    public ToolRulesGroupFeatureEditor(ToolRulesGroupFeature enchantsGroupFeature) {
        super("&lTool rules feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (ToolRuleFeature enchantment : attributesGroupFeature.getToolRules().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        attributesGroupFeature.getEnable().initAndUpdateItemParentEditor(this, 20);
        attributesGroupFeature.getDefaultMiningSpeed().initAndUpdateItemParentEditor(this, 24);
        attributesGroupFeature.getDamagePerBlock().initAndUpdateItemParentEditor(this, 25);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, TM.g(Text.EDITOR_RESET_NAME), false, false, "", TM.g(Text.EDITOR_RESET_DESCRIPTION));

        // new attribute
        if (!attributesGroupFeature.isPremium() && attributesGroupFeature.getToolRules().size() >= attributesGroupFeature.getPremiumLimit()) {
            createItem(PURPLE, 1, 22, GUI.PREMIUM, false, false, "", "&d&oIt requires premium to", "&d&ohave more than " + attributesGroupFeature.getPremiumLimit() + " tool rules !");
        } else
            createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add a new tool rule");

        // Save
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", TM.g(Text.EDITOR_SAVE_DESCRIPTION));
    }

    @Override
    public ToolRulesGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
