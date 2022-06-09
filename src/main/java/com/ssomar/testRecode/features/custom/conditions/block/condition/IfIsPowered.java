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

public class IfIsPowered extends BlockConditionFeature<BooleanFeature, IfIsPowered> {

    public IfIsPowered(FeatureParentInterface parent) {
        super(parent, "", "", new String[]{}, Material.ANVIL, false);
    }


    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if(getCondition().getValue()) {
            //SsomarDev.testMsg("block: "+b.getType()+ "   isBlockpowered: "+b.isBlockPowered()+ " is Powerable: "+(b.getBlockData() instanceof Powerable)+ "power: "+b.getBlockPower());
            boolean notPowered = !b.isBlockPowered() && b.getBlockPower() == 0;

            if(b.getBlockData() instanceof Powerable) {
                Powerable power = (Powerable)b.getBlockData();
                notPowered = !power.isPowered();
            }

            if(notPowered) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public String [] getEditorDescription(){
        String [] finalDescription = new String[super.getEditorDescription().length + 1];
        if(getCondition().getValue())
            finalDescription[finalDescription.length - 5] = "&7Enable: &a&l✔";
        else
            finalDescription[finalDescription.length - 5] = "&7Enable: &c&l✘";
        return finalDescription;
    }

    @Override
    public IfIsPowered getValue() {
        return this;
    }

    @Override
    public IfIsPowered initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifIsPowered", false,"If is powered", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public IfIsPowered getNewInstance() {
        return new IfIsPowered(getParent());
    }
}
