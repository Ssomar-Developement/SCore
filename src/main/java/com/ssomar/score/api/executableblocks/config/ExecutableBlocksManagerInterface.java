package com.ssomar.score.api.executableblocks.config;

import java.util.List;
import java.util.Optional;

public interface ExecutableBlocksManagerInterface {

    /**
     * Verify if id is a valid ExecutableBlock ID
     *
     * @param id The ID to verify
     * @return true if it is a valid ID, false otherwise
     **/
    boolean isValidID(String id);

    /**
     * Get an ExecutableBlock from its ID
     *
     * @param id The ID of the ExecutableBlock
     * @return The ExecutableBlock
     **/
    Optional<ExecutableBlockInterface> getExecutableBlock(String id);

    /**
     * Get all ExecutableBlocks Ids
     *
     * @return All ExecutableBlocks ids
     **/
    List<String> getExecutableBlockIdsList();
}
