package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.score.features.FeatureReturnCheckPremium;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.TypeTarget;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class PickupFeature extends FeatureAbstract<Optional<org.bukkit.entity.Arrow.PickupStatus>, PickupFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<org.bukkit.entity.Arrow.PickupStatus> value;
    private Optional<org.bukkit.entity.Arrow.PickupStatus> defaultValue;

    public PickupFeature(FeatureParentInterface parent, String name, Optional<org.bukkit.entity.Arrow.PickupStatus> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
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
                org.bukkit.entity.Arrow.PickupStatus material = org.bukkit.entity.Arrow.PickupStatus.valueOf(colorStr);
                value = Optional.ofNullable(material);
                FeatureReturnCheckPremium<org.bukkit.entity.Arrow.PickupStatus> checkPremium = checkPremium("Pickup status", material, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the Pickup status value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Pickup status available: ALLOWED, CREATIVE_ONLY, DISALLOWED");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<org.bukkit.entity.Arrow.PickupStatus> value = getValue();
        value.ifPresent(typeTarget -> config.set(this.getName(), typeTarget.name()));
    }

    @Override
    public Optional<org.bukkit.entity.Arrow.PickupStatus> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public PickupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<org.bukkit.entity.Arrow.PickupStatus> value = getValue();
        org.bukkit.entity.Arrow.PickupStatus finalValue = value.orElse(Arrow.PickupStatus.ALLOWED);
        updateTypeTarget(finalValue, gui);
    }

    @Override
    public PickupFeature clone(FeatureParentInterface newParent) {
        PickupFeature clone = new PickupFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), requirePremium());
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
        updateTypeTarget(nextPickupStatus(getPickupStatus((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateTypeTarget(prevPickupStatus(getPickupStatus((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public org.bukkit.entity.Arrow.PickupStatus nextPickupStatus(org.bukkit.entity.Arrow.PickupStatus material) {
        boolean next = false;
        for (org.bukkit.entity.Arrow.PickupStatus check : getSortPickupStatus()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortPickupStatus().get(0);
    }

    public org.bukkit.entity.Arrow.PickupStatus prevPickupStatus(org.bukkit.entity.Arrow.PickupStatus material) {
        int i = -1;
        int cpt = 0;
        for (org.bukkit.entity.Arrow.PickupStatus check : getSortPickupStatus()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortPickupStatus().get(getSortPickupStatus().size() - 1);
        else return getSortPickupStatus().get(cpt - 1);
    }

    public void updateTypeTarget(org.bukkit.entity.Arrow.PickupStatus typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortPickupStatus().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (org.bukkit.entity.Arrow.PickupStatus check : getSortPickupStatus()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (org.bukkit.entity.Arrow.PickupStatus check : getSortPickupStatus()) {
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

    public org.bukkit.entity.Arrow.PickupStatus getPickupStatus(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return org.bukkit.entity.Arrow.PickupStatus.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<org.bukkit.entity.Arrow.PickupStatus> getSortPickupStatus() {
        SortedMap<String, org.bukkit.entity.Arrow.PickupStatus> map = new TreeMap<String, org.bukkit.entity.Arrow.PickupStatus>();
        for (org.bukkit.entity.Arrow.PickupStatus l : org.bukkit.entity.Arrow.PickupStatus.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
