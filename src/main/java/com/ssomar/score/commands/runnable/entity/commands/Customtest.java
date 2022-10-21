package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* BURN {timeinsecs} */
public class Customtest extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {

        float particleDensity = 10;
        int particleAmount = 1;
        double particleSpeed = 0.00001;
        double particleOffset = 0.00001;
        Particle particle = Particle.FLAME;
        /* ]0;1]*/
        float animationSpeed = 1f;

        EntityDamageEvent dmgEvt = entity.getLastDamageCause();
        //SsomarDev.testMsg("dmgEvt: "+dmgEvt, true);
        if(dmgEvt != null) {
            if(dmgEvt instanceof EntityDamageByEntityEvent){
                EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent) dmgEvt;
                Entity damager = evt.getDamager();
                if(damager instanceof Projectile){
                    damager = (Entity) ((Projectile) damager).getShooter();
                }

                Vector v = damager.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize();

                int max = (int) (damager.getLocation().distance(entity.getLocation())*particleDensity);
                int seq = (int) (max*animationSpeed);

                int cpt = 0;
                int delais = 0;
                for(int i = 0; i < damager.getLocation().distance(entity.getLocation()); i++) {
                    final int distance = i;
                    for(float j = 1; j <= particleDensity; j++) {
                        final double x = 1f/j;
                        if(cpt%seq == 0) delais++;
                        BukkitRunnable runnable = new BukkitRunnable() {
                            public void run() {
                                //SsomarDev.testMsg("x: "+x, true);
                                Vector newVector = v.clone().multiply(distance+x);
                                //SsomarDev.testMsg("vec: "+newVector, true);
                                Location loc = entity.getLocation().add(newVector);
                                //SsomarDev.testMsg("loc: "+loc, true);
                                ParticleEffect.FLAME.display(loc, (float) particleOffset, (float) particleOffset, (float) particleOffset, (float) particleSpeed, particleAmount, null);

                            }
                        };
                        runnable.runTaskLater(SCore.plugin, delais);
                        cpt++;
                    }
                }
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CUSTOMTEST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CUSTOMTEST";
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
