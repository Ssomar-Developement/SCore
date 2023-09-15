package com.ssomar.score.linkedplugins;

import com.ssomar.executableblocks.api.ExecutableBlocksAPI;
import com.ssomar.score.SCore;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.sobject.NewSObject;
import com.ssomar.score.splugin.SPlugin;
import org.jetbrains.annotations.Nullable;

public class LinkedPlugins {

    @Nullable
    public static NewSObject getSObject(SPlugin sPlugin, String objectID) {

        switch (sPlugin.getName().toUpperCase()) {

            // TODO: Add  plugins here
            case "EXECUTABLEBLOCKS":
                if (SCore.hasExecutableBlocks) {
                    return (NewSObject) ExecutableBlocksAPI.getExecutableBlocksManager().getExecutableBlock(objectID).get();
                }
                break;

            case "EXECUTABLEITEMS":
                if (SCore.hasExecutableItems) {
                    return (NewSObject) ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(objectID).get();
                }
                break;

            default:
                return null;
        }

        return null;
    }
}
