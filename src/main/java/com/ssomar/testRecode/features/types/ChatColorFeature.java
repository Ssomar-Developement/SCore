package com.ssomar.testRecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireOnlyClicksInEditor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class ChatColorFeature extends FeatureAbstract<Optional<ChatColor>, ChatColorFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<ChatColor> value;
    private Optional<ChatColor> defaultValue;

    public ChatColorFeature(FeatureParentInterface parent, String name, Optional<ChatColor> defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(getName(), "NULL");
        try {
            value = Optional.ofNullable(ChatColor.valueOf(colorStr));
            if(requirePremium() && !isPremiumLoading) {
                errors.add("&cERROR, Couldn't load the ChatColor value of " + getName() + " from config, value: " + colorStr+ " &7&o"+getParent().getParentInfo()+" &6>> Because it's a premium feature !");
                value = Optional.empty();
            }
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the ChatColor value of " + getName() + " from config, value: " + colorStr+ " &7&o"+getParent().getParentInfo()+" &6>> ChatColors available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/ChatColor.html");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), value);
    }

    @Override
    public Optional<ChatColor> getValue() {
        if(value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public ChatColorFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<ChatColor> value = getValue();
        ChatColor finalValue = value.orElse(ChatColor.WHITE);
        updateChatColor(finalValue, gui);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        this.value = Optional.of(getChatColor( (GUI) manager.getCache().get(player)));
    }

    @Override
    public ChatColorFeature clone() {
        return new ChatColorFeature(getParent(), getName(), defaultValue, getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        updateChatColor(nextChatColor(getChatColor( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
    }

    public ChatColor nextChatColor(ChatColor particle) {
        boolean next = false;
        for (ChatColor check : ChatColor.values()) {
            if (check.equals(particle)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return ChatColor.values()[0];
    }

    public ChatColor prevChatColor(ChatColor color) {
        int i = -1;
        int cpt = 0;
        for (ChatColor check : ChatColor.values()) {
            if (check.equals(color)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return ChatColor.values()[ChatColor.values().length - 1];
        else return ChatColor.values()[cpt - 1];
    }

    public void updateChatColor(ChatColor color, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, 2);
        boolean find = false;
        for (ChatColor check : ChatColor.values()) {
            if (color.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" +color.name()));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (ChatColor check : ChatColor.values()) {
            if (lore.size() == 17) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Update the gui only for the right click , for the left it updated automaticaly idk why */
        for (HumanEntity e : gui.getInv().getViewers()) {
            if (e instanceof Player) {
                Player p = (Player) e;
                p.updateInventory();
            }
        }
    }

    public ChatColor getChatColor(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return ChatColor.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

}
