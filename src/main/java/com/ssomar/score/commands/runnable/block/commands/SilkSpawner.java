package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.strings.StringConverter;
import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedSpawner;
import dev.rosewood.rosestacker.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SilkSpawner extends BlockCommand {

    @Override
    public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {


        if(SCore.hasRoseStacker && args.size() == 1 && args.get(0).equalsIgnoreCase("RoseStacker")){
            StackedSpawner sp =  RoseStackerAPI.getInstance().getStackedSpawner(block);
            if(sp == null) return;
            ItemStack item = ItemUtils.getSpawnerAsStackedItemStack(sp.getSpawnerTile().getSpawnerType(), sp.getStackSize());
            sp.setStackSize(0);
            RoseStackerAPI.getInstance().removeSpawnerStack(sp);
            block.setType(Material.AIR);
            if(p != null) p.getInventory().addItem(item);
        }
        else {
            Material spawer;
            if (SCore.is1v12Less()) {
                spawer = Material.valueOf("MOB_SPAWNER");
            } else spawer = Material.SPAWNER;

            if (block.getType().equals(spawer)) {
                CreatureSpawner cs = (CreatureSpawner) block.getState();
                ItemStack spawner_to_give = new ItemStack(spawer);
                BlockStateMeta meta = (BlockStateMeta) spawner_to_give.getItemMeta();
                CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();
                csm.setSpawnedType(cs.getSpawnedType());
                if(csm.getSpawnedType() != null) {
                    String name = csm.getSpawnedType().toString().replace("_", " ");
                    name = name.toLowerCase();
                    name = (name.charAt(0) + "").toUpperCase() + name.substring(1, name.length());
                    name = "&e" + name;
                    name = StringConverter.coloredString(name);
                    meta.setDisplayName(name);
                    meta.setLocalizedName("FROM_EXECUTABLEITEM");
                }
                meta.setBlockState((BlockState) csm);
                meta.addItemFlags(new org.bukkit.inventory.ItemFlag[0]);
                spawner_to_give.setItemMeta((ItemMeta) meta);

                block.setType(Material.AIR);
                p.getInventory().addItem(spawner_to_give);
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
       return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SILK_SPAWNER");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SILK_SPAWNER [RoseStacker]";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

}
