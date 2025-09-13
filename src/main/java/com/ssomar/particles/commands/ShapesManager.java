package com.ssomar.particles.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.*;

public class ShapesManager {

    private static ShapesManager instance;

    private Map<Entity, List<ScheduledTask>> runningShapes;

    Map<String, Shape> shapes;

    public ShapesManager() {
        instance = this;
        shapes = new HashMap<>();
        runningShapes = new HashMap<>();
        init();

        // Loop that clears the running shapes every 10 seconds
        Runnable task = () -> {
            Iterator<Entity> iterator = runningShapes.keySet().iterator();
            while (iterator.hasNext()) {
                Entity entity = iterator.next();
                if (entity instanceof Player) {
                    if(!((Player) entity).isOnline()){
                        iterator.remove();
                        continue;
                    }
                    // No more shapes running for this player so remove it from the map
                    if(endedShapes(entity)){
                        iterator.remove();
                    }
                }
                else if(entity.isDead()){
                    iterator.remove();
                }
            }
        };
        // Schedule the task to run every 10 seconds
        SCore.schedulerHook.runAsyncRepeatingTask(task, 0L, 200L);
    }

    public void init() {
        List<String> whitelistedShapes = new ArrayList<>();
        whitelistedShapes.add("atom"); // s  // PROBLEM DOUBLE PARTICLE
        whitelistedShapes.add("atomic");
        whitelistedShapes.add("blackSun");
        whitelistedShapes.add("blackhole");
        whitelistedShapes.add("chaoticDoublePendulum");
        whitelistedShapes.add("circle");
        whitelistedShapes.add("circularBeam");
        whitelistedShapes.add("cone");
        whitelistedShapes.add("crescent");
        whitelistedShapes.add("cylinder"); // old , now based on circle
        whitelistedShapes.add("diamond");
        whitelistedShapes.add("dna"); // s
        whitelistedShapes.add("dnaReplication");
        whitelistedShapes.add("ellipse");
        whitelistedShapes.add("explosionWave"); // s
        whitelistedShapes.add("eye");
        whitelistedShapes.add("filledCircle"); // old , now based on circle

        whitelistedShapes.add("heart");
        whitelistedShapes.add("helix");
        whitelistedShapes.add("illuminati");
        whitelistedShapes.add("magicCircles");
        whitelistedShapes.add("meguminExplosion");
        whitelistedShapes.add("polygon");
        whitelistedShapes.add("rainbow");
        whitelistedShapes.add("ring"); // old , now based on circle
        whitelistedShapes.add("sphere");
        whitelistedShapes.add("spikeSphere");
        whitelistedShapes.add("square"); // new
        whitelistedShapes.add("star");
        whitelistedShapes.add("tesseract");
        whitelistedShapes.add("vortex");


        whitelistedShapes.add("infinity");
        //whitelistedShapes.add("spread");
        // PRBLEM ARGUMENTS + INFINITE LOOP whitelistedShapes.add("lightning");
        //whitelistedShapes.add("drawLine");
        // PROBLEM DOUBLE PARTICLE whitelistedShapes.add("cloud");
        //ROBLEM DOUBLE PARTICLE whitelistedShapes.add("neopaganPentagram");


        Method[] methods = XParticle.class.getDeclaredMethods();
        for (Method method : methods) {
            if (whitelistedShapes.contains(method.getName())) {
                //System.out.println("Loading shape: " + method.getName());
                Shape shape = new Shape(method.getName(), method);
                shapes.put(method.getName(), shape);
            }
        }
    }

    public Optional<Shape> getShape(String shapeName) {
       // SsomarDev.testMsg("Looking for shape: " + shapeName, true);
        /*for(Shape shape : shapes.values()){
            SsomarDev.testMsg("Shape available: " + shape.getName(), true);
        }*/

        // Get shape ingorecase
        for (String name : shapes.keySet()) {
            if (name.equalsIgnoreCase(shapeName)) {
                return Optional.of(shapes.get(name));
            }
        }

        return Optional.empty();
    }

    public List<String> getShapesNames(){
        List<String> shapesNames = new ArrayList<>();
        for(Shape shape : shapes.values()){
            shapesNames.add(shape.getName());
        }
        return shapesNames;
    }

    public static ShapesManager getInstance() {
        if (instance == null) instance = new ShapesManager();
        return instance;
    }

    public void addRunningShape(Entity player, ScheduledTask task) {
        if (!runningShapes.containsKey(player)) {
            runningShapes.put(player, new ArrayList<>());
        }
        runningShapes.get(player).add(task);
    }

    public void clearRunningShapes(Entity player) {
        if (runningShapes.containsKey(player)) {
            for (ScheduledTask task : runningShapes.get(player)) {
                task.cancel();
            }
            runningShapes.remove(player);
        }
    }

    // Check if the tasks are still running, if not remove them from the list
    // true -> The player has no more shapes running and can be removed from the map
    // false -> The player still has shapes running
    public boolean endedShapes(Entity player) {
        if (runningShapes.containsKey(player)) {
            List<ScheduledTask> shapesStillRunning = new ArrayList<>();
            for (ScheduledTask task : runningShapes.get(player)) {
                // Check if the task is still running
                if (task != null && !task.isCancelled()) {
                    shapesStillRunning.add(task);
                }
            }
            // Update the list of running shapes
            if (!shapesStillRunning.isEmpty()) {
                runningShapes.put(player, shapesStillRunning);
                return false;
            }
            // If no shapes are running, remove the player from the map
            // why no runningShapes.remove(player); -> Because it produces ConcurrentModificationException
        }
        return true;
    }
}
