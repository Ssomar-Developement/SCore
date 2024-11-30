package com.ssomar.score.commands.runnable;

import com.ssomar.executableblocks.api.ExecutableBlocksAPI;
import com.ssomar.score.SCore;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.GetItem;
import com.ssomar.score.utils.emums.BetterEquipmentSlot;
import com.ssomar.score.utils.messages.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class SCommand {

    public static final SendMessage sm = new SendMessage();
    protected static final String tooManyArgs = "&cThere are &6too many args &cfor the command: &e";
    protected static final String notEnoughArgs = "&cThere is &6not enough args &cfor the command: &e";
    protected static final String invalidMaterial = "&cA SCommand contains an &6invalid material&c: &e";
    protected static final String invalidEquipmentSlot = "&cA SCommand contains an &6invalid equipmentSlot&c: &e";
    protected static final String invalidWorld = "&cA SCommand contains an &6invalid world&c: &e";
    protected static final String invalidEntityType = "&cA SCommand contains an &6invalid entityType&c: &e";
    protected static final String invalidQuantity = "&cA SCommand contains an &6invalid quantity&c: &e";
    protected static final String invalidDistance = "&cA SCommand contains an &6invalid distance&c: &e";
    protected static final String invalidCoordinate = "&cA SCommand contains an &6invalid coordinate&c: &e";
    protected static final String invalidTime = "&cA SCommand contains an &6invalid time or number&c: &e";
    protected static final String invalidInteger = "&cA SCommand contains an &6invalid integer&c: &e";
    protected static final String invalidDouble = "&cA SCommand contains an &6invalid double&c: &e";
    protected static final String invalidUUID = "&cA SCommand contains an &6invalid UUID&c: &e";
    protected static final String invalidSlot = "&cA SCommand contains an &6invalid slot &7&o(valids: [-1 to 40])&c: &e";
    protected static final String invalidRange = "&cA SCommand contains an &6invalid range&c: &e";
    protected static final String invalidBoolean = "&cA SCommand contains an &6invalid boolean&c: &e";
    protected static final String invalidColor = "&cA SCommand contains an &6invalid color &7&o( &8https://hub.spigotmc.org/javadocs/spigot/org/bukkit/ChatColor.html &7&o) &c: &e";

    protected static final String invalidBarColor = "&cA SCommand contains an &6invalid bar color &7&o( &8https://hub.spigotmc.org/javadocs/spigot/org/bukkit/boss/BarColor.html &7&o) &c: &e";
    protected static String invalidExecutableItems = "&cA SCommand contains an &6invalid id of ExecutableItems&c: &e";

    @Setter
    @Getter
    private int priority;

    @Setter
    @Getter
    private boolean canExecuteCommands;

    // I dont want to convert all the commands at the same time, so I kee the old settings and will update part by
    @Getter
    @Setter
    private boolean newSettingsMode = false;
    @Getter
    @Setter
    private List<CommandSetting> settings = new ArrayList<>();

    public SCommand() {
        this.priority = 1;
        this.canExecuteCommands = false;
    }

    public static ArgumentChecker checkInteger(@NotNull String arg, boolean isFinalVerification, String template) {
        return checkInteger(arg, isFinalVerification, template, false);
    }

    public static ArgumentChecker checkInteger(@NotNull String arg, boolean isFinalVerification, String template, boolean acceptPercentage) {
        ArgumentChecker ac = new ArgumentChecker();
        if ((!arg.contains("%") && !isFinalVerification) || (isFinalVerification && !acceptPercentage)) {
            try {
                Double.valueOf(arg);
            } catch (NumberFormatException e) {
                ac.setValid(false);
                ac.setError(invalidInteger + arg + " &cfor command: &e" + template);
            }
        }
        return ac;
    }

    public static ArgumentChecker checkFloat(@NotNull String arg, boolean isFinalVerification, String template) {
        return checkFloat(arg, isFinalVerification, template, false);
    }

    public static ArgumentChecker checkFloat(@NotNull String arg, boolean isFinalVerification, String template, boolean acceptPercentage) {
        ArgumentChecker ac = new ArgumentChecker();
        if ((!arg.contains("%") && !isFinalVerification) || (isFinalVerification && !acceptPercentage)) {
            try {
                Float.valueOf(arg);
            } catch (NumberFormatException e) {
                ac.setValid(false);
                ac.setError(invalidInteger + arg + " &cfor command: &e" + template);
            }
        }
        return ac;
    }

    public static ArgumentChecker checkDouble(@NotNull String arg, boolean isFinalVerification, String template) {
        return checkDouble(arg, isFinalVerification, template, false);
    }

    public static ArgumentChecker checkDouble(@NotNull String arg, boolean isFinalVerification, String template, boolean acceptPercentage) {
        ArgumentChecker ac = new ArgumentChecker();
        if ((!arg.contains("%") && !isFinalVerification) || (isFinalVerification && !acceptPercentage)) {
            try {
                Double.valueOf(arg);
            } catch (NumberFormatException e) {
                ac.setValid(false);
                ac.setError(invalidDouble + arg + " &cfor command: &e" + template);
            }
        }
        return ac;
    }

    public static ArgumentChecker checkBoolean(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();
        if (!arg.equalsIgnoreCase("true") && !arg.equalsIgnoreCase("false")) {
            ac.setValid(false);
            ac.setError(invalidBoolean + arg + " &cfor command: &e" + template);
        }

        return ac;
    }

    public static ArgumentChecker checkSlot(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            boolean err = false;
            int check = 0;
            try {
                check = Integer.parseInt(arg);
            } catch (NumberFormatException e) {
                err = true;
            }
            if (err || check < -1 || check > 40) {
                ac.setValid(false);
                ac.setError(invalidSlot + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkMaterial(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if (((!arg.contains("%") && !isFinalVerification) || isFinalVerification) && !GetItem.containsCustomPluginWord(arg)) {
            /* Some commands accept FURNCANE[LIT=TRUE] for example */
            if (arg.contains("[")) {
                arg = arg.split("\\[")[0];
            }
            try {
                Material.valueOf(arg.toUpperCase());
            } catch (Exception e) {
                ac.setValid(false);
                ac.setError(invalidMaterial + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkEnchantment(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            if (Enchantment.getByKey(NamespacedKey.minecraft(arg.toLowerCase())) == null) {
                ac.setValid(false);
                ac.setError("&cA SCommand contains an &6invalid enchantment&c: &e" + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkAttribute(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            try {
                Attribute.valueOf(arg.toUpperCase());
            } catch (Exception e) {
                ac.setValid(false);
                ac.setError("&cA SCommand contains an &6invalid attribute&c: &e" + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkBlockFace(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            try {
                org.bukkit.block.BlockFace.valueOf(arg.toUpperCase());
            } catch (Exception e) {
                ac.setValid(false);
                ac.setError("&cA SCommand contains an &6invalid block face&c: &e" + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkEquipmentSlot(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            if (!BetterEquipmentSlot.isEquipmentSlot(arg)) {
                ac.setValid(false);
                ac.setError(invalidEquipmentSlot + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkUUID(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            try {
                UUID.fromString(arg.toUpperCase());
            } catch (Exception e) {
                ac.setValid(false);
                ac.setError(invalidUUID + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkEntity(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            try {
                EntityType.valueOf(arg.toUpperCase());
            } catch (Exception e) {
                ac.setValid(false);
                ac.setError(invalidEntityType + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkChatColor(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            try {
                ChatColor.valueOf(arg.toUpperCase());
            } catch (Exception e) {
                ac.setValid(false);
                ac.setError(invalidColor + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkBarColor(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            try {
                BarColor.valueOf(arg.toUpperCase());
            } catch (Exception e) {
                ac.setValid(false);
                ac.setError(invalidBarColor + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkExecutableBlockID(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if (!SCore.hasExecutableBlocks) {
            ac.setValid(false);
            ac.setError("&cA SCommand requires &6ExecutableBlocks&c to be executed:  &cfor command: &e" + template);
        } else {
            if (isFinalVerification && !ExecutableBlocksAPI.getExecutableBlocksManager().isValidID(arg)) {
                ac.setValid(false);
                ac.setError("&cA SCommand contains an &6invalid ExecutableBlock&c: &e" + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkExecutableItemID(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if (!SCore.hasExecutableItems) {
            ac.setValid(false);
            ac.setError("&cA SCommand requires &6ExecutableItems&c to be executed:  &cfor command: &e" + template);
        } else {
            if (isFinalVerification && !ExecutableItemsAPI.getExecutableItemsManager().isValidID(arg)) {
                ac.setValid(false);
                ac.setError("&cA SCommand contains an &6invalid ExecutableItem&c: &e" + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public static ArgumentChecker checkWorld(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();

        if (isFinalVerification) {
            Optional<World> worldOptional = AllWorldManager.getWorld(arg);
            if (!worldOptional.isPresent()) {
                ac.setValid(false);
                ac.setError(invalidWorld + arg + " &cfor command: &e" + template);
            }
        }

        return ac;
    }

    public abstract List<String> getNames();

    public abstract String getTemplate();

    public abstract ChatColor getColor();

    public abstract ChatColor getExtraColor();

    public ChatColor getExtraColorNotNull() {
        if (getExtraColor() == null) return ChatColor.DARK_PURPLE;
        return getExtraColor();
    }

    public ChatColor getColorNotNull() {
        if (getColor() == null) return ChatColor.LIGHT_PURPLE;
        return getColor();
    }

    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (isNewSettingsMode()) {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg).append(" ");
            }
            return verifySettings(sb.toString());
        }
        return Optional.empty();
    }

    public Optional<String> verifySettings(String entry) {

        for (String name : getNames()) {
            if (entry.startsWith(name)) {
                entry = entry.substring(name.length());
                break;
            }
        }
        entry = entry.trim();
        //SsomarDev.testMsg("entry: " + entry, true);

        List<String> arguments = new ArrayList<>();
        arguments.addAll(Arrays.asList(entry.split(" ")));
        // fully new system
        for (CommandSetting setting : getSettings()) {
            if (arguments.size() > 0) {
                for(String name : setting.getNames()) {
                    Optional<String> value = arguments.stream().filter(arg -> arg.startsWith(name + ":")).findFirst();
                    if (value.isPresent()) {
                        ArgumentChecker ac = setting.checkValue(value.get().replace(name + ":", ""), getTemplate());
                        if (ac != null && !ac.isValid()) return Optional.of(ac.getError());
                        break;
                    }
                }
            }
        }
        return Optional.empty();
    }
}
