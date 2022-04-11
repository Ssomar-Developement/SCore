package com.ssomar.score.conditions;

import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Optional;

@Getter @Setter
public class PlayerConditions extends NewConditions {


    public boolean verifConditions(Player player, Optional<Player> playerOpt, SendMessage messageSender) {

        for(Object object : getConditions().values()){
            PlayerCondition playerCondition = (PlayerCondition)object;
            if(!playerCondition.verifCondition(player, playerOpt, messageSender)) return false;
        }
        return true;
    }

    /*private List<IfPlayerHasExecutableItem> ifPlayerHasExecutableItem;
    private static final String IF_PLAYER_HAS_EXECUTABLE_ITEM_MSG = " &cYou don't have all correct ExecutableItems to active the activator: &6%activator% &cof this item!";
    private String ifPlayerHasExecutableItemMsg;

    private Map<Material, Integer> ifPlayerHasItem;
    private static final String IF_PLAYER_HAS_ITEM_MSG = " &cYou don't have all correct Items to active the activator: &6%activator% &cof this item!";
    private String ifPlayerHasItemMsg;

    private Map<PotionEffectType, Integer> ifPlayerHasEffect;
    private static final String IF_PLAYER_HAS_EFFECT_MSG = " &cYou don't have all correct effects to active the activator: &6%activator% &cof this item!";
    private String ifPlayerHasEffectMsg;

    private Map<PotionEffectType, Integer> ifPlayerHasEffectEquals;
    private String ifPlayerHasEffectEqualsMsg;



    public boolean verifConditions(Player p, Player toMsg) {



        if (this.hasIfPlayerHasExecutableItem() || this.hasIfPlayerHasItem()) {
            ItemStack[] content = p.getInventory().getContents();

            Map<Material, Integer> verifI = new HashMap<>(this.getIfPlayerHasItem());

            int cpt = -1;
            for (ItemStack is : content) {
                cpt++;
                if (is == null) continue;

                if (verifI.containsKey(is.getType()) && ((verifI.get(is.getType()) == cpt) || verifI.get(is.getType()) == -1 && cpt == p.getInventory().getHeldItemSlot())) {
                    verifI.remove(is.getType());
                }
            }

            if (!verifI.isEmpty()) {
                this.getSm().sendMessage(toMsg, this.getIfPlayerHasItemMsg());
                return false;
            }
        }

        if (this.hasIfPlayerHasExecutableItem()) {
            if (SCore.hasExecutableItems) {
                for (IfPlayerHasExecutableItem cdt : this.ifPlayerHasExecutableItem) {
                    if (!cdt.verify(p)) {
                        this.getSm().sendMessage(toMsg, this.getIfPlayerHasExecutableItemMsg());
                        return false;
                    }
                }
            }
        }

        if (this.ifPlayerHasEffect.size() > 0) {
            for (PotionEffectType pET : ifPlayerHasEffect.keySet()) {
                if (!p.hasPotionEffect(pET)) {
                    this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectMsg());
                    return false;
                } else {
                    if (p.getPotionEffect(pET).getAmplifier() < ifPlayerHasEffect.get(pET)) {
                        this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectMsg());
                        return false;
                    }
                }
            }
        }

        if (this.ifPlayerHasEffectEquals.size() > 0) {
            for (PotionEffectType pET : ifPlayerHasEffectEquals.keySet()) {
                if (!p.hasPotionEffect(pET)) {
                    this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectEqualsMsg());
                    return false;
                } else {
                    if (p.getPotionEffect(pET).getAmplifier() != ifPlayerHasEffectEquals.get(pET)) {
                        this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectEqualsMsg());
                        return false;
                    }
                }
            }
        }


        return true;
    }*/

