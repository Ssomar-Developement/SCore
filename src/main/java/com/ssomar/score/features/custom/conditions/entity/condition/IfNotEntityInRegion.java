package com.ssomar.score.features.custom.conditions.entity.condition;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Optional;

public class IfNotEntityInRegion extends EntityConditionFeature<ListUncoloredStringFeature, IfNotEntityInRegion> {
    public IfNotEntityInRegion(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNotEntityInRegion);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            if ((SCore.hasWorldEdit || SCore.hasFastAsyncWorldEdit) && SCore.hasWorldGuard) {
                Entity entity = request.getEntity();
                Location loc = BukkitAdapter.adapt(entity.getLocation());
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regions = container.get(BukkitAdapter.adapt(entity.getWorld()));

                if (regions == null) return true;

                ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

                for (String name : getCondition().getValue(request.getSp())) {
                    for (ProtectedRegion region : set) {
                        if (region.getId().equalsIgnoreCase(name)) {
                            return false;
                        }
                    }
                }
            }
            else return false;

        }
        return true;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), new ArrayList<>(), FeatureSettingsSCore.ifNotEntityInRegion, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getValue().isEmpty();
    }

    @Override
    public IfNotEntityInRegion getNewInstance(FeatureParentInterface newParent) {
        return new IfNotEntityInRegion(newParent);
    }

    @Override
    public IfNotEntityInRegion getValue() {
        return this;
    }
}
