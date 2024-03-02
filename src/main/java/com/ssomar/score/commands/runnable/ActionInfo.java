package com.ssomar.score.commands.runnable;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.SCore;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocks;
import com.ssomar.score.utils.logging.Debugers;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.sevents.events.player.click.onentity.left.PlayerLeftClickOnEntityEvent;
import com.ssomar.sevents.events.player.click.onplayer.left.PlayerLeftClickOnPlayerEvent;
import com.ssomar.sevents.events.player.receivehit.byentity.PlayerReceiveHitByEntityEvent;
import com.ssomar.sevents.events.player.receivehit.byplayer.PlayerReceiveHitByPlayerEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class ActionInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;

    /* The slot where the action was activated */
    private Integer slot;

    /* The executableItem that actives the action (to know from which ei a custom projectile has been launched) */
    private transient ExecutableItemObject executableItem;

    private boolean isEventFromCustomBreakCommand;

    private boolean isActionRelatedToDamageEvent;

    /* Important info */
    private UUID launcherUUID;

    private UUID receiverUUID;

    /* ------------------ */
    private int blockLocationX;
    private int blockLocationY;
    private int blockLocationZ;
    private UUID blockLocationWorld;
    private BlockFace blockFace;

    private DetailedBlocks detailedBlocks;

    private String oldBlockMaterialName;

    /* ------------------ */
    private UUID entityUUID;

    /* ------------------ */

    private transient Optional<Vector> velocity;

    private boolean silenceOutput;

    /* It can cause problem for example with REMOVEBLOCK , its useless to know who is the launcher in block command with LOOP activator */
    private boolean noPlayerTriggeredTheAction;

    private StringPlaceholder sp;

    /* For custom commands that can runs other custom commands ...*/
    private int step;

    private Debugers debugers;

    public ActionInfo(String name, StringPlaceholder sp) {
        this.name = name;
        this.slot = -1;
        this.sp = sp;
        this.executableItem = null;
        this.isEventFromCustomBreakCommand = false;
        this.launcherUUID = null;
        this.receiverUUID = null;
        this.blockLocationX = -1;
        this.blockLocationY = -1;
        this.blockLocationZ = -1;
        this.blockLocationWorld = null;
        this.oldBlockMaterialName = null;
        this.entityUUID = null;
        this.silenceOutput = false;
        this.noPlayerTriggeredTheAction = false;
        this.velocity = Optional.empty();
        this.debugers = new Debugers();
        this.step = 0;
    }

    public ActionInfo clone() {
        ActionInfo result = new ActionInfo(this.name, this.sp);
        result.setExecutableItem(executableItem);
        result.setEventFromCustomBreakCommand(isEventFromCustomBreakCommand);
        result.setLauncherUUID(launcherUUID);
        result.setReceiverUUID(receiverUUID);
        result.setOldBlockMaterialName(oldBlockMaterialName);
        result.setEntityUUID(entityUUID);
        result.setSilenceOutput(silenceOutput);
        result.setNoPlayerTriggeredTheAction(noPlayerTriggeredTheAction);
        result.setBlockLocationX(blockLocationX);
        result.setBlockLocationY(blockLocationY);
        result.setBlockLocationZ(blockLocationZ);
        result.setBlockLocationWorld(blockLocationWorld);
        result.setBlockFace(blockFace);
        result.setDetailedBlocks(detailedBlocks);
        result.setVelocity(velocity);
        result.setStep(step);

        return result;
    }

    public void setBlock(Block block) {
        Location bLoc = block.getLocation();
        this.blockLocationX = bLoc.getBlockX();
        this.blockLocationY = bLoc.getBlockY();
        this.blockLocationZ = bLoc.getBlockZ();
        this.blockLocationWorld = bLoc.getWorld().getUID();
        // second condition is the whitelist to check only the blocks useful for the plugin
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                BlockData blockData = null;
                if(block != null && block.getType().equals(Material.COCOA) && (blockData = block.getBlockData()) != null) {
                    if(blockData instanceof Directional){
                        blockFace = ((Directional) blockData).getFacing();
                    }
                }
                else blockFace = null;
            }
        };
        SCore.schedulerHook.runLocationTask(runnable, bLoc, 0);
    }

    public void initActionRelatedToDamageEvent(Event event){

        isActionRelatedToDamageEvent = event instanceof EntityDamageByEntityEvent || event instanceof PlayerReceiveHitByPlayerEvent
        || event instanceof PlayerReceiveHitByEntityEvent || event instanceof PlayerLeftClickOnPlayerEvent || event instanceof PlayerLeftClickOnEntityEvent;

    }
}
