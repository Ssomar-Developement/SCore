package com.ssomar.score.customprojectiles;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pose;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class CustomArrow implements Arrow{

	@Override
	public int getKnockbackStrength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setKnockbackStrength(int knockbackStrength) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDamage(double damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPierceLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPierceLevel(int pierceLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCritical() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCritical(boolean critical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Block getAttachedBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PickupStatus getPickupStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPickupStatus(PickupStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isShotFromCrossbow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setShotFromCrossbow(boolean shotFromCrossbow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProjectileSource getShooter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShooter(ProjectileSource source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean doesBounce() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBounce(boolean doesBounce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation(Location loc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVelocity(Vector velocity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoundingBox getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnGround() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInWater() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRotation(float yaw, float pitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean teleport(Location location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(Location location, TeleportCause cause) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(Entity destination) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(Entity destination, TeleportCause cause) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Entity> getNearbyEntities(double x, double y, double z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEntityId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFireTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVisualFire(boolean fire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisualFire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getFreezeTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxFreezeTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFreezeTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFrozen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPersistent(boolean persistent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity getPassenger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setPassenger(Entity passenger) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Entity> getPassengers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addPassenger(Entity passenger) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removePassenger(Entity passenger) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eject() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getFallDistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFallDistance(float distance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastDamageCause(EntityDamageEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityDamageEvent getLastDamageCause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getUniqueId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTicksLived() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTicksLived(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playEffect(EntityEffect type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInsideVehicle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leaveVehicle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Entity getVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCustomNameVisible(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCustomNameVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGlowing(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isGlowing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInvulnerable(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInvulnerable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSilent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSilent(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasGravity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGravity(boolean gravity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPortalCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPortalCooldown(int cooldown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getScoreboardTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addScoreboardTag(String tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeScoreboardTag(String tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockFace getFacing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pose getPose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spigot spigot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MetadataValue> getMetadata(String metadataKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasMetadata(String metadataKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeMetadata(String metadataKey, Plugin owningPlugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String[] messages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(UUID sender, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(UUID sender, String[] messages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPermissionSet(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(Permission perm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(Permission perm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recalculatePermissions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOp(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCustomName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCustomName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersistentDataContainer getPersistentDataContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBasePotionData(PotionData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PotionData getBasePotionData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasCustomEffects() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PotionEffect> getCustomEffects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addCustomEffect(PotionEffect effect, boolean overwrite) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeCustomEffect(PotionEffectType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasCustomEffect(PotionEffectType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clearCustomEffects() {
		// TODO Auto-generated method stub
		
	}

}
