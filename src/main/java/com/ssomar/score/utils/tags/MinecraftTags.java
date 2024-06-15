package com.ssomar.score.utils.tags;

import com.ssomar.score.SCore;
import org.bukkit.Material;
import org.bukkit.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftTags {

    private static MinecraftTags instance;

    private Map<String, Tag> keyedTags;
    private List<Tag> tags;

    public MinecraftTags() {
        keyedTags = new HashMap<>();
        tags = new ArrayList<>();

        /* add all the tags */

        if (SCore.is1v20Plus()) {
            tags.add(Tag.ACACIA_LOGS);
            tags.add(Tag.ALL_HANGING_SIGNS);
            tags.add(Tag.ALL_SIGNS);
            tags.add(Tag.ANCIENT_CITY_REPLACEABLE);
            tags.add(Tag.ANIMALS_SPAWNABLE_ON);
            tags.add(Tag.ANVIL);
            tags.add(Tag.AXOLOTL_TEMPT_ITEMS);
            tags.add(Tag.AXOLOTLS_SPAWNABLE_ON);
            tags.add(Tag.AZALEA_GROWS_ON);
            tags.add(Tag.AZALEA_ROOT_REPLACEABLE);
            tags.add(Tag.BAMBOO_BLOCKS);
            tags.add(Tag.BAMBOO_PLANTABLE_ON);
            tags.add(Tag.BANNERS);
            tags.add(Tag.BASE_STONE_NETHER);
            tags.add(Tag.BASE_STONE_OVERWORLD);
            tags.add(Tag.BEACON_BASE_BLOCKS);
            tags.add(Tag.BEDS);
            tags.add(Tag.BEE_GROWABLES);
            tags.add(Tag.BEEHIVES);
            tags.add(Tag.BIG_DRIPLEAF_PLACEABLE);
            tags.add(Tag.BIRCH_LOGS);
            tags.add(Tag.BUTTONS);
            //tags.add(Tag.CAMEL_SAND_STEP_SOUND_BLOCKS);
            tags.add(Tag.CAMPFIRES);
            tags.add(Tag.CANDLE_CAKES);
            tags.add(Tag.CANDLES);
            tags.add(Tag.CARPETS);
            tags.add(Tag.WOOL_CARPETS);
            tags.add(Tag.CAULDRONS);
            tags.add(Tag.CAVE_VINES);
            tags.add(Tag.CEILING_HANGING_SIGNS);
            tags.add(Tag.CHERRY_LOGS);
            tags.add(Tag.CLIMBABLE);
            tags.add(Tag.CLUSTER_MAX_HARVESTABLES);
            tags.add(Tag.COAL_ORES);
            tags.add(Tag.COMBINATION_STEP_SOUND_BLOCKS);
            tags.add(Tag.COMPLETES_FIND_TREE_TUTORIAL);
            //tags.add(Tag.CONCRETE_POWDER);
            tags.add(Tag.CONVERTABLE_TO_MUD);
            tags.add(Tag.COPPER_ORES);
            tags.add(Tag.CORAL_BLOCKS);
            tags.add(Tag.CORAL_PLANTS);
            tags.add(Tag.CORALS);
            tags.add(Tag.CRIMSON_STEMS);
            tags.add(Tag.CROPS);
            tags.add(Tag.CRYSTAL_SOUND_BLOCKS);
            tags.add(Tag.DAMPENS_VIBRATIONS);
            tags.add(Tag.DARK_OAK_LOGS);
            tags.add(Tag.DEAD_BUSH_MAY_PLACE_ON);
            tags.add(Tag.DEEPSLATE_ORE_REPLACEABLES);
            tags.add(Tag.DIAMOND_ORES);
            tags.add(Tag.DIRT);
            tags.add(Tag.DOORS);
            tags.add(Tag.DRAGON_IMMUNE);
            tags.add(Tag.DRAGON_TRANSPARENT);
            tags.add(Tag.DRIPSTONE_REPLACEABLE);
            tags.add(Tag.EMERALD_ORES);
            tags.add(Tag.ENCHANTMENT_POWER_PROVIDER);
            tags.add(Tag.ENCHANTMENT_POWER_TRANSMITTER);
            tags.add(Tag.ENDERMAN_HOLDABLE);
            tags.add(Tag.ENTITY_TYPES_ARROWS);
            tags.add(Tag.ENTITY_TYPES_AXOLOTL_ALWAYS_HOSTILES);
            tags.add(Tag.ENTITY_TYPES_AXOLOTL_HUNT_TARGETS);
            tags.add(Tag.ENTITY_TYPES_BEEHIVE_INHABITORS);
            //tags.add(Tag.ENTITY_TYPES_CAN_TURN_IN_BOATS);
            //tags.add(Tag.ENTITY_TYPES_DEFLECTS_ARROWS);
            //tags.add(Tag.ENTITY_TYPES_DEFLECTS_TRIDENTS);
            tags.add(Tag.ENTITY_TYPES_DISMOUNTS_UNDERWATER);
            tags.add(Tag.ENTITY_TYPES_FALL_DAMAGE_IMMUNE);
            tags.add(Tag.ENTITY_TYPES_FREEZE_HURTS_EXTRA_TYPES);
            tags.add(Tag.ENTITY_TYPES_FREEZE_IMMUNE_ENTITY_TYPES);
            tags.add(Tag.ENTITY_TYPES_FROG_FOOD);
            tags.add(Tag.ENTITY_TYPES_IMPACT_PROJECTILES);
            //tags.add(Tag.ENTITY_TYPES_NON_CONTROLLING_RIDER);
            tags.add(Tag.ENTITY_TYPES_POWDER_SNOW_WALKABLE_MOBS);
            tags.add(Tag.ENTITY_TYPES_RAIDERS);
            tags.add(Tag.ENTITY_TYPES_SKELETONS);
            tags.add(Tag.FALL_DAMAGE_RESETTING);
            tags.add(Tag.FEATURES_CANNOT_REPLACE);
            tags.add(Tag.FENCE_GATES);
            tags.add(Tag.FENCES);
            tags.add(Tag.FIRE);
            tags.add(Tag.FLOWER_POTS);
            tags.add(Tag.FLOWERS);
            tags.add(Tag.FLUIDS_LAVA);
            tags.add(Tag.FLUIDS_WATER);
            tags.add(Tag.FOX_FOOD);
            tags.add(Tag.FOXES_SPAWNABLE_ON);
            tags.add(Tag.FREEZE_IMMUNE_WEARABLES);
            tags.add(Tag.FROG_PREFER_JUMP_TO);
            tags.add(Tag.FROGS_SPAWNABLE_ON);
            tags.add(Tag.GEODE_INVALID_BLOCKS);
            tags.add(Tag.GOATS_SPAWNABLE_ON);
            tags.add(Tag.GOLD_ORES);
            tags.add(Tag.GUARDED_BY_PIGLINS);
            tags.add(Tag.HOGLIN_REPELLENTS);
            tags.add(Tag.ICE);
            tags.add(Tag.IGNORED_BY_PIGLIN_BABIES);
            tags.add(Tag.IMPERMEABLE);
            tags.add(Tag.INFINIBURN_END);
            tags.add(Tag.INFINIBURN_NETHER);
            tags.add(Tag.INFINIBURN_OVERWORLD);
            tags.add(Tag.INSIDE_STEP_SOUND_BLOCKS);
            tags.add(Tag.INVALID_SPAWN_INSIDE);
            tags.add(Tag.IRON_ORES);
            tags.add(Tag.ITEMS_ARROWS);
            tags.add(Tag.ITEMS_AXES);
            tags.add(Tag.ITEMS_BANNERS);
            tags.add(Tag.ITEMS_BEACON_PAYMENT_ITEMS);
            tags.add(Tag.ITEMS_BOATS);
            tags.add(Tag.ITEMS_BOOKSHELF_BOOKS);
            tags.add(Tag.ITEMS_BREAKS_DECORATED_POTS);
            tags.add(Tag.ITEMS_CHEST_BOATS);
            tags.add(Tag.ITEMS_COALS);
            tags.add(Tag.ITEMS_COMPASSES);
            tags.add(Tag.ITEMS_CREEPER_DROP_MUSIC_DISCS);
            tags.add(Tag.ITEMS_CREEPER_IGNITERS);
            tags.add(Tag.ITEMS_DECORATED_POT_INGREDIENTS);
            tags.add(Tag.ITEMS_DECORATED_POT_SHERDS);
            tags.add(Tag.ITEMS_FISHES);
            tags.add(Tag.ITEMS_FURNACE_MATERIALS);
            tags.add(Tag.ITEMS_HANGING_SIGNS);
            tags.add(Tag.ITEMS_HOES);
            tags.add(Tag.ITEMS_LECTERN_BOOKS);
            tags.add(Tag.ITEMS_NON_FLAMMABLE_WOOD);
            tags.add(Tag.ITEMS_NOTE_BLOCK_TOP_INSTRUMENTS);
            tags.add(Tag.ITEMS_PICKAXES);
            tags.add(Tag.ITEMS_PIGLIN_LOVED);
            tags.add(Tag.ITEMS_SHOVELS);
            tags.add(Tag.ITEMS_SNIFFER_FOOD);
            tags.add(Tag.ITEMS_STONE_TOOL_MATERIALS);
            tags.add(Tag.ITEMS_SWORDS);
            tags.add(Tag.ITEMS_TOOLS);
            tags.add(Tag.ITEMS_TRIM_MATERIALS);
            tags.add(Tag.ITEMS_TRIM_TEMPLATES);
            tags.add(Tag.ITEMS_TRIMMABLE_ARMOR);
            tags.add(Tag.ITEMS_VILLAGER_PLANTABLE_SEEDS);
            tags.add(Tag.JUNGLE_LOGS);
            tags.add(Tag.LAPIS_ORES);
            tags.add(Tag.LAVA_POOL_STONE_CANNOT_REPLACE);
            tags.add(Tag.LEAVES);
            tags.add(Tag.LOGS);
            tags.add(Tag.LOGS_THAT_BURN);
            tags.add(Tag.LUSH_GROUND_REPLACEABLE);
            tags.add(Tag.MAINTAINS_FARMLAND);
            tags.add(Tag.MANGROVE_LOGS);
            tags.add(Tag.MANGROVE_LOGS_CAN_GROW_THROUGH);
            tags.add(Tag.MANGROVE_ROOTS_CAN_GROW_THROUGH);
            tags.add(Tag.MINEABLE_AXE);
            tags.add(Tag.MINEABLE_HOE);
            tags.add(Tag.MINEABLE_PICKAXE);
            tags.add(Tag.MINEABLE_SHOVEL);
            tags.add(Tag.MOOSHROOMS_SPAWNABLE_ON);
            tags.add(Tag.MOSS_REPLACEABLE);
            tags.add(Tag.MUSHROOM_GROW_BLOCK);
            tags.add(Tag.NEEDS_DIAMOND_TOOL);
            tags.add(Tag.NEEDS_IRON_TOOL);
            tags.add(Tag.NEEDS_STONE_TOOL);
            tags.add(Tag.NETHER_CARVER_REPLACEABLES);
            tags.add(Tag.NYLIUM);
            tags.add(Tag.OAK_LOGS);
            tags.add(Tag.OCCLUDES_VIBRATION_SIGNALS);
            tags.add(Tag.OVERWORLD_CARVER_REPLACEABLES);
            tags.add(Tag.PARROTS_SPAWNABLE_ON);
            tags.add(Tag.PIGLIN_FOOD);
            tags.add(Tag.PIGLIN_REPELLENTS);
            tags.add(Tag.PLANKS);
            tags.add(Tag.POLAR_BEARS_SPAWNABLE_ON_ALTERNATE);
            tags.add(Tag.PORTALS);
            tags.add(Tag.PRESSURE_PLATES);
            tags.add(Tag.PREVENT_MOB_SPAWNING_INSIDE);
            tags.add(Tag.RABBITS_SPAWNABLE_ON);
            tags.add(Tag.RAILS);
            tags.add(Tag.REDSTONE_ORES);
            /* tags.add(Tag.REGISTRY_BLOCKS);
            tags.add(Tag.REGISTRY_ENTITY_TYPES);
            tags.add(Tag.REGISTRY_FLUIDS);
            tags.add(Tag.REGISTRY_ITEMS); */
            tags.add(Tag.REPLACEABLE);
            tags.add(Tag.REPLACEABLE_BY_TREES);
            tags.add(Tag.SAND);
            tags.add(Tag.SAPLINGS);
            tags.add(Tag.SCULK_REPLACEABLE);
            tags.add(Tag.SCULK_REPLACEABLE_WORLD_GEN);
            tags.add(Tag.SHULKER_BOXES);
            tags.add(Tag.SIGNS);
            tags.add(Tag.SLABS);
            tags.add(Tag.SMALL_DRIPLEAF_PLACEABLE);
            tags.add(Tag.SMALL_FLOWERS);
            tags.add(Tag.SMELTS_TO_GLASS);
            tags.add(Tag.SNAPS_GOAT_HORN);
            tags.add(Tag.SNIFFER_DIGGABLE_BLOCK);
            tags.add(Tag.SNIFFER_EGG_HATCH_BOOST);
            tags.add(Tag.SNOW);
            tags.add(Tag.SNOW_LAYER_CAN_SURVIVE_ON);
            tags.add(Tag.SNOW_LAYER_CANNOT_SURVIVE_ON);
            tags.add(Tag.SOUL_FIRE_BASE_BLOCKS);
            tags.add(Tag.SOUL_SPEED_BLOCKS);
            tags.add(Tag.SPRUCE_LOGS);
            tags.add(Tag.STAIRS);
            tags.add(Tag.STANDING_SIGNS);
            tags.add(Tag.STONE_BRICKS);
            tags.add(Tag.STONE_BUTTONS);
            tags.add(Tag.STONE_ORE_REPLACEABLES);
            tags.add(Tag.STONE_PRESSURE_PLATES);
            tags.add(Tag.STRIDER_WARM_BLOCKS);
            tags.add(Tag.SWORD_EFFICIENT);
            tags.add(Tag.TALL_FLOWERS);
            tags.add(Tag.TERRACOTTA);
            tags.add(Tag.TRAIL_RUINS_REPLACEABLE);
            tags.add(Tag.TRAPDOORS);
            tags.add(Tag.UNDERWATER_BONEMEALS);
            tags.add(Tag.UNSTABLE_BOTTOM_CENTER);
            tags.add(Tag.VALID_SPAWN);
            tags.add(Tag.VIBRATION_RESONATORS);
            tags.add(Tag.WALL_CORALS);
            tags.add(Tag.WALL_HANGING_SIGNS);
            tags.add(Tag.WALL_POST_OVERRIDE);
            tags.add(Tag.WALL_SIGNS);
            tags.add(Tag.WALLS);
            tags.add(Tag.WARPED_STEMS);
            tags.add(Tag.WART_BLOCKS);
            tags.add(Tag.WITHER_IMMUNE);
            tags.add(Tag.WITHER_SUMMON_BASE_BLOCKS);
            tags.add(Tag.WOLVES_SPAWNABLE_ON);
            tags.add(Tag.WOODEN_BUTTONS);
            tags.add(Tag.WOODEN_DOORS);
            tags.add(Tag.WOODEN_FENCES);
            tags.add(Tag.WOODEN_PRESSURE_PLATES);
            tags.add(Tag.WOODEN_SLABS);
            tags.add(Tag.WOODEN_STAIRS);
            tags.add(Tag.WOODEN_TRAPDOORS);
            tags.add(Tag.WOOL);
            tags.add(Tag.WOOL_CARPETS);

            if (!SCore.isIs1v21Plus()) {
                // Add the tag ITEMS_MUSIC_DISCS that is not in 1.21 but in the spigot 1.20 wth reflection
                try {
                    tags.add((Tag) Tag.class.getDeclaredField("ITEMS_MUSIC_DISCS").get(null));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (SCore.is1v20Plus()) {

            }
        }
    }

    public Tag<Material> getTag(String key) {
        //SsomarDev.testMsg("Key: "+key, true);
        Tag tag = keyedTags.get(key);
        if (tag == null) {
            for (Tag t : tags) {
                //SsomarDev.testMsg("Tag: "+t.getKey().toString(), true);
                if (t != null && t.getKey().toString().equals(key)) {
                    //SsomarDev.testMsg("Tag: "+t.getKey().toString(), true);
                    tag = t;
                    keyedTags.put(key, tag);
                    break;
                }
            }
        }
        return tag;
    }

    /* I need to check that here and not in ListDetailedMaterial to not have java.lang.NoClassDefFoundError: org/bukkit/Keyed for 1.8 users*/
    public boolean checkIfTagged(Material material, Tag<Material> tag) {
        return tag.isTagged(material);
    }

    public static MinecraftTags getInstance() {
        if (instance == null) instance = new MinecraftTags();
        return instance;
    }
}
