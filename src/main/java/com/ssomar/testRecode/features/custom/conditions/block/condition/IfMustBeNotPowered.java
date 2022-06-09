package com.ssomar.testRecode.features.custom.conditions.block.condition;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.testRecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfMustBeNotPowered extends BlockConditionFeature<BooleanFeature, IfMustBeNotPowered> {


    public IfMustBeNotPowered(FeatureParentInterface parent) {
         super(parent, "", "", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender, Event event) {
        if(getCondition().getValue() && b.getBlockData() instanceof Powerable) {
            Powerable power = (Powerable)b.getBlockData();
            if(power.isPowered()) {
                sendErrorMsg(playerOpt, messangeSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfMustBeNotPowered getValue() {
        return this;
    }

    @Override
    public String [] getEditorDescription(){
        String [] finalDescription = new String[super.getEditorDescription().length + 1];
        if(getCondition().getValue())
            finalDescription[finalDescription.length - 1] = "&7Enable: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Enable: &c&l✘";
        return finalDescription;
    }

    @Override
    public IfMustBeNotPowered initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifMustBeNotPowered", false,"If must be not powered", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public IfMustBeNotPowered getNewInstance() {
        return new IfMustBeNotPowered(getParent());
    }
}
