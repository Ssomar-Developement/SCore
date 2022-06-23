package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.AttributeSlot;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.scoretestrecode.features.FeatureReturnCheckPremium;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;

import java.util.*;

@Getter @Setter
public class PotionTypeFeature extends FeatureAbstract<Optional<PotionType>, PotionTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<PotionType> value;
    private Optional<PotionType> defaultValue;

    public PotionTypeFeature(FeatureParentInterface parent, String name, Optional<PotionType> defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if(colorStr.equals("NULL")) {
            if(defaultValue.isPresent()) {
                value = defaultValue;
            }
            else value = Optional.empty();
        } else {
            try {
                PotionType attributeSlot = PotionType.valueOf(colorStr.toUpperCase());
                value = Optional.ofNullable(attributeSlot);
                FeatureReturnCheckPremium<PotionType> checkPremium = checkPremium("PotionType", attributeSlot, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the PotionType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> List: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionType.html");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<PotionType> value = getValue();
        if(value.isPresent()) config.set(this.getName(), value.get().name());
    }

    @Override
    public Optional<PotionType> getValue() {
        if(value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public PotionTypeFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<PotionType> value = getValue();
        PotionType finalValue = value.orElse(PotionType.WATER);
        updatePotionType(finalValue, gui);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        this.value = Optional.of(getPotionType( (GUI) manager.getCache().get(player)));
    }

    @Override
    public PotionTypeFeature clone() {
        PotionTypeFeature clone = new PotionTypeFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), requirePremium());
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
        PotionType slot = getPotionType( (GUI) manager.getCache().get(editor));
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        slot = nextPotionType(slot);
        updatePotionType(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        PotionType slot = getPotionType( (GUI) manager.getCache().get(editor));
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        slot = prevPotionType(slot);
        updatePotionType(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updatePotionType(nextPotionType(getPotionType( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updatePotionType(prevPotionType(getPotionType( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public PotionType nextPotionType(PotionType slot) {
        boolean next = false;
        for (PotionType check : getSortPotionTypes()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortPotionTypes().get(0);
    }

    public PotionType prevPotionType(PotionType slot) {
        int i = -1;
        int cpt = 0;
        for (PotionType check : getSortPotionTypes()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortPotionTypes().get(getSortPotionTypes().size()- 1);
        else return getSortPotionTypes().get(cpt - 1);
    }

    public void updatePotionType(PotionType slot, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortPotionTypes().size();
        if(maxSize > 17)  maxSize = 17;
        boolean find = false;
        for (PotionType check : getSortPotionTypes()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" +slot.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (PotionType check : getSortPotionTypes()) {
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

    public PotionType getPotionType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return PotionType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<PotionType> getSortPotionTypes() {
        SortedMap<String, PotionType> map = new TreeMap<String, PotionType>();
        for (PotionType l : PotionType.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
