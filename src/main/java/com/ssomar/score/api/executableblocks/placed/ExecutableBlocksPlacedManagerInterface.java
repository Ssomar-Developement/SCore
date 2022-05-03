package com.ssomar.score.api.executableblocks.placed;

import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.score.api.executableblocks.config.ExecutableBlockInterface;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExecutableBlocksPlacedManagerInterface {

    /** Get an ExecutableBlockPlaced from its location
     * @param location The location of the potential ExecutableBlockPlaced
     * @return The ExecutableBlockPlaced **/
    Optional<ExecutableBlockPlacedInterface> getExecutableBlockPlaced(Location location);

    /** Get all ExecutableBlockPlaceds present in a specific chunk
     * @param chunk The chunk to get ExecutableBlockPlaceds from
     * @return The list of ExecutableBlockPlaced **/
    List<ExecutableBlockPlacedInterface> getExecutableBlocksPlaced(Chunk chunk);

    /** Get an ExecutableBlockPlaced near a specific location
     * @param location The location of the potential ExecutableBlockPlaced
     * @param distance The maximum distance to search
     * @return The list of ExecutableBlockPlaced **/
    List<ExecutableBlockPlacedInterface> getExecutableBlocksPlacedNear(Location location, double distance);

    /** Get all the ExecutableBlocksPlaced
     * @return The map with all the ExecutableBlocksPlaced **/
    Map<Location, ExecutableBlockPlacedInterface> getAllExecutableBlocksPlaced();
}
