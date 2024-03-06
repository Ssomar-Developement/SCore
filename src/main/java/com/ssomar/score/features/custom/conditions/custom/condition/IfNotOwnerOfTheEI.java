package com.ssomar.score.features.custom.conditions.custom.condition;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.custom.CustomConditionFeature;
import com.ssomar.score.features.custom.conditions.custom.CustomConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class IfNotOwnerOfTheEI extends CustomConditionFeature<BooleanFeature, IfNotOwnerOfTheEI> {


    public IfNotOwnerOfTheEI(FeatureParentInterface parent) {
        super(parent, "ifNotOwnerOfTheEI", "If not owner of the EI", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(CustomConditionRequest request) {
        if (hasCondition()) {
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
                if (!invalid && uuid.equals(player.getUniqueId())) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfNotOwnerOfTheEI getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(this, "ifNotOwnerOfTheEI", false, "If not owner of the EI", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfNotOwnerOfTheEI getNewInstance(FeatureParentInterface parent) {
        return new IfNotOwnerOfTheEI(parent);
    }
}
