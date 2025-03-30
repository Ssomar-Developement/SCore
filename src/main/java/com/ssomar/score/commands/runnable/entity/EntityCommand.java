package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.entity.display.DisplaySCommand;

public abstract class EntityCommand extends SCommand implements EntitySCommand, DisplaySCommand {

    @Override
    public String getWikiLink() {
        return "https://docs.ssomar.com/tools-for-all-plugins-score/custom-commands/entity-commands#" + getNames().get(0).toLowerCase();
    }
}
