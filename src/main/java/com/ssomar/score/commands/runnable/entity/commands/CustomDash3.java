package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.numbers.NTools;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* ANGRYAT {ENTITYUUID} */
public class CustomDash3 extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        /////////////////////////////////////

        String function = args.get(0);

        double maxX = NTools.getInteger(args.get(1)).get();
        if(maxX <= 0) return;

        int front = 1;
        //if(args.get(3).equalsIgnoreCase("false")) front = -1;
        if(args.get(2).equalsIgnoreCase("false")) front = -1;

        /////////////////////////////////////

        int steps = (int) (maxX * 10);
        final int[] currentStep = {0};
        double stepSize = maxX / steps;

        /////////////////////////////////////

        Integer finalFront = front;

        //if(args.get(2).equalsIgnoreCase("y")) {
        if(true) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (currentStep[0] >= steps) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                    //crear la función con la variable x, ojo con esto porque puede ser posible tener más variables, interesante
                    Expression expression = new ExpressionBuilder(function)
                            .variables("x")
                            .build();

                    double x = currentStep[0] * stepSize;
                    double y = expression.setVariable("x", x).evaluate();

                    double nextX = (currentStep[0] + 1) * stepSize;
                    double nextY = expression.setVariable("x", nextX).evaluate();
                    double yVelocity = (nextY - y) * 2;

                    double yaw = entity.getLocation().getYaw();
                    if (yaw < 0) yaw += 360;
                    yaw = Math.toRadians(yaw);

                    Vector velocity;

                    velocity = new Vector(finalFront *(-stepSize * Math.sin(yaw)), yVelocity, finalFront*(stepSize * Math.cos(yaw)));

                    entity.setVelocity(velocity);

                    currentStep[0]++;
                }
            }.runTaskTimer(SCore.plugin, 1L, 1L);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac1 = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac1.isValid()) return Optional.of(ac1.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CUSTOMDASH3");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CUSTOMDASH3 {function} {max x value} {front z default TRUE}";
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
