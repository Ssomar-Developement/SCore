package com.ssomar.testRecode.features.custom.conditions.block.condition;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.testRecode.features.types.BooleanFeature;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfPlayerMustBeOnTheBlock extends BlockConditionFeature<BooleanFeature, IfPlayerMustBeOnTheBlock> {

    public IfPlayerMustBeOnTheBlock(FeatureParentInterface parent) {
        super(parent, "", "", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if(getCondition().getValue()){
            boolean onBlock = false;
            Location bLoc = b.getLocation();
            bLoc = bLoc.add(0.5,1,0.5);
            for(Player pl : Bukkit.getServer().getOnlinePlayers()){
                Location pLoc = pl.getLocation();
                if(bLoc.getWorld().getUID().equals(pLoc.getWorld().getUID())) {
                    if (bLoc.distance(pLoc) < 1.135) {
                        onBlock = true;
                        break;
                    }
                }
            }
            if(!onBlock){
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerMustBeOnTheBlock getValue() {
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
    public IfPlayerMustBeOnTheBlock initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifPlayerMustBeOnTheBlock", false, "If player must be on the block", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public IfPlayerMustBeOnTheBlock getNewInstance() {
        return new IfPlayerMustBeOnTheBlock(getParent());
    }
}
