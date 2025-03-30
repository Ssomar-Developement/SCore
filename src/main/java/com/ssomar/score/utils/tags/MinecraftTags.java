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

        if(SCore.is1v21v5Plus()){

        }

        if (SCore.is1v20Plus()) {
            if(!SCore.is1v21v5Plus()){
                addTag(tags, "ALL_HANGING_SIGNS");
                addTag(tags, "ALL_SIGNS");
                addTag(tags, "ANCIENT_CITY_REPLACEABLE");
                addTag(tags, "ANIMALS_SPAWNABLE_ON");
                addTag(tags, "AXOLOTLS_SPAWNABLE_ON");
                addTag(tags, "AZALEA_GROWS_ON");
                addTag(tags, "AZALEA_ROOT_REPLACEABLE");
                addTag(tags, "BAMBOO_BLOCKS");
                addTag(tags, "BASE_STONE_NETHER");
                addTag(tags, "BASE_STONE_OVERWORLD");
                addTag(tags, "BIG_DRIPLEAF_PLACEABLE");
                addTag(tags, "CANDLE_CAKES");
                addTag(tags, "CANDLES");
                addTag(tags, "WOOL_CARPETS");
                addTag(tags, "CAULDRONS");
                addTag(tags, "CAVE_VINES");
                addTag(tags, "CEILING_HANGING_SIGNS");
                addTag(tags, "CHERRY_LOGS");
                addTag(tags, "CLUSTER_MAX_HARVESTABLES");
                addTag(tags, "COAL_ORES");
                addTag(tags, "COMBINATION_STEP_SOUND_BLOCKS");
                addTag(tags, "COMPLETES_FIND_TREE_TUTORIAL");
                addTag(tags, "CONVERTABLE_TO_MUD");
                addTag(tags, "COPPER_ORES");
                addTag(tags, "CRYSTAL_SOUND_BLOCKS");
                addTag(tags, "DAMPENS_VIBRATIONS");
                addTag(tags, "DEAD_BUSH_MAY_PLACE_ON");
                addTag(tags, "DEEPSLATE_ORE_REPLACEABLES");
                addTag(tags, "DIAMOND_ORES");
                addTag(tags, "DIRT");
                addTag(tags, "DRAGON_TRANSPARENT");
                addTag(tags, "DRIPSTONE_REPLACEABLE");
                addTag(tags, "EMERALD_ORES");
                addTag(tags, "ENCHANTMENT_POWER_PROVIDER");
                addTag(tags, "ENCHANTMENT_POWER_TRANSMITTER");
                addTag(tags, "ENTITY_TYPES_ARROWS");
                addTag(tags, "ENTITY_TYPES_AXOLOTL_ALWAYS_HOSTILES");
                addTag(tags, "ENTITY_TYPES_AXOLOTL_HUNT_TARGETS");
                addTag(tags, "ENTITY_TYPES_BEEHIVE_INHABITORS");
                addTag(tags, "ENTITY_TYPES_DISMOUNTS_UNDERWATER");
                addTag(tags, "ENTITY_TYPES_FALL_DAMAGE_IMMUNE");
                addTag(tags, "ENTITY_TYPES_FREEZE_HURTS_EXTRA_TYPES");
                addTag(tags, "ENTITY_TYPES_FREEZE_IMMUNE_ENTITY_TYPES");
                addTag(tags, "ENTITY_TYPES_FROG_FOOD");
                addTag(tags, "ENTITY_TYPES_IMPACT_PROJECTILES");
                addTag(tags, "ENTITY_TYPES_POWDER_SNOW_WALKABLE_MOBS");
                addTag(tags, "ENTITY_TYPES_RAIDERS");
                addTag(tags, "ENTITY_TYPES_SKELETONS");
                addTag(tags, "FALL_DAMAGE_RESETTING");
                addTag(tags, "FEATURES_CANNOT_REPLACE");
                addTag(tags, "FLUIDS_LAVA");
                addTag(tags, "FLUIDS_WATER");
                addTag(tags, "FOXES_SPAWNABLE_ON");
                addTag(tags, "FREEZE_IMMUNE_WEARABLES");
                addTag(tags, "FROG_PREFER_JUMP_TO");
                addTag(tags, "FROGS_SPAWNABLE_ON");
                addTag(tags, "GEODE_INVALID_BLOCKS");
                addTag(tags, "GOATS_SPAWNABLE_ON");
                addTag(tags, "IGNORED_BY_PIGLIN_BABIES");
                addTag(tags, "INSIDE_STEP_SOUND_BLOCKS");
                addTag(tags, "INVALID_SPAWN_INSIDE");
                addTag(tags, "IRON_ORES");
                addTag(tags, "ITEMS_AXES");
                addTag(tags, "ITEMS_BOOKSHELF_BOOKS");
                addTag(tags, "ITEMS_BREAKS_DECORATED_POTS");
                addTag(tags, "ITEMS_CHEST_BOATS");
                addTag(tags, "ITEMS_COMPASSES");
                addTag(tags, "ITEMS_CREEPER_IGNITERS");
                addTag(tags, "ITEMS_DECORATED_POT_INGREDIENTS");
                addTag(tags, "ITEMS_DECORATED_POT_SHERDS");
                addTag(tags, "ITEMS_HANGING_SIGNS");
                addTag(tags, "ITEMS_HOES");
                addTag(tags, "ITEMS_NON_FLAMMABLE_WOOD");
                addTag(tags, "ITEMS_NOTE_BLOCK_TOP_INSTRUMENTS");
                addTag(tags, "ITEMS_PICKAXES");
                addTag(tags, "ITEMS_SHOVELS");
                addTag(tags, "ITEMS_SNIFFER_FOOD");
                addTag(tags, "ITEMS_SWORDS");
                addTag(tags, "ITEMS_TRIM_MATERIALS");
                addTag(tags, "ITEMS_TRIMMABLE_ARMOR");
                addTag(tags, "ITEMS_VILLAGER_PLANTABLE_SEEDS");
                addTag(tags, "LAPIS_ORES");
                addTag(tags, "LAVA_POOL_STONE_CANNOT_REPLACE");
                addTag(tags, "LUSH_GROUND_REPLACEABLE");
                addTag(tags, "MAINTAINS_FARMLAND");
                addTag(tags, "MANGROVE_LOGS");
                addTag(tags, "MANGROVE_LOGS_CAN_GROW_THROUGH");
                addTag(tags, "MANGROVE_ROOTS_CAN_GROW_THROUGH");
                addTag(tags, "MINEABLE_AXE");
                addTag(tags, "MINEABLE_HOE");
                addTag(tags, "MINEABLE_PICKAXE");
                addTag(tags, "MINEABLE_SHOVEL");
                addTag(tags, "MOOSHROOMS_SPAWNABLE_ON");
                addTag(tags, "MOSS_REPLACEABLE");
                addTag(tags, "MUSHROOM_GROW_BLOCK");
                addTag(tags, "NEEDS_DIAMOND_TOOL");
                addTag(tags, "NEEDS_IRON_TOOL");
                addTag(tags, "NEEDS_STONE_TOOL");
                addTag(tags, "NETHER_CARVER_REPLACEABLES");
                addTag(tags, "OCCLUDES_VIBRATION_SIGNALS");
                addTag(tags, "OVERWORLD_CARVER_REPLACEABLES");
                addTag(tags, "PARROTS_SPAWNABLE_ON");
                addTag(tags, "POLAR_BEARS_SPAWNABLE_ON_ALTERNATE");
                addTag(tags, "RABBITS_SPAWNABLE_ON");
                addTag(tags, "REDSTONE_ORES");
                addTag(tags, "REPLACEABLE");
                addTag(tags, "REPLACEABLE_BY_TREES");
                addTag(tags, "SCULK_REPLACEABLE");
                addTag(tags, "SCULK_REPLACEABLE_WORLD_GEN");
                addTag(tags, "SMALL_DRIPLEAF_PLACEABLE");
                addTag(tags, "SMELTS_TO_GLASS");
                addTag(tags, "SNAPS_GOAT_HORN");
                addTag(tags, "SNIFFER_DIGGABLE_BLOCK");
                addTag(tags, "SNIFFER_EGG_HATCH_BOOST");
                addTag(tags, "SNOW");
                addTag(tags, "SNOW_LAYER_CAN_SURVIVE_ON");
                addTag(tags, "SNOW_LAYER_CANNOT_SURVIVE_ON");
                addTag(tags, "STONE_BUTTONS");
                addTag(tags, "STONE_ORE_REPLACEABLES");
                addTag(tags, "SWORD_EFFICIENT");
                addTag(tags, "TERRACOTTA");
                addTag(tags, "TRAIL_RUINS_REPLACEABLE");
                addTag(tags, "VIBRATION_RESONATORS");
                addTag(tags, "WALL_HANGING_SIGNS");
                addTag(tags, "WOLVES_SPAWNABLE_ON");
                addTag(tags, "WOOL_CARPETS");
                addTag(tags, "AXOLOTL_TEMPT_ITEMS");
                addTag(tags, "FOX_FOOD");
                addTag(tags, "ITEMS_TOOLS");
                addTag(tags, "PIGLIN_FOOD");
            }
            tags.add(Tag.ACACIA_LOGS);
            tags.add(Tag.ANVIL);
            tags.add(Tag.BAMBOO_PLANTABLE_ON);
            tags.add(Tag.BANNERS);
            tags.add(Tag.BEACON_BASE_BLOCKS);
            tags.add(Tag.BEDS);
            tags.add(Tag.BEE_GROWABLES);
            tags.add(Tag.BEEHIVES);
            tags.add(Tag.BIRCH_LOGS);
            tags.add(Tag.BUTTONS);
            tags.add(Tag.CAMPFIRES);
            tags.add(Tag.CLIMBABLE);

            tags.add(Tag.CORAL_BLOCKS);
            tags.add(Tag.CORAL_PLANTS);
            tags.add(Tag.CORALS);
            tags.add(Tag.CRIMSON_STEMS);
            tags.add(Tag.CROPS);
            tags.add(Tag.DARK_OAK_LOGS);
            tags.add(Tag.DOORS);
            tags.add(Tag.DRAGON_IMMUNE);
            tags.add(Tag.ENDERMAN_HOLDABLE);
            tags.add(Tag.FENCE_GATES);
            tags.add(Tag.FENCES);
            tags.add(Tag.FIRE);
            tags.add(Tag.FLOWER_POTS);
            tags.add(Tag.FLOWERS);
            tags.add(Tag.GOLD_ORES);
            tags.add(Tag.GUARDED_BY_PIGLINS);
            tags.add(Tag.HOGLIN_REPELLENTS);
            tags.add(Tag.ICE);
            tags.add(Tag.IMPERMEABLE);
            tags.add(Tag.INFINIBURN_END);
            tags.add(Tag.INFINIBURN_NETHER);
            tags.add(Tag.INFINIBURN_OVERWORLD);
            tags.add(Tag.ITEMS_ARROWS);
            tags.add(Tag.ITEMS_BANNERS);
            tags.add(Tag.ITEMS_BEACON_PAYMENT_ITEMS);
            tags.add(Tag.ITEMS_BOATS);
            tags.add(Tag.ITEMS_COALS);
            tags.add(Tag.ITEMS_CREEPER_DROP_MUSIC_DISCS);
            tags.add(Tag.ITEMS_FISHES);
            tags.add(Tag.ITEMS_LECTERN_BOOKS);
            tags.add(Tag.ITEMS_PIGLIN_LOVED);
            tags.add(Tag.ITEMS_STONE_TOOL_MATERIALS);
            tags.add(Tag.JUNGLE_LOGS);
            tags.add(Tag.LEAVES);
            tags.add(Tag.LOGS);
            tags.add(Tag.LOGS_THAT_BURN);
            tags.add(Tag.NYLIUM);
            tags.add(Tag.OAK_LOGS);
            tags.add(Tag.PIGLIN_REPELLENTS);
            tags.add(Tag.PLANKS);
            tags.add(Tag.PORTALS);
            tags.add(Tag.PRESSURE_PLATES);
            tags.add(Tag.PREVENT_MOB_SPAWNING_INSIDE);
            tags.add(Tag.RAILS);
            tags.add(Tag.SAND);
            tags.add(Tag.SAPLINGS);
            tags.add(Tag.SHULKER_BOXES);
            tags.add(Tag.SIGNS);
            tags.add(Tag.SLABS);
            tags.add(Tag.SMALL_FLOWERS);
            tags.add(Tag.SOUL_FIRE_BASE_BLOCKS);
            tags.add(Tag.SOUL_SPEED_BLOCKS);
            tags.add(Tag.SPRUCE_LOGS);
            tags.add(Tag.STAIRS);
            tags.add(Tag.STANDING_SIGNS);
            tags.add(Tag.STONE_BRICKS);
            tags.add(Tag.STONE_PRESSURE_PLATES);
            tags.add(Tag.STRIDER_WARM_BLOCKS);
            tags.add(Tag.TRAPDOORS);
            tags.add(Tag.UNDERWATER_BONEMEALS);
            tags.add(Tag.UNSTABLE_BOTTOM_CENTER);
            tags.add(Tag.VALID_SPAWN);
            tags.add(Tag.WALL_CORALS);
            tags.add(Tag.WALL_POST_OVERRIDE);
            tags.add(Tag.WALL_SIGNS);
            tags.add(Tag.WALLS);
            tags.add(Tag.WARPED_STEMS);
            tags.add(Tag.WART_BLOCKS);
            tags.add(Tag.WITHER_IMMUNE);
            tags.add(Tag.WITHER_SUMMON_BASE_BLOCKS);
            tags.add(Tag.WOODEN_BUTTONS);
            tags.add(Tag.WOODEN_DOORS);
            tags.add(Tag.WOODEN_FENCES);
            tags.add(Tag.WOODEN_PRESSURE_PLATES);
            tags.add(Tag.WOODEN_SLABS);
            tags.add(Tag.WOODEN_STAIRS);
            tags.add(Tag.WOODEN_TRAPDOORS);
            tags.add(Tag.WOOL);

            // Deprecated tags
            tags.add(Tag.CARPETS);
            tags.add(Tag.ITEMS_FURNACE_MATERIALS);

            // Removed tags
            addTag(tags,"ITEMS_MUSIC_DISCS");
            addTag(tags,"TALL_FLOWERS");
            addTag(tags,"ITEMS_TRIM_TEMPLATES");
            addTag(tags,"CONCRETE_POWDER");
        }
    }

    public void addTag(List<Tag> tags, String tag){
        try {
            tags.add((Tag) Tag.class.getDeclaredField(tag).get(null));
        } catch (NoSuchFieldException | IllegalAccessException ignore) {}
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
