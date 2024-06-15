package com.ssomar.score.features.types;

import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
@Setter
public class ItemsAdderFeature extends FeatureAbstract<Optional<String>, ItemsAdderFeature> implements FeatureRequireOnlyClicksInEditor {

    private static final boolean DEBUG = false;
    private Optional<String> value;

    public ItemsAdderFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "");
        if (colorStr.isEmpty()) {
            value = Optional.empty();
        } else {
            value = Optional.of(colorStr);
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        value.ifPresent(s -> config.set(this.getName(), s));
    }

    @Override
    public Optional<String> getValue() {
        if (value.isPresent() && SCore.hasItemsAdder) {
            if (CustomStack.getInstance(value.get()) != null) {
                return value;
            }
        }
        return Optional.empty();
    }

    public Optional<ItemStack> getItemStack() {
        if (getValue().isPresent()) {
            return Optional.of(CustomStack.getInstance(getValue().get()).getItemStack());
        }
        return Optional.empty();
    }

    public Object placeItemAdder(Location location, ItemStack itemStack) {
        if (getValue().isPresent()) {
            String id = getValue().get();
            try {
                CustomBlock customBlock = CustomBlock.getInstance(id);
                if (customBlock != null) {
                    SsomarDev.testMsg("placeItemsAdder Block: " + id, DEBUG);
                    customBlock.place(location);
                    return customBlock;
                }
            } catch (Exception e) {
                try {
                    SsomarDev.testMsg("placeItemsAdder is Furniture " + id, DEBUG);
                    CustomFurniture customFurniture = CustomFurniture.spawnPreciseNonSolid(id, location);
                    EntityEquipment entityEquip = ((ArmorStand) customFurniture.getArmorstand()).getEquipment();
                    BukkitRunnable runnable3 = new BukkitRunnable() {
                        @Override
                        public void run() {
                            entityEquip.setItem(EquipmentSlot.HEAD, itemStack);
                        }
                    };
                    runnable3.runTaskLater(ExecutableBlocks.plugin, 10);
                    return customFurniture;
                } catch (Exception e1) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public ItemsAdderFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        ItemStack item = new ItemStack(getEditorMaterial());
        if (getValue().isPresent()) {
            item = getItemStack().get();
        }

        gui.createItem(item, 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if (SCore.hasItemsAdder) {
            if (getValue().isPresent()) updateItemAdder(value.get(), gui);
            else updateItemAdder(ItemsAdder.getAllItems().get(0).getId(), gui);
        } else
            updateItemAdder(null, gui);
    }

    @Override
    public ItemsAdderFeature clone(FeatureParentInterface newParent) {
        ItemsAdderFeature clone = new ItemsAdderFeature(newParent, getFeatureSettings());
        clone.value = value;
        return clone;
    }

    @Override
    public void reset() {
        this.value = Optional.empty();
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
        if (SCore.hasItemsAdder) {
            String id = getItemAdder((GUI) manager.getCache().get(editor)).get();
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            id = nextItemAdder(id);
            updateItemAdder(id, (GUI) manager.getCache().get(editor));
        }
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        if (SCore.hasItemsAdder) {
            String id = getItemAdder((GUI) manager.getCache().get(editor)).get();
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            id = prevItemAdder(id);
            updateItemAdder(id, (GUI) manager.getCache().get(editor));
        }
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        if (SCore.hasItemsAdder)
            updateItemAdder(nextItemAdder(getItemAdder((GUI) manager.getCache().get(editor)).get()), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        if (SCore.hasItemsAdder)
            updateItemAdder(prevItemAdder(getItemAdder((GUI) manager.getCache().get(editor)).get()), (GUI) manager.getCache().get(editor));
        return true;
    }

    public String nextItemAdder(String id) {
        boolean next = false;
        for (String check : getSortItemsAdder()) {
            if (check.equals(id)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortItemsAdder().get(0);
    }

    public String prevItemAdder(String id) {
        int i = -1;
        int cpt = 0;
        for (String check : getSortItemsAdder()) {
            if (check.equals(id)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortItemsAdder().get(getSortItemsAdder().size() - 1);
        else return getSortItemsAdder().get(cpt - 1);
    }

    public void updateItemAdder(String id, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        int slot = gui.getInv().first(item);
        initItemParentEditor(gui, slot);
        item = gui.getByName(getEditorName());

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 3);

        if (id == null) {
            id = "NULLLLL";
        }
        value = Optional.of(id);

        if (SCore.hasItemsAdder) {
            boolean find = false;
            for (String check : getSortItemsAdder()) {
                if (id.equals(check)) {
                    lore.add(StringConverter.coloredString("&2➤ &a" + id));
                    find = true;
                } else if (find) {
                    if (lore.size() == 17) break;
                    lore.add(StringConverter.coloredString("&6✦ &e" + check));
                }
            }
            for (String check : getSortItemsAdder()) {
                if (lore.size() == 17) break;
                else {
                    lore.add(StringConverter.coloredString("&6✦ &e" + check));
                }
            }
        } else {
            lore.add(StringConverter.coloredString("&4➤ &cYou must have &6ItemsAdder"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public Optional<String> getItemAdder(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                if (str.contains("You must have &6ItemsAdder")) return Optional.empty();
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return Optional.ofNullable(str.split("➤ ")[1]);
            }
        }
        return Optional.empty();
    }

    public List<String> getSortItemsAdder() {
        SortedMap<String, String> map = new TreeMap<String, String>();
        if (SCore.hasItemsAdder) {
            for (CustomStack c : ItemsAdder.getAllItems()) {
                map.put(c.getId(), c.getId());
            }
        }
        return new ArrayList<>(map.values());
    }

}
