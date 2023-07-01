package com.ssomar.score.features.custom.conditions.customei;


import com.ssomar.score.features.custom.conditions.ConditionRequest;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Getter
@Setter
public class CustomEIConditionRequest extends ConditionRequest {

    private Player player;
    private ItemStack itemStack;
    private Optional<Player> playerOpt;

    private StringPlaceholder sp;

    public CustomEIConditionRequest(@NotNull Player player, @NotNull ItemStack itemStack, @NotNull Optional<Player> playerOpt, @Nullable StringPlaceholder sp, @Nullable Event event) {
        super(event);
        this.player = player;
        this.itemStack = itemStack;
        this.playerOpt = playerOpt;
        if (sp == null) this.sp = new StringPlaceholder();
        else this.sp = sp;
    }
}
