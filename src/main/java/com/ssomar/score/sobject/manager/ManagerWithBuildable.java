package com.ssomar.score.sobject.manager;

import com.ssomar.score.sobject.SObject;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface ManagerWithBuildable<T extends SObject> {

    Optional<SObject> getObject(ItemStack item);
}
