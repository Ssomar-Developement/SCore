package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
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
public class LoopTypeFeature extends FeatureAbstract<Optional<LoopTypeFeature.LoopType>, LoopTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    public enum LoopType {
        PER_PLAYER_LOOP, SERVER_LOOP
    }

    private Optional<LoopType> value;
    private Optional<LoopType> defaultValue;

    public LoopTypeFeature(FeatureParentInterface parent, Optional<LoopType> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            LoopType chatColor = LoopType.valueOf(colorStr);
            value = Optional.ofNullable(chatColor);
            FeatureReturnCheckPremium<LoopType> checkPremium = checkPremium("LoopType", chatColor, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the LoopType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> LoopType available: PER_PLAYER_LOOP, SERVER_LOOP");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<LoopType> value = getValue();
        value.ifPresent(chatColor -> config.set(this.getName(), chatColor.name()));
    }

    @Override
    public Optional<LoopType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public LoopTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && isRequirePremium()) {
            finalDescription[finalDescription.length - 1] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<LoopType> value = getValue();
        LoopType finalValue = value.orElse(LoopType.PER_PLAYER_LOOP);
        updateLoopType(finalValue, gui);
    }

    @Override
    public LoopTypeFeature clone(FeatureParentInterface newParent) {
        LoopTypeFeature clone = new LoopTypeFeature(newParent,  getDefaultValue(), getFeatureSettings());
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
        if (!isPremium() && isRequirePremium()) return true;
        updateLoopType(nextLoopType(getLoopType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        if (!isPremium() && isRequirePremium()) return true;
        updateLoopType(prevLoopType(getLoopType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public LoopType nextLoopType(LoopType particle) {
        boolean next = false;
        for (LoopType check : LoopType.values()) {
            if (check.equals(particle)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return LoopType.values()[0];
    }

    public LoopType prevLoopType(LoopType color) {
        int i = -1;
        int cpt = 0;
        for (LoopType check : LoopType.values()) {
            if (check.equals(color)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return LoopType.values()[ChatColor.values().length - 1];
        else return LoopType.values()[cpt - 1];
    }

    public void updateLoopType(LoopType color, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        value = Optional.of(color);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, 2);
        boolean find = false;
        for (LoopType check : LoopType.values()) {
            if (color.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + color.name()));
                find = true;
            } else if (find) {
                if (lore.size() == 4) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (LoopType check : LoopType.values()) {
            if (lore.size() == 4) break;
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

    public LoopType getLoopType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return LoopType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

}
