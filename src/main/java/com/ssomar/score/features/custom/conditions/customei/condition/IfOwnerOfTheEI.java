package com.ssomar.score.features.custom.conditions.customei.condition;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.customei.CustomEIConditionFeature;
import com.ssomar.score.features.custom.conditions.customei.CustomEIConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class IfOwnerOfTheEI extends CustomEIConditionFeature<BooleanFeature, IfOwnerOfTheEI> {

    public IfOwnerOfTheEI(FeatureParentInterface parent) {
        super(parent, "ifOwnerOfTheEI", "If owner of the EI", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(CustomEIConditionRequest request) {
        if (hasCondition()) {
            ItemStack itemStack = request.getItemStack();
            Player player = request.getPlayer();
            if (itemStack.hasItemMeta()) {
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
        setCondition(new BooleanFeature(this, "ifOwnerOfTheEI", false, "If owner of the EI", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfOwnerOfTheEI getNewInstance(FeatureParentInterface parent) {
        return new IfOwnerOfTheEI(parent);
    }
}
