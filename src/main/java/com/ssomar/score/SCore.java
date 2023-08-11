package com.ssomar.score;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.plotsquared.core.PlotAPI;
import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.score.CommandsClass;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageInterface;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.data.Database;
import com.ssomar.score.events.EventsHandler;
import com.ssomar.score.events.loop.LoopManager;
import com.ssomar.score.features.custom.cooldowns.CooldownsHandler;
import com.ssomar.score.features.custom.useperday.manager.UsagePerDayManager;
import com.ssomar.score.hardness.HardnessesHandler;
import com.ssomar.score.hardness.hardness.loader.HardnessLoader;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.loader.SProjectileLoader;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.PlaceholderAPISCoreExpansion;
import com.ssomar.score.usedapi.ProtocolLibAPI;
import com.ssomar.score.utils.display.Display;
import com.ssomar.score.utils.display.PacketManager;
import com.ssomar.score.utils.display.TryDisplayModule;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.scheduler.BukkitSchedulerHook;
import com.ssomar.score.utils.scheduler.RegionisedSchedulerHook;
import com.ssomar.score.utils.scheduler.SchedulerHook;
import com.ssomar.score.variables.loader.VariablesLoader;
import com.ssomar.score.variables.manager.VariablesManager;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SCore extends JavaPlugin implements SPlugin {

    public static final String NAME = "SCore";
    public static final String NAME_COLOR = "&eSCore";
    public static final String NAME_2 = "[SCore]";
    public static SCore plugin;

    public static SchedulerHook schedulerHook;
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

    public static boolean hasRoseStacker = false;
    public static boolean hasMMOCore = false;
    public static boolean hasProtectionStones = false;

    public static boolean hasTerra = false;

    public static boolean hasJetsMinions = false;

    public static boolean hasTAB = false;
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
    private static boolean is1v19v4 = false;
    private static boolean is1v20 = false;

    private static boolean is1v20v1 = false;


    private static boolean isSpigot = false;
    private static boolean isPaper = false;
    private static boolean isFolia = false;
    private CommandsClass commandClass;

    /* The server is folia? */
    public static boolean isFolia() {
        return isFolia;
    }

    /* The server is spigot? */
    public static boolean isSpigot() {
        return isSpigot;
    }

    /* The server is paper? */
    public static boolean isPaper() {
        return isPaper;
    }

    /* The server is in 1.8 ? */
    public static boolean is1v8() {
        return is1v8;
    }

    /* The server is in 1.9 ? */
    public static boolean is1v9() {
        return is1v9;
    }

    /* The server is in 1.10 ? */
    public static boolean is1v10() {
        return is1v10;
    }

    /* The server is in 1.11 ? */
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

    public static boolean is1v19v4() {
        return is1v19v4;
    }

    /* The server is in 1.20? */
    public static boolean is1v20() {
        return is1v20;
    }

    /* The server is in 1.20.1? */
    public static boolean is1v20v1() {
        return is1v20v1;
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
        return is1v16() || is1v17Plus();
    }

    /* The server is in 1.17 or + ? */
    public static boolean is1v17Plus() {
        return is1v17() || is1v18Plus();
    }

    /* The server is in 1.18 or + ? */
    public static boolean is1v18Plus() {
        return is1v18() || is1v19Plus();
    }

    /* The server is in 1.19 or + ? */
    public static boolean is1v19Plus() {
        return is1v19() || is1v19v1() || is1v19v4Plus();
    }

    public static boolean is1v19v4Plus() {
        return is1v19v4() || is1v20Plus();
    }

    /* The server is in 1.20 or + ? */
    public static boolean is1v20Plus() {
        return is1v20() || is1v20v1Plus();
    }

    /* The server is in 1.20.1 or + ? */
    public static boolean is1v20v1Plus() {
        return is1v20v1();
    }

    @Override
    public void onEnable() {
        plugin = this;
        if (isFolia()) schedulerHook = new RegionisedSchedulerHook(this);
        else schedulerHook = new BukkitSchedulerHook(this);
        commandClass = new CommandsClass(this);

        this.initVersion();

        Utils.sendConsoleMsg("&7================ " + NAME_COLOR + " &7================");

        if (isFolia()) {
            Utils.sendConsoleMsg(NAME_COLOR + " &7is running on &eFolia");
        }
        this.displayVersion();

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

        /* Hardnesses instance part */
        HardnessLoader.getInstance().load();

        /* Variables instance part */
        VariablesLoader.getInstance().load();

        CommandsHandler.getInstance().onEnable();

        UsagePerDayManager.getInstance();

        CooldownsHandler.loadCooldowns();

        if (SCore.hasPlaceholderAPI) {
            new PlaceholderAPISCoreExpansion(this).register();
        }

        Utils.sendConsoleMsg("&7================ " + NAME_COLOR + " &7================");
    }

    public boolean hookSoftDependency(String plugin) {
        Plugin softDepend = null;
        if ((softDepend = Bukkit.getPluginManager().getPlugin(plugin)) != null) {
            String when = " &8&oLoad Before";
            if (!softDepend.isEnabled()) {
                when = "&8&oLoad After";
            }
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7" + plugin + " hooked !  &6(" + softDepend.getDescription().getVersion() + "&6) " + when);
            return true;
        }
        return false;
    }

    public void loadDependency() {
        /* Soft-Dependency part */
        hasExecutableItems = hookSoftDependency("ExecutableItems");

        hasExecutableBlocks = hookSoftDependency("ExecutableBlocks");

        hasExecutableEvents = hookSoftDependency("ExecutableEvents");

        hasCustomPiglinsTrades = hookSoftDependency("CustomPiglinsTrades");

        hasSParkour = hookSoftDependency("SParkour");

        hasPlaceholderAPI = hookSoftDependency("PlaceholderAPI");

        hasWorldGuard = hookSoftDependency("WorldGuard");

        hasVault = hookSoftDependency("Vault");

        hasIridiumSkyblock = hookSoftDependency("IridiumSkyblock");

        hasSuperiorSkyblock2 = hookSoftDependency("SuperiorSkyblock2");

        hasBentoBox = hookSoftDependency("BentoBox");

        hasMultiverse = hookSoftDependency("Multiverse-Core");

        hasLands = hookSoftDependency("Lands");

        hasTowny = hookSoftDependency("Towny");

        hasGriefPrevention = hookSoftDependency("GriefPrevention");

        hasGriefDefender = hookSoftDependency("GriefDefender");

        hasCoreProtect = hookSoftDependency("CoreProtect");


        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7ProtocolLib hooked !");
            hasProtocolLib = true;
            /* Protocolib */
            protocolManager = ProtocolLibrary.getProtocolManager();
            ProtocolLibAPI.reduceDamageIndicator();

            if(!SCore.is1v12Less()) {
                Display.registerDisplayModule(new TryDisplayModule());
                PacketManager.newDisplay();

                BukkitRunnable runnable3 = new BukkitRunnable() {
                    @Override
                    public void run() {
                        //System.out.println(">>> RUNNABLE DISPLAY");
                        /* if no modification its not necessary to update the inv of all players  for nothing */
                        if(!Display.isSomethingToModify()) return;
                        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                            //System.out.println(">>>"+player.getName());
                            ItemStack itemStack = player.getOpenInventory().getCursor();
                            if (player.getGameMode() != GameMode.CREATIVE && (itemStack == null || itemStack.getType() == Material.AIR)) {
                                //System.out.println(">>>"+player.getName()+" update");
                                player.updateInventory();
                            }
                        /* else {
                            System.out.println(">>>"+player.getName()+" no update cursor >> "+player.getOpenInventory().getCursor().getType());
                        }*/
                        });
                    }
                };
                runnable3.runTaskTimerAsynchronously(SCore.plugin, 0, 20);
            }


            new HardnessesHandler().registerListener();
        }

        hasNBTAPI = hookSoftDependency("NBTAPI");

        hasResidence = hookSoftDependency("Residence");


        if (Bukkit.getPluginManager().getPlugin("PlotSquared") != null) {
            try {
                PlotAPI plotAPI = new PlotAPI();
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7PlotSquared hooked !");
                hasPlotSquared = true;
            } catch (NoClassDefFoundError e) {
                SCore.plugin.getServer().getLogger().severe("[" + NAME + "] PlotSquared hooked BUT you haven't the good version ! (try to update it) !");
                hasPlotSquared = false;
            }
        }

        hasHeadDatabase = hookSoftDependency("HeadDatabase");

        hasHeadDB = hookSoftDependency("HeadDB");

        hasMythicMobs = hookSoftDependency("MythicMobs");

        hasDecentHolograms = hookSoftDependency("DecentHolograms");

        if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            try {
                HolographicDisplaysAPI.get(SCore.plugin);
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7HolographicDisplays hooked !");
                hasHolographicDisplays = true;
            } catch (Exception | Error e) {
                SCore.plugin.getServer().getLogger().severe("[" + NAME + "] HolographicDisplays hooked BUT you haven't the good version ! (require 3.0 or higher) !");
                e.printStackTrace();
                hasHolographicDisplays = false;
            }
        }

        hasCMI = hookSoftDependency("CMI");

        hasAureliumSkills = hookSoftDependency("AureliumSkills");

        hasItemsAdder = hookSoftDependency("ItemsAdder");

        hasOraxen = hookSoftDependency("Oraxen");

        hasShopGUIPlus = hookSoftDependency("ShopGUIPlus");

        hasRoseLoot = hookSoftDependency("RoseLoot");

        hasRoseStacker = hookSoftDependency("RoseStacker");

        hasMMOCore = hookSoftDependency("MMOCore");

        hasProtectionStones = hookSoftDependency("ProtectionStones");

        hasTAB = hookSoftDependency("TAB");

        hasTerra = hookSoftDependency("Terra");

        hasJetsMinions = hookSoftDependency("JetsMinions");
    }

    @Override
    public void onDisable() {
        if (GeneralConfig.getInstance().isUseMySQL()) {
            VariablesManager.getInstance().updateAllLoadedMySQL(VariablesManager.MODE.IMPORT);
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Save &6" + VariablesManager.getInstance().getLoadedObjects().size() + " &7variables from your MySQL Database !");
        }

        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Save UsagePerDay....");
        UsagePerDayManager.getInstance().save();
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Save UsagePerDay done !");
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Save Commands...");
        CommandsHandler.getInstance().onDisable();
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Save Commands done !");
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Save Cooldowns...");
        CooldownsHandler.closeServerSaveAll();
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Save Cooldowns done !");
    }

    public void onReload() {
        Utils.sendConsoleMsg("&7================ " + NAME_COLOR + " &7================");
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
        is1v19v4 = Bukkit.getServer().getVersion().contains("1.19.4");
        is1v20 = Bukkit.getServer().getVersion().contains("1.20");
        is1v20v1 = Bukkit.getServer().getVersion().contains("1.20.1");

        isSpigot = Bukkit.getServer().getVersion().contains("Spigot");
        isPaper = Bukkit.getServer().getVersion().contains("Paper");
        isFolia = Bukkit.getServer().getVersion().contains("Folia");
    }

    public void displayVersion() {
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Version of the server &6" + Bukkit.getServer().getVersion() + " &7!");
    }
}
