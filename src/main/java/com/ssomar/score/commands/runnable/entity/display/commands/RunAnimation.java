package com.ssomar.score.commands.runnable.entity.display.commands;

import com.ssomar.myfurniture.api.MyFurnitureAPI;
import com.ssomar.myfurniture.features.animation.*;
import com.ssomar.myfurniture.furniture.placedfurniture.FurniturePlaced;
import com.ssomar.myfurniture.furniture.placedfurniture.FurniturePlaced;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.display.DisplayCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

/**
 * RUN_ANIMATION {bbmodel_file} [animation_name_or_index]
 *
 * Spawns animated bone entities around the placed furniture and starts playback.
 * The bbmodel file is loaded from plugins/MyFurniture/animations/
 * Resource pack models are auto-generated if needed.
 */
public class RunAnimation extends DisplayCommand {

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        if (args.isEmpty()) return;

        String fileName = args.get(0);
        if (!fileName.endsWith(".bbmodel")) fileName += ".bbmodel";

        File animDir = new File(com.ssomar.myfurniture.MyFurniture.plugin.getDataFolder(), "animations");
        File bbmodelFile = new File(animDir, fileName);
        if (!bbmodelFile.exists()) return;

        try {
            BBModelParser parser = new BBModelParser();
            parser.parse(bbmodelFile);

            if (parser.getAnimations().isEmpty()) return;

            // Find animation by name or index
            int animIndex = 0;
            if (args.size() >= 2) {
                String animArg = args.get(1);
                try {
                    animIndex = Integer.parseInt(animArg);
                } catch (NumberFormatException e) {
                    for (int i = 0; i < parser.getAnimations().size(); i++) {
                        if (parser.getAnimations().get(i).getName().equalsIgnoreCase(animArg)) {
                            animIndex = i;
                            break;
                        }
                    }
                }
            }

            // Use the furniture's entity UUID as the animation instance ID
            // so we can stop it later
            UUID animId = entity.getUniqueId();

            // Stop existing animation on this entity if any
            FurnitureAnimationManager.getInstance().removeAndUnregister(animId);

            // Hide the furniture for all online players
            Optional<FurniturePlaced> fpOpt = MyFurnitureAPI.getFurniturePlacedManager().getFurniturePlaced(entity);
            if (fpOpt.isPresent()) {
                for (org.bukkit.entity.Player online : org.bukkit.Bukkit.getOnlinePlayers()) {
                    fpOpt.get().hideFurniture(online);
                }
            }

            // Spawn animation at the furniture's location
            AnimationInstance instance = BBModelSpawner.spawnAndAnimate(
                    parser, entity.getLocation(), animIndex, "myfurniture", bbmodelFile);

            if (instance != null) {
                instance.setFurniturePlaced(fpOpt.orElse(null));
                FurnitureAnimationManager.getInstance().register(animId, instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.isEmpty()) return Optional.of("Missing bbmodel file name");
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("RUN_ANIMATION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "RUN_ANIMATION {file.bbmodel} [animation_name]";
    }

    @Override
    public ChatColor getColor() { return null; }

    @Override
    public ChatColor getExtraColor() { return null; }

    @Override
    public String getWikiLink() { return null; }
}
