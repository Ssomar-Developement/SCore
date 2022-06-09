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

public class IfNoPlayerMustBeOnTheBlock extends BlockConditionFeature<BooleanFeature, IfNoPlayerMustBeOnTheBlock> {

    public IfNoPlayerMustBeOnTheBlock(FeatureParentInterface parent) {
        super(parent, "", "", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender, Event event) {
        if(getCondition().getValue()){
            boolean onBlock = false;
            Location bLoc = b.getLocation();
            bLoc = bLoc.add(0.5,1,0.5);
            for(Player pl : Bukkit.getServer().getOnlinePlayers()){
                Location pLoc = pl.getLocation();
                if(bLoc.getWorld().getUID().equals(pLoc.getWorld().getUID())) {
                    if (bLoc.distance(pl.getLocation()) < 1.135) {
                        onBlock = true;
                        break;
                    }
                }
            }
            if(onBlock){
                sendErrorMsg(playerOpt, messangeSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNoPlayerMustBeOnTheBlock getValue() {
        return this;
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
    public IfNoPlayerMustBeOnTheBlock initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNoPlayerMustBeOnTheBlock", false, "If no player must be on the block", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public IfNoPlayerMustBeOnTheBlock getNewInstance() {
        return new IfNoPlayerMustBeOnTheBlock(getParent());
    }
}
