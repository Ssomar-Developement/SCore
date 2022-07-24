package com.ssomar.score.commands.runnable;

import com.ssomar.score.utils.SendMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public abstract class SCommand {

    public static SendMessage sm = new SendMessage();
    protected static String tooManyArgs = "&cThere are &6too many args &cfor the command: &e";
    protected static String notEnoughArgs = "&cThere is &6not enough args &cfor the command: &e";
    protected static String invalidMaterial = "&cA SCommand contains an &6invalid material&c: &e";
    protected static String invalidWorld = "&cA SCommand contains an &6invalid world&c: &e";
    protected static String invalidEntityType = "&cA SCommand contains an &6invalid entityType&c: &e";
    protected static String invalidQuantity = "&cA SCommand contains an &6invalid quantity&c: &e";
    protected static String invalidDistance = "&cA SCommand contains an &6invalid distance&c: &e";
    protected static String invalidCoordinate = "&cA SCommand contains an &6invalid coordinate&c: &e";
    protected static String invalidTime = "&cA SCommand contains an &6invalid time or number&c: &e";
    protected static String invalidInteger = "&cA SCommand contains an &6invalid integer&c: &e";
    protected static String invalidDouble = "&cA SCommand contains an &6invalid double&c: &e";
    protected static String invalidSlot = "&cA SCommand contains an &6invalid slot &7&o(valids: [-1 to 40])&c: &e";
    protected static String invalidRange = "&cA SCommand contains an &6invalid range&c: &e";
    protected static String invalidBoolean = "&cA SCommand contains an &6invalid boolean&c: &e";
    protected static String invalidColor = "&cA SCommand contains an &6invalid color &7&o( &8https://hub.spigotmc.org/javadocs/spigot/org/bukkit/ChatColor.html &7&o) &c: &e";
    protected static String invalidExecutableItems = "&cA SCommand contains an &6invalid id of ExecutableItems&c: &e";

    public static ArgumentChecker checkInteger(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();
        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
            try {
                Double.valueOf(arg);
            } catch (NumberFormatException e) {
                ac.setValid(false);
                ac.setError(invalidInteger + arg + " &cfor command: &e" + template);
            }
        }
        return ac;
    }

    public static ArgumentChecker checkDouble(@NotNull String arg, boolean isFinalVerification, String template) {
        ArgumentChecker ac = new ArgumentChecker();
        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
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

        if ((!arg.contains("%") && !isFinalVerification) || isFinalVerification) {
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

    public abstract Optional<String> verify(List<String> args, boolean isFinalVerification);

}
