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

public class IfBlockInRegion extends BlockConditionFeature<ListUncoloredStringFeature, IfBlockInRegion> {

    public IfBlockInRegion(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifBlockInRegion);
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (hasCondition()) {
            if ((SCore.hasWorldEdit || SCore.hasFastAsyncWorldEdit) && SCore.hasWorldGuard) {
                Block block = request.getBlock();
                Location loc = BukkitAdapter.adapt(block.getLocation());
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regions = container.get(BukkitAdapter.adapt(block.getWorld()));

                if (regions == null) {
                    runInvalidCondition(request);
                    return false;
                }

                ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

                for (String name : getCondition().getValue(request.getSp())) {
                    for (ProtectedRegion region : set) {
                        if (region.getId().equalsIgnoreCase(name)) {
                            return true;
                        }
                    }
                }

                // falsify the condition since the wanted region isn't detected even after iterations
                runInvalidCondition(request);
                return false;
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
    public IfBlockInRegion getNewInstance(FeatureParentInterface newParent) {
        return new IfBlockInRegion(newParent);
    }

    @Override
    public IfBlockInRegion getValue() {
        return this;
    }
}
