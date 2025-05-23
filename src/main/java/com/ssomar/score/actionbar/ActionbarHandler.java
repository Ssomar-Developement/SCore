package com.ssomar.score.actionbar;

import com.ssomar.score.SCore;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ActionbarHandler {

    private static ActionbarHandler instance;
    private final HashMap<Player, List<Actionbar>> actionbarHandler = new HashMap<>();
    @Getter @Setter
    private List<Player> hideActionbar = new ArrayList<>();

    public static ActionbarHandler getInstance() {
        if (instance == null) instance = new ActionbarHandler();
        return instance;
    }

    public void load() {
        startActionbarDisplay();
    }

    /**
     * Start the loop for the display on Actionbar (delay 1 sec)
     */
    public void startActionbarDisplay() {
        Runnable runnable = new Runnable() {
            int cpt = 1;

            @Override
            public void run() {
                List<Player> playerEmpty = new ArrayList<>();
                for (Player player : actionbarHandler.keySet()) {
                    List<Actionbar> actionbars = actionbarHandler.get(player);
                    if (player.isOnline()) {
                        if (!hideActionbar.contains(player)) displayDesactivationActionbars(actionbars, player);
                        boolean oneIsRemove = removeDesactionActionabars(actionbars) != 0;
                        if (actionbars.size() == 0) {
                            playerEmpty.add(player);
                            continue;
                        }
                        activeActionbarIfNotExist(actionbars);

                        if (cpt == 10) {
                            activeNextActionbar(actionbars);
                            cpt = 1;
                        } else cpt++;

                        if (!oneIsRemove && !hideActionbar.contains(player)) displayActiveActionbar(actionbars, player);
                    }
                    decrementTimeActionbars(actionbars);
                }
            }
        };
        SCore.schedulerHook.runRepeatingTask(runnable, 0L, 20L);
    }

    public void removeActionbars(Player p) {
        actionbarHandler.remove(p);
    }

    public void addActionbar(Player player, Actionbar actionbar) {
        if (!actionbarHandler.containsKey(player)) {
            List<Actionbar> list = new ArrayList<>();
            list.add(actionbar);
            actionbarHandler.put(player, list);
        } else {
            actionbarHandler.get(player).add(actionbar);
        }
    }

    public void activeActionbarIfNotExist(List<Actionbar> actionbars) {
        boolean existActiveActionbar = false;
        for (Actionbar actionbar : actionbars) {
            if (actionbar.isActive()) {
                existActiveActionbar = true;
                break;
            }
        }
        if (!existActiveActionbar && actionbars.size() >= 1) actionbars.get(0).setActive(true);
    }

    public int removeDesactionActionabars(List<Actionbar> actionbars) {
        List<Actionbar> toRemove = new ArrayList<>();
        for (Actionbar actionbar : actionbars) {
            if (actionbar.isDesactivation()) toRemove.add(actionbar);
        }
        for (Actionbar actionbar : toRemove) {
            actionbars.remove(actionbar);
        }
        return toRemove.size();
    }

    public void displayDesactivationActionbars(List<Actionbar> actionbars, Player p) {
        StringPlaceholder sp = new StringPlaceholder();
        sp.setPlayerPlcHldr(p.getUniqueId());

        List<Actionbar> desactivation = new ArrayList<>();
        for (Actionbar actionbar : actionbars) {
            if (actionbar.isDesactivation()) desactivation.add(actionbar);
        }

        if (desactivation.size() >= 1) {
            StringBuilder items = new StringBuilder();
            for (Actionbar a : desactivation) {
                items.append(" ").append(a.getName());
            }
            sp.setItem(items.toString());
            String message = sp.replacePlaceholder(MessageMain.getInstance().getMessage(SCore.plugin, Message.ACTIONBAR_END));
            Bukkit.getServer().getPlayer(p.getName()).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

        }
    }

    public void decrementTimeActionbars(List<Actionbar> actionbars) {
        for (Actionbar actionbar : actionbars) {
            if (actionbar.getTime() - 1 == 0) {
                actionbar.setDesactivation(true);
                actionbar.setTime(-1);
            } else actionbar.setTime(actionbar.getTime() - 1);
        }
    }

    public void activeNextActionbar(List<Actionbar> actionbars) {
        int activeAB = this.getActiveActionbar(actionbars);
        if (activeAB + 1 > actionbars.size() - 1) {
            actionbars.get(activeAB).setActive(false);
            actionbars.get(0).setActive(true);
        } else {
            actionbars.get(activeAB).setActive(false);
            actionbars.get(activeAB + 1).setActive(true);
        }
    }

    public int getActiveActionbar(List<Actionbar> actionbars) {
        int cpt = 0;
        for (Actionbar actionbar : actionbars) {
            if (actionbar.isActive()) return cpt;
            cpt++;
        }

        return Integer.MIN_VALUE;
    }

    public void displayActiveActionbar(List<Actionbar> actionbars, Player p) {
        Actionbar actionbar = actionbars.get(this.getActiveActionbar(actionbars));
        StringPlaceholder sp = new StringPlaceholder();
        sp.setItem(actionbar.getName());
        sp.getTimePlch().setTimePlcHldr(actionbar.getTime());
        sp.setPlayerPlcHldr(p.getUniqueId());
        String message = sp.replacePlaceholder(MessageMain.getInstance().getMessage(SCore.plugin, Message.ACTIONBAR_MESSAGE));
        Bukkit.getServer().getPlayer(p.getName()).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

}

