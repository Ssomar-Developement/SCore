package com.ssomar.score.commands.runnable.item;

import com.ssomar.score.commands.runnable.SCommand;

public abstract class ItemMetaCommand extends SCommand implements ItemMetaSCommand {

    @Override
    public String getWikiLink() {
        return "https://docs.ssomar.com/tools-for-all-plugins-score/custom-commands/item-commands#" + getNames().get(0).toLowerCase();
    }

}
