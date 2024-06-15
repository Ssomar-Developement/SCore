package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.SProjectileType;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class SProjectileTypeFeature extends FeatureAbstract<Optional<SProjectileType>, SProjectileTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<SProjectileType> value;
    private Optional<SProjectileType> defaultValue;

    public SProjectileTypeFeature(FeatureParentInterface parent, Optional<SProjectileType> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (colorStr.equals("NULL")) {
            value = defaultValue;
        } else {
            try {
                SProjectileType material = SProjectileType.valueOfCustom(colorStr);
                value = Optional.ofNullable(material);
                FeatureReturnCheckPremium<SProjectileType> checkPremium = checkPremium("Projectile type", material, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the Projectile type value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Type target available: ONLY_BLOCK, ONLY_AIR, NO_TYPE_TARGET");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<SProjectileType> value = getValue();
        value.ifPresent(sProjectileType -> config.set(this.getName(), sProjectileType.name()));
    }

    @Override
    public Optional<SProjectileType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public SProjectileTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getValue().get().getMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<SProjectileType> value = getValue();
        SProjectileType finalValue = value.orElse(SProjectileType.ARROW);
        updateSprojectileType(finalValue, gui);
    }

    @Override
    public SProjectileTypeFeature clone(FeatureParentInterface newParent) {
        SProjectileTypeFeature clone = new SProjectileTypeFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        updateSprojectileType(nextSProjectileType(getSProjectileType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateSprojectileType(prevSProjectileType(getSProjectileType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public SProjectileType nextSProjectileType(SProjectileType material) {
        boolean next = false;
        for (SProjectileType check : getSortSProjectileTypes()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortSProjectileTypes().get(0);
    }

    public SProjectileType prevSProjectileType(SProjectileType material) {
        int i = -1;
        int cpt = 0;
        for (SProjectileType check : getSortSProjectileTypes()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortSProjectileTypes().get(getSortSProjectileTypes().size() - 1);
        else return getSortSProjectileTypes().get(cpt - 1);
    }

    public void updateSprojectileType(SProjectileType typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortSProjectileTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (SProjectileType check : getSortSProjectileTypes()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (SProjectileType check : getSortSProjectileTypes()) {
            if (lore.size() == maxSize) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public SProjectileType getSProjectileType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return SProjectileType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<SProjectileType> getSortSProjectileTypes() {
        SortedMap<String, SProjectileType> map = new TreeMap<String, SProjectileType>();
        for (SProjectileType l : SProjectileType.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
