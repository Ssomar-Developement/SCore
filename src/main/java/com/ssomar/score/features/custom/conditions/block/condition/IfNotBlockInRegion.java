package com.ssomar.score.features.custom.conditions.block.condition;

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
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Optional;

public class IfNotBlockInRegion extends BlockConditionFeature<ListUncoloredStringFeature, IfNotBlockInRegion> {

    public IfNotBlockInRegion(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifNotBlockInRegion);
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (hasCondition()) {
            if ((SCore.hasWorldEdit || SCore.hasFastAsyncWorldEdit) && SCore.hasWorldGuard) {
                Block block = request.getBlock();
                Location loc = BukkitAdapter.adapt(block.getLocation());
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regions = container.get(BukkitAdapter.adapt(block.getWorld()));

                if (regions == null) return true;

                ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

                for (String name : getCondition().getValue(request.getSp())) {
                    for (ProtectedRegion region : set) {
                        if (region.getId().equalsIgnoreCase(name)) {
                            runInvalidCondition(request);
                            return false;
                        }
                    }
                }

                // the unwanted region wasn't detected so return it to true
                return true;
            }
            else {
                runInvalidCondition(request);
                return false;
            }

        }
        return true;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), new ArrayList<>(), FeatureSettingsSCore.ifBlockInRegion, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getValue().isEmpty();
    }

    @Override
    public IfNotBlockInRegion getNewInstance(FeatureParentInterface newParent) {
        return new IfNotBlockInRegion(newParent);
    }

    @Override
    public IfNotBlockInRegion getValue() {
        return this;
    }
}
