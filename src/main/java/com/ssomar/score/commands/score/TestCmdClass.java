package com.ssomar.score.commands.score;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the execution of /score.
 */
@RequiredArgsConstructor
public final class TestCmdClass implements CommandExecutor, TabExecutor {



    @Override
    public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {

        final List<String> arguments = new ArrayList<>();
        //SsomarDev.testMsg("Test command executed!", true);

        if (command.getName().equalsIgnoreCase("/")) {

            if(!(sender instanceof Player)) return arguments;
            Player player = (Player) sender;
            PlayerInventory inv = player.getInventory();
            if(inv.getItemInMainHand().getType() != Material.STONE_SWORD) return arguments;

            if (args.length == 1) {
                arguments.add("omni");
            }
            if (args.length == 2) {
                arguments.add("pick");
                arguments.add("rod");
                arguments.add("sword");
            }

            return arguments;
        }

        return arguments;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return true;
    }
}