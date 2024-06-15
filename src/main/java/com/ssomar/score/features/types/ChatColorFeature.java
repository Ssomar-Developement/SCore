package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ChatColorFeature extends FeatureAbstract<Optional<ChatColor>, ChatColorFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<ChatColor> value;
    private Optional<ChatColor> defaultValue;

    public ChatColorFeature(FeatureParentInterface parent, Optional<ChatColor> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            ChatColor chatColor = ChatColor.valueOf(colorStr);
            value = Optional.ofNullable(chatColor);
            FeatureReturnCheckPremium<ChatColor> checkPremium = checkPremium("ChatColor", chatColor, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the ChatColor value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> ChatColors available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/ChatColor.html");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<ChatColor> value = getValue();
        value.ifPresent(chatColor -> config.set(this.getName(), chatColor.name()));
    }

    @Override
    public Optional<ChatColor> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public ChatColorFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 1] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<ChatColor> value = getValue();
        ChatColor finalValue = value.orElse(ChatColor.WHITE);
        updateChatColor(finalValue, gui);
    }

    @Override
    public ChatColorFeature clone(FeatureParentInterface newParent) {
        ChatColorFeature clone = new ChatColorFeature(newParent, getDefaultValue(), getFeatureSettings());
        clone.value = value;
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        return;
    }

    @Override
    public boolean noShiftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        if (!isPremium() && this.isRequirePremium()) return true;
        updateChatColor(nextChatColor(getChatColor((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        if (!isPremium() && this.isRequirePremium()) return true;
        updateChatColor(prevChatColor(getChatColor((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
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
        value = Optional.of(color);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, 2);
        boolean find = false;
        for (ChatColor check : ChatColor.values()) {
            if (color.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + color.name()));
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
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
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
