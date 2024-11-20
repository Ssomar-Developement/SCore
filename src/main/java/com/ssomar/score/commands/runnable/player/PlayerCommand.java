package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.commands.runnable.SCommand;

public abstract class PlayerCommand extends SCommand implements PlayerSCommand {

    @Override
    public String getWikiLink() {
        return "https://docs.ssomar.com/tools-for-all-plugins-score/custom-commands/player-and-target-commands#" + getNames().get(0).toLowerCase();
    }

}
