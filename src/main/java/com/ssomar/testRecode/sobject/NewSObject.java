package com.ssomar.testRecode.sobject;


import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.sobject.sactivator.NewSActivator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface NewSObject extends FeatureParentInterface {

    String getId();

    void setId(String id);

    String getPath();

    List<NewSActivator> getActivators();

    ItemStack buildItem(int quantity, Optional<Player> creatorOpt);

    @Nullable
    NewSActivator getActivator(String actID);

    List<String> getDescription();
}
