package com.ssomar.score.utils.display;

import com.ssomar.score.SCore;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TryDisplayModule  extends DisplayModule{
    public TryDisplayModule() {
        super(SCore.plugin, DisplayPriority.HIGH);
    }

    public boolean display(@NotNull ItemStack itemStack, @Nullable Player player,  @NotNull Object... args) {
        /* ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            System.out.println("TryDisplayModule display");
            meta.setDisplayName("§c§lTry >> "+player.getName());
            itemStack.setItemMeta(meta);
        }*/

        return false;
    }
}
