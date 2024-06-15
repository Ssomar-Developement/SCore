package com.ssomar.score.features.custom.conditions.custom.condition;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.custom.CustomConditionFeature;
import com.ssomar.score.features.custom.conditions.custom.CustomConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class IfOwnerOfTheEI extends CustomConditionFeature<BooleanFeature, IfOwnerOfTheEI> {

    public IfOwnerOfTheEI(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifOwnerOfTheEI);
    }

    @Override
    public boolean verifCondition(CustomConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            ItemStack itemStack = request.getItemStack();
            Player player = request.getLauncher();
            if (itemStack.hasItemMeta() && player != null) {
                ItemMeta iM = itemStack.getItemMeta();

                NamespacedKey key = new NamespacedKey(ExecutableItems.getPluginSt(), "EI-OWNER");
                String uuidStr = iM.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                boolean invalid = false;
                UUID uuid = null;
                try {
                    uuid = UUID.fromString(uuidStr);
                } catch (Exception e) {
                    invalid = true;
                }
                if (invalid || !uuid.equals(player.getUniqueId())) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfOwnerOfTheEI getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(this, false, FeatureSettingsSCore.ifOwnerOfTheEI, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfOwnerOfTheEI getNewInstance(FeatureParentInterface parent) {
        return new IfOwnerOfTheEI(parent);
    }
}
