package com.ssomar.score.features.types;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class ItemRarityFeature extends FeatureAbstract<Optional<ItemRarity>, ItemRarityFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<ItemRarity> value;
    private Optional<ItemRarity> defaultValue;

    public ItemRarityFeature(FeatureParentInterface parent, Optional<ItemRarity> defaultValue, FeatureSettingsInterface settings) {
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
                errors.add("&cERROR, Couldn't load the ItemRarity value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> COMMON, EPIC, RARE, UNCOMMON ");
                value = Optional.empty();
            }
            return errors;
        }
        try {
            ItemRarity attributeSlot = ItemRarity.valueOf(colorStr.toUpperCase());
            value = Optional.ofNullable(attributeSlot);
            FeatureReturnCheckPremium<ItemRarity> checkPremium = checkPremium("ItemRarity", attributeSlot, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the ItemRarity value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> COMMON, EPIC, RARE, UNCOMMON");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (getValue().isPresent()) {
            if(defaultValue.isPresent() && isSavingOnlyIfDiffDefault() && getValue().get().equals(defaultValue.get())){
                config.set(this.getName(), null);
                return;
            }
            else config.set(this.getName(), getValue().get().name());
        }
        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    @Override
    public Optional<ItemRarity> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public ItemRarityFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = TM.g(Text.EDITOR_CURRENTLY_NAME);

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<ItemRarity> value = getValue();
        ItemRarity finalValue = value.orElse(ItemRarity.COMMON);
        updateDropType(finalValue, gui);
    }

    @Override
    public ItemRarityFeature clone(FeatureParentInterface newParent) {
        ItemRarityFeature clone = new ItemRarityFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        updateDropType(nextCreationType(getDropType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateDropType(prevCreationType(getDropType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean doubleClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    public ItemRarity nextCreationType(ItemRarity slot) {
        boolean next = false;
        for (ItemRarity check : getSortDropTypes()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortDropTypes().get(0);
    }

    public ItemRarity prevCreationType(ItemRarity slot) {
        int i = -1;
        int cpt = 0;
        for (ItemRarity check : getSortDropTypes()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortDropTypes().get(getSortDropTypes().size() - 1);
        else return getSortDropTypes().get(cpt - 1);
    }

    public void updateDropType(ItemRarity slot, GUI gui) {

        this.value = Optional.of(slot);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortDropTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (ItemRarity check : getSortDropTypes()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + slot.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (ItemRarity check : getSortDropTypes()) {
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

    public ItemRarity getDropType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return ItemRarity.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<ItemRarity> getSortDropTypes() {
        SortedMap<String, ItemRarity> map = new TreeMap<String, ItemRarity>();
        for (ItemRarity l : ItemRarity.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
