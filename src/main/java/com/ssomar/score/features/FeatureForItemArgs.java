package com.ssomar.score.features;

import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

@Getter
public class FeatureForItemArgs {

    @Setter
    private @Nullable ItemStack item;

    private @Nullable ItemMeta meta;

    private @Nullable Material material;

    @Setter
    private Optional<UUID> ownerUUID = Optional.empty();

    @Setter
    private Optional<StringPlaceholder> stringPlaceholder = Optional.empty();

    public FeatureForItemArgs(ItemMeta meta, Material material) {
        this.meta = meta;
        this.material = material;
    }

    public FeatureForItemArgs(ItemStack item) {
        this.item = item;
    }


    public static FeatureForItemArgs create(ItemMeta meta, Material material) {
        return new FeatureForItemArgs(meta, material);
    }

    public static FeatureForItemArgs create(ItemMeta meta) {
        return new FeatureForItemArgs(meta, null);
    }

    public static FeatureForItemArgs create(ItemStack item) {
        return new FeatureForItemArgs(item);
    }

    public static FeatureForItemArgs create(ItemMeta meta, Material material, UUID ownerUUID, StringPlaceholder sp) {
        FeatureForItemArgs args = new FeatureForItemArgs(meta, material);
        args.setOwnerUUID(Optional.ofNullable(ownerUUID));
        args.setStringPlaceholder(Optional.ofNullable(sp));
        return args;
    }

    public static FeatureForItemArgs create(ItemStack item, UUID ownerUUID, StringPlaceholder sp) {
        FeatureForItemArgs args = new FeatureForItemArgs(item);
        args.setOwnerUUID(Optional.ofNullable(ownerUUID));
        args.setStringPlaceholder(Optional.ofNullable(sp));
        return args;
    }
}
