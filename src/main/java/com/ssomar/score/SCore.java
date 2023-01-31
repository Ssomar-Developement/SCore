package com.ssomar.score;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.plotsquared.core.PlotAPI;
import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.score.CommandsClass;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageInterface;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.data.Database;
import com.ssomar.score.events.EventsHandler;
import com.ssomar.score.events.loop.LoopManager;
import com.ssomar.score.features.custom.cooldowns.CooldownsHandler;
import com.ssomar.score.features.custom.useperday.manager.UsagePerDayManager;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.loader.SProjectileLoader;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.ProtocolibAPI;
import com.ssomar.score.usedapi.PlaceholderAPISCoreExpansion;
import com.ssomar.score.utils.Utils;
import com.ssomar.score.variables.loader.VariablesLoader;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SCore extends JavaPlugin implements SPlugin {

    public static final String NAME = "SCore";
    public static final String NAME_COLOR = "&eSCore";
    public static final String NAME_2 = "[SCore]";
    public static SCore plugin;
    public static boolean hasPlaceholderAPI = false;
    public static boolean hasExecutableItems = false;
    public static boolean hasExecutableBlocks = false;
    public static boolean hasExecutableEvents = false;
    public static boolean hasCustomPiglinsTrades = false;
    public static boolean hasSParkour = false;
    public static boolean hasWorldGuard = false;
    public static boolean hasVault = false;
    public static boolean hasIridiumSkyblock = false;
    public static boolean hasSuperiorSkyblock2 = false;
    public static boolean hasBentoBox = false;
    public static boolean hasMultiverse = false;
    public static boolean hasLands = false;
    public static boolean hasTowny = false;
    public static boolean hasGriefPrevention = false;
    public static boolean hasGriefDefender = false;
    public static boolean hasCoreProtect = false;
    public static boolean hasProtocolLib = false;
    public static boolean hasPlotSquared = false;
    public static boolean hasResidence = false;
    public static boolean hasNBTAPI = false;
    public static ProtocolManager protocolManager;
    public static boolean hasHeadDatabase = false;
    public static boolean hasHeadDB = false;
    public static boolean hasMythicMobs = false;
    public static boolean hasHolographicDisplays = false;
    public static boolean hasDecentHolograms = false;
    public static boolean hasCMI = false;
    public static boolean hasAureliumSkills = false;
    public static boolean hasItemsAdder = false;
    public static boolean hasOraxen = false;
    public static boolean hasShopGUIPlus = false;
    public static boolean hasRoseLoot = false;
    public static boolean hasMMOCore = false;
    private static boolean is1v8 = false;
    private static boolean is1v9 = false;
    private static boolean is1v10 = false;
    private static boolean is1v11 = false;
    private static boolean is1v12 = false;
    private static boolean is1v13 = false;
    private static boolean is1v14 = false;
    private static boolean is1v15 = false;
    private static boolean is1v16 = false;
    private static boolean is1v16v1 = false;
    private static boolean is1v17 = false;
    private static boolean is1v18 = false;
    private static boolean is1v19 = false;
    private static boolean is1v19v1 = false;
    private CommandsClass commandClass;

    /* The server is in 1.12 ? */
    public static boolean is1v8() {
        return is1v8;
    }

    /* The server is in 1.12 ? */
    public static boolean is1v9() {
        return is1v9;
    }

    /* The server is in 1.12 ? */
    public static boolean is1v10() {
        return is1v10;
    }

    /* The server is in 1.12 ? */
    public static boolean is1v11() {
        return is1v11;
    }

    /* The server is in 1.12 ? */
    public static boolean is1v12() {
        return is1v12;
    }

    /* The server is in 1.13 ? */
    public static boolean is1v13() {
        return is1v13;
    }

    /* The server is in 1.14 ? */
    public static boolean is1v14() {
        return is1v14;
    }

    /* The server is in 1.15 ? */
    public static boolean is1v15() {
        return is1v15;
    }

    /* The server is in 1.16 ? */
    public static boolean is1v16() {
        return is1v16;
    }

    /* The server is in 1.16 ? */
    public static boolean is1v16v1() {
        return is1v16v1;
    }

    /* The server is in 1.17 ? */
    public static boolean is1v17() {
        return is1v17;
    }

    /* The server is in 1.18 ? */
    public static boolean is1v18() {
        return is1v18;
    }

    /* The server is in 1.19 ? */
    public static boolean is1v19() {
        return is1v19;
    }
    /* The server is in 1.19 ? */
    public static boolean is1v19v1() {
        return is1v19v1;
    }

    /* The server is in 1.12 or - ? */
    public static boolean is1v11Less() {
        return is1v8() || is1v9() || is1v10() || is1v11();
    }

    /* The server is in 1.12 or - ? */
    public static boolean is1v12Less() {
        return is1v8() || is1v9() || is1v10() || is1v11() || is1v12();
    }

    /* The server is in 1.13 or - ? */
    public static boolean is1v13Less() {
        return is1v8() || is1v9() || is1v10() || is1v11() || is1v12() || is1v13();
    }

    /* The server is in 1.16 or + ? */
    public static boolean is1v16Plus() {
        return is1v16() || is1v17() || is1v18() || is1v19();
    }

    /* The server is in 1.17 or + ? */
    public static boolean is1v17Plus() {
        return is1v17() || is1v18() || is1v19();
    }

    /* The server is in 1.17 or + ? */
    public static boolean is1v18Plus() {
        return is1v18() || is1v19();
    }

    @Override
    public void onEnable() {
        plugin = this;
        commandClass = new CommandsClass(this);

        Utils.sendConsoleMsg("&7================ " + NAME_COLOR + " &7================");

        this.initVersion();

        this.loadDependency();

        GUI.init();

        GeneralConfig.getInstance();

        TM.getInstance().load();

        TM.getInstance().loadTexts();

        MessageMain.getInstance().load();

        MessageMain.getInstance().loadMessagesOf(plugin, MessageInterface.getMessagesEnum(Message.values()));

        /* Loop instance part */
        LoopManager.getInstance();

        ActionbarHandler.getInstance().load();

        /* Database */
        Database.getInstance().load();

        /* Events instance part */
        EventsHandler.getInstance().setup(this);

        /* Commands part */
        this.getCommand("score").setExecutor(commandClass);

        /* Projectiles instance part */
        SProjectileLoader.getInstance().load();

        /* Variables instance part */
        VariablesLoader.getInstance().load();

        CommandsHandler.getInstance().onEnable();

        UsagePerDayManager.getInstance();

        CooldownsHandler.loadCooldowns();

        if(SCore.hasPlaceholderAPI){
            new PlaceholderAPISCoreExpansion(this).register();
        }

        Utils.sendConsoleMsg("&7================ " + NAME_COLOR + " &7================");
    }

    public void loadDependency() {
        /* Soft-Dependency part */
        if (Bukkit.getPluginManager().getPlugin("ExecutableItems") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7ExecutableItems hooked !");
            hasExecutableItems = true;
        }

        if (Bukkit.getPluginManager().getPlugin("ExecutableBlocks") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7ExecutableBlocks hooked !");
            hasExecutableBlocks = true;
        }

        if (Bukkit.getPluginManager().getPlugin("ExecutableEvents") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7ExecutableEvents hooked !");
            hasExecutableEvents= true;
        }

        if (Bukkit.getPluginManager().getPlugin("CustomPiglinsTrades") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7CustomPiglinsTrades hooked !");
            hasCustomPiglinsTrades = true;
        }

        if (Bukkit.getPluginManager().getPlugin("SParkour") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7SParkour hooked !");
            hasSParkour = true;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7PlaceholderAPI hooked !");
            hasPlaceholderAPI = true;
        }

        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7WorldGuard hooked !");
            hasWorldGuard = true;
        }

        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Vault hooked !");
            hasVault = true;
        }
        if (Bukkit.getPluginManager().getPlugin("IridiumSkyblock") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7IridiumSkyblock hooked !");
            hasIridiumSkyblock = true;
        }
        if (Bukkit.getPluginManager().getPlugin("SuperiorSkyblock2") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7SuperiorSkyblock2 hooked !");
            hasSuperiorSkyblock2 = true;
        }
        if (Bukkit.getPluginManager().getPlugin("BentoBox") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7BentoBox hooked !");
            hasBentoBox = true;
        }
        if (Bukkit.getPluginManager().getPlugin("Multiverse-Core") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Multiverse-Core hooked !");
            hasMultiverse = true;
        }

        if (Bukkit.getPluginManager().getPlugin("Lands") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Lands hooked !");
            hasLands = true;
        }

        if (Bukkit.getPluginManager().getPlugin("Towny") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Towny hooked !");
            hasTowny = true;
        }

        if (Bukkit.getPluginManager().getPlugin("GriefPrevention") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7GriefPrevention hooked !");
            hasGriefPrevention = true;
        }

        if (Bukkit.getPluginManager().getPlugin("GriefDefender") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7GriefDefender hooked !");
            hasGriefDefender = true;
        }

        if (Bukkit.getPluginManager().getPlugin("CoreProtect") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7CoreProtect hooked !");
            hasCoreProtect = true;
        }

        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7ProtocolLib hooked !");
            hasProtocolLib = true;
            /* Protocolib */
            protocolManager = ProtocolLibrary.getProtocolManager();
            ProtocolibAPI.reduceDamageIndicator();
        }

        if (Bukkit.getPluginManager().getPlugin("NBTAPI") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7NBTAPI hooked !");
            hasNBTAPI = true;
        }

        if (Bukkit.getPluginManager().getPlugin("Residence") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Residence hooked !");
            hasResidence = true;
        }


        if (Bukkit.getPluginManager().getPlugin("PlotSquared") != null) {
            try {
                PlotAPI plotAPI = new PlotAPI();
                Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7PlotSquared hooked !");
                hasPlotSquared = true;
            } catch (NoClassDefFoundError e) {
                SCore.plugin.getServer().getLogger().severe("[" + NAME + "] PlotSquared hooked BUT you haven't the good version ! (try to update it) !");
                hasPlotSquared = false;
            }
        }

        if (Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7HeadDatabase hooked !");
            hasHeadDatabase = true;
        }

        if (Bukkit.getPluginManager().getPlugin("HeadDB") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7HeadDB hooked !");
            hasHeadDB = true;
        }

        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7MythicMobs hooked !");
            hasMythicMobs = true;
        }

        if(Bukkit.getPluginManager().getPlugin("DecentHolograms") != null){
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7DecentHolograms hooked !");
            hasDecentHolograms = true;
        }

        if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            try {
                HolographicDisplaysAPI.get(SCore.plugin);
                Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7HolographicDisplays hooked !");
                hasHolographicDisplays = true;
            }  catch (Exception | Error e) {
                SCore.plugin.getServer().getLogger().severe("[" + NAME + "] HolographicDisplays hooked BUT you haven't the good version ! (require 3.0 or higher) !");
                e.printStackTrace();
                hasHolographicDisplays = false;
            }
        }

        if (Bukkit.getPluginManager().getPlugin("CMI") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7CMI hooked !");
            hasCMI = true;
        }

        if (Bukkit.getPluginManager().getPlugin("AureliumSkills") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7AureliumSkills hooked !");
            hasAureliumSkills = true;
        }

        if (Bukkit.getPluginManager().getPlugin("ItemsAdder") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7ItemsAdder hooked !");
            hasItemsAdder = true;
        }

        if (Bukkit.getPluginManager().getPlugin("Oraxen") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Oraxen hooked !");
            hasOraxen = true;
        }

        if (Bukkit.getPluginManager().getPlugin("ShopGUIPlus") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7ShopGUIPlus hooked !");
            hasShopGUIPlus = true;
        }

        if (Bukkit.getPluginManager().getPlugin("RoseLoot") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7RoseLoot hooked !");
            hasRoseLoot = true;
        }

        if (Bukkit.getPluginManager().getPlugin("MMOCore") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7MMOCore hooked !");
            hasMMOCore = true;
        }
    }

    @Override
    public void onDisable() {
        Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Save UsagePerDay....");
        UsagePerDayManager.getInstance().save();
        Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Save UsagePerDay done !");
        Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Save Commands...");
        CommandsHandler.getInstance().onDisable();
        Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Save Commands done !");
        Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Save Cooldowns...");
        CooldownsHandler.closeServerSaveAll();
        Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Save Cooldowns done !");
    }

    public void onReload() {
        Utils.sendConsoleMsg("&7================ " + NAME_COLOR+ " &7================");
        GeneralConfig.getInstance().reload();

        LoopManager.getInstance();

        /* Projectiles instance part */
        SProjectileLoader.getInstance().reload();

        /* Variables instance part */
        VariablesLoader.getInstance().reload();

        TM.getInstance().loadTexts();

        MessageMain.getInstance().loadMessagesOf(plugin, MessageInterface.getMessagesEnum(Message.values()));

        Utils.sendConsoleMsg("&7================ " + NAME_COLOR + " &7================");
    }

    @Override
    public String getShortName() {
        return NAME;
    }

    @Override
    public String getNameDesign() {
        return NAME_2;
    }

    @Override
    public String getObjectName() {
        return null;
    }

    @Override
    public SCore getPlugin() {
        return plugin;
    }

    @Override
    public boolean isLotOfWork() {
        return false;
    }

    @Override
    public int getMaxSObjectsLimit() {
        return Integer.MAX_VALUE;
    }

    public CommandsClass getCommandClass() {
        return commandClass;
    }

    public void initVersion() {
        is1v8 = Bukkit.getServer().getVersion().contains("1.8");
        is1v9 = Bukkit.getServer().getVersion().contains("1.9");
        is1v10 = Bukkit.getServer().getVersion().contains("1.10");
        is1v11 = Bukkit.getServer().getVersion().contains("1.11");
        is1v12 = Bukkit.getServer().getVersion().contains("1.12");
        is1v13 = Bukkit.getServer().getVersion().contains("1.13");
        is1v14 = Bukkit.getServer().getVersion().contains("1.14");
        is1v15 = Bukkit.getServer().getVersion().contains("1.15");
        is1v16 = Bukkit.getServer().getVersion().contains("1.16");
        is1v16v1 = Bukkit.getServer().getVersion().contains("1.16.1");
        is1v17 = Bukkit.getServer().getVersion().contains("1.17");
        is1v18 = Bukkit.getServer().getVersion().contains("1.18");
        is1v19 = Bukkit.getServer().getVersion().contains("1.19");
        is1v19v1 = Bukkit.getServer().getVersion().contains("1.19.1");
        Utils.sendConsoleMsg(SCore.NAME_COLOR+" &7Version of the server &6"+Bukkit.getServer().getVersion()+" &7!");
    }
}
