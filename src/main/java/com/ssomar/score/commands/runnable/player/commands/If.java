package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.ComparatorFeature;
import com.ssomar.score.features.types.PlaceholderConditionTypeFeature;
import com.ssomar.score.utils.emums.Comparator;
import com.ssomar.score.utils.emums.PlaceholdersCdtType;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class If extends PlayerCommand {

    public If() {
        setCanExecuteCommands(true);
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        //SsomarDev.testMsg("IF CMD", true);
        PlaceholderConditionFeature conditionFeature = PlaceholderConditionFeature.buildNull();
        conditionFeature.setType(PlaceholderConditionTypeFeature.buildNull(PlaceholdersCdtType.PLAYER_PLAYER));
        String condition = args.get(0);
        SsomarDev.testMsg("IF condition: " + condition, true);

        boolean conditionContainsPlaceholder = condition.contains("%");
        String split = conditionContainsPlaceholder ? "%" : "";

        // "%"+c.getSymbol() because the placeholder can also contains comparator so to be sure the comparator is outside the placeholder we need to be sure there is a % before
        Comparator comparator = null;
        for (Comparator c : Comparator.values()) {
            /* Check if it contains placeholder or if its just a direct value */
            if (condition.contains(split + c.getSymbol())) {
                conditionFeature.setComparator(ComparatorFeature.buildNull(c));
                comparator = c;
                break;
            }
        }
        if (comparator == null) {
            SsomarDev.testMsg("IF STOPPED because comparator null ", true);
            return;
        }
        String[] parts = condition.split(split + comparator.getSymbol());
        conditionFeature.setPart1(ColoredStringFeature.buildNull(parts[0] + split));
        conditionFeature.setPart2(ColoredStringFeature.buildNull(parts[1]));

        StringPlaceholder sp = aInfo.getSp();
        if (sp == null) sp = new StringPlaceholder();
        sp.setPlayerPlcHldr(receiver.getUniqueId(), aInfo.getSlot());
        sp.reloadAllPlaceholders();

        List<Player> targets = new ArrayList<>();
        targets.add(receiver);

        /* for (String commandToRun: args.subList(1, args.size())){
            SsomarDev.testMsg("Command to run IF >> "+commandToRun, true);
        } */

        if (conditionFeature.verify(receiver, null, sp)) {
            CommmandThatRunsCommand.runPlayerCommands(targets, args.subList(1, args.size()), aInfo);
        } else {
            //SsomarDev.testMsg("IF STOPPED", true);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("IF");
        return names;
    }

    @Override
    public String getTemplate() {
        return "IF {condition_without_spaces} {command1} <+> {command2} <+> ...";
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
