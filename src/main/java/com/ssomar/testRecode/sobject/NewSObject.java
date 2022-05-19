package com.ssomar.testRecode.sobject;


import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.testRecode.features.FeatureParentInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface NewSObject extends FeatureParentInterface {

    String getId();

    void setId(String id);

    String getPath();

    List<NewSObject> getActivators();

    ItemStack buildItem(int quantity, Optional<Player> creatorOpt);

    @Nullable
    SActivator getActivator(String actID);

    List<String> getDescription();
}
