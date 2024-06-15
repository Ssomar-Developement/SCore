package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.PlaceholdersCdtType;
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
public class PlaceholderConditionTypeFeature extends FeatureAbstract<Optional<PlaceholdersCdtType>, PlaceholderConditionTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<PlaceholdersCdtType> value;
    private Optional<PlaceholdersCdtType> defaultValue;

    public PlaceholderConditionTypeFeature(FeatureParentInterface parent, Optional<PlaceholdersCdtType> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    public static PlaceholderConditionTypeFeature buildNull(PlaceholdersCdtType value) {
        PlaceholderConditionTypeFeature p = new PlaceholderConditionTypeFeature(null, Optional.empty(), null);
        p.setValue(Optional.of(value));
        return p;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            PlaceholdersCdtType material = PlaceholdersCdtType.valueOf(colorStr);
            value = Optional.ofNullable(material);
            FeatureReturnCheckPremium<PlaceholdersCdtType> checkPremium = checkPremium("PlaceholdersCdtType", material, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the PlaceholdersCdtType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Type target available: PLAYER_NUMBER, PLAYER_STRING, PLAYER_PLAYER, TARGET_NUMBER, TARGET_STRING, TARGET_TARGET, PLAYER_TARGET");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<PlaceholdersCdtType> value = getValue();
        value.ifPresent(placeholdersCdtType -> config.set(this.getName(), placeholdersCdtType.name()));
    }

    @Override
    public Optional<PlaceholdersCdtType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public PlaceholderConditionTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<PlaceholdersCdtType> value = getValue();
        PlaceholdersCdtType finalValue = value.orElse(PlaceholdersCdtType.PLAYER_NUMBER);
        updatePlaceholdersCdtType(finalValue, gui);
    }

    @Override
    public PlaceholderConditionTypeFeature clone(FeatureParentInterface newParent) {
        PlaceholderConditionTypeFeature clone = new PlaceholderConditionTypeFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        updatePlaceholdersCdtType(nextPlaceholdersCdtType(getPlaceholdersCdtType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updatePlaceholdersCdtType(prevPlaceholdersCdtType(getPlaceholdersCdtType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public PlaceholdersCdtType nextPlaceholdersCdtType(PlaceholdersCdtType material) {
        boolean next = false;
        for (PlaceholdersCdtType check : getSortPlaceholdersCdtTypes()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortPlaceholdersCdtTypes().get(0);
    }

    public PlaceholdersCdtType prevPlaceholdersCdtType(PlaceholdersCdtType material) {
        int i = -1;
        int cpt = 0;
        for (PlaceholdersCdtType check : getSortPlaceholdersCdtTypes()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortPlaceholdersCdtTypes().get(getSortPlaceholdersCdtTypes().size() - 1);
        else return getSortPlaceholdersCdtTypes().get(cpt - 1);
    }

    public void updatePlaceholdersCdtType(PlaceholdersCdtType typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortPlaceholdersCdtTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (PlaceholdersCdtType check : getSortPlaceholdersCdtTypes()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (PlaceholdersCdtType check : getSortPlaceholdersCdtTypes()) {
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

    public PlaceholdersCdtType getPlaceholdersCdtType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return PlaceholdersCdtType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<PlaceholdersCdtType> getSortPlaceholdersCdtTypes() {
        SortedMap<String, PlaceholdersCdtType> map = new TreeMap<String, PlaceholdersCdtType>();
        for (PlaceholdersCdtType l : PlaceholdersCdtType.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
