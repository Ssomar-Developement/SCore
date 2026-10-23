package com.ssomar.score.commands.runnable.entity.display.commands;

import com.ssomar.myfurniture.api.MyFurnitureAPI;
import com.ssomar.myfurniture.furniture.placedfurniture.FurniturePlaced;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.display.DisplayCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * RUN_ANIMATION {bbmodel_file} [animation_name_or_index]
 */
public class RunAnimation extends DisplayCommand {

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        if (args.isEmpty()) return;

        Optional<FurniturePlaced> fpOpt = MyFurnitureAPI.getFurniturePlacedManager().getFurniturePlaced(entity);
        if (!fpOpt.isPresent()) return;

        String fileName = args.get(0);
        String animName = args.size() >= 2 ? args.get(1) : null;

        fpOpt.get().runAnimation(fileName, animName);
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
