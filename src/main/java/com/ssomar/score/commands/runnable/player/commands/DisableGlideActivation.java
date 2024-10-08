package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

/* BURN {timeinsecs} */
public class DisableGlideActivation extends PlayerCommand {

    private static DisableGlideActivation instance;
    @Getter
    private final Map<UUID, Integer> activeDisabled;

    public DisableGlideActivation() {
        activeDisabled = new HashMap<>();
    }

    public static DisableGlideActivation getInstance() {
        if (instance == null) instance = new DisableGlideActivation();
        return instance;
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        int time = Double.valueOf(args.get(0)).intValue();

        if (activeDisabled.containsKey(receiver.getUniqueId())) {
            activeDisabled.put(receiver.getUniqueId(), activeDisabled.get(receiver.getUniqueId()) + 1);
        } else activeDisabled.put(receiver.getUniqueId(), 1);

        final UUID receiverUUID = receiver.getUniqueId();

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                //SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
                if (activeDisabled.containsKey(receiverUUID)) {
                    if (activeDisabled.get(receiverUUID) > 1) {
                        activeDisabled.put(receiverUUID, activeDisabled.get(receiverUUID) - 1);
                    } else activeDisabled.remove(receiverUUID);
                }
            }
        };
        SCore.schedulerHook.runEntityTask(runnable3, null, receiver, time *20L);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DISABLE_GLIDE_ACTIVATION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DISABLE_GLIDE_ACTIVATION {timeinsecs}";
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
