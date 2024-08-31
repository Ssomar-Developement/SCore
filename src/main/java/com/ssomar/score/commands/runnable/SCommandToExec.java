package com.ssomar.score.commands.runnable;

import com.ssomar.score.SsomarDev;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class SCommandToExec {

    private SCommand sCommand;

    private Map<CommandSetting, Object> settingsValues;

    private List<String> otherArgs;

    private ActionInfo actionInfo;

    public SCommandToExec(SCommand sCommand) {
        super();
        this.sCommand = sCommand;
    }

    public SCommandToExec() {
        this.settingsValues = new HashMap<>();
        this.otherArgs = new ArrayList<>();
    }

    public void extractSettings(String entry) {

        for (String name : sCommand.getNames()) {
            if (entry.startsWith(name)) {
                entry = entry.substring(name.length());
                break;
            }
        }
        entry = entry.trim();
        SsomarDev.testMsg("entry: "+entry, true);
        if (entry.equals("")) {
            settingsValues = new HashMap<>();
            otherArgs = new ArrayList<>();

            for (CommandSetting setting : sCommand.getSettings()) {
               settingsValues.put(setting, setting.getValue(null));
            }
        }
        else{
            List<String> arguments = new ArrayList<>();
            arguments.addAll(Arrays.asList(entry.split(" ")));
            Map<CommandSetting, Object> settingObjectMap = new HashMap<>();
            // fully new system
            for(CommandSetting setting : sCommand.getSettings()){
                if(arguments.size() > 0){
                    Optional<String> value = arguments.stream().filter(arg -> arg.startsWith(setting.getName()+":")).findFirst();
                    if(value.isPresent()){
                        settingObjectMap.put(setting, setting.getValue(value.get().replace(setting.getName()+":", "")));
                        arguments.remove(value.get());
                    }
                }
            }
            // Maintain old system
            List<Integer> indexToRemove = new ArrayList<>();
            for (CommandSetting setting : sCommand.getSettings()) {
                if(settingObjectMap.containsKey(setting)) continue;
                if(setting.getOldSystemIndex() == -1) settingObjectMap.put(setting, setting.getValue(null));
                else if(arguments.size() > setting.getOldSystemIndex()) {
                    String value = arguments.get(setting.getOldSystemIndex());
                    if(setting.isOldSystemOptional()){
                        if(setting.getType() == Boolean.class && !(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
                            settingObjectMap.put(setting, setting.getValue(null));
                        }
                        else if(setting.getType() == Double.class || setting.getType() == Integer.class) {
                            try {
                                Double.parseDouble(value);
                                settingObjectMap.put(setting, setting.getValue(value));
                                indexToRemove.add(setting.getOldSystemIndex());
                            } catch (NumberFormatException e) {
                                settingObjectMap.put(setting, setting.getValue(null));
                            }
                        }
                        else {
                            settingObjectMap.put(setting, setting.getValue(value));
                            indexToRemove.add(setting.getOldSystemIndex());
                        }
                    }else {
                        settingObjectMap.put(setting, setting.getValue(arguments.get(setting.getOldSystemIndex())));
                        indexToRemove.add(setting.getOldSystemIndex());
                    }
                }
            }
            // Sort the index to remove
            Collections.sort(indexToRemove, Collections.reverseOrder());
            for(int index : indexToRemove) arguments.remove(index);
            this.settingsValues = settingObjectMap;
            this.otherArgs = arguments;
        }
    }

    public Object getSettingValue(String setting) {
        CommandSetting commandSetting = sCommand.getSettings().stream().filter(s -> s.getName().equals(setting)).findFirst().orElse(null);
        if(commandSetting != null) return settingsValues.get(commandSetting);
        return null;
    }
}
