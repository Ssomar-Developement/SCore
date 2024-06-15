package com.ssomar.score.features.types.list;

import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.*;

/* List string to support biomes from custom plugins */
@Getter
@Setter
public class ListBiomeFeature extends ListFeatureAbstract<String, ListBiomeFeature> {

    public ListBiomeFeature(FeatureParentInterface parent, List<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, "List of Biomes", defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<String> loadValues(List<String> entries, List<String> errors) {
        List<String> value = new ArrayList<>();
        for (String s : entries) {
            s = StringConverter.decoloredString(s);

            boolean found = false;
            for (String biome : getBiomes()) {
                if (biome.equalsIgnoreCase(s)) {
                    found = true;
                    value.add(biome);
                    break;
                }
            }
            if (!found)
                errors.add("&cERROR, Couldn't load the Biome value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Biomes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/block/Biome.html");
        }
        return value;
    }

    public boolean isValid(Location location) {
        String biome = location.getBlock().getBiome().name();
        if (SCore.hasTerra) {
            try {
                BukkitServerWorld worldS = new BukkitServerWorld(location.getWorld());
                biome = worldS.getBiomeProvider().getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getSeed()).getID();
            } catch (Exception ignored) {
                // ignored.printStackTrace();
            } // ignore if not terra world
        }
        //SsomarDev.testMsg("IfInBiome >> "+biome, true);

        for (String biomeStr : getValues()) {
            if (biomeStr.equalsIgnoreCase(biome)) return true;
        }
        return false;
    }

    @Override
    public String transfromToString(String value) {
        return value;
    }

    @Override
    public ListBiomeFeature clone(FeatureParentInterface newParent) {
        ListBiomeFeature clone = new ListBiomeFeature(newParent, getDefaultValue(),getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    @Override
    public Optional<String> verifyMessage(String message) {
        message = StringConverter.decoloredString(message);

        for (String biome : getBiomes()) {
            if (biome.equalsIgnoreCase(message)) return Optional.empty();
        }
        return Optional.of("&4&l[ERROR] &cThe message you entered is not a Biome &6>> Biomes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/block/Biome.html");
    }


    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();

        for (String biome : getBiomes()) {
            map.put(biome, new Suggestion(biome, "&6[" + "&e" + biome + "&6]", "&7Add &e" + biome));
        }
        return new ArrayList<>(map.values());
    }

    public List<String> getBiomes() {
        List<String> biomes = new ArrayList<>();

        if (SCore.hasTerra) {
            for (String worldStr : AllWorldManager.getWorlds()) {
                // SsomarDev.testMsg("world: "+worldStr, true);
                Optional<World> worldOpt = AllWorldManager.getWorld(worldStr);
                if (!worldOpt.isPresent()) continue;
                World world = worldOpt.get();
                try {
                    BukkitServerWorld worldS = new BukkitServerWorld(world);
                    worldS.getBiomeProvider().getBiomes().forEach(biome -> {
                        //SsomarDev.testMsg("biome: "+biome.getID(), true);
                        biomes.add(biome.getID());
                    });
                } catch (Exception ignored) {
                    // ignored.printStackTrace();
                } // ignore if not terra world
            }
        }
        for (Biome biome : Biome.values()) {
            biomes.add(biome.name());
        }
        return biomes;
    }

    @Override
    public String getTips() {
        return "&8Example &7&oFOREST";
    }

    @Override
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, true, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
