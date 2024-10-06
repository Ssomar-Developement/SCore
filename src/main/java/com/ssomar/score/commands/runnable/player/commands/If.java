package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
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
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ActionInfo aInfo = sCommandToExec.getActionInfo();
        List<String> args = sCommandToExec.getOtherArgs();
    
        String condition = args.get(0);
        SsomarDev.testMsg("IF condition: " + condition, true);
    
        StringPlaceholder sp = aInfo.getSp();
        if (sp == null) sp = new StringPlaceholder();
        sp.setPlayerPlcHldr(receiver.getUniqueId(), aInfo.getSlot());
        sp.reloadAllPlaceholders();
    
        List<Player> targets = new ArrayList<>();
        targets.add(receiver);
    
        boolean finalResult = evaluateCondition(condition, receiver, sp);
        
        if (finalResult) {
            CommmandThatRunsCommand.runPlayerCommands(targets, args.subList(1, args.size()), aInfo);
        } else {
            SsomarDev.testMsg("IF STOPPED", true);
        }
    }
    
    private boolean evaluateCondition(String condition, Player receiver, StringPlaceholder sp) {
        // Split by OR (||) first
        String[] orConditions = condition.split("\\|\\|");
        boolean orResult = false;
    
        for (String orCondition : orConditions) {
            // Split by AND (&&) and evaluate all subconditions
            String[] andConditions = orCondition.split("&&");
            boolean andResult = true;
    
            for (String andCondition : andConditions) {
                andCondition = andCondition.trim();
                if (!evaluateSingleCondition(andCondition, receiver, sp)) {
                    andResult = false;
                    break;
                }
            }
    
            if (andResult) {
                orResult = true;
                break;
            }
        }
    
        return orResult;
    }
    
    private boolean evaluateSingleCondition(String condition, Player receiver, StringPlaceholder sp) {
        PlaceholderConditionFeature conditionFeature = PlaceholderConditionFeature.buildNull();
        conditionFeature.setType(PlaceholderConditionTypeFeature.buildNull(PlaceholdersCdtType.PLAYER_PLAYER));
    
        boolean conditionContainsPlaceholder = condition.contains("%");
        String split = conditionContainsPlaceholder ? "%" : "";
    
        Comparator comparator = null;
        for (Comparator c : Comparator.values()) {
            if (condition.contains(split + c.getSymbol())) {
                conditionFeature.setComparator(ComparatorFeature.buildNull(c));
                comparator = c;
                break;
            }
        }
        if (comparator == null) {
            SsomarDev.testMsg("IF STOPPED because comparator null for condition: " + condition, true);
            return false;
        }
    
        String[] parts = condition.split(split + comparator.getSymbol());
        if (parts.length < 2) {
            SsomarDev.testMsg("IF STOPPED because parts are invalid for condition: " + condition, true);
            return false;
        }
    
        conditionFeature.setPart1(ColoredStringFeature.buildNull(parts[0].trim() + split));
        conditionFeature.setPart2(ColoredStringFeature.buildNull(parts[1].trim()));
    
        return conditionFeature.verify(receiver, null, sp);
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
