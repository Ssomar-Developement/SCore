package com.ssomar.testRecode.features.custom.conditions.block.condition;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.custom.conditions.ConditionFeature;
import com.ssomar.testRecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.testRecode.features.types.NumberConditionFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class IfBlockAge extends BlockConditionFeature<NumberConditionFeature, IfBlockAge> {


    public IfBlockAge(FeatureParentInterface parent) {
        super(parent, "", "", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public IfBlockAge getValue() {
        return this;
    }

    @Override
    public String [] getEditorDescription(){
        String [] finalDescription = new String[super.getEditorDescription().length + 1];
        if(getCondition().getValue().isPresent()) {
            finalDescription[finalDescription.length - 3] = "&7Condition: &e" + getCondition().getValue().get();
        } else {
            finalDescription[finalDescription.length - 3] = "&7Condition: &cNO CONDITION";
        }
        return finalDescription;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), "ifBlockAge", "If Block Age", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public IfBlockAge getNewInstance() {
        return new IfBlockAge(getParent());
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event) {
        if(b.getState().getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) b.getState().getBlockData();
            int age = ageable.getAge();
            if(!getCondition().getValue().isPresent() && !StringCalculation.calculation(getCondition().getValue().get(), age)) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }
}
