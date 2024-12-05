package com.ssomar.score.features;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class FeatureForBlockArgs {

    private @NotNull BlockData data;

    private @Nullable BlockState blockState;

    private @Nullable Material material;

    public FeatureForBlockArgs(BlockData data, BlockState blockState, Material material) {
        this.data = data;
        this.blockState = blockState;
        this.material = material;
    }

    public static FeatureForBlockArgs create(BlockData data, BlockState state, Material material) {
        return new FeatureForBlockArgs(data, state, material);
    }

    public static FeatureForBlockArgs create(BlockData data) {
        return new FeatureForBlockArgs(data, null,null);
    }
}
