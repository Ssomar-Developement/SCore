package com.ssomar.testRecode.features.custom.conditions.block.condition;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.testRecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfPlantFullyGrown extends BlockConditionFeature<BooleanFeature, IfPlantFullyGrown> {

    public IfPlantFullyGrown(FeatureParentInterface parent) {
        super(parent, "", "", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if(getCondition().getValue() && b.getState().getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) b.getState().getBlockData();
            int age = ageable.getAge();
            if(age != ageable.getMaximumAge()) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlantFullyGrown getValue() {
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
    public IfPlantFullyGrown initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifPlantFullyGrown", false,"If plant fully grown", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public IfPlantFullyGrown getNewInstance() {
        return new IfPlantFullyGrown(getParent());
    }
}
