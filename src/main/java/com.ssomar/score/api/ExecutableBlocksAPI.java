package com.ssomar.score.api;

import com.ssomar.executableblocks.executableblocks.NewExecutableBlock;
import com.ssomar.executableblocks.executableblocks.manager.NewExecutableBlocksManager;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

@Deprecated
public class ExecutableBlocksAPI {

    /* Verify if id is a valid ExecutableBlock ID*/
    @Deprecated
    public static boolean isValidID(String id) {
        return NewExecutableBlocksManager.getInstance().getLoadedObjectWithID(id).isPresent();
    }

    @Deprecated
    public static ItemStack getExecutableBlock(String id) {
        Optional<NewExecutableBlock> oOpt = NewExecutableBlocksManager.getInstance().getLoadedObjectWithID(id);
        if (oOpt.isPresent()) {
            return oOpt.get().buildItem(1, Optional.empty());
        } else return null;
    }
}
