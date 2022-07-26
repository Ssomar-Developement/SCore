package com.ssomar.score.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOnlyClicksInEditor;
import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.items.OraxenItems;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.BlockLocation;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureMechanic;
import io.th0rgal.oraxen.shaded.customblockdata.CustomBlockData;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

import static io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureMechanic.*;

@Getter
@Setter
public class OraxenFeature extends FeatureAbstract<Optional<String>, OraxenFeature> implements FeatureRequireOnlyClicksInEditor {

    private static final boolean DEBUG = false;
    private Optional<String> value;

    public OraxenFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
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
        if (value.isPresent()) config.set(this.getName(), value.get());
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

    public void removeBlock(Block block) {
        if (SCore.hasOraxen) {
            final PersistentDataContainer customBlockData = new CustomBlockData(block, OraxenPlugin.get());
            if (!customBlockData.has(FURNITURE_KEY, PersistentDataType.STRING))
                return;

            final String oraxenID = customBlockData.get(FURNITURE_KEY, PersistentDataType.STRING);
            Mechanic mechanic = FurnitureFactory.getInstance().getMechanic(oraxenID);
            if (mechanic instanceof FurnitureMechanic) {
                final BlockLocation rootBlockLocation = new BlockLocation(customBlockData.get(ROOT_KEY, PersistentDataType.STRING));
                ((FurnitureMechanic) mechanic).removeSolid(block.getWorld(), rootBlockLocation, customBlockData.get(ORIENTATION_KEY, PersistentDataType.FLOAT));
            }
        }
    }


    @Override
    public OraxenFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        ItemStack item = new ItemStack(getEditorMaterial());
        if (getValue().isPresent()) {
            item = getItemStack().get();
        }

        gui.createItem(item, 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
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
        OraxenFeature clone = new OraxenFeature(newParent, this.getName(), getEditorName(), getEditorDescription(), getEditorMaterial(), requirePremium());
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
            lore.add(StringConverter.coloredString("&4➤ &cYou must have &6Oraxenr"));
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
