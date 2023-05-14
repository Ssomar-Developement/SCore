package com.ssomar.score.features.types.list;

import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class ListBiomeFeature extends ListFeatureAbstract<Biome, ListBiomeFeature> {

    public ListBiomeFeature(FeatureParentInterface parent, String name, List<Biome> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, "List of Biomes", editorName, editorDescription, editorMaterial, defaultValue, requirePremium, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<Biome> loadValues(List<String> entries, List<String> errors) {
        List<Biome> value = new ArrayList<>();
        for (String s : entries) {
            s = StringConverter.decoloredString(s);
            try {
                Biome biome = Biome.valueOf(s);
                value.add(biome);
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the Biome value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> Biomes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/block/Biome.html");
            }
        }
        return value;
    }

    @Override
    public String transfromToString(Biome value) {
        return value.name();
    }

    @Override
    public ListBiomeFeature clone(FeatureParentInterface newParent) {
        ListBiomeFeature clone = new ListBiomeFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    @Override
    public Optional<String> verifyMessage(String message) {
        message = StringConverter.decoloredString(message);
        try {
            Biome biome = Biome.valueOf(message);
            getValues().add(biome);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a Biome &6>> Biomes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/block/Biome.html");
        }
    }



    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();

        for (Biome biome : Biome.values()) {
            map.put(biome.toString(), new Suggestion(biome + "", "&6[" + "&e" + biome + "&6]", "&7Add &e" + biome));
        }

        if (SCore.hasTerra) {
            for(String worldStr : AllWorldManager.getWorlds()){
                Optional<World> worldOpt = AllWorldManager.getWorld(worldStr);
                if (!worldOpt.isPresent()) continue;
                World world = worldOpt.get();
                try {
                    BukkitServerWorld worldS = new BukkitServerWorld(world);
                    worldS.getBiomeProvider().getBiomes().forEach(biome -> {
                        map.put(biome.toString(), new Suggestion(biome + "", "&6[" + "&e" + biome + "&6]", "&7Add &e" + biome));
                    });
                }catch (Exception ignored){} // ignore if not terra world
            }

        }
        return new ArrayList<>(map.values());
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
