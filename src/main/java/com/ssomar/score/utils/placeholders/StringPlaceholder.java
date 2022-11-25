package com.ssomar.score.utils.placeholders;

import com.ssomar.score.SCore;
import com.ssomar.score.features.custom.variables.real.VariableReal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;

@Getter
@Setter
public class StringPlaceholder extends PlaceholdersInterface implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /* placeholders of the player */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final PlayerPlaceholders playerPlch = new PlayerPlaceholders();

    /* placeholders of the target player */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final TargetPlaceholders targetPlch = new TargetPlaceholders();

    /* placeholders of the owner */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final OwnerPlaceholders ownerPlch = new OwnerPlaceholders();

    /* placeholders of the owner */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final ProjectilePlaceholders projectilePlch = new ProjectilePlaceholders();

    /* placeholders of the time */
    private final transient TimePlaceholders timePlch = new TimePlaceholders();
    /* placeholders of the entity */
    private final EntityPlaceholders entityPlch = new EntityPlaceholders();
    /* placeholders of the target entity */
    private final EntityPlaceholders targetEntityPlch = new EntityPlaceholders();
    /* placeholders of the block */
    private final BlockPlaceholders blockPlch = new BlockPlaceholders();
    /* placeholders of the target block */
    private final TargetBlockPlaceholders targetBlockPlch = new TargetBlockPlaceholders();
    /* placeholders of the around target player */
    AroundPlayerTargetPlaceholders aroundPlayerTargetPlch = new AroundPlayerTargetPlaceholders();
    /* placeholders of the around target entity */
    AroundEntityTargetPlaceholders aroundEntityTargetPlch = new AroundEntityTargetPlaceholders();
    /* placeholders of the item */
    private String activator = "";
    private String item = "";
    private String quantity = "";
    private String usage = "";
    private String usageLimit = "";
    private String maxUsePerDayItem = "";
    private String maxUsePerDayActivator = "";
    /* placeholders tools */
    private String launcher = "";
    private String blockface = "";
    /* placeholders of the cooldown */
    private String cooldown = "";
    private List<VariableReal> variables = new ArrayList<>();
    private Map<String, String> extraPlaceholders = new HashMap<>();

    public static String replaceRandomPlaceholders(String s) {
        String result = s;
        if (result.contains("%rand:")) {
            int part1;
            int part2;
            String[] decompRand = result.split("%rand:");
            boolean cont = true;
            for (String strRand : decompRand) {
                if (cont) {
                    cont = false;
                    continue;
                }

                if (strRand.contains("%")) {
                    String[] decomp = strRand.split("%");
                    if ((decomp.length >= 2 || (strRand.endsWith("%") && decomp.length == 1)) && decomp[0].contains("|")) {
                        decomp = decomp[0].split("\\|");

                        try {
                            part1 = Integer.parseInt(decomp[0]);
                            part2 = Integer.parseInt(decomp[1]);
                        } catch (Exception e) {
                            continue;
                        }

                        if (part1 < part2) {
                            int random = part1 + (int) (Math.random() * ((part2 - part1) + 1));
                            result = result.replace("%rand:" + part1 + "|" + part2 + "%", random + "");
                        }
                    }
                }
            }
        }

        return result;
    }

    public void setPlayerPlcHldr(UUID uuid) {
        playerPlch.setPlayerPlcHldr(uuid);
    }

    public void setPlayerPlcHldr(UUID uuid, int fixSlot) {
        playerPlch.setPlayerPlcHldr(uuid, fixSlot);
    }

    public void setTargetPlcHldr(UUID uuid) {
        targetPlch.setPlayerPlcHldr(uuid);
    }

    public void setOwnerPlcHldr(UUID uuid) {
        ownerPlch.setPlayerPlcHldr(uuid);
    }

    public void setProjectilePlcHldr(Projectile proj, String blockFace) {
        projectilePlch.setProjectilePlcHldr(proj, blockFace);
    }

    public void setEntityPlcHldr(UUID uuid) {
        entityPlch.setEntityPlcHldr(uuid);
    }

    public void setTargetEntityPlcHldr(UUID uuid) {
        targetEntityPlch.setEntityPlcHldr(uuid);
    }

    public void setBlockPlcHldr(Block block) {
        blockPlch.setBlockPlcHldr(block);
    }

    public void setBlockPlcHldr(Block block, Material fixType) {
        blockPlch.setBlockPlcHldr(block, fixType);
    }

    public void setTargetBlockPlcHldr(Block block) {
        targetBlockPlch.setTargetBlockPlcHldr(block);
    }

    public void setTargetBlockPlcHldr(Block block, Material fixType) {
        targetBlockPlch.setTargetBlockPlcHldr(block, fixType);
    }

    public void setAroundTargetPlayerPlcHldr(UUID uuid) {
        aroundPlayerTargetPlch.setPlayerPlcHldr(uuid);
    }

    public void setAroundTargetEntityPlcHldr(UUID uuid) {
        aroundEntityTargetPlch.setEntityPlcHldr(uuid);
    }

    public void reloadAllPlaceholders() {
        playerPlch.reloadPlayerPlcHldr();
        targetPlch.reloadPlayerPlcHldr();
        ownerPlch.reloadPlayerPlcHldr();
        entityPlch.reloadEntityPlcHldr();
        if(targetEntityPlch != null) targetEntityPlch.reloadEntityPlcHldr();
        blockPlch.reloadBlockPlcHldr();
        targetBlockPlch.reloadTargetBlockPlcHldr();
        aroundPlayerTargetPlch.reloadPlayerPlcHldr();
        aroundEntityTargetPlch.reloadEntityPlcHldr();
        /* delayed command with old version has this to null */
        if (projectilePlch != null) projectilePlch.reloadProjectilePlcHldr();
    }

    public List<String> replacePlaceholders(List<String> list) {
        this.reloadAllPlaceholders();
        List<String> result = new ArrayList<>();
        for (String s : list) {
            result.add(replacePlaceholderWithoutReload(s, true));
        }
        return result;
    }

    public String replacePlaceholder(String str) {
        return replacePlaceholder(str, true);
    }

    public String replacePlaceholder(String str, boolean withPAPI) {
        this.reloadAllPlaceholders();
        return replacePlaceholderWithoutReload(str, withPAPI);
    }

    public String replacePlaceholderWithoutReload(String str, boolean withPAPI) {
        String s = str;

        if (str.trim().length() == 0) return "";

        s = replaceRandomPlaceholders(s);

        Map<String, String> placeholders = new HashMap<>();

        if (this.hasActivator()) {
            placeholders.put("%activator%", this.getActivator());
        }
        if (this.hasItem()) {
            placeholders.put("%item%", Matcher.quoteReplacement(this.getItem()));
        }
        if (this.hasQuantity()) {
            s = replaceCalculPlaceholder(s, "%quantity%", quantity, true);
            s = replaceCalculPlaceholder(s, "%amount%", quantity, true);
        }
        if (this.hasCoolodwn()) {
            placeholders.put("%cooldown%", this.getCooldown());
        }
        if (this.hasBlockFace()) {
            placeholders.put("%blockface%", this.getBlockface());
        }
        if (this.hasUsage()) {
            s = replaceCalculPlaceholder(s, "%usage%", usage, true);
        }
        if (this.hasMaxUsePerDayActivator()) {
            placeholders.put("%max_use_per_day_activator%", this.getMaxUsePerDayActivator());
        }
        if (this.hasMaxUsePerDayItem()) {
            placeholders.put("%max_use_per_day_item%", this.getMaxUsePerDayItem());
        }

        placeholders.put("%timestamp%", (System.currentTimeMillis()-1667000000000L)+"");

        /* there are replace with calcul */
        if (variables != null) {
            for (VariableReal var : variables) {
                s = var.replaceVariablePlaceholder(s);
            }
        }

        placeholders.putAll(playerPlch.getPlaceholders());
        s = playerPlch.replacePlaceholder(s);

        placeholders.putAll(targetPlch.getPlaceholders());
        s = targetPlch.replacePlaceholder(s);

        placeholders.putAll(ownerPlch.getPlaceholders());
        s = ownerPlch.replacePlaceholder(s);

        s = entityPlch.replacePlaceholder(s);

        /* //TODO oen abstract class for block and targetblock*/
        placeholders.putAll(blockPlch.getPlaceholders());
        s = blockPlch.replacePlaceholder(s);

        s = targetBlockPlch.replacePlaceholder(s);
        /* ^^^ ^^^*/

        placeholders.putAll(aroundPlayerTargetPlch.getPlaceholders());
        s = aroundPlayerTargetPlch.replacePlaceholder(s);

        s = aroundEntityTargetPlch.replacePlaceholder(s);

        if(timePlch != null) s = timePlch.replacePlaceholder(s);

        if (projectilePlch != null) s = projectilePlch.replacePlaceholder(s);

        if(targetEntityPlch != null) s = targetEntityPlch.replacePlaceholder(s);

        if (extraPlaceholders != null && !extraPlaceholders.isEmpty()) {
            for (String key : extraPlaceholders.keySet()) {
                placeholders.put(key, extraPlaceholders.get(key));
            }
        }

        final String[] keys = placeholders.keySet().toArray(new String[0]);
        final String[] values = placeholders.values().toArray(new String[0]);

        s = StringUtils.replaceEach( s, keys, values );

        if (withPAPI) return replacePlaceholderOfPAPI(s);
        else return s;
    }

    public String replacePlaceholderOfPAPI(String s) {
        String replace = s;
        if(SCore.hasPlaceholderAPI) {
            UUID uuid;
            if ((uuid = playerPlch.getPlayerUUID()) == null) {
                if(!Bukkit.getOnlinePlayers().isEmpty()){
                    uuid = Bukkit.getOnlinePlayers().iterator().next().getUniqueId();
                }
            }
            Player p;
            if (uuid != null && (p = Bukkit.getPlayer(uuid)) != null)
                replace = PlaceholderAPI.setPlaceholders(p, replace);
        }
        return replace;
    }

    public boolean hasActivator() {
        return activator.length() != 0;
    }

    public boolean hasItem() {
        return item.length() != 0;
    }

    public boolean hasQuantity() {
        return quantity.length() != 0;
    }

    public boolean hasCoolodwn() {
        return cooldown.length() != 0;
    }

    public boolean hasUsageLimit() {
        return usageLimit.length() != 0;
    }

    public boolean hasBlockFace() {
        return this.blockface.length() != 0;
    }

    public boolean hasUsage() {
        return this.usage.length() != 0;
    }

    public boolean hasMaxUsePerDayItem() {
        return maxUsePerDayItem.length() != 0;
    }

    public boolean hasMaxUsePerDayActivator() {
        return maxUsePerDayActivator.length() != 0;
    }
}
