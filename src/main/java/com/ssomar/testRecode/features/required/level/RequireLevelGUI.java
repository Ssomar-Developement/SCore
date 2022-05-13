package com.ssomar.testRecode.features.required.level;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.required.level.RequiredLevel;

public class RequireLevelGUI extends GUI {

    public RequiredLevel requiredLevel;

    public RequireLevelGUI(RequiredLevel requiredLevel) {
        super("Required Level Editor", 3*9);
        this.requiredLevel = requiredLevel.clone();
    }

    public void fillTheGUI() {

        requiredLevel.getCancelEventIfError().initEditorItem(this, 0);

        // exit
        createItem(RED, 	1, 18, "&4&l▶&c Back", false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, "&4&l✘ &cReset", false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, "&2&l✔ &aSave settings", false, false, "", "&a&oClick here to save");
    }

}
