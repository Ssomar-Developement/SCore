package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.safeplace.SafePlace;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/* BURN {timeinsecs} */
public class CropsGrowthBoost extends PlayerCommand {

    private static CropsGrowthBoost instance;


    public CropsGrowthBoost() {
    }

    public static CropsGrowthBoost getInstance() {
        if (instance == null) instance = new CropsGrowthBoost();
        return instance;
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        int radius = Double.valueOf(args.get(0)).intValue();
        int delay = Double.valueOf(args.get(1)).intValue();
        int duration = Double.valueOf(args.get(2)).intValue();
        int chance = Double.valueOf(args.get(3)).intValue();
        if (chance > 100) chance = 100;
        if (chance < 0) chance = 0;
        final int finalChance = chance;

        final int[] i = {0};

        AtomicReference<ScheduledTask> task = new AtomicReference<>();

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                if (i[0] >= duration) {
                    task.get().cancel();
                } else {
                    grownAgeableBlocks(receiver.getLocation(), radius, finalChance, receiver);
                    i[0] = i[0] + delay;
                }
            }
        };
        task.set(SCore.schedulerHook.runRepeatingTask(runnable3, 0L, delay));
    }

    public void grownAgeableBlocks(Location start, int radius, int finalChance, Player receiver) {
        for (double x = start.getX() - radius; x <= start.getX() + radius; x++) {
            for (double y = start.getY() - radius; y <= start.getY() + radius; y++) {
                for (double z = start.getZ() - radius; z <= start.getZ() + radius; z++) {
                    Location loc = new Location(start.getWorld(), x, y, z);
                    Block block = loc.getBlock();
                    BlockData data = block.getBlockData();
                    if (!(data instanceof Ageable)) continue;

                    int random = new Random().nextInt(100);
                    //SsomarDev.testMsg(random +" " + finalChance);
                    if (random <= finalChance) {
                        if (receiver != null && (receiver.isOp() || SafePlace.verifSafePlace(receiver.getUniqueId(), block))) {
                            Ageable ageable = (Ageable) data;
                            if (ageable.getAge() < ageable.getMaximumAge()) {
                                ageable.setAge(ageable.getAge() + 1);
                                block.setBlockData(ageable);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 4) return Optional.of(notEnoughArgs + getTemplate());
        for (String arg : args) {
            ArgumentChecker ac = checkInteger(arg, isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CROPS_GROWTH_BOOST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CROPS_GROWTH_BOOST {radius} {delay between two growths in ticks} {total duration in ticks} {chance 0-100}";
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
