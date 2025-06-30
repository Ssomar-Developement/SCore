package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HitscanEntities extends MixedCommand {


    public HitscanEntities() {
        CommandSetting range = new CommandSetting("range", -1, Double.class, 5d);
        CommandSetting radius = new CommandSetting("radius", -1, Double.class, 0d);
        CommandSetting pitch = new CommandSetting("pitch", -1, Double.class, 0d);
        CommandSetting yaw = new CommandSetting("yaw", -1, Double.class, 0d);
        CommandSetting leftRightShift = new CommandSetting("leftRightShift", -1, Double.class, 0d);
        CommandSetting yShift = new CommandSetting("yShift", -1, Double.class, 0d);
        CommandSetting throughBlocks = new CommandSetting("throughBlocks", -1, Boolean.class, true);
        CommandSetting throughEntities = new CommandSetting("throughEntities", -1, Boolean.class, true);
        CommandSetting limit = new CommandSetting("limit", -1, Integer.class, -1);
        CommandSetting sort = new CommandSetting("sort", -1, String.class, "NEAREST");
        List<CommandSetting> settings = getSettings();
        settings.add(range);
        settings.add(radius);
        settings.add(pitch);
        settings.add(yaw);
        settings.add(leftRightShift);
        settings.add(yShift);
        settings.add(throughEntities);
        settings.add(throughBlocks);
        settings.add(limit);
        settings.add(sort);
        setNewSettingsMode(true);
        setCanExecuteCommands(true);
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        List<Entity> entities = runHitscan(receiver, sCommandToExec, false);

        String sort = (String) sCommandToExec.getSettingValue("sort");
        int limit = (int) sCommandToExec.getSettingValue("limit");

        if (sort.equalsIgnoreCase("NEAREST")) {
            entities.sort((e1, e2) -> {
                double d1 = e1.getLocation().distanceSquared(receiver.getLocation());
                double d2 = e2.getLocation().distanceSquared(receiver.getLocation());
                return Double.compare(d1, d2);
            });
        } else if (sort.equalsIgnoreCase("RANDOM")) {
            Collections.shuffle(entities);
        }

        if (limit > 0 && entities.size() > limit) {
            entities = entities.subList(0, limit);
        }

        if (sCommandToExec.getOtherArgs().stream().anyMatch(s -> s.contains("%hitscan_target_uuid_"))) {
            List<String> commands = new ArrayList<>(sCommandToExec.getOtherArgs());
            for (int i = 0; i < entities.size(); i++) {
                for (int j = 0; j < commands.size(); j++) {
                    commands.set(j, commands.get(j).replace("%hitscan_target_uuid_" + i + "%", entities.get(i).getUniqueId().toString()));
                }
            }
            CommmandThatRunsCommand.runEntityCommands(new ArrayList<>(), commands, sCommandToExec.getActionInfo());
        } else {
            CommmandThatRunsCommand.runEntityCommands(entities, sCommandToExec.getOtherArgs(), sCommandToExec.getActionInfo());
        }
    }

    public static List<Entity> runHitscan(Entity receiver, SCommandToExec sCommandToExec, boolean playerOnly){
        List<Entity> entities = new ArrayList<>();

        double range = (double) sCommandToExec.getSettingValue("range");
        double radius = (double) sCommandToExec.getSettingValue("radius");
        double pitch = (double) sCommandToExec.getSettingValue("pitch");
        double yaw = (double) sCommandToExec.getSettingValue("yaw");
        double leftRightShift = (double) sCommandToExec.getSettingValue("leftRightShift");
        double yShift = (double) sCommandToExec.getSettingValue("yShift");
        boolean throughBlocks = (boolean) sCommandToExec.getSettingValue("throughBlocks");
        boolean throughEntities = (boolean) sCommandToExec.getSettingValue("throughEntities");

        if (receiver instanceof LivingEntity) {

            LivingEntity fromEntity = (LivingEntity) receiver;
            // 1. Initialize the Hitscan Start Location
            Location startLocation = fromEntity.getEyeLocation().clone();
            startLocation.setPitch((float) (startLocation.getPitch() + pitch));
            startLocation.setYaw((float) (startLocation.getYaw() + yaw));
            Vector offset = calculateOffset(fromEntity, leftRightShift, yShift);
            startLocation.add(offset);

            // 2. Get All Entities Around This Location Within the Specified Range
            List<Entity> nearbyEntities = (List<Entity>) startLocation.getWorld().getNearbyEntities(startLocation, range, range, range);

            for (Entity entity : nearbyEntities) {
                if(entity == receiver) continue;
                if ((playerOnly && !(entity instanceof Player))
                        || (!playerOnly && (entity instanceof Player))
                        || entity.equals(fromEntity)
                        || !(entity instanceof LivingEntity))
                    continue; // Skip players and the shooter
                //SsomarDev.testMsg("CHECK ENTITY > " + entity.getType(), true);

                // 3. Verify That the Player Is in the Hitscan Cylinder
                Location hitPosition = null;
                if ((hitPosition = isWithinCylinder(startLocation, entity, range, radius)) == null) {
                    SsomarDev.testMsg("ENTITY > " + entity.getType() + " NOT IN CYLINDER", true);
                    continue;
                }

                SsomarDev.testMsg("CHECK ENTITY > " + entity.getName(), true);

                // 4. Raytrace Blocks and Entities
                boolean hitSuccess = performRaytraceChecks(fromEntity, startLocation, hitPosition, (LivingEntity) entity, range, radius, throughBlocks, throughEntities);

                // 5. Verify That a Raytrace Exists Without Hitting a Block (Null Raytrace)
                if (hitSuccess) {
                    SsomarDev.testMsg("YESSS HITSCAN > " + entity.getType(), true);
                    entities.add(entity);
                }
            }
        }
        return entities;
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {


        return Optional.empty();
    }

    private static Vector calculateOffset(LivingEntity fromEntity, double leftRightDisplacement, double yDisplacement) {
        Vector offset = new Vector();

        // Calculate the left/right displacement
        Vector rightDirection = fromEntity.getLocation().getDirection().crossProduct(new Vector(0, 1, 0)).normalize();
        offset.add(rightDirection.multiply(leftRightDisplacement));

        // Calculate the Y displacement (vertical)
        offset.add(new Vector(0, yDisplacement, 0));

        return offset;
    }

    private static Location isWithinCylinder(Location start, Entity target, double range, double radius) {
        Vector direction = start.getDirection().normalize();

        RayTraceResult rayTraceResult = start.getWorld().rayTraceEntities(start, direction, range, radius, entity -> entity.equals(target));

        if (rayTraceResult != null) return rayTraceResult.getHitPosition().toLocation(start.getWorld());
        else return null;
    }

    private static boolean isWithinCylinder(Location start, Location target, double range, double radius) {
        Vector direction = start.getDirection().normalize();
        Vector toTarget = target.toVector().subtract(start.toVector());
        double distanceAlongAxis = toTarget.dot(direction);

        if (distanceAlongAxis < 0 || distanceAlongAxis > range) {
            return false; // Outside the range
        }

        Vector closestPoint = direction.multiply(distanceAlongAxis).add(start.toVector());
        double distanceToAxis = closestPoint.distance(target.toVector());

        return distanceToAxis <= radius;
    }


    private static boolean performRaytraceChecks(LivingEntity fromEntity, Location startLocation, Location hitPosition, LivingEntity target, double range, double radius, boolean throughBlocks, boolean throughEntities) {

        if (throughBlocks && throughEntities) return true;

        Location shooterEyeLocation = startLocation;
        Location targetBodyLocation = target.getLocation();

        double height = target.getHeight();
        double heightDic = height / 4;
        //SsomarDev.testMsg("heightdiv : " + heightDic, true);

        // Perform several raytraces between the shooter and the target's body
        List<Location> targetLocs = new ArrayList<>();
        if (radius <= heightDic){
            SsomarDev.testMsg("hitpost: "+hitPosition, true);
            targetLocs.add(hitPosition);
        }
        targetLocs.add(targetBodyLocation.clone());
        targetLocs.add(targetBodyLocation.clone().add(0, heightDic * 1, 0));
        targetLocs.add(targetBodyLocation.clone().add(0, heightDic * 2, 0));
        targetLocs.add(targetBodyLocation.clone().add(0, heightDic * 3, 0));
        targetLocs.add(targetBodyLocation.clone().add(0, heightDic * 4, 0));


        for (Location targetLoc : targetLocs) {

            if (!isWithinCylinder(startLocation, targetLoc.clone(), range, radius)) continue;

            Vector dir = targetLoc.clone().subtract(startLocation).toVector().normalize();

            RayTraceResult rayTraceResult = null;
            if (!throughBlocks && !throughEntities) {
                rayTraceResult = startLocation.getWorld().rayTrace(
                        shooterEyeLocation, dir, shooterEyeLocation.distance(targetBodyLocation),
                        FluidCollisionMode.NEVER, // Ignore fluids
                        true,  // Consider entities
                        0.1,   // Hitbox margin
                        entity -> !entity.equals(fromEntity) && !entity.equals(target) // Exclude shooter
                );
            } else if (throughBlocks) {
                SsomarDev.testMsg("TEST throughBlocks tEntity > "+target.getType()+" from > "+fromEntity+" eye > "+shooterEyeLocation+" dir > "+dir+" exclude > "+target, true);
                double distance = shooterEyeLocation.distance(targetBodyLocation);
                rayTraceResult = startLocation.getWorld().rayTraceEntities(
                        shooterEyeLocation, dir, distance, entity -> !entity.equals(fromEntity) && !entity.equals(target) // Exclude  & target
                );
            } else  {
                rayTraceResult = fromEntity.getWorld().rayTraceBlocks(
                        shooterEyeLocation, dir, shooterEyeLocation.distance(targetBodyLocation),
                        FluidCollisionMode.NEVER
                );
            }

            if (rayTraceResult == null) {
                SsomarDev.testMsg("entity : "+target.getType()+ " loc "+targetLoc, true);
                return true; // Found a successful hit or a null raytrace
            }
        }

        return false; // All raytraces failed
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("HITSCAN_ENTITIES");
        return names;
    }

    @Override
    public String getTemplate() {
        return "HITSCAN_ENTITIES range:5 radius:0 pitch:0 yaw:0 leftRightShift:0 yShift:0 throughBlocks:true throughEntities:true limit:-1 sort:NEAREST COMMANDS HERE";
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

