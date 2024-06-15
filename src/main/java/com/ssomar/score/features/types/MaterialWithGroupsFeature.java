package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.MaterialWithGroups;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class MaterialWithGroupsFeature extends FeatureAbstract<Optional<String>, MaterialWithGroupsFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<String> value;
    private Optional<String> defaultValue;
    private boolean acceptAir;
    private boolean acceptItems;
    private boolean acceptBlocks;

    public MaterialWithGroupsFeature(FeatureParentInterface parent, Optional<String> defaultValue, FeatureSettingsInterface featureSettings, boolean acceptAir, boolean acceptItems, boolean acceptBlocks) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
        this.acceptAir = acceptAir;
        this.acceptItems = acceptItems;
        this.acceptBlocks = acceptBlocks;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        value = MaterialWithGroups.getMaterialWithGroups(colorStr);
        if (!value.isPresent()) {
            errors.add("&cERROR, Couldn't load the Material with groups value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Materials with groups available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
        } else if (this.isRequirePremium() && !isPremiumLoading) {
            errors.add("&cERROR, Couldn't load the Material with groups value of " + this.getName() + " from config, value: " + value + " &7&o" + getParent().getParentInfo() + " &6>> Because it's a premium feature !");
            value = Optional.empty();
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<String> value = getValue();
        value.ifPresent(s -> config.set(this.getName(), s));
    }

    @Override
    public Optional<String> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public MaterialWithGroupsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<String> value = getValue();
        String finalValue = value.orElse(getSortMaterials().get(0));
        updateMaterialWithGroups(finalValue, gui);
    }

    @Override
    public MaterialWithGroupsFeature clone(FeatureParentInterface newParent) {
        MaterialWithGroupsFeature clone = new MaterialWithGroupsFeature(newParent, getDefaultValue(), getFeatureSettings(), isAcceptAir(), isAcceptItems(), isAcceptBlocks());
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
        String string = getMaterialWithGroups((GUI) manager.getCache().get(editor));
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        string = nextMaterialWithGroups(string);
        updateMaterialWithGroups(string, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        String string = getMaterialWithGroups((GUI) manager.getCache().get(editor));
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        string = prevMaterialWithGroups(string);
        updateMaterialWithGroups(string, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateMaterialWithGroups(nextMaterialWithGroups(getMaterialWithGroups((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateMaterialWithGroups(prevMaterialWithGroups(getMaterialWithGroups((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public String nextMaterialWithGroups(String material) {
        boolean next = false;
        for (String check : getSortMaterials()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortMaterials().get(0);
    }

    public String prevMaterialWithGroups(String material) {
        int i = -1;
        int cpt = 0;
        for (String check : getSortMaterials()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortMaterials().get(getSortMaterials().size() - 1);
        else return getSortMaterials().get(cpt - 1);
    }

    public void updateMaterialWithGroups(String material, GUI gui) {
        value = Optional.of(material);
        ItemStack item = gui.getByName(getEditorName());
        item.setType(MaterialWithGroups.getMaterial(material));
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 3);
        boolean find = false;
        for (String check : getSortMaterials()) {
            if (material.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + material));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check));
            }
        }
        for (String check : getSortMaterials()) {
            if (lore.size() == 17) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public String getMaterialWithGroups(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return MaterialWithGroups.getMaterialWithGroups(str.split("➤ ")[1]).get();
            }
        }
        return null;
    }

    public List<String> getSortMaterials() {
        return MaterialWithGroups.getMaterialWithGroupsList(acceptAir, acceptItems, acceptBlocks);
    }

}
