package com.ssomar.score.commands.runnable.entity.display.commands;

import com.ssomar.myfurniture.features.animation.AnimationInstance;
import com.ssomar.myfurniture.features.animation.FurnitureAnimationManager;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.display.DisplayCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * STOP_ANIMATION
 *
 * Stops the animation running on the placed furniture and removes the bone entities.
 * Matches the animation started by RUN_ANIMATION on the same entity.
 */
public class StopAnimation extends DisplayCommand {

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        UUID animId = entity.getUniqueId();
        AnimationInstance instance = FurnitureAnimationManager.getInstance().get(animId);
        if (instance != null) {
            instance.restoreFurnitureEntity();
        }
        FurnitureAnimationManager.getInstance().removeAndUnregister(animId);
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
