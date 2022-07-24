package com.ssomar.score.commands.runnable;

import java.util.List;

public interface CommandManager {

    SCommand getCommand(String brutCommand);

    List<String> getArgs(String command);

}
