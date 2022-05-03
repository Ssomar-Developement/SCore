package com.ssomar.score.sobject.sactivator.features;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.executableitems.configs.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.cooldowns.Cooldown;
import com.ssomar.score.sobject.sactivator.cooldowns.CooldownsManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

@Getter@Setter
public class CooldownFeature {

    /* Cooldowns / delay */
    private int cooldown;
    private String cooldownMessage;
    private boolean displayCooldownMessage;
    private boolean isCooldownInTicks;
    private boolean cancelEventIfInCooldown;
    private SPlugin sPlugin;
    private SObject sO;
    private SActivator sAct;
    private boolean enableCooldownForOp;

    public CooldownFeature(ConfigurationSection config, SPlugin sPlugin, SObject sO, SActivator sAct, boolean enableCooldownForOp) {
        this.cooldown = config.getInt("cooldown", 0);
        this.displayCooldownMessage = config.getBoolean("displayCooldownMessage", false);
        this.cancelEventIfInCooldown = config.getBoolean("cancelEventIfInCooldown", false);
        this.isCooldownInTicks = config.getBoolean("isCooldownInTicks", false);
        this.cooldownMessage = config.getString("cooldownMsg", "");
        this.sPlugin = sPlugin;
        this.sO = sO;
        this.sAct = sAct;
        this.enableCooldownForOp = enableCooldownForOp;
    }

    public CooldownFeature(SPlugin sPlugin, SObject sO, SActivator sAct, boolean enableCooldownForOp){
        this.cooldown = 0;
        this.displayCooldownMessage = false;
        this.cancelEventIfInCooldown = false;
        this.isCooldownInTicks = false;
        this.cooldownMessage = "";
        this.sPlugin = sPlugin;
        this.sO = sO;
        this.sAct = sAct;
        this.enableCooldownForOp = enableCooldownForOp;
    }


    /**
     *
     * @param p The player
     * @param e The event
     * @param sp The placeholder associate to the event
     * @return True in No coooldown, false if its on cooldown
     */
    public boolean checkCooldown(Player p, Event e, StringPlaceholder sp){

        if(cooldown <= 0) return true;

        /* Check if the activator is in cooldown for the player or not  */
        if (!hasNoCDPerm(p)) {
            Optional<Integer> inCooldownOpt = CooldownsManager.getInstance().isInCooldownForPlayer(ExecutableItems.plugin, sO, sAct, p.getUniqueId());
            if (inCooldownOpt.isPresent()) {
                if (this.displayCooldownMessage) {
                    int cooldown = this.cooldown - inCooldownOpt.get();
                    displayCooldownMessage(p, cooldown, sp);
                }
                SActivator.cancelEvent(e, this.isCancelEventIfInCooldown());
                return false;
            }
        }
        return true;
    }

    public void displayCooldownMessage(Player player, int timeLeft, StringPlaceholder sp){
        String message = cooldownMessage;
        if(message.isEmpty()){
            message = MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.TIME_LEFT);
        }
        sp.getTimePlch().setTimePlcHldr(timeLeft, isCooldownInTicks);
        message = sp.replacePlaceholder(message);
        player.sendMessage(StringConverter.coloredString(message));
    }

    /**
     *
     * @param p The player
     */
    public void addCooldown(Player p){
        if (!hasNoCDPerm(p) && this.cooldown != 0) {
            Cooldown cooldown = new Cooldown(ExecutableItems.plugin, sO, sAct, p.getUniqueId(), this.cooldown, isCooldownInTicks, System.currentTimeMillis(), false);
            CooldownsManager.getInstance().addCooldown(cooldown);
        }
    }

    public void saveCooldownFeature(ConfigurationSection config) {
        config.set("cooldown", cooldown);
        config.set("displayCooldownMessage", displayCooldownMessage);
        config.set("cancelEventIfInCooldown", cancelEventIfInCooldown);
        config.set("isCooldownInTicks", isCooldownInTicks);
        config.set("cooldownMsg", cooldownMessage);
    }


    public boolean hasNoCDPerm(Player p) {
        String id = sO.getId();

        if(sPlugin.isLotOfWork()) return false;

        if(p.isOp() || p.hasPermission("*")) {
            return !enableCooldownForOp;
        }
        else{
            return p.hasPermission(sPlugin.getName()+".nocd." + id) ||
                    p.hasPermission(sPlugin.getShortName().toLowerCase()+".nocd." + id) ||
                    p.hasPermission(sPlugin.getName()+".nocd.*") ||
                    p.hasPermission(sPlugin.getShortName().toLowerCase()+".nocd.*");
        }
    }


}
