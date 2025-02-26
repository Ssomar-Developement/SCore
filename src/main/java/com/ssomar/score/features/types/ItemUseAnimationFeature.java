package com.ssomar.score.features.types;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
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
public class ItemUseAnimationFeature extends FeatureAbstract<Optional<ItemUseAnimation>, ItemUseAnimationFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<ItemUseAnimation> value;
    private Optional<ItemUseAnimation> defaultValue;

    public ItemUseAnimationFeature(FeatureParentInterface parent, Optional<ItemUseAnimation> defaultValue, FeatureSettingsInterface settings) {
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
                errors.add("&cERROR, Couldn't load the ItemUseAnimation value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> BLOCK, BOW, BRUSH, BUNDLE, CROSSBOW, DRINK, EAT, NONE, SPEAR, SPYGLASS, TOOT_HORN (https://jd.papermc.io/paper/1.21.4/io/papermc/paper/datacomponent/item/consumable/ItemUseAnimation.html)");
                value = Optional.empty();
            }
            return errors;
        }
        try {
            ItemUseAnimation attributeSlot = ItemUseAnimation.valueOf(colorStr.toUpperCase());
            value = Optional.ofNullable(attributeSlot);
            FeatureReturnCheckPremium<ItemUseAnimation> checkPremium = checkPremium("ItemUseAnimation", attributeSlot, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the ItemUseAnimation value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> BLOCK, BOW, BRUSH, BUNDLE, CROSSBOW, DRINK, EAT, NONE, SPEAR, SPYGLASS, TOOT_HORN (https://jd.papermc.io/paper/1.21.4/io/papermc/paper/datacomponent/item/consumable/ItemUseAnimation.html)");
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
    public Optional<ItemUseAnimation> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public ItemUseAnimationFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = TM.g(Text.EDITOR_CURRENTLY_NAME);

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<ItemUseAnimation> value = getValue();
        ItemUseAnimation finalValue = value.orElse(ItemUseAnimation.EAT);
        updateDropType(finalValue, gui);
    }

    @Override
    public ItemUseAnimationFeature clone(FeatureParentInterface newParent) {
        ItemUseAnimationFeature clone = new ItemUseAnimationFeature(newParent, getDefaultValue(), getFeatureSettings());
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

    public ItemUseAnimation nextCreationType(ItemUseAnimation slot) {
        boolean next = false;
        for (ItemUseAnimation check : getSortDropTypes()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortDropTypes().get(0);
    }

    public ItemUseAnimation prevCreationType(ItemUseAnimation slot) {
        int i = -1;
        int cpt = 0;
        for (ItemUseAnimation check : getSortDropTypes()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortDropTypes().get(getSortDropTypes().size() - 1);
        else return getSortDropTypes().get(cpt - 1);
    }

    public void updateDropType(ItemUseAnimation slot, GUI gui) {

        this.value = Optional.of(slot);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortDropTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (ItemUseAnimation check : getSortDropTypes()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + slot.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (ItemUseAnimation check : getSortDropTypes()) {
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

    public ItemUseAnimation getDropType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return ItemUseAnimation.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<ItemUseAnimation> getSortDropTypes() {
        SortedMap<String, ItemUseAnimation> map = new TreeMap<String, ItemUseAnimation>();
        for (ItemUseAnimation l : ItemUseAnimation.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
