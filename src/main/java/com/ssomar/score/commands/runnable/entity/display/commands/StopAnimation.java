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
 * STOP_ANIMATION
 */
public class StopAnimation extends DisplayCommand {

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        Optional<FurniturePlaced> fpOpt = MyFurnitureAPI.getFurniturePlacedManager().getFurniturePlaced(entity);
        if (!fpOpt.isPresent()) return;

        fpOpt.get().stopAnimation();
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("STOP_ANIMATION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "STOP_ANIMATION";
    }

    @Override
    public ChatColor getColor() { return null; }
    @Override
    public ChatColor getExtraColor() { return null; }
    @Override
    public String getWikiLink() { return null; }
}
