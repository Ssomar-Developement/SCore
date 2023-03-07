package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.score.features.FeatureReturnCheckPremium;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.CustomColor;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class BukkitColorFeature extends FeatureAbstract<Optional<Color>, BukkitColorFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<Color> value;
    private Optional<Color> defaultValue;

    public BukkitColorFeature(FeatureParentInterface parent, String name, Optional<Color> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (!colorStr.equals("NULL") && !colorStr.equals("NO_COLOR")) {
            try {
                Color chatColor = CustomColor.valueOf(colorStr);
                value = Optional.ofNullable(chatColor);
                FeatureReturnCheckPremium<Color> checkPremium = checkPremium("Color", chatColor, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the Color value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> ChatColors available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Color.html");
                value = Optional.empty();
            }
        } else value = Optional.empty();
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<Color> value = getValue();
        value.ifPresent(chatColor -> config.set(this.getName(), CustomColor.getName(chatColor)));
    }

    @Override
    public Optional<Color> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public BukkitColorFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && requirePremium()) {
            finalDescription[finalDescription.length - 1] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<Color> value = getValue();
        Color finalValue = value.orElse(CustomColor.getNullColor());
        updateColor(finalValue, gui);
    }

    @Override
    public BukkitColorFeature clone(FeatureParentInterface newParent) {
        BukkitColorFeature clone = new BukkitColorFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), requirePremium());
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
        if (!isPremium() && requirePremium()) return true;
        updateColor(nextColor(getColor((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        if (!isPremium() && requirePremium()) return true;
        updateColor(prevColor(getColor((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public Color nextColor(Color particle) {
        boolean next = false;
        for (Color check : CustomColor.values()) {
            if (check.equals(particle)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return CustomColor.values()[0];
    }

    public Color prevColor(Color color) {
        int i = -1;
        int cpt = 0;
        for (Color check : CustomColor.values()) {
            if (check.equals(color)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return CustomColor.values()[CustomColor.values().length - 1];
        else return CustomColor.values()[cpt - 1];
    }

    public void updateColor(Color color, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        value = Optional.of(color);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, 2);
        boolean find = false;
        for (Color check : CustomColor.values()) {
            if (color.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + CustomColor.getName(color)));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + CustomColor.getName(check)));
            }
        }
        for (Color check : CustomColor.values()) {
            if (lore.size() == 17) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + CustomColor.getName(check)));
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

    public Color getColor(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return CustomColor.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

}
