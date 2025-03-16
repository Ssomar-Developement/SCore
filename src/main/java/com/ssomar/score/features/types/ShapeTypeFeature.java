package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ShapeType;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class ShapeTypeFeature extends FeatureAbstract<Optional<ShapeType>, ShapeTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<ShapeType> value;
    private Optional<ShapeType> defaultValue;

    public ShapeTypeFeature(FeatureParentInterface parent, Optional<ShapeType> defaultValue, FeatureSettingsInterface settings) {
        super(parent, settings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (colorStr.equals("NULL")) {
            if (defaultValue.isPresent()) {
                value = defaultValue;
            } else {
                errors.add("&cERROR, Couldn't load the ShapeType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> MATCH_SHAPE, SHAPELESS");
                value = Optional.empty();
            }
            return errors;
        }
        try {
            ShapeType attributeSlot = ShapeType.valueOf(colorStr);
            value = Optional.ofNullable(attributeSlot);
            FeatureReturnCheckPremium<ShapeType> checkPremium = checkPremium("ShapeType", attributeSlot, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the ShapeType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> MATCH_SHAPE, SHAPELESS");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<ShapeType> value = getValue();
        value.ifPresent(type -> config.set(this.getName(), type.name()));
    }

    @Override
    public Optional<ShapeType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public ShapeTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = TM.g(Text.EDITOR_CURRENTLY_NAME);

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<ShapeType> value = getValue();
        ShapeType finalValue = value.orElse(ShapeType.MATCH_SHAPE);
        updateShapeType(finalValue, gui);
    }

    @Override
    public ShapeTypeFeature clone(FeatureParentInterface newParent) {
        ShapeTypeFeature clone = new ShapeTypeFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        updateShapeType(nextCreationType(getRecipeType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateShapeType(prevCreationType(getRecipeType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean doubleClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    public ShapeType nextCreationType(ShapeType slot) {
        boolean next = false;
        for (ShapeType check : getSortShapeTypes()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortShapeTypes().get(0);
    }

    public ShapeType prevCreationType(ShapeType slot) {
        int i = -1;
        int cpt = 0;
        for (ShapeType check : getSortShapeTypes()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortShapeTypes().get(getSortShapeTypes().size() - 1);
        else return getSortShapeTypes().get(cpt - 1);
    }

    public void updateShapeType(ShapeType slot, GUI gui) {

        this.value = Optional.of(slot);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortShapeTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (ShapeType check : getSortShapeTypes()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + slot.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (ShapeType check : getSortShapeTypes()) {
            if (lore.size() == maxSize) break;
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

    public ShapeType getRecipeType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return ShapeType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<ShapeType> getSortShapeTypes() {
        SortedMap<String, ShapeType> map = new TreeMap<String, ShapeType>();
        for (ShapeType l : ShapeType.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
