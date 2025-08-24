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

public class IfEntityInRegion extends EntityConditionFeature<ListUncoloredStringFeature, IfEntityInRegion> {
    public IfEntityInRegion(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifEntityInRegion);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {

            if (SCore.hasWorldEdit || SCore.hasFastAsyncWorldEdit) {
                Entity entity = request.getEntity();
                Location loc = BukkitAdapter.adapt(entity.getLocation());
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regions = container.get(BukkitAdapter.adapt(entity.getWorld()));

                if (regions == null) return false;

                ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

                for (String name : getCondition().getValue(request.getSp())) {
                    for (ProtectedRegion region : set) {
                        if (region.getId().equalsIgnoreCase(name)) {
                            return true;
                        }
                    }
                }
            }
            return false;


        } else return false;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), new ArrayList<>(), FeatureSettingsSCore.ifEntityInRegion, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getValue().isEmpty();
    }

    @Override
    public IfEntityInRegion getNewInstance(FeatureParentInterface newParent) {
        return new IfEntityInRegion(newParent);
    }

    @Override
    public IfEntityInRegion getValue() {
        return this;
    }
}
