package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Getter
@Setter
public class BlockFaceFeature extends FeatureAbstract<Optional<BlockFace>, BlockFaceFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<BlockFace> value;
    private Optional<BlockFace> defaultValue;
    private Set<BlockFace> whitelist;

    public BlockFaceFeature(FeatureParentInterface parent, Optional<BlockFace> defaultValue, @Nullable Set<BlockFace> whitelist, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
        this.whitelist = whitelist;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (colorStr.equals("NULL")) {
            value = defaultValue;
        } else {
            try {
                BlockFace blockFace = BlockFace.valueOf(colorStr.toUpperCase());
                if(whitelist != null && !whitelist.contains(blockFace)) {
                    errors.add("&cERROR, the BlockFace value of " + this.getName() + " from config is not in the whitelist, value: " + colorStr + " &7&o" + getParent().getParentInfo());
                    return errors;
                }
                value = Optional.of(blockFace);
                FeatureReturnCheckPremium<BlockFace> checkPremium = checkPremium("BlockFace", blockFace, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the BlockFace value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> List: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/block/BlockFace.html");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<BlockFace> value = getValue();
        value.ifPresent(potionType -> config.set(this.getName(), potionType.name()));
    }

    @Override
    public Optional<BlockFace> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public BlockFaceFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<BlockFace> value = getValue();
        BlockFace finalValue = value.orElse(BlockFace.NORTH);
        updateBlockFace(finalValue, gui);
    }

    @Override
    public BlockFaceFeature clone(FeatureParentInterface newParent) {
        BlockFaceFeature clone = new BlockFaceFeature(newParent, getDefaultValue(), getWhitelist(),getFeatureSettings());
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
        BlockFace slot = getBlockFace((GUI) manager.getCache().get(editor));
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        slot = nextBlockFace(slot);
        updateBlockFace(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        BlockFace slot = getBlockFace((GUI) manager.getCache().get(editor));
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        slot = prevBlockFace(slot);
        updateBlockFace(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateBlockFace(nextBlockFace(getBlockFace((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateBlockFace(prevBlockFace(getBlockFace((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public BlockFace nextBlockFace(BlockFace slot) {
        boolean next = false;
        for (BlockFace check : getSortBlockFace()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortBlockFace().get(0);
    }

    public BlockFace prevBlockFace(BlockFace slot) {
        int i = -1;
        int cpt = 0;
        for (BlockFace check : getSortBlockFace()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortBlockFace().get(getSortBlockFace().size() - 1);
        else return getSortBlockFace().get(cpt - 1);
    }

    public void updateBlockFace(BlockFace slot, GUI gui) {
        value = Optional.of(slot);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortBlockFace().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (BlockFace check : getSortBlockFace()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + slot.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (BlockFace check : getSortBlockFace()) {
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

    public BlockFace getBlockFace(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return BlockFace.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<BlockFace> getSortBlockFace() {
        SortedMap<String, BlockFace> map = new TreeMap<String, BlockFace>();
        for (BlockFace l : BlockFace.values()) {
            if (whitelist == null || whitelist.contains(l)) map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
