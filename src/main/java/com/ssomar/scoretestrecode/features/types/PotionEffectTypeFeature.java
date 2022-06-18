package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.*;

@Getter @Setter
public class PotionEffectTypeFeature extends FeatureAbstract<Optional<PotionEffectType>, PotionEffectTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<PotionEffectType> value;
    private Optional<PotionEffectType> defaultValue;

    public PotionEffectTypeFeature(FeatureParentInterface parent, String name, Optional<PotionEffectType> defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            PotionEffectType attributeSlot = PotionEffectType.getByName(colorStr.toUpperCase());
            value = Optional.ofNullable(attributeSlot);
            FeatureReturnCheckPremium<PotionEffectType> checkPremium = checkPremium("PotionEffectType", attributeSlot, defaultValue, isPremiumLoading);
            if(checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the PotionEffectType value of " + this.getName() + " from config, value: " + colorStr+ " &7&o"+getParent().getParentInfo()+" &6>> List: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<PotionEffectType> value = getValue();
        if(value.isPresent()) config.set(this.getName(), value.get().getName());
    }

    @Override
    public Optional<PotionEffectType> getValue() {
        if(value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public PotionEffectTypeFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<PotionEffectType> value = getValue();
        PotionEffectType finalValue = value.orElse(PotionEffectType.HEAL);
        updatePotionEffectType(finalValue, gui);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        this.value = Optional.of(getPotionEffectType( (GUI) manager.getCache().get(player)));
    }

    @Override
    public PotionEffectTypeFeature clone() {
        PotionEffectTypeFeature clone = new PotionEffectTypeFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), requirePremium());
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
        PotionEffectType slot = getPotionEffectType( (GUI) manager.getCache().get(editor));
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        slot = nextPotionEffectType(slot);
        updatePotionEffectType(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        PotionEffectType slot = getPotionEffectType( (GUI) manager.getCache().get(editor));
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        slot = prevPotionEffectType(slot);
        updatePotionEffectType(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updatePotionEffectType(nextPotionEffectType(getPotionEffectType( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updatePotionEffectType(prevPotionEffectType(getPotionEffectType( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public PotionEffectType nextPotionEffectType(PotionEffectType slot) {
        boolean next = false;
        for (PotionEffectType check : getSortPotionEffectTypes()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortPotionEffectTypes().get(0);
    }

    public PotionEffectType prevPotionEffectType(PotionEffectType slot) {
        int i = -1;
        int cpt = 0;
        for (PotionEffectType check : getSortPotionEffectTypes()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortPotionEffectTypes().get(getSortPotionEffectTypes().size()- 1);
        else return getSortPotionEffectTypes().get(cpt - 1);
    }

    public void updatePotionEffectType(PotionEffectType slot, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortPotionEffectTypes().size();
        if(maxSize > 17)  maxSize = 17;
        boolean find = false;
        for (PotionEffectType check : getSortPotionEffectTypes()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" +slot.getName()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.getName()));
            }
        }
        for (PotionEffectType check : getSortPotionEffectTypes()) {
            if (lore.size() == maxSize) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.getName()));
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

    public PotionEffectType getPotionEffectType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return PotionEffectType.getByName(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<PotionEffectType> getSortPotionEffectTypes() {
        SortedMap<String, PotionEffectType> map = new TreeMap<String, PotionEffectType>();
        for (PotionEffectType l : PotionEffectType.values()) {
            map.put(l.getName(), l);
        }
        return new ArrayList<>(map.values());
    }

}
