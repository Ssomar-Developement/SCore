package com.ssomar.scoretestrecode.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.activators.group.ActivatorsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.activators.group.ActivatorsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.attributes.attribute.AttributeFullOptionsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.attributes.attribute.AttributeFullOptionsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.attributes.group.AttributesGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.attributes.group.AttributesGroupFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.cancelevents.CancelEventFeaturesEditor;
import com.ssomar.scoretestrecode.features.custom.cancelevents.CancelEventFeaturesEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.ConditionFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.ConditionFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.block.parent.BlockConditionsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.block.parent.BlockConditionsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.customei.parent.CustomEIConditionsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.customei.parent.CustomEIConditionsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.parent.EntityConditionsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.parent.EntityConditionsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.item.parent.ItemConditionsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.item.parent.ItemConditionsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.placeholders.group.PlaceholderConditionGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.placeholders.group.PlaceholderConditionGroupFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.player.parent.PlayerConditionsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.player.parent.PlayerConditionsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.conditions.world.parent.WorldConditionsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.world.parent.WorldConditionsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.cooldowns.NewCooldownFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.cooldowns.NewCooldownFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.detailedslots.DetailedSlotsEditor;
import com.ssomar.scoretestrecode.features.custom.detailedslots.DetailedSlotsEditorManager;
import com.ssomar.scoretestrecode.features.custom.drop.DropFeaturesEditor;
import com.ssomar.scoretestrecode.features.custom.drop.DropFeaturesEditorManager;
import com.ssomar.scoretestrecode.features.custom.enchantments.enchantment.EnchantmentWithLevelFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.enchantments.enchantment.EnchantmentWithLevelFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.enchantments.group.EnchantmentsGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.enchantments.group.EnchantmentsGroupFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.entities.entity.EntityTypeForGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.entities.entity.EntityTypeForGroupFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.entities.group.EntityTypeGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.entities.group.EntityTypeGroupFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.givefirstjoin.GiveFirstJoinFeaturesEditor;
import com.ssomar.scoretestrecode.features.custom.givefirstjoin.GiveFirstJoinFeaturesEditorManager;
import com.ssomar.scoretestrecode.features.custom.hiders.HidersEditor;
import com.ssomar.scoretestrecode.features.custom.hiders.HidersEditorManager;
import com.ssomar.scoretestrecode.features.custom.loop.LoopFeaturesEditor;
import com.ssomar.scoretestrecode.features.custom.loop.LoopFeaturesEditorManager;
import com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.materialandtags.MaterialAndTagsFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.materialandtags.MaterialAndTagsFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.required.executableitems.group.RequiredExecutableItemGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.required.executableitems.group.RequiredExecutableItemGroupFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.required.executableitems.item.RequiredExecutableItemFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.required.executableitems.item.RequiredExecutableItemFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.required.items.group.RequiredItemGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.required.items.group.RequiredItemGroupFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.required.items.item.RequiredItemFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.required.items.item.RequiredItemFeatureEditorManager;
import com.ssomar.scoretestrecode.features.custom.required.parent.RequiredGroupEditor;
import com.ssomar.scoretestrecode.features.custom.required.parent.RequiredGroupEditorManager;
import com.ssomar.scoretestrecode.features.custom.required.level.RequiredLevelEditor;
import com.ssomar.scoretestrecode.features.custom.required.level.RequiredLevelEditorManager;
import com.ssomar.scoretestrecode.features.custom.required.money.RequiredMoneyEditor;
import com.ssomar.scoretestrecode.features.custom.required.money.RequiredMoneyEditorManager;
import com.ssomar.scoretestrecode.features.custom.restrictions.RestrictionsEditor;
import com.ssomar.scoretestrecode.features.custom.restrictions.RestrictionsEditorManager;
import com.ssomar.scoretestrecode.features.custom.useperday.UsePerDayFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.useperday.UsePerDayFeatureEditorManager;
import com.ssomar.scoretestrecode.sobject.menu.NewSObjectsEditorAbstract;
import com.ssomar.scoretestrecode.sobject.menu.NewSObjectsManagerEditor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class NewEditorInteractionsListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player)) return;

        String title = e.getView().getTitle();
        Player player = (Player) e.getWhoClicked();
        try {
            if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
        } catch (NullPointerException error) {
            return;
        }

        int slot = e.getSlot();
        ItemStack itemS = e.getView().getItem(slot);
        //boolean notNullItem = itemS!=null;

        try {
            if (e.getInventory().getHolder() instanceof GUI)
                this.manage(player, itemS, title, "", e);
        } catch (NullPointerException error) {
            error.printStackTrace();
        }
    }

    public void manage(Player player, ItemStack itemS, String title, String guiType, InventoryClickEvent e) {

        e.setCancelled(true);

        if (itemS == null) return;

        if (!itemS.hasItemMeta()) return;

        if (itemS.getItemMeta().getDisplayName().isEmpty()) return;

        //String itemName = itemS.getItemMeta().getDisplayName();

        boolean isShiftLeft = e.getClick().equals(ClickType.SHIFT_LEFT);

        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof NewSObjectsEditorAbstract) {
            NewSObjectsManagerEditor.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        } else if (holder instanceof RequiredLevelEditor) {
            RequiredLevelEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof RequiredMoneyEditor) {
            RequiredMoneyEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof RequiredItemFeatureEditor) {
            RequiredItemFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof RequiredItemGroupFeatureEditor) {
            RequiredItemGroupFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof RequiredExecutableItemFeatureEditor) {
            RequiredExecutableItemFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof RequiredExecutableItemGroupFeatureEditor) {
            RequiredExecutableItemGroupFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof RequiredGroupEditor) {
            RequiredGroupEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof DropFeaturesEditor) {
            DropFeaturesEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof HidersEditor) {
            HidersEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof GiveFirstJoinFeaturesEditor) {
            GiveFirstJoinFeaturesEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof EnchantmentWithLevelFeatureEditor) {
            EnchantmentWithLevelFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof EnchantmentsGroupFeatureEditor) {
            EnchantmentsGroupFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof AttributeFullOptionsFeatureEditor) {
            AttributeFullOptionsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof AttributesGroupFeatureEditor) {
            AttributesGroupFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof RestrictionsEditor) {
            RestrictionsEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof CancelEventFeaturesEditor) {
            CancelEventFeaturesEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof ActivatorsFeatureEditor) {
            ActivatorsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof NewCooldownFeatureEditor) {
            NewCooldownFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof LoopFeaturesEditor) {
            LoopFeaturesEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof ConditionFeatureEditor) {
            ConditionFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof BlockConditionsFeatureEditor) {
            BlockConditionsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof EntityConditionsFeatureEditor) {
            EntityConditionsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof WorldConditionsFeatureEditor) {
            WorldConditionsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof ItemConditionsFeatureEditor) {
            ItemConditionsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof PlayerConditionsFeatureEditor) {
            PlayerConditionsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof CustomEIConditionsFeatureEditor) {
            CustomEIConditionsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof PlaceholderConditionFeatureEditor) {
            PlaceholderConditionFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof PlaceholderConditionGroupFeatureEditor) {
            PlaceholderConditionGroupFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof MaterialAndTagsFeatureEditor) {
            MaterialAndTagsFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof MaterialAndTagsGroupFeatureEditor) {
            MaterialAndTagsGroupFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof EntityTypeGroupFeatureEditor) {
            EntityTypeGroupFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof EntityTypeForGroupFeatureEditor) {
            EntityTypeForGroupFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof DetailedSlotsEditor) {
            DetailedSlotsEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
        else if (holder instanceof UsePerDayFeatureEditor) {
            UsePerDayFeatureEditorManager.getInstance().clicked(player, itemS, title, e.getClick());
            return;
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatEvent(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        if (NewSObjectsManagerEditor.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            NewSObjectsManagerEditor.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (RequiredLevelEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredLevelEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (RequiredMoneyEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredMoneyEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (RequiredItemFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredItemFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (RequiredItemGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredItemGroupFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (RequiredExecutableItemFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredExecutableItemFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (RequiredExecutableItemGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredExecutableItemGroupFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (RequiredGroupEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredGroupEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (DropFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DropFeaturesEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (HidersEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HidersEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (GiveFirstJoinFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            GiveFirstJoinFeaturesEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (EnchantmentWithLevelFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EnchantmentWithLevelFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (EnchantmentsGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EnchantmentsGroupFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (AttributeFullOptionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            AttributeFullOptionsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (AttributesGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            AttributesGroupFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (RestrictionsEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RestrictionsEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (CancelEventFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            CancelEventFeaturesEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (ActivatorsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ActivatorsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (NewCooldownFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            NewCooldownFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (LoopFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            LoopFeaturesEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (ConditionFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ConditionFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (BlockConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            BlockConditionsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (EntityConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EntityConditionsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (WorldConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            WorldConditionsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (ItemConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ItemConditionsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (PlayerConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PlayerConditionsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (CustomEIConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            CustomEIConditionsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (PlaceholderConditionFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PlaceholderConditionFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (PlaceholderConditionGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PlaceholderConditionGroupFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (MaterialAndTagsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            MaterialAndTagsFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (MaterialAndTagsGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            MaterialAndTagsGroupFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (EntityTypeGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EntityTypeGroupFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (EntityTypeForGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EntityTypeForGroupFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (DetailedSlotsEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DetailedSlotsEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }
        else if (UsePerDayFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            UsePerDayFeatureEditorManager.getInstance().receiveMessage(p, e.getMessage());
        }

    }
}
