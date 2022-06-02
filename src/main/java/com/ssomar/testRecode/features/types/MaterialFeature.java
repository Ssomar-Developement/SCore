package com.ssomar.testRecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireOnlyClicksInEditor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter @Setter
public class MaterialFeature extends FeatureAbstract<Optional<Material>, MaterialFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<Material> value;
    private Optional<Material> defaultValue;

    public MaterialFeature(FeatureParentInterface parent, String name, Optional<Material> defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            value = Optional.ofNullable(Material.valueOf(colorStr));
            if(requirePremium() && !isPremiumLoading) {
                errors.add("&cERROR, Couldn't load the Material value of " + this.getName() + " from config, value: " + colorStr+ " &7&o"+getParent().getParentInfo()+" &6>> Because it's a premium feature !");
                value = Optional.empty();
            }
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the Material value of " + this.getName() + " from config, value: " + colorStr+ " &7&o"+getParent().getParentInfo()+" &6>> Materials available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<Material> value = getValue();
        if(value.isPresent()) config.set(this.getName(), value.get().name());
    }

    @Override
    public Optional<Material> getValue() {
        if(value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public MaterialFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<Material> value = getValue();
        Material finalValue = value.orElse(Material.STONE);
        updateMaterial(finalValue, gui);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        this.value = Optional.of(getMaterial( (GUI) manager.getCache().get(player)));
    }

    @Override
    public MaterialFeature clone() {
        MaterialFeature clone = new MaterialFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), requirePremium());
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
        Material material = getMaterial( (GUI) manager.getCache().get(editor));
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        updateMaterial(material, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        Material material = getMaterial( (GUI) manager.getCache().get(editor));
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        updateMaterial(material, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateMaterial(nextMaterial(getMaterial( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateMaterial(prevMaterial(getMaterial( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public Material nextMaterial(Material material) {
        boolean next = false;
        for (Material check : getSortMaterials()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortMaterials().get(0);
    }

    public Material prevMaterial(Material material) {
        int i = -1;
        int cpt = 0;
        for (Material check : getSortMaterials()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortMaterials().get(getSortMaterials().size()- 1);
        else return getSortMaterials().get(cpt - 1);
    }

    public void updateMaterial(Material material, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        item.setType(material);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 3);
        boolean find = false;
        for (Material check : getSortMaterials()) {
            if (material.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" +material.name()));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (Material check : getSortMaterials()) {
            if (lore.size() == 17) break;
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

    public Material getMaterial(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return Material.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<Material> getSortMaterials() {
        SortedMap<String, Material> map = new TreeMap<String, Material>();
        for (Material l : Material.values()) {
            if(l.isItem() && !l.isAir()) map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
