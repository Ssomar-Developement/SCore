package com.ssomar.score.features.types;

import com.ssomar.myfurniture.furniture.Furniture;
import com.ssomar.myfurniture.furniture.FurnitureManager;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.SObjectBuildable;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.Dependency;
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
public class MyFurnitureFeature extends FeatureAbstract<Optional<Furniture>, MyFurnitureFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<String> value;
    private boolean checkAlreadyLinked = true;

    public MyFurnitureFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
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

    public Optional<SObjectBuildable> getBuildable() {
        if(value.isPresent() && Dependency.MY_FURNITURE.isEnabled() && FurnitureManager.getInstance().isValidID(value.get())) {
            return Optional.of(FurnitureManager.getInstance().getFurniture(value.get()).get());
        }
        return Optional.empty();
    }

    @Override
    public void save(ConfigurationSection config) {
        value.ifPresent(s -> config.set(this.getName(), s));
    }

    @Override
    public Optional<Furniture> getValue() {
        if (value.isPresent() && Dependency.MY_FURNITURE.isEnabled() && FurnitureManager.getInstance().isValidID(value.get()))
            return FurnitureManager.getInstance().getFurniture(value.get());
        else return Optional.empty();
    }

    public Optional<String> getValueID() {
        return value;
    }

    @Override
    public MyFurnitureFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 3] = "&8>> &6DOUBLE CLICK : &bOPEN EDITOR";
        finalDescription[finalDescription.length - 2] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        ItemStack item = new ItemStack(getEditorMaterial());
        if (value.isPresent() && Dependency.MY_FURNITURE.isEnabled() && FurnitureManager.getInstance().isValidID(value.get())) {
            item = FurnitureManager.getInstance().getFurniture(value.get()).get().getIconItem();
        }
        gui.createItem(item, 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if (Dependency.MY_FURNITURE.isEnabled()) {
            if (value.isPresent() && FurnitureManager.getInstance().isValidID(value.get()))
                updateMyFurniture(value.get(), gui, true);
            else
                updateMyFurniture(FurnitureManager.getInstance().getLoadedObjectsIDs().get(0), gui, true);
        } else
            updateMyFurniture(null, gui, true);
    }

    @Override
    public MyFurnitureFeature clone(FeatureParentInterface newParent) {
        MyFurnitureFeature clone = new MyFurnitureFeature(newParent, this.getFeatureSettings());
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
        if (Dependency.MY_FURNITURE.isEnabled()) {
            String id = getMyFurniture((GUI) manager.getCache().get(editor)).get();
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            id = nextMyFurniture(id);
            updateMyFurniture(id, (GUI) manager.getCache().get(editor), true);
        }
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        if (Dependency.MY_FURNITURE.isEnabled()) {
            String id = getMyFurniture((GUI) manager.getCache().get(editor)).get();
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            id = prevMyFurniture(id);
            updateMyFurniture(id, (GUI) manager.getCache().get(editor),false);
        }
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        if (Dependency.MY_FURNITURE.isEnabled())
            updateMyFurniture(nextMyFurniture(getMyFurniture((GUI) manager.getCache().get(editor)).get()), (GUI) manager.getCache().get(editor), true);
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        if (Dependency.MY_FURNITURE.isEnabled())
            updateMyFurniture(prevMyFurniture(getMyFurniture((GUI) manager.getCache().get(editor)).get()), (GUI) manager.getCache().get(editor), false);
        return true;
    }

    @Override
    public boolean doubleClicked(Player editor, NewGUIManager manager) {
        // to reverse the double click
        rightClicked(editor, manager);
        rightClicked(editor, manager);
        if (Dependency.MY_FURNITURE.isEnabled()) {
            Optional<Furniture> furniture = getValue();
            furniture.ifPresent(furniture1 -> furniture1.openEditor(editor));
        }
        return true;
    }

    public String nextMyFurniture(String id) {
        boolean next = false;
        for (String check : getSortMyFurniture()) {
            if (check.equals(id)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortMyFurniture().get(0);
    }

    public String prevMyFurniture(String id) {
        int i = -1;
        int cpt = 0;
        for (String check : getSortMyFurniture()) {
            if (check.equals(id)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortMyFurniture().get(getSortMyFurniture().size() - 1);
        else return getSortMyFurniture().get(cpt - 1);
    }

    public void updateMyFurniture(String id, GUI gui, boolean next) {
        if (id == null) id = "NULLLLL";
        Map<String, String> alreadyLinkedMap = getMyFurnitureAlreadyLinkedToAnEI(id);
        Set<String> alreadyLinked = alreadyLinkedMap.keySet();
        while (alreadyLinked.contains(id)) {
            if (next) id = nextMyFurniture(id);
            else id = prevMyFurniture(id);
        }
        value = Optional.of(id);
        ItemStack item = gui.getByName(getEditorName());
        int slot = gui.getInv().first(item);
        initItemParentEditor(gui, slot);
        item = gui.getByName(getEditorName());

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 4);
        int maxSize = lore.size();
        maxSize += getSortMyFurniture().size();
        if (maxSize > 17) maxSize = 17;

        if (Dependency.MY_FURNITURE.isEnabled()) {
            boolean find = false;
            for (String check : getSortMyFurniture()) {
                if (id.equals(check)) {
                    lore.add(StringConverter.coloredString("&2➤ &a" + id));
                    find = true;
                } else if (find) {
                    if (lore.size() == maxSize) break;
                    if (alreadyLinked.contains(check))
                        lore.add(StringConverter.coloredString("&6✦ &e" + check + " &7Already linked to an EI (ID: " + alreadyLinkedMap.get(check) + ")"));
                    else
                        lore.add(StringConverter.coloredString("&6✦ &e" + check));
                }
            }
            for (String check : getSortMyFurniture()) {
                if (lore.size() == maxSize) break;
                else {
                    if (alreadyLinked.contains(check))
                        lore.add(StringConverter.coloredString("&6✦ &e" + check + " &7Already linked to an EI (ID: " + alreadyLinkedMap.get(check) + ")"));
                    else
                        lore.add(StringConverter.coloredString("&6✦ &e" + check));
                }
            }
        } else {
            lore.add(StringConverter.coloredString("&4➤ &cYou must have &6ExecutableItems"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item);
    }

    public Optional<String> getMyFurniture(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                if (str.contains("You must have &6ExecutableItems")) return Optional.empty();
                str = StringConverter.decoloredString(str);
                return Optional.ofNullable(str.split("➤ ")[1]);
            }
        }
        return Optional.empty();
    }

    public List<String> getSortMyFurniture() {
        SortedMap<String, String> map = new TreeMap<String, String>();
        if (Dependency.MY_FURNITURE.isEnabled()) {
            for (String id : FurnitureManager.getInstance().getLoadedObjectsIDs()) {
                map.put(id, id);
            }
        }
        return new ArrayList<>(map.values());
    }

    public Map<String, String> getMyFurnitureAlreadyLinkedToAnEI(String idToIgnore) {
        Map<String, String> list = new HashMap<>();
        if(Dependency.MY_FURNITURE.isEnabled()) {
            for (Furniture furniture : FurnitureManager.getInstance().getLoadedObjects()) {
                if(furniture.getEi().getValueID().isPresent() && !furniture.getId().equals(idToIgnore)) {
                    list.put(furniture.getId(), furniture.getEi().getValueID().get());
                }
            }
        }
        return list;
    }

}
