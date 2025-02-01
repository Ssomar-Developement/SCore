package com.ssomar.score.api.executableblocks.config.placed;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.Optional;

public interface ExecutableBlocksPlacedManagerInterface {

    void removeExecutableBlockPlaced(ExecutableBlockPlacedInterface eBP);

    Optional<ExecutableBlockPlacedInterface> getExecutableBlockPlaced(Location location);

    Optional<ExecutableBlockPlacedInterface> getExecutableBlockPlaced(Block block);
}
