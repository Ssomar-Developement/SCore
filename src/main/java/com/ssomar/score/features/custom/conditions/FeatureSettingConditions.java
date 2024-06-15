package com.ssomar.score.features.custom.conditions;

import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.FeatureSettingsSCoreNoEnum;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.FixedMaterial;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FeatureSettingConditions {

    private static FeatureSettingConditions instance;

    public Map<String, FeatureSettingsInterface> errorMessages = new HashMap<>();
    public Map<String, FeatureSettingsInterface> cancel = new HashMap<>();
    public Map<String, FeatureSettingsInterface> commands = new HashMap<>();

    public static FeatureSettingConditions getInstance() {
        if(instance == null) instance = new FeatureSettingConditions();
        return instance;
    }

    public FeatureSettingsInterface getErrorMessage(String condition) {
        if(!errorMessages.containsKey(condition)) {
            FeatureSettingsInterface featureSettings = new FeatureSettingsSCoreNoEnum(condition+"Msg", "Invalid condition message", new String[] {"&7&oThe message displayed", "&7&owhen the condition is not met"}, GUI.WRITABLE_BOOK, false);
            errorMessages.put(condition, featureSettings);
        }
        return errorMessages.get(condition);
    }

    public FeatureSettingsInterface getCancelEventIfError(String condition) {
        if(!cancel.containsKey(condition)) {
            FeatureSettingsInterface featureSettings = new FeatureSettingsSCoreNoEnum(condition+"Cancel", "Cancel Event If Invalid", new String[] {"&7&oThe event will be cancelled", "&7&owhen the condition is not met"}, null, false);
            cancel.put(condition, featureSettings);
        }
        return cancel.get(condition);
    }

    public FeatureSettingsInterface getConsoleCommandsIfError(String condition) {
        if(!commands.containsKey(condition)) {
            FeatureSettingsInterface featureSettings = new FeatureSettingsSCoreNoEnum(condition+"Cmds", "Console Commands If Invalid", new String[]{"&7&oConsole Commands If Error"}, FixedMaterial.getMaterial(Arrays.asList("COMMAND_BLOCK", "WRITABLE_BOOK", "BOOK_AND_QUILL")), false);
            commands.put(condition, featureSettings);
        }
        return commands.get(condition);
    }
}
