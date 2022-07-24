package com.ssomar.score.commands.runnable;

import java.util.*;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;

public abstract class RunCommandsBuilder {

    /* Commands to run */
    private List<String> commands;

    private ActionInfo actionInfo;

    /* delay in tick - commands */
    private HashMap<Integer, List<RunCommand>> finalCommands = new HashMap<>();

    public static SendMessage sm = new SendMessage();

    public RunCommandsBuilder(List<String> commands, ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
        this.commands = commands;
        this.init();
    }

    public void init() {
        this.commands = this.replaceLoop(commands);
        this.initFinalCommands();
    }

    public List<String> selectRandomCommands(List<String> commands, Integer amount) {
        List<String> commandsList = new ArrayList<>(commands);

        List<String> result = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            if (commandsList.size() == 0) return result;
            int rdn = (int) (Math.random() * commandsList.size());
            result.add(commandsList.get(rdn));
            commandsList.remove(rdn);
        }
        return result;
    }

    public List<String> replaceLoop(List<String> commands) {

        List<String> result = new ArrayList<>();
        boolean isInLoop = false;
        int loopAmount = 0;
        List<String> commandsInLoop = new ArrayList<>();

        for (String s : commands) {

            String command = s;

            if (!command.contains("+++")) {
                if (command.contains("LOOP START: ")) {
                    try {
                        String secondPart = command.split("LOOP START: ")[1].replaceAll(" ", "");
                        if (secondPart.contains("%")) {
                            secondPart = actionInfo.getSp().replacePlaceholder(secondPart, true);
                        }
                        loopAmount = Integer.parseInt(secondPart);
                        isInLoop = true;
                        continue;
                    } catch (Exception e) {
                        loopAmount = 0;
                        isInLoop = false;
                        continue;
                    }
                } else if (command.contains("LOOP END")) {
                    for (int k = 0; k < loopAmount; k++) {
                        result.addAll(commandsInLoop);
                    }
                    loopAmount = 0;
                    isInLoop = false;
                    commandsInLoop.clear();
                    continue;
                }
            } else command = StringConverter.coloredString(s);

            if (isInLoop) commandsInLoop.add(command);
            else result.add(command);
        }
        return result;
    }

    public abstract RunCommand buildRunCommand(Integer delay, String command, ActionInfo aInfo);

    public List<RunCommand> buildRunCommands(Integer delay, List<String> command) {
        List<RunCommand> result = new ArrayList<>();
        for (String s : commands) {
            result.add(this.buildRunCommand(delay, s, actionInfo));
        }
        return result;
    }


    public void inserFinalCommands(Integer delay, String command) {
        RunCommand runCommand = this.buildRunCommand(delay, command, actionInfo);
        if (finalCommands.containsKey(delay)) {
            finalCommands.get(delay).add(runCommand);
        } else {
            List<RunCommand> result = new ArrayList<>(Arrays.asList(runCommand));
            finalCommands.put(delay, result);
        }
    }

    public void inserFinalCommands(Integer delay, List<String> commands) {
        List<RunCommand> runCommands = this.buildRunCommands(delay, commands);
        if (finalCommands.containsKey(delay)) {
            finalCommands.get(delay).addAll(runCommands);
        } else finalCommands.put(delay, runCommands);
    }


    public List<String> replaceNothing(String command) {
        List<String> result = new ArrayList<>();

        if (command.contains("nothing*")) {
            try {
                int m = 0;
                if (command.contains("//")) m = Integer.parseInt(command.split("nothing\\*")[1].split("//")[0].trim());
                else m = Integer.parseInt(command.split("nothing\\*")[1]);

                for (int k = 0; k < m; k++) {
                    if (command.contains("//")) result.add("SENDMESSAGE " + command.split("//")[1]);
                    else result.add("");
                }

            } catch (Exception err) {
                return Collections.singletonList(command);
            }
        } else if (command.contains("NOTHING*")) {
            try {
                int m = 0;
                if (command.contains("//") && !command.contains("https://"))
                    m = Integer.parseInt(command.split("NOTHING\\*")[1].split("//")[0].trim());
                else m = Integer.parseInt(command.split("NOTHING\\*")[1]);

                for (int k = 0; k < m; k++) {
                    if (command.contains("//") && !command.contains("https://"))
                        result.add("SENDMESSAGE " + command.split("//")[1]);
                    else result.add("");
                }
            } catch (Exception err) {
                return Collections.singletonList(command);
            }
        } else return Collections.singletonList(command);
        return result;
    }

    /*
     * Method to select the random commands of RANDOM RUN / RANDOM END
     * */
    public List<String> replaceRandomCommands(List<String> commands) {

        List<String> result = new ArrayList<>();
        List<String> commandsRandom = new ArrayList<>();
        boolean inRandom = false;
        int nbRandom = 0;

        for (String command : commands) {

            if (command.contains("RANDOM RUN:")) {
                String secondPart = command.split("RANDOM RUN:")[1].replaceAll(" ", "");
                if (secondPart.contains("%")) {
                    secondPart = actionInfo.getSp().replacePlaceholder(secondPart, true);
                }
                nbRandom = Integer.parseInt(secondPart);
                inRandom = true;
                continue;
            } else if (command.contains("RANDOM END")) {
                result.addAll(this.selectRandomCommands(commandsRandom, nbRandom));
                inRandom = false;
                commandsRandom.clear();
                nbRandom = 0;
                continue;
            } else if (inRandom) {
                commandsRandom.addAll(this.replaceNothing(command));
                continue;
            } else result.add(command);
        }

        if (commandsRandom.size() > 0) {
            result.addAll(this.selectRandomCommands(commandsRandom, nbRandom));
        }

        return result;
    }

    public List<String> decompMultipleCommandsAndMsg(List<String> commands) {
        List<String> result = new ArrayList<>();

        for (String command : commands) {
            String[] tab;
            if (command.contains("+++")) tab = command.split("\\+\\+\\+");
            else {
                tab = new String[1];
                tab[0] = command;
            }
            for (String s : tab) {
                while (s.startsWith(" ")) {
                    s = s.substring(1);
                }
                while (s.endsWith(" ")) {
                    s = s.substring(0, s.length() - 1);
                }
                if (s.startsWith("/")) s = s.substring(1);
                result.add(s);
            }

            String s = command;
            if (!result.isEmpty()) {
                s = result.get(result.size() - 1);
                result.remove(result.size() - 1);
            }

            if (s.contains("//") && !command.contains("https://")) {
                String[] spliter = s.split("//");

                String commandF = spliter[0];
                result.add(commandF);
                String message = "";
                if (spliter.length >= 2) {
                    if (spliter[1].charAt(0) != ' ') {
                        message = "SENDMESSAGE " + spliter[1];
                    } else message = "SENDMESSAGE" + spliter[1];
                    result.add(message);
                }

            } else result.add(s);
        }
        return result;
    }

    public boolean initFinalCommands() {

        int delay = 0;

        commands = this.replaceRandomCommands(commands);

        commands = this.decompMultipleCommandsAndMsg(commands);

        commands = this.replaceLoop(commands);

        for (String command : commands) {

            if (command.trim().length() == 0) continue;

            /* The delay for AROUND and MOB_AROUND is catch after */
            if (command.contains("DELAYTICK ") && !command.contains("AROUND")) {
                /* Verify that there is no multiple commands after DELAYTICK */
                String delayStr = command;
                if (command.contains("+++")) {
                    String[] tab = command.split("\\+\\+\\+");
                    for (String s : tab) {
                        if (s.contains("DELAYTICK ")) delayStr = s;
                    }
                }
                /* ---------------------- */
                delay = delay + (Integer.parseInt(delayStr.replaceAll("DELAYTICK ", "").replaceAll(" ", "")));
            } else if (command.contains("DELAY ") && !command.contains("AROUND")) {
                /* Verify that there is no multiple commands after DELAY */
                String delayStr = command;
                if (command.contains("+++")) {
                    String[] tab = command.split("\\+\\+\\+");
                    for (String s : tab) {
                        if (s.contains("DELAY ")) delayStr = s;
                    }
                }
                /* ----------------------- */
                delay = delay + (Integer.parseInt(delayStr.replaceAll("DELAY ", "").replaceAll(" ", "")) * 20);
            } else {
                this.inserFinalCommands(delay, command);
            }
        }

        return true;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public static SendMessage getSm() {
        return sm;
    }

    public static void setSm(SendMessage sm) {
        RunCommandsBuilder.sm = sm;
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public HashMap<Integer, List<RunCommand>> getFinalCommands() {
        return finalCommands;
    }

    public void setFinalCommands(HashMap<Integer, List<RunCommand>> finalCommands) {
        this.finalCommands = finalCommands;
    }
}
