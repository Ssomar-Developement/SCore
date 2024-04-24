package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.safebreak.SafeBreak;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/* SMELT */
public class Smelt extends BlockCommand {

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {


        UUID pUUID = null;
        if (p != null) pUUID = p.getUniqueId();

        boolean event = true;
        if (args.size() >= 1) {
            event = Boolean.parseBoolean(args.get(0));
        }
        // if its from an event generated
        if(event && aInfo.isEventFromCustomBreakCommand()) return;

        if (SafeBreak.verifSafeBreak(pUUID, block)) {
            ItemStack itemInHand = p.getInventory().getItemInMainHand();

            ItemStack smelted = getSmeltedItem(block.getType());
            if (smelted == null) return;

            // Determine the amount of smelted item to drop
            int amountToDrop = 1;
            Enchantment fortune = SCore.is1v20v5Plus() ? Enchantment.FORTUNE : Enchantment.getByName("LOOT_BONUS_BLOCKS");
            if (itemInHand.containsEnchantment(fortune)) {
                int fortuneLevel = itemInHand.getEnchantmentLevel(fortune);
                amountToDrop = ThreadLocalRandom.current().nextInt(fortuneLevel + 1) + 1;
            }
            // BREAK
            SafeBreak.breakBlockWithEvent(block, pUUID, aInfo.getSlot(), false, event, false);

            // Drop the smelted item
            Location dropLocation = block.getLocation();
            block.getWorld().dropItemNaturally(dropLocation, new ItemStack(smelted.getType(), amountToDrop));
        }
    }

    public ItemStack getSmeltedItem(Material material) {
        ItemStack result = null;
        switch (material) {
            case ACACIA_LOG:
            case BIRCH_LOG:
            case DARK_OAK_LOG:
            case JUNGLE_LOG:
            case OAK_LOG:
            case SPRUCE_LOG:
            case ACACIA_WOOD:
            case BIRCH_WOOD:
            case DARK_OAK_WOOD:
            case JUNGLE_WOOD:
            case OAK_WOOD:
            case SPRUCE_WOOD:
                result = new ItemStack(Material.CHARCOAL);
                break;
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                result = new ItemStack(Material.COAL);
                break;
            case NETHERRACK:
                result = new ItemStack(Material.NETHER_BRICK);
                break;
            case SAND:
                result = new ItemStack(Material.GLASS);
                break;
            case COBBLESTONE:
                result = new ItemStack(Material.STONE);
                break;
            case CLAY:
                result = new ItemStack(Material.TERRACOTTA);
                break;
            case GOLD_ORE:
            case RAW_GOLD:
            case DEEPSLATE_GOLD_ORE:
            case RAW_GOLD_BLOCK:
                result = new ItemStack(Material.GOLD_INGOT);
                break;
            case IRON_ORE:
            case RAW_IRON:
            case DEEPSLATE_IRON_ORE:
            case RAW_IRON_BLOCK:
                result = new ItemStack(Material.IRON_INGOT);
                break;
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                result = new ItemStack(Material.LAPIS_LAZULI);
                break;
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                result = new ItemStack(Material.REDSTONE);
                break;
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                result = new ItemStack(Material.DIAMOND);
                break;
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                result = new ItemStack(Material.EMERALD);
                break;
            case CACTUS:
                result = new ItemStack(Material.GREEN_DYE);
                break;
            case BAMBOO:
                result = new ItemStack(Material.BAMBOO);
                break;
            case SEA_PICKLE:
                result = new ItemStack(Material.LIME_DYE);
                break;
            case KELP:
                result = new ItemStack(Material.DRIED_KELP);
                break;
            case RED_SAND:
                result = new ItemStack(Material.RED_STAINED_GLASS);
                break;
            case OBSIDIAN:
                result = new ItemStack(Material.CRYING_OBSIDIAN);
                break;
            case GRAVEL:
                result = new ItemStack(Material.FLINT);
                break;
            case SNOW_BLOCK:
                result = new ItemStack(Material.SNOWBALL);
                break;
            case WARPED_STEM:
            case STRIPPED_WARPED_STEM:
            case WARPED_HYPHAE:
            case STRIPPED_WARPED_HYPHAE:
                result = new ItemStack(Material.WARPED_WART_BLOCK);
                break;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
                result = new ItemStack(Material.COPPER_INGOT);
                break;
            case POTATOES:
                result = new ItemStack(Material.BAKED_POTATO);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SMELT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SMELT [generateEvent true or false]";
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
