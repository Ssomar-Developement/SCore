package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import com.ssomar.sevents.events.player.equip.armor.ArmorType;
import com.ssomar.sevents.events.player.equip.armor.PlayerEquipArmorEvent;
import com.ssomar.sevents.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TESTEVENT implements Listener {

    private final List<String> blockedMaterials;

    public TESTEVENT() {
        blockedMaterials = new ArrayList<>();
        blockedMaterials.add("BEACON");
        blockedMaterials.add("CRAFTING_TABLE");
        blockedMaterials.add("ENCHANTMENT_TABLE");
        blockedMaterials.add("ENDER_CHEST");
        blockedMaterials.add("DIODE_BLOCK_OFF");
        blockedMaterials.add("DIODE_BLOCK_ON");
        blockedMaterials.add("REDSTONE_COMPARATOR_OFF");
        blockedMaterials.add("REDSTONE_COMPARATOR_ON");
        blockedMaterials.add("LEVER");
        blockedMaterials.add("DAYLIGHT_DETECTOR_INVERTED");
        blockedMaterials.add("DAYLIGHT_DETECTOR");
        blockedMaterials.add("CARTOGRAPHY_TABLE");
        blockedMaterials.add("ANVIL");
        blockedMaterials.add("CHIPPED_ANVIL");
        blockedMaterials.add("DAMAGED_ANVIL");
        blockedMaterials.add("GRINDSTONE");
        blockedMaterials.add("LOOM");
        blockedMaterials.add("STONECUTTER");
        blockedMaterials.add("BELL");
        blockedMaterials.add("SMITHING_TABLE");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerInteractEvent(PlayerInteractEvent e) {
        SsomarDev.testMsg("playerInteractEvent1" ,true);
        if (e.useItemInHand().equals(Event.Result.DENY)) {
            return;
        }
        SsomarDev.testMsg("playerInteractEvent2" ,true);
        if (e.getAction() == Action.PHYSICAL) {
            return;
        }
        SsomarDev.testMsg("playerInteractEvent3" ,true);
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            SsomarDev.testMsg("playerInteractEvent3" ,true);
            if (!e.useInteractedBlock().equals(Event.Result.DENY)) {
                if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && !player.isSneaking()) {// Having both of these checks is useless, might as well do it though.
                    // Some blocks have actions when you right click them which stops the client from equipping the armor in hand.
                    if ((e.getClickedBlock().getState() != null) && !Version.is1v11Less() && e.getClickedBlock().getState() instanceof Container) {
                        return;
                    }
                    SsomarDev.testMsg("playerInteractEvent4" ,true);
                    Material mat = e.getClickedBlock().getType();
                    if (mat.toString().contains("SIGN")
                            || mat.toString().contains("TRAPDOOR")
                            || mat.toString().contains("DOOR")
                            || mat.toString().contains("BUTTON")
                            || mat.toString().contains("FENCE_GATE")
                            || mat.toString().contains("BED")
                            || mat.toString().contains("FLOWER_POT")) {
                        return;
                    }
                    for (String s : blockedMaterials) {
                        if (mat.name().equalsIgnoreCase(s)) {
                            return;
                        }
                    }
                }
            }
            ArmorType newArmorType = ArmorType.matchType(e.getItem(), true);
            if (newArmorType != null) {
                SsomarDev.testMsg("playerInteractEvent5" ,true);
                if (newArmorType.equals(ArmorType.HELMET) && isAirOrNull(e.getPlayer().getInventory().getHelmet()) || newArmorType.equals(ArmorType.CHESTPLATE) && isAirOrNull(e.getPlayer().getInventory().getChestplate()) || newArmorType.equals(ArmorType.LEGGINGS) && isAirOrNull(e.getPlayer().getInventory().getLeggings()) || newArmorType.equals(ArmorType.BOOTS) && isAirOrNull(e.getPlayer().getInventory().getBoots())) {
                    PlayerEquipArmorEvent armorEquipEvent = new PlayerEquipArmorEvent(e.getPlayer(), PlayerEquipArmorEvent.EquipMethod.HOTBAR, ArmorType.matchType(e.getItem(), true), null, e.getItem());
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    SsomarDev.testMsg("playerInteractEvent6" ,true);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                        player.updateInventory();
                    }
                } else if (Version.is1v19v4Plus()){
                    ItemStack currentArmorPiece = null;
                    if(newArmorType.equals(ArmorType.HELMET) && !isAirOrNull(e.getPlayer().getInventory().getHelmet())) {
                        currentArmorPiece = e.getPlayer().getInventory().getHelmet();
                    }
                    else if(newArmorType.equals(ArmorType.CHESTPLATE) && !isAirOrNull(e.getPlayer().getInventory().getChestplate())){
                        currentArmorPiece = e.getPlayer().getInventory().getChestplate();
                        SsomarDev.testMsg("playerInteractEvent7" ,true);
                    }
                    else if(newArmorType.equals(ArmorType.LEGGINGS) && !isAirOrNull(e.getPlayer().getInventory().getLeggings())){
                        currentArmorPiece = e.getPlayer().getInventory().getLeggings();
                    }
                    else if(newArmorType.equals(ArmorType.BOOTS) && !isAirOrNull(e.getPlayer().getInventory().getBoots())){
                        currentArmorPiece = e.getPlayer().getInventory().getBoots();
                    }
                    if (currentArmorPiece != null) {
                        PlayerEquipArmorEvent armorEquipEvent = new PlayerEquipArmorEvent(e.getPlayer(), PlayerEquipArmorEvent.EquipMethod.HOTBAR, ArmorType.matchType(e.getItem(), true), currentArmorPiece, e.getItem());
                        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                        if (armorEquipEvent.isCancelled()) {
                            e.setCancelled(true);
                            player.updateInventory();
                        }
                    }
                }
            }
        }
    }

    /**
     * A utility method to support versions that use null or air ItemStacks.
     */
    public static boolean isAirOrNull(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }
}
