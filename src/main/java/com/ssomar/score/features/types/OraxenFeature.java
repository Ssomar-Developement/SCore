package com.ssomar.score.features.types;

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
import io.th0rgal.oraxen.api.OraxenBlocks;
import io.th0rgal.oraxen.api.OraxenFurniture;
import io.th0rgal.oraxen.api.OraxenItems;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class OraxenFeature extends FeatureAbstract<Optional<String>, OraxenFeature> implements FeatureRequireOnlyClicksInEditor {

    private static final boolean DEBUG = false;
    private Optional<String> value;

    public OraxenFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
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
        if (value.isPresent() && SCore.hasOraxen) {
            if (OraxenItems.getItemById(value.get()) != null) {
                return value;
            }
        }
        return Optional.empty();
    }

    public Optional<ItemStack> getItemStack() {
        if (getValue().isPresent()) {
            return Optional.of(OraxenItems.getItemById(value.get()).build());
        }
        return Optional.empty();
    }

    public boolean placeOraxen(Location location, ItemStack itemStack) {
        if (getValue().isPresent()) {
            String id = getValue().get();
            //SsomarDev.testMsg("id: " + id, true);
            if(OraxenFurniture.isFurniture(id)){
                OraxenFurniture.place(id, location, Rotation.NONE, BlockFace.UP);
                return true;
            }
            else if (OraxenBlocks.isOraxenBlock(id)) {
                //SsomarDev.testMsg("isOraxenBlock", true);
                OraxenBlocks.place(id, location);
                return true;
            }
        }
        return false;
    }

    public void removeBlock(Block block, Optional<Entity> blockEntity) {
        if (SCore.hasOraxen) {
            if(blockEntity.isPresent()){
                Entity entity = blockEntity.get();
                if(OraxenFurniture.isFurniture(entity)) {
                    SsomarDev.testMsg("isFurniture IF", true);
                    OraxenFurniture.remove(entity, null);
                    return;
                }
            }
            else if(OraxenFurniture.isFurniture(block)) {
                SsomarDev.testMsg("isFurniture", true);
                OraxenFurniture.remove(block.getLocation(), null);
            }
            else if (OraxenBlocks.isOraxenBlock(block)) {
                SsomarDev.testMsg("isOraxenBlock", true);
                OraxenBlocks.remove(block.getLocation(), null);
            }
            else {
                SsomarDev.testMsg("isNothing", true);
            }
        }
    }


    @Override
    public OraxenFeature initItemParentEditor(GUI gui, int slot) {
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
        if (SCore.hasOraxen) {
            if (getValue().isPresent()) updateOraxen(value.get(), gui);
            else updateOraxen(getSortOraxen().get(0), gui);
        } else
            updateOraxen(null, gui);
    }

    @Override
    public OraxenFeature clone(FeatureParentInterface newParent) {
        OraxenFeature clone = new OraxenFeature(newParent, getFeatureSettings());
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
        if (SCore.hasOraxen) {
            String id = getOraxen((GUI) manager.getCache().get(editor)).get();
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            id = nextOraxen(id);
            updateOraxen(id, (GUI) manager.getCache().get(editor));
        }
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        if (SCore.hasOraxen) {
            String id = getOraxen((GUI) manager.getCache().get(editor)).get();
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            id = prevOraxen(id);
            updateOraxen(id, (GUI) manager.getCache().get(editor));
        }
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        if (SCore.hasOraxen)
            updateOraxen(nextOraxen(getOraxen((GUI) manager.getCache().get(editor)).get()), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        if (SCore.hasOraxen)
            updateOraxen(prevOraxen(getOraxen((GUI) manager.getCache().get(editor)).get()), (GUI) manager.getCache().get(editor));
        return true;
    }

    public String nextOraxen(String id) {
        boolean next = false;
        for (String check : getSortOraxen()) {
            if (check.equals(id)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortOraxen().get(0);
    }

    public String prevOraxen(String id) {
        int i = -1;
        int cpt = 0;
        for (String check : getSortOraxen()) {
            if (check.equals(id)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortOraxen().get(getSortOraxen().size() - 1);
        else return getSortOraxen().get(cpt - 1);
    }

    public void updateOraxen(String id, GUI gui) {
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

        if (SCore.hasOraxen) {
            boolean find = false;
            for (String check : getSortOraxen()) {
                if (id.equals(check)) {
                    lore.add(StringConverter.coloredString("&2➤ &a" + id));
                    find = true;
                } else if (find) {
                    if (lore.size() == 17) break;
                    lore.add(StringConverter.coloredString("&6✦ &e" + check));
                }
            }
            for (String check : getSortOraxen()) {
                if (lore.size() == 17) break;
                else {
                    lore.add(StringConverter.coloredString("&6✦ &e" + check));
                }
            }
        } else {
            lore.add(StringConverter.coloredString("&4➤ &cYou must have &6Oraxen"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public Optional<String> getOraxen(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                if (str.contains("You must have &6Oraxen")) return Optional.empty();
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return Optional.ofNullable(str.split("➤ ")[1]);
            }
        }
        return Optional.empty();
    }

    public List<String> getSortOraxen() {
        SortedMap<String, String> map = new TreeMap<String, String>();
        if (SCore.hasOraxen) {
            for (String c : OraxenItems.getItemNames()) {
                map.put(c, c);
            }
        }
        return new ArrayList<>(map.values());
    }

}