    /*public static PlayerConditions getPlayerConditions(ConfigurationSection playerCdtSection, List<String> errorList, String pluginName) {

        PlayerConditions pCdt = new PlayerConditions();

        List<Material> mat = new ArrayList<>();
        for (String s : playerCdtSection.getStringList("ifIsOnTheBlock")) {
            try {
                mat.add(Material.valueOf(s.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        pCdt.setIfIsOnTheBlock(mat);
        pCdt.setIfIsOnTheBlockMsg(playerCdtSection.getString("ifIsOnTheBlockMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_IS_ON_THE_BLOCK_MSG));

        List<Material> mat2 = new ArrayList<>();
        for (String s : playerCdtSection.getStringList("ifIsNotOnTheBlock")) {
            try {
                mat2.add(Material.valueOf(s.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        pCdt.setIfIsNotOnTheBlock(mat2);
        pCdt.setIfIsNotOnTheBlockMsg(playerCdtSection.getString("ifIsNotOnTheBlockMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_IS_NOT_ON_THE_BLOCK_MSG));


        if (playerCdtSection.contains("ifInRegion") || playerCdtSection.contains("ifNotInRegion")) {

            if (SCore.is1v12()) {
                errorList.add(pluginName + " Error the conditions ifInRegion and ifNotInRegion are not available in 1.12 due to a changement of worldguard API ");
            } else {
                pCdt.setIfInRegion(playerCdtSection.getStringList("ifInRegion"));

                pCdt.setIfNotInRegion(playerCdtSection.getStringList("ifNotInRegion"));
            }
        }

        mat = new ArrayList<>();
        for (String s : playerCdtSection.getStringList("ifTargetBlock")) {
            try {
                mat.add(Material.valueOf(s.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        pCdt.setIfTargetBlock(mat);
        pCdt.setIfTargetBlockMsg(playerCdtSection.getString("ifTargetBlockMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_TARGET_BLOCK_MSG));

        mat = new ArrayList<>();
        for (String s : playerCdtSection.getStringList("ifNotTargetBlock")) {
            try {
                mat.add(Material.valueOf(s.toUpperCase()));
            } catch (Exception ignored) {
            }
        }

        List<IfPlayerHasExecutableItem> verifEI = new ArrayList<>();
        if (playerCdtSection.contains("ifPlayerHasExecutableItem")) {
            ConfigurationSection sec = playerCdtSection.getConfigurationSection("ifPlayerHasExecutableItem");
            if (sec != null && sec.getKeys(false).size() > 0) {
                for (String s : sec.getKeys(false)) {
                    IfPlayerHasExecutableItem cdt = new IfPlayerHasExecutableItem(sec.getConfigurationSection(s));
                    if (cdt.isValid())
                        verifEI.add(cdt);
                    else
                        errorList.add(pluginName + " Invalid configuration of ifPlayerHasExecutableItems with id : " + s + " !");
                }
            } else if (playerCdtSection.getStringList("ifPlayerHasExecutableItem").size() > 0) {
                for (String s : playerCdtSection.getStringList("ifPlayerHasExecutableItem")) {
                    IfPlayerHasExecutableItem cdt = new IfPlayerHasExecutableItem(s);
                    if (cdt.isValid())
                        verifEI.add(cdt);
                    else
                        errorList.add(pluginName + " Invalid configuration of ifPlayerHasExecutableItems: " + s + " !");
                }
            }
        }

        pCdt.setIfPlayerHasExecutableItem(verifEI);
        pCdt.setIfPlayerHasExecutableItemMsg(playerCdtSection.getString("ifPlayerHasExecutableItemMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HAS_EXECUTABLE_ITEM_MSG));

        Map<Material, Integer> verifI = new HashMap<>();
        for (String s : playerCdtSection.getStringList("ifPlayerHasItem")) {
            String[] spliter;
            if (s.contains(":") && (spliter = s.split(":")).length == 2) {
                int slot = 0;
                Material material = null;
                try {
                    material = Material.valueOf(spliter[0]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasItem condition: " + s + " correct form > MATERIAL:SLOT  example> DIAMOND:5 !");
                    continue;
                }
                try {
                    slot = Integer.parseInt(spliter[1]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasItem condition: " + s + " correct form > MATERIAL:SLOT  example> DIAMOND:5 !");
                    continue;
                }
                verifI.put(material, slot);
            }
        }
        pCdt.setIfPlayerHasItem(verifI);
        pCdt.setIfPlayerHasItemMsg(playerCdtSection.getString("ifPlayerHasItemMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HAS_ITEM_MSG));

        Map<PotionEffectType, Integer> verifETP = new HashMap<>();
        for (String s : playerCdtSection.getStringList("ifPlayerHasEffect")) {
            String[] spliter;
            if (s.contains(":") && (spliter = s.split(":")).length == 2) {
                int value = 0;
                PotionEffectType type = PotionEffectType.getByName(spliter[0]);
                if (type == null) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasEffect condition: " + s + " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }

                try {
                    value = Integer.parseInt(spliter[1]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasEffect condition: " + s + " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }
                verifETP.put(type, value);
            }
        }

        pCdt.setIfPlayerHasEffect(verifETP);
        pCdt.setIfPlayerHasEffectMsg(playerCdtSection.getString("ifPlayerHasEffectMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HAS_EFFECT_MSG));

        Map<PotionEffectType, Integer> verifETPE = new HashMap<>();
        for (String s : playerCdtSection.getStringList("ifPlayerHasEffectEquals")) {
            String[] spliter;
            if (s.contains(":") && (spliter = s.split(":")).length == 2) {
                int value = 0;
                PotionEffectType type = PotionEffectType.getByName(spliter[0]);
                if (type == null) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasEffectEquals condition: " + s + " correct form > EFFECT:AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }

                try {
                    value = Integer.parseInt(spliter[1]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasEffectEquals condition: " + s + " correct form > EFFECT:AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }
                verifETPE.put(type, value);
            }
        }

        pCdt.setIfPlayerHasEffectEquals(verifETPE);
        pCdt.setIfPlayerHasEffectEqualsMsg(playerCdtSection.getString("ifPlayerHasEffectEqualsMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HAS_EFFECT_MSG));

        pCdt.setIfCursorDistance(playerCdtSection.getString("ifCursorDistance", ""));
        pCdt.setIfCursorDistanceMsg(playerCdtSection.getString("ifCursorDistanceMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_CURSOR_DISTANCE_MSG));

        return pCdt;

    }*/


    @Override
    public NewConditions createNewInstance() {
        return new PlayerConditions();
    }
}
