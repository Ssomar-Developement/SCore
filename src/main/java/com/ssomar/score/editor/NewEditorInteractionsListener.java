package com.ssomar.score.editor;

import com.ssomar.score.features.custom.activators.group.ActivatorsFeatureEditor;
import com.ssomar.score.features.custom.activators.group.ActivatorsFeatureEditorManager;
import com.ssomar.score.features.custom.armortrim.ArmorTrimEditor;
import com.ssomar.score.features.custom.armortrim.ArmorTrimEditorManager;
import com.ssomar.score.features.custom.aroundblock.aroundblock.AroundBlockFeatureEditor;
import com.ssomar.score.features.custom.aroundblock.aroundblock.AroundBlockFeatureEditorManager;
import com.ssomar.score.features.custom.aroundblock.group.AroundBlockGroupFeatureEditor;
import com.ssomar.score.features.custom.aroundblock.group.AroundBlockGroupFeatureEditorManager;
import com.ssomar.score.features.custom.attributes.attribute.AttributeFullOptionsFeatureEditor;
import com.ssomar.score.features.custom.attributes.attribute.AttributeFullOptionsFeatureEditorManager;
import com.ssomar.score.features.custom.attributes.group.AttributesGroupFeatureEditor;
import com.ssomar.score.features.custom.attributes.group.AttributesGroupFeatureEditorManager;
import com.ssomar.score.features.custom.autoupdate.AutoUpdateFeaturesEditor;
import com.ssomar.score.features.custom.autoupdate.AutoUpdateFeaturesEditorManager;
import com.ssomar.score.features.custom.bannersettings.BannerSettingsFeatureEditor;
import com.ssomar.score.features.custom.bannersettings.BannerSettingsFeatureEditorManager;
import com.ssomar.score.features.custom.blocktitle.BlockTitleFeaturesEditor;
import com.ssomar.score.features.custom.blocktitle.BlockTitleFeaturesEditorManager;
import com.ssomar.score.features.custom.brewingstand.BrewingStandFeaturesEditor;
import com.ssomar.score.features.custom.brewingstand.BrewingStandFeaturesEditorManager;
import com.ssomar.score.features.custom.canbeusedbyowner.CanBeUsedOnlyByOwnerFeaturesEditor;
import com.ssomar.score.features.custom.canbeusedbyowner.CanBeUsedOnlyByOwnerFeaturesEditorManager;
import com.ssomar.score.features.custom.cancelevents.CancelEventFeaturesEditor;
import com.ssomar.score.features.custom.cancelevents.CancelEventFeaturesEditorManager;
import com.ssomar.score.features.custom.chiseledbookshelf.ChiseledBookshelfFeaturesEditor;
import com.ssomar.score.features.custom.chiseledbookshelf.ChiseledBookshelfFeaturesEditorManager;
import com.ssomar.score.features.custom.conditions.ConditionFeatureEditor;
import com.ssomar.score.features.custom.conditions.ConditionFeatureEditorManager;
import com.ssomar.score.features.custom.conditions.block.parent.BlockConditionsFeatureEditor;
import com.ssomar.score.features.custom.conditions.block.parent.BlockConditionsFeatureEditorManager;
import com.ssomar.score.features.custom.conditions.custom.parent.CustomConditionsFeatureEditor;
import com.ssomar.score.features.custom.conditions.custom.parent.CustomConditionsFeatureEditorManager;
import com.ssomar.score.features.custom.conditions.entity.parent.EntityConditionsFeatureEditor;
import com.ssomar.score.features.custom.conditions.entity.parent.EntityConditionsFeatureEditorManager;
import com.ssomar.score.features.custom.conditions.item.parent.ItemConditionsFeatureEditor;
import com.ssomar.score.features.custom.conditions.item.parent.ItemConditionsFeatureEditorManager;
import com.ssomar.score.features.custom.conditions.placeholders.group.PlaceholderConditionGroupFeatureEditor;
import com.ssomar.score.features.custom.conditions.placeholders.group.PlaceholderConditionGroupFeatureEditorManager;
import com.ssomar.score.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeatureEditor;
import com.ssomar.score.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeatureEditorManager;
import com.ssomar.score.features.custom.conditions.player.parent.PlayerConditionsFeatureEditor;
import com.ssomar.score.features.custom.conditions.player.parent.PlayerConditionsFeatureEditorManager;
import com.ssomar.score.features.custom.conditions.world.parent.WorldConditionsFeatureEditor;
import com.ssomar.score.features.custom.conditions.world.parent.WorldConditionsFeatureEditorManager;
import com.ssomar.score.features.custom.container.ContainerFeaturesEditor;
import com.ssomar.score.features.custom.container.ContainerFeaturesEditorManager;
import com.ssomar.score.features.custom.cooldowns.CooldownFeatureEditor;
import com.ssomar.score.features.custom.cooldowns.CooldownFeatureEditorManager;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocksEditor;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocksEditorManager;
import com.ssomar.score.features.custom.detailedeffects.DetailedEffectsEditor;
import com.ssomar.score.features.custom.detailedeffects.DetailedEffectsEditorManager;
import com.ssomar.score.features.custom.detaileditems.DetailedItemsEditor;
import com.ssomar.score.features.custom.detaileditems.DetailedItemsEditorManager;
import com.ssomar.score.features.custom.detailedslots.DetailedSlotsEditor;
import com.ssomar.score.features.custom.detailedslots.DetailedSlotsEditorManager;
import com.ssomar.score.features.custom.directional.DirectionalFeaturesEditor;
import com.ssomar.score.features.custom.directional.DirectionalFeaturesEditorManager;
import com.ssomar.score.features.custom.displayConditions.DisplayConditionsFeaturesEditor;
import com.ssomar.score.features.custom.displayConditions.DisplayConditionsFeaturesEditorManager;
import com.ssomar.score.features.custom.drop.DropFeaturesEditor;
import com.ssomar.score.features.custom.drop.DropFeaturesEditorManager;
import com.ssomar.score.features.custom.durabilityFeatures.DurabilityFeaturesEditor;
import com.ssomar.score.features.custom.durabilityFeatures.DurabilityFeaturesEditorManager;
import com.ssomar.score.features.custom.enchantments.enchantment.EnchantmentWithLevelFeatureEditor;
import com.ssomar.score.features.custom.enchantments.enchantment.EnchantmentWithLevelFeatureEditorManager;
import com.ssomar.score.features.custom.enchantments.group.EnchantmentsGroupFeatureEditor;
import com.ssomar.score.features.custom.enchantments.group.EnchantmentsGroupFeatureEditorManager;
import com.ssomar.score.features.custom.entities.entity.EntityTypeForGroupFeatureEditor;
import com.ssomar.score.features.custom.entities.entity.EntityTypeForGroupFeatureEditorManager;
import com.ssomar.score.features.custom.entities.group.EntityTypeGroupFeatureEditor;
import com.ssomar.score.features.custom.entities.group.EntityTypeGroupFeatureEditorManager;
import com.ssomar.score.features.custom.foodFeatures.FoodFeaturesEditor;
import com.ssomar.score.features.custom.foodFeatures.FoodFeaturesEditorManager;
import com.ssomar.score.features.custom.furnace.FurnaceFeaturesEditor;
import com.ssomar.score.features.custom.furnace.FurnaceFeaturesEditorManager;
import com.ssomar.score.features.custom.givefirstjoin.GiveFirstJoinFeaturesEditor;
import com.ssomar.score.features.custom.givefirstjoin.GiveFirstJoinFeaturesEditorManager;
import com.ssomar.score.features.custom.headfeatures.HeadFeaturesEditor;
import com.ssomar.score.features.custom.headfeatures.HeadFeaturesEditorManager;
import com.ssomar.score.features.custom.hiders.HidersEditor;
import com.ssomar.score.features.custom.hiders.HidersEditorManager;
import com.ssomar.score.features.custom.hopper.HopperFeaturesEditor;
import com.ssomar.score.features.custom.hopper.HopperFeaturesEditorManager;
import com.ssomar.score.features.custom.ifhas.executableitems.attribute.HasExecutableItemFeatureEditor;
import com.ssomar.score.features.custom.ifhas.executableitems.attribute.HasExecutableItemFeatureEditorManager;
import com.ssomar.score.features.custom.ifhas.executableitems.group.HasExecutableItemGroupFeatureEditor;
import com.ssomar.score.features.custom.ifhas.executableitems.group.HasExecutableItemGroupFeatureEditorManager;
import com.ssomar.score.features.custom.ifhas.items.attribute.HasItemFeatureEditor;
import com.ssomar.score.features.custom.ifhas.items.attribute.HasItemFeatureEditorManager;
import com.ssomar.score.features.custom.ifhas.items.group.HasItemGroupFeatureEditor;
import com.ssomar.score.features.custom.ifhas.items.group.HasItemGroupFeatureEditorManager;
import com.ssomar.score.features.custom.loop.LoopFeaturesEditor;
import com.ssomar.score.features.custom.loop.LoopFeaturesEditorManager;
import com.ssomar.score.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeatureEditor;
import com.ssomar.score.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeatureEditorManager;
import com.ssomar.score.features.custom.materialwithgroupsandtags.materialandtags.MaterialAndTagsFeatureEditor;
import com.ssomar.score.features.custom.materialwithgroupsandtags.materialandtags.MaterialAndTagsFeatureEditorManager;
import com.ssomar.score.features.custom.othereicooldowns.cooldown.OtherEICooldownEditor;
import com.ssomar.score.features.custom.othereicooldowns.cooldown.OtherEICooldownEditorManager;
import com.ssomar.score.features.custom.othereicooldowns.group.OtherEICooldownGroupFeatureEditor;
import com.ssomar.score.features.custom.othereicooldowns.group.OtherEICooldownGroupFeatureEditorManager;
import com.ssomar.score.features.custom.particles.group.ParticlesGroupFeatureEditor;
import com.ssomar.score.features.custom.particles.group.ParticlesGroupFeatureEditorManager;
import com.ssomar.score.features.custom.particles.particle.ParticleFeatureEditor;
import com.ssomar.score.features.custom.particles.particle.ParticleFeatureEditorManager;
import com.ssomar.score.features.custom.patterns.group.PatternsGroupFeatureEditor;
import com.ssomar.score.features.custom.patterns.group.PatternsGroupFeatureEditorManager;
import com.ssomar.score.features.custom.patterns.subgroup.PatternFeatureEditor;
import com.ssomar.score.features.custom.patterns.subgroup.PatternFeatureEditorManager;
import com.ssomar.score.features.custom.patterns.subpattern.SubPatternFeatureEditor;
import com.ssomar.score.features.custom.patterns.subpattern.SubPatternFeatureEditorManager;
import com.ssomar.score.features.custom.potioneffects.group.PotionEffectGroupFeatureEditor;
import com.ssomar.score.features.custom.potioneffects.group.PotionEffectGroupFeatureEditorManager;
import com.ssomar.score.features.custom.potioneffects.potioneffect.PotionEffectFeatureEditor;
import com.ssomar.score.features.custom.potioneffects.potioneffect.PotionEffectFeatureEditorManager;
import com.ssomar.score.features.custom.potionsettings.PotionSettingsFeatureEditor;
import com.ssomar.score.features.custom.potionsettings.PotionSettingsFeatureEditorManager;
import com.ssomar.score.features.custom.required.executableitems.group.RequiredExecutableItemGroupFeatureEditor;
import com.ssomar.score.features.custom.required.executableitems.group.RequiredExecutableItemGroupFeatureEditorManager;
import com.ssomar.score.features.custom.required.executableitems.item.RequiredExecutableItemFeatureEditor;
import com.ssomar.score.features.custom.required.executableitems.item.RequiredExecutableItemFeatureEditorManager;
import com.ssomar.score.features.custom.required.experience.RequiredExperienceEditor;
import com.ssomar.score.features.custom.required.experience.RequiredExperienceEditorManager;
import com.ssomar.score.features.custom.required.items.group.RequiredItemGroupFeatureEditor;
import com.ssomar.score.features.custom.required.items.group.RequiredItemGroupFeatureEditorManager;
import com.ssomar.score.features.custom.required.items.item.RequiredItemFeatureEditor;
import com.ssomar.score.features.custom.required.items.item.RequiredItemFeatureEditorManager;
import com.ssomar.score.features.custom.required.level.RequiredLevelEditor;
import com.ssomar.score.features.custom.required.level.RequiredLevelEditorManager;
import com.ssomar.score.features.custom.required.magic.group.RequiredMagicGroupFeatureEditor;
import com.ssomar.score.features.custom.required.magic.group.RequiredMagicGroupFeatureEditorManager;
import com.ssomar.score.features.custom.required.magic.magic.RequiredMagicFeatureEditor;
import com.ssomar.score.features.custom.required.magic.magic.RequiredMagicFeatureEditorManager;
import com.ssomar.score.features.custom.required.mana.RequiredManaEditor;
import com.ssomar.score.features.custom.required.mana.RequiredManaEditorManager;
import com.ssomar.score.features.custom.required.money.RequiredMoneyEditor;
import com.ssomar.score.features.custom.required.money.RequiredMoneyEditorManager;
import com.ssomar.score.features.custom.required.parent.RequiredGroupEditor;
import com.ssomar.score.features.custom.required.parent.RequiredGroupEditorManager;
import com.ssomar.score.features.custom.restrictions.RestrictionsEditor;
import com.ssomar.score.features.custom.restrictions.RestrictionsEditorManager;
import com.ssomar.score.features.custom.useperday.UsePerDayFeatureEditor;
import com.ssomar.score.features.custom.useperday.UsePerDayFeatureEditorManager;
import com.ssomar.score.features.custom.variables.base.group.VariablesGroupFeatureEditor;
import com.ssomar.score.features.custom.variables.base.group.VariablesGroupFeatureEditorManager;
import com.ssomar.score.features.custom.variables.base.variable.VariableFeatureEditor;
import com.ssomar.score.features.custom.variables.base.variable.VariableFeatureEditorManager;
import com.ssomar.score.features.custom.variables.update.group.VariableUpdateGroupFeatureEditor;
import com.ssomar.score.features.custom.variables.update.group.VariableUpdateGroupFeatureEditorManager;
import com.ssomar.score.features.custom.variables.update.variable.VariableUpdateFeatureEditor;
import com.ssomar.score.features.custom.variables.update.variable.VariableUpdateFeatureEditorManager;
import com.ssomar.score.hardness.hardness.HardnessEditor;
import com.ssomar.score.hardness.hardness.HardnessEditorManager;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.SProjectileEditor;
import com.ssomar.score.projectiles.SProjectileEditorManager;
import com.ssomar.score.projectiles.features.fireworkFeatures.FireworkFeaturesEditor;
import com.ssomar.score.projectiles.features.fireworkFeatures.FireworkFeaturesEditorManager;
import com.ssomar.score.projectiles.features.visualItemFeature.VisualItemFeatureEditor;
import com.ssomar.score.projectiles.features.visualItemFeature.VisualItemFeatureEditorManager;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.sobject.menu.SObjectsEditorAbstract;
import com.ssomar.score.sobject.menu.defaultobjects.NewDefaultObjectsEditor;
import com.ssomar.score.sobject.menu.defaultobjects.NewDefaultObjectsEditorManager;
import com.ssomar.score.variables.VariableEditor;
import com.ssomar.score.variables.VariableEditorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class NewEditorInteractionsListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryCreativeEvent e) {
        //SsomarDev.testMsg(">> click1: "+e.getClick());
        if (!(e.getWhoClicked() instanceof Player) || e.getClick() != ClickType.MIDDLE) return;
        
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
                this.manage(player, itemS, "", e);
        } catch (NullPointerException error) {
            error.printStackTrace();
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        //SsomarDev.testMsg(">> click2: "+e.getClick());
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();
        try {
            if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
        } catch (NullPointerException error) {
            return;
        }

        int slot = e.getSlot();
        ItemStack itemS = e.getClickedInventory().getItem(slot);
        //boolean notNullItem = itemS!=null;

        try {
            if (e.getInventory().getHolder() instanceof GUI)
                this.manage(player, itemS, "", e);
        } catch (NullPointerException error) {
            error.printStackTrace();
        }
    }

    public void manage(Player player, ItemStack itemS, String guiType, InventoryClickEvent e) {

        e.setCancelled(true);

        if (itemS == null) return;

        if (!itemS.hasItemMeta()) return;

        if (itemS.getItemMeta().getDisplayName().isEmpty()) return;

        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof SObjectsEditorAbstract) {
            NewSObjectsManagerEditor.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof NewDefaultObjectsEditor) {
            NewDefaultObjectsEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredLevelEditor) {
            RequiredLevelEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredExperienceEditor) {
            RequiredExperienceEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredMoneyEditor) {
            RequiredMoneyEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredManaEditor) {
            RequiredManaEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredItemFeatureEditor) {
            RequiredItemFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredItemGroupFeatureEditor) {
            RequiredItemGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredExecutableItemFeatureEditor) {
            RequiredExecutableItemFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredExecutableItemGroupFeatureEditor) {
            RequiredExecutableItemGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredMagicFeatureEditor) {
            RequiredMagicFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RequiredMagicGroupFeatureEditor) {
            RequiredMagicGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }else if (holder instanceof RequiredGroupEditor) {
            RequiredGroupEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof DropFeaturesEditor) {
            DropFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof HidersEditor) {
            HidersEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof ArmorTrimEditor) {
            ArmorTrimEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof GiveFirstJoinFeaturesEditor) {
            GiveFirstJoinFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof EnchantmentWithLevelFeatureEditor) {
            EnchantmentWithLevelFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof EnchantmentsGroupFeatureEditor) {
            EnchantmentsGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof AttributeFullOptionsFeatureEditor) {
            AttributeFullOptionsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof AttributesGroupFeatureEditor) {
            AttributesGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof RestrictionsEditor) {
            RestrictionsEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof CancelEventFeaturesEditor) {
            CancelEventFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof ActivatorsFeatureEditor) {
            ActivatorsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof CooldownFeatureEditor) {
            CooldownFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof LoopFeaturesEditor) {
            LoopFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof ConditionFeatureEditor) {
            ConditionFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof BlockConditionsFeatureEditor) {
            BlockConditionsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof EntityConditionsFeatureEditor) {
            EntityConditionsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof WorldConditionsFeatureEditor) {
            WorldConditionsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof ItemConditionsFeatureEditor) {
            ItemConditionsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof PlayerConditionsFeatureEditor) {
            PlayerConditionsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof CustomConditionsFeatureEditor) {
            CustomConditionsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof PlaceholderConditionFeatureEditor) {
            PlaceholderConditionFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof PlaceholderConditionGroupFeatureEditor) {
            PlaceholderConditionGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof MaterialAndTagsFeatureEditor) {
            MaterialAndTagsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof MaterialAndTagsGroupFeatureEditor) {
            MaterialAndTagsGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof EntityTypeGroupFeatureEditor) {
            EntityTypeGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof EntityTypeForGroupFeatureEditor) {
            EntityTypeForGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof DetailedSlotsEditor) {
            DetailedSlotsEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof UsePerDayFeatureEditor) {
            UsePerDayFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof DetailedBlocksEditor) {
            DetailedBlocksEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof DetailedEffectsEditor) {
            DetailedEffectsEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if(holder instanceof DetailedItemsEditor){
            DetailedItemsEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof HeadFeaturesEditor) {
            HeadFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof PotionEffectFeatureEditor) {
            PotionEffectFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof PotionEffectGroupFeatureEditor) {
            PotionEffectGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof PotionSettingsFeatureEditor) {
            PotionSettingsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof SubPatternFeatureEditor) {
            SubPatternFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof PatternFeatureEditor) {
            PatternFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof PatternsGroupFeatureEditor) {
            PatternsGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof BannerSettingsFeatureEditor) {
            BannerSettingsFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof VariableFeatureEditor) {
            VariableFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof VariablesGroupFeatureEditor) {
            VariablesGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof VariableUpdateFeatureEditor) {
            VariableUpdateFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof VariableUpdateGroupFeatureEditor) {
            VariableUpdateGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof HasExecutableItemFeatureEditor) {
            HasExecutableItemFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof HasExecutableItemGroupFeatureEditor) {
            HasExecutableItemGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof HasItemFeatureEditor) {
            HasItemFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof HasItemGroupFeatureEditor) {
            HasItemGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof BlockTitleFeaturesEditor) {
            BlockTitleFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof AroundBlockFeatureEditor) {
            AroundBlockFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof AroundBlockGroupFeatureEditor) {
            AroundBlockGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof SProjectileEditor) {
            SProjectileEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof HardnessEditor) {
            HardnessEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof VariableEditor) {
            VariableEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof VisualItemFeatureEditor) {
            VisualItemFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof FireworkFeaturesEditor) {
            FireworkFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof ParticleFeatureEditor) {
            ParticleFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof ParticlesGroupFeatureEditor) {
            ParticlesGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof OtherEICooldownEditor) {
            OtherEICooldownEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof OtherEICooldownGroupFeatureEditor) {
            OtherEICooldownGroupFeatureEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof AutoUpdateFeaturesEditor) {
            AutoUpdateFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof CanBeUsedOnlyByOwnerFeaturesEditor) {
            CanBeUsedOnlyByOwnerFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof DisplayConditionsFeaturesEditor) {
            DisplayConditionsFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof FoodFeaturesEditor) {
            FoodFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof DurabilityFeaturesEditor) {
            DurabilityFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof ContainerFeaturesEditor) {
            ContainerFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof HopperFeaturesEditor) {
            HopperFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof FurnaceFeaturesEditor) {
            FurnaceFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof ChiseledBookshelfFeaturesEditor) {
            ChiseledBookshelfFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        } else if (holder instanceof DirectionalFeaturesEditor) {
            DirectionalFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }
        else if (holder instanceof BrewingStandFeaturesEditor) {
            BrewingStandFeaturesEditorManager.getInstance().clicked(player, itemS, e.getClick());
            return;
        }

    }

    /*@EventHandler(priority = EventPriority.LOWEST)
    public void onChatEvent(AsyncPlayerChatPreviewEvent e) {
        SsomarDev.testMsg("ChatEventpreview > "+e.getMessage());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatEvent(PlayerChatEvent e) {
        SsomarDev.testMsg("Chat sync > "+e.getMessage());
    }

     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatEvent2(PlayerCommandPreprocessEvent e) {
       if(e.getMessage().contains("/score interact ")) {
           String message = e.getMessage().replace("/score interact ", "");
           receiveMessage(e.getPlayer(), message, e);
       }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatEvent(AsyncPlayerChatEvent e) {
        receiveMessage(e.getPlayer(), e.getMessage(), e);
    }

    public void receiveMessage(Player player, String message, Cancellable e){
        Player p = player;
        if (NewSObjectsManagerEditor.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            NewSObjectsManagerEditor.getInstance().receiveMessage(p, message);
        } else if (NewDefaultObjectsEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            NewDefaultObjectsEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredLevelEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredLevelEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredExperienceEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredExperienceEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredMoneyEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredMoneyEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredManaEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredManaEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredItemFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredItemFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredItemGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredItemGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredExecutableItemFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredExecutableItemFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredExecutableItemGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredExecutableItemGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredMagicFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredMagicFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (RequiredMagicGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredMagicGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        }else if (RequiredGroupEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RequiredGroupEditorManager.getInstance().receiveMessage(p, message);
        } else if (DropFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DropFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (HidersEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HidersEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (ArmorTrimEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ArmorTrimEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (GiveFirstJoinFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            GiveFirstJoinFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (EnchantmentWithLevelFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EnchantmentWithLevelFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (EnchantmentsGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EnchantmentsGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (AttributeFullOptionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            AttributeFullOptionsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (AttributesGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            AttributesGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (RestrictionsEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            RestrictionsEditorManager.getInstance().receiveMessage(p, message);
        } else if (CancelEventFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            CancelEventFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (ActivatorsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ActivatorsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (CooldownFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            CooldownFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (LoopFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            LoopFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (ConditionFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ConditionFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (BlockConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            BlockConditionsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (EntityConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EntityConditionsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (WorldConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            WorldConditionsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (ItemConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ItemConditionsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (PlayerConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PlayerConditionsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (CustomConditionsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            CustomConditionsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (PlaceholderConditionFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PlaceholderConditionFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (PlaceholderConditionGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PlaceholderConditionGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (MaterialAndTagsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            MaterialAndTagsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (MaterialAndTagsGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            MaterialAndTagsGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (EntityTypeGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EntityTypeGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (EntityTypeForGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            EntityTypeForGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (DetailedSlotsEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DetailedSlotsEditorManager.getInstance().receiveMessage(p, message);
        } else if (UsePerDayFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            UsePerDayFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (DetailedBlocksEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DetailedBlocksEditorManager.getInstance().receiveMessage(p, message);
        } else if (DetailedEffectsEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DetailedEffectsEditorManager.getInstance().receiveMessage(p, message);
        } else if (DetailedItemsEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DetailedItemsEditorManager.getInstance().receiveMessage(p, message);
        } else if (HeadFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HeadFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (PotionEffectFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PotionEffectFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (PotionEffectGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PotionEffectGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (PotionSettingsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PotionSettingsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (SubPatternFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            SubPatternFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (PatternFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PatternFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (PatternsGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            PatternsGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (BannerSettingsFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            BannerSettingsFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (VariableFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            VariableFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (VariablesGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            VariablesGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (VariableUpdateFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            VariableUpdateFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (VariableUpdateGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            VariableUpdateGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (HasExecutableItemFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HasExecutableItemFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (HasExecutableItemGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HasExecutableItemGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (HasItemFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HasItemFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (HasItemGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HasItemGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (BlockTitleFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            BlockTitleFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (AroundBlockFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            AroundBlockFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (AroundBlockGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            AroundBlockGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (SProjectileEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            SProjectileEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (HardnessEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HardnessEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (VariableEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            VariableEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (ParticleFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ParticleFeatureEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (VisualItemFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            VisualItemFeatureEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (FireworkFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            FireworkFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (ParticlesGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ParticlesGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if(FireworkFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)){
            e.setCancelled(true);
            FireworkFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }else if (OtherEICooldownEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            OtherEICooldownEditorManager.getInstance().receiveMessage(p, message);
        } else if (OtherEICooldownGroupFeatureEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            OtherEICooldownGroupFeatureEditorManager.getInstance().receiveMessage(p, message);
        } else if (AutoUpdateFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            AutoUpdateFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (CanBeUsedOnlyByOwnerFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            CanBeUsedOnlyByOwnerFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (DisplayConditionsFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DisplayConditionsFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (FoodFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            FoodFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (DurabilityFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DurabilityFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (ContainerFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ContainerFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (HopperFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            HopperFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (FurnaceFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            FurnaceFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }
        else if (ChiseledBookshelfFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            ChiseledBookshelfFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (DirectionalFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            DirectionalFeaturesEditorManager.getInstance().receiveMessage(p, message);
        } else if (BrewingStandFeaturesEditorManager.getInstance().getRequestWriting().containsKey(p)) {
            e.setCancelled(true);
            BrewingStandFeaturesEditorManager.getInstance().receiveMessage(p, message);
        }
    }
}
