package com.ssomar.score.sobject.sactivator;

import com.ssomar.score.features.custom.activators.activator.SActivator;
import com.ssomar.score.utils.emums.DetailedClick;
import com.ssomar.score.utils.emums.DetailedInteraction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
@Setter
public class EventInfo {

    private SOption option;

    private Event eventSource;

    /* Whitelist */
    private List<SActivator> whitelistActivators;

    private List<String> whitelistActivatorsId;

    /* PLAYER */
    private Optional<Player> player;

    private Optional<Player> targetPlayer;

    /* ENTITY */
    private Optional<Entity> entity;

    private Optional<Entity> targetEntity;

    /* EFFECT */
    private Optional<PotionEffect> effect;

    /* BLOCK */
    private Optional<Block> block;

    private Optional<Block> targetBlock;

    private Optional<Material> oldMaterialBlock;

    private Optional<Material> oldMaterialTargetBlock;

    private Optional<String> oldStatesBlock;

    private Optional<String> oldStatesTargetBlock;

    private Optional<String> blockface;

    private Optional<String> blockfaceTarget;

    /* ITEM */
    private Optional<ItemStack> item;

    private Optional<ItemStack> targetItem;

    /* WORLD */
    private Optional<World> world;

    /* PROJECTILE */
    private Optional<Projectile> projectile;

    private Optional<String> projectileProvenance;

    /* MISC */
    private Optional<DetailedClick> detailedClick;

    private Optional<DetailedInteraction> detailedInteraction;

    private Optional<EntityDamageEvent.DamageCause> damageCause;

    private Optional<InventoryType> inventoryType;

    private Optional<Inventory> inventory;

    /* Projectile velocity */
    private Optional<Vector> velocity;

    /* Bow shoot force */
    private Optional<Float> bowForce;

    private boolean isFromCustomBreakCommand;

    private Optional<String> command;

    private boolean mainHand;

    private boolean forceMainHand;

    private boolean mustDoNothing;

    private Optional<Integer> slot;

    private Map<String, String> placeholders;

    public EventInfo(Event eventSource) {
        super();
        if(eventSource == null) {
            eventSource = new Event() {
                @NotNull
                @Override
                public HandlerList getHandlers() {
                    return null;
                }
            };
        }
        this.whitelistActivatorsId = new ArrayList<>();
        this.whitelistActivators = new ArrayList<>();
        this.eventSource = eventSource;
        this.player = Optional.empty();
        this.targetPlayer = Optional.empty();
        this.entity = Optional.empty();
        this.targetEntity = Optional.empty();
        this.effect = Optional.empty();
        this.block = Optional.empty();
        this.targetBlock = Optional.empty();
        this.oldMaterialBlock = Optional.empty();
        this.oldMaterialTargetBlock = Optional.empty();
        this.oldStatesBlock = Optional.empty();
        this.oldStatesTargetBlock = Optional.empty();
        this.blockface = Optional.empty();
        this.blockfaceTarget = Optional.empty();
        this.projectile = Optional.empty();
        this.projectileProvenance = Optional.empty();
        this.detailedClick = Optional.empty();
        this.detailedInteraction = Optional.empty();
        this.damageCause = Optional.empty();
        this.velocity = Optional.empty();
        this.bowForce = Optional.empty();
        this.command = Optional.empty();
        this.slot = Optional.empty();
        this.item = Optional.empty();
        this.targetItem = Optional.empty();
        this.world = Optional.empty();
        this.inventoryType = Optional.empty();
        this.placeholders = new HashMap<>();
    }

    public EventInfo clone() {
        EventInfo eInfo = new EventInfo(eventSource);
        eInfo.setPlayer(player);
        eInfo.setTargetPlayer(targetPlayer);
        eInfo.setEntity(entity);
        eInfo.setTargetEntity(targetEntity);
        eInfo.setEffect(effect);
        eInfo.setBlock(block);
        eInfo.setTargetBlock(targetBlock);
        eInfo.setOldMaterialBlock(oldMaterialBlock);
        eInfo.setOldMaterialTargetBlock(oldMaterialTargetBlock);
        eInfo.setOldStatesBlock(oldStatesBlock);
        eInfo.setOldStatesTargetBlock(oldStatesTargetBlock);
        eInfo.setBlockface(blockface);
        eInfo.setBlockfaceTarget(blockfaceTarget);
        eInfo.setProjectile(projectile);
        eInfo.setProjectileProvenance(projectileProvenance);
        eInfo.setDetailedClick(detailedClick);
        eInfo.setDetailedInteraction(detailedInteraction);
        eInfo.setDamageCause(damageCause);
        eInfo.setVelocity(velocity);
        eInfo.setBowForce(bowForce);
        eInfo.setFromCustomBreakCommand(isFromCustomBreakCommand);
        eInfo.setCommand(command);
        eInfo.setMainHand(mainHand);
        eInfo.setForceMainHand(forceMainHand);
        eInfo.setMustDoNothing(mustDoNothing);
        eInfo.setSlot(slot);
        eInfo.setItem(item);
        eInfo.setTargetItem(targetItem);
        eInfo.setWorld(world);
        eInfo.setInventoryType(inventoryType);
        eInfo.setPlaceholders(placeholders);

        return eInfo;
    }

    public Map<String, String> getPlaceholderOfCommand(){
        Map<String, String> placeholders = new HashMap<>();
        if(command.isPresent()){
            placeholders.put("%all_args%", command.get().replaceAll("%[^ ]*%", ""));
            String[] split = command.get().split(" ");
            StringBuilder allArgsWithoutFirst = new StringBuilder();
            int i = 0;
            for (String arg : split) {
                if(i != 0) allArgsWithoutFirst.append(arg).append(" ");
                placeholders.put("%arg" + i + "%", arg.replaceAll("%[^ ]*%", ""));
                i++;
            }
            allArgsWithoutFirst = new StringBuilder(allArgsWithoutFirst.toString().trim());
            placeholders.put("%all_args_without_first%", allArgsWithoutFirst.toString().replaceAll("%[^ ]*%", ""));
        }
        return placeholders;
    }
}
