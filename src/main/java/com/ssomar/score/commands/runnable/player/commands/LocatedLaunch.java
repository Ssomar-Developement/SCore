package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableitems.listeners.projectiles.ProjectileInfo;
import com.ssomar.executableitems.listeners.projectiles.ProjectilesHandler;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.PlayerCustomLaunchEntityEvent;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.projectiles.SProjectileType;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* LAUNCH {projectileType} */

@SuppressWarnings("deprecation")
public class LocatedLaunch extends PlayerCommand {

    //OCATED_LAUNCH {projectileType} {frontValue positive=front , negative=back} {rightValue right=positive, negative=left} {yValue} {velocity} [vertical rotation] [horizontal rotation]";
    //    }

    public LocatedLaunch() {
        CommandSetting projectileType = new CommandSetting("projectile", 0, String.class, null);
        CommandSetting frontValue = new CommandSetting("frontValue", 1, Double.class, 0.0);
        CommandSetting rightValue = new CommandSetting("rightValue", 2, Double.class, 0.0);
        CommandSetting yValue = new CommandSetting("yValue", 3, Double.class, 0.0);
        CommandSetting velocity = new CommandSetting("velocity", 4, Double.class, 1.0);
        CommandSetting angleRotationVertical = new CommandSetting("angleRotationVertical", 5, Double.class, 0.0);
        CommandSetting angleRotationHorizontal = new CommandSetting("angleRotationHorizontal", 6, Double.class, 0.0);
        List<CommandSetting> settings = getSettings();
        settings.add(projectileType);
        settings.add(frontValue);
        settings.add(rightValue);
        settings.add(yValue);
        settings.add(velocity);
        settings.add(angleRotationVertical);
        settings.add(angleRotationHorizontal);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ActionInfo aInfo = sCommandToExec.getActionInfo();

        /* postivite value = front , negative = back */
        double frontValue = (double) sCommandToExec.getSettingValue("frontValue");

        /* postivite value = right , negative = left */
        double rightValue = (double) sCommandToExec.getSettingValue("rightValue");

        double yValue = (double) sCommandToExec.getSettingValue("yValue");

        double velocity = (double) sCommandToExec.getSettingValue("velocity");

        double rotationVertical = (double) sCommandToExec.getSettingValue("angleRotationVertical");
        double rotationHorizontal = (double) sCommandToExec.getSettingValue("angleRotationHorizontal");

        Location eyeLoc = receiver.getEyeLocation();
        Vector eyeDir = eyeLoc.getDirection();
        Vector front = eyeDir.clone().multiply(frontValue);
        Vector right = new Vector(0, 1, 0).crossProduct(eyeDir).normalize().multiply(rightValue);
        Vector calcul = front.add(right);

        Location recLoc = receiver.getLocation();
        double newX = recLoc.getX() + calcul.getX();
        double newY = recLoc.getY() + calcul.getY();
        double newZ = recLoc.getZ() + calcul.getZ();
        Location toLaunchLoc = new Location(recLoc.getWorld(), newX, newY, newZ);
        toLaunchLoc.add(0, yValue, 0);
        //SsomarDev.testMsg("x: "+newX+" y: "+newY+" z: "+newZ);


        Projectile entity = null;
        String type = (String) sCommandToExec.getSettingValue("projectile");
        Optional<SProjectile> projectileOptional = null;
        SProjectile projectile = null;
        if (SProjectileType.getProjectilesClasses().containsKey(type)) {
            entity = (Projectile) recLoc.getWorld().spawn(toLaunchLoc, SProjectileType.getProjectilesClasses().get(type));
            // Set projectile eye direction
            entity.setVelocity(eyeLoc.getDirection());
        } else if ((projectileOptional = SProjectilesManager.getInstance().getLoadedObjectWithID(type)).isPresent()) {
            projectile = projectileOptional.get();
            entity = (Projectile) recLoc.getWorld().spawn(toLaunchLoc, SProjectileType.getProjectilesClasses().get(projectile.getType().getValue().get().getValidNames()[0]));
            projectile.transformTheProjectile(entity, receiver, projectile.getType().getValue().get().getMaterial());
        } else entity = recLoc.getWorld().spawn(toLaunchLoc, Arrow.class);


        // Set projectile shooter
        entity.setShooter(receiver);


        // Rotate the velocity of the projectile vertically
        Vector vector = entity.getVelocity().rotateAroundAxis(entity.getVelocity().getCrossProduct(new Vector(0, 1, 0)), rotationVertical * Math.PI / 180);

        // Rotate the velocity of the projectile horizontally depending on the player's head rotation
        vector = vector.rotateAroundAxis(getPlayerHeadVector(receiver), rotationHorizontal * Math.PI / 180);

        // Check that each coord of the vector is not NaN
        if (Double.isNaN(vector.getX())) {
            vector.setX(0);
        }
        if (Double.isNaN(vector.getY())) {
            vector.setY(0);
        }
        if (Double.isNaN(vector.getZ())) {
            vector.setZ(0);
        }
        entity.setVelocity(vector);

        // Multiply the velocity
        entity.setVelocity(entity.getVelocity().multiply(velocity));


        if (SCore.hasExecutableItems && aInfo.getExecutableItem() != null) {
            ProjectileInfo pInfo = new ProjectileInfo(receiver, entity.getUniqueId(), Optional.ofNullable(aInfo.getExecutableItem()), aInfo.getSlot(), System.currentTimeMillis());
            ProjectilesHandler.getInstance().addProjectileInfo(pInfo);
        }

        PlayerCustomLaunchEntityEvent playerCustomLaunchProjectileEvent = new PlayerCustomLaunchEntityEvent(receiver, entity);
        Bukkit.getServer().getPluginManager().callEvent(playerCustomLaunchProjectileEvent);

    }

    public Vector getPlayerHeadVector(Player player) {
        // Get player's eye direction for orientation reference
        Vector lookDirection = player.getEyeLocation().getDirection();

        // Get player's "up" vector in their local space
        // We can get this by taking the cross product twice:
        // First to get the "right" vector, then to get the "up" vector
        Vector right = lookDirection.getCrossProduct(new Vector(0, 1, 0)).normalize();
        Vector up = right.getCrossProduct(lookDirection).normalize();

        // Scale it to head height (0.4 blocks)
        return up.multiply(0.4);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("LOCATED_LAUNCH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "LOCATED_LAUNCH projectile:ARROW frontValue:0 rightValue:0 yValue:0 velocity:1 angleRotationVertical:0 angleRotationHorizontal:0";
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
