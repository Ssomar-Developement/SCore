package com.ssomar.score.features.custom.customlists;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.logging.Utils;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

/**
 * Manager for custom entity and block lists defined in SCore config.yml
 * Allows users to define reusable lists that can be referenced with SCORE: prefix
 */
@Getter
public class CustomListsManager {

    private static CustomListsManager instance;

    private Map<String, List<String>> entityLists;
    private Map<String, List<String>> blockLists;

    private CustomListsManager() {
        this.entityLists = new HashMap<>();
        this.blockLists = new HashMap<>();
    }

    public static CustomListsManager getInstance() {
        if (instance == null) {
            instance = new CustomListsManager();
        }
        return instance;
    }

    /**
     * Load custom lists from SCore config.yml
     */
    public void load() {
        entityLists.clear();
        blockLists.clear();

        File configFile = new File(SCore.plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            Utils.sendConsoleMsg("&e[SCore] CustomListsManager: config.yml not found, skipping custom lists loading");
            return;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        // Load entity lists
        ConfigurationSection entityListsSection = config.getConfigurationSection("entityLists");
        if (entityListsSection != null) {
            for (String listName : entityListsSection.getKeys(false)) {
                List<String> entities = entityListsSection.getStringList(listName);
                if (!entities.isEmpty()) {
                    entityLists.put(listName.toUpperCase(), entities);
                    Utils.sendConsoleMsg("&a[SCore] CustomListsManager: Loaded entity list '" + listName + "' with " + entities.size() + " entries");
                }
            }
        }

        // Load block lists
        ConfigurationSection blockListsSection = config.getConfigurationSection("blockLists");
        if (blockListsSection != null) {
            for (String listName : blockListsSection.getKeys(false)) {
                List<String> blocks = blockListsSection.getStringList(listName);
                if (!blocks.isEmpty()) {
                    blockLists.put(listName.toUpperCase(), blocks);
                    Utils.sendConsoleMsg("&a[SCore] CustomListsManager: Loaded block list '" + listName + "' with " + blocks.size() + " entries");
                }
            }
        }

        Utils.sendConsoleMsg("&a[SCore] CustomListsManager: Loaded " + entityLists.size() + " entity lists and " + blockLists.size() + " block lists");
    }

    /**
     * Get the entities from a custom list by name
     * @param listName The name of the custom list (case insensitive)
     * @return Optional containing the list of entities, or empty if not found
     */
    public Optional<List<String>> getEntityList(String listName) {
        List<String> list = entityLists.get(listName.toUpperCase());
        return list != null ? Optional.of(new ArrayList<>(list)) : Optional.empty();
    }

    /**
     * Get the blocks from a custom list by name
     * @param listName The name of the custom list (case insensitive)
     * @return Optional containing the list of blocks, or empty if not found
     */
    public Optional<List<String>> getBlockList(String listName) {
        List<String> list = blockLists.get(listName.toUpperCase());
        return list != null ? Optional.of(new ArrayList<>(list)) : Optional.empty();
    }

    /**
     * Check if an entity list exists
     * @param listName The name of the custom list (case insensitive)
     * @return true if the list exists
     */
    public boolean hasEntityList(String listName) {
        return entityLists.containsKey(listName.toUpperCase());
    }

    /**
     * Check if a block list exists
     * @param listName The name of the custom list (case insensitive)
     * @return true if the list exists
     */
    public boolean hasBlockList(String listName) {
        return blockLists.containsKey(listName.toUpperCase());
    }

    /**
     * Reload all custom lists from config
     */
    public void reload() {
        load();
    }
}
