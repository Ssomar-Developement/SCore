package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.sactivator.SOption;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireOnlyClicksInEditor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Getter
@Setter
public class SOptionFeature extends FeatureAbstract<SOption, SOptionFeature> implements FeatureRequireOnlyClicksInEditor {

    private SOption value;
    private SPlugin plugin;
    private SOption builderInstance;

    public SOptionFeature(SPlugin sPlugin, SOption builderInstance, FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.plugin = sPlugin;
        this.builderInstance = builderInstance;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            SOption option = builderInstance.getOption(colorStr);
            if(option == null){
                errors.add("&cERROR, Couldn't load the Option value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Options available: https://docs.ssomar.com/");
                option = builderInstance.getDefaultValue();
            }
            this.value = option;
            if (!isPremiumLoading && builderInstance.getPremiumOption().contains(option)) {
                errors.add("&cERROR, Couldn't load the Option value of " + this.getName() + " from config, value: " + value + " &7&o" + getParent().getParentInfo() + " &6>> Because it's a premium Option !");
                value = builderInstance.getDefaultValue();
            }
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the Option value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Options available: https://docs.ssomar.com/");
            this.value = builderInstance.getDefaultValue();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), value.toString());
    }

    @Override
    public SOption getValue() {
        return value;
    }

    @Override
    public SOptionFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        updateOption(getValue(), gui, !getPlugin().isLotOfWork(), true);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {}

    @Override
    public SOptionFeature clone() {
        SOptionFeature clone = new SOptionFeature(plugin, builderInstance, getParent(), this.getName(), getEditorName(), getEditorDescription(), getEditorMaterial(), requirePremium());
        clone.setValue(value);
        return clone;
    }

    @Override
    public void reset() {
        this.value = builderInstance.getDefaultValue();
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
        SOption option = getOption((GUI) manager.getCache().get(editor));
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        option = nextOption(option);
        updateOption(option, (GUI) manager.getCache().get(editor), !getPlugin().isLotOfWork(), true);
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        SOption option = getOption((GUI) manager.getCache().get(editor));
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        option = prevOption(option);
        updateOption(option, (GUI) manager.getCache().get(editor), !getPlugin().isLotOfWork(), false);
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateOption(nextOption(getOption((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor), !getPlugin().isLotOfWork(), true);
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateOption(prevOption(getOption((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor), !getPlugin().isLotOfWork(), false);
        return true;
    }

    public SOption nextOption(SOption option) {
        boolean next = false;
        for (SOption check : getSortOptions()) {
            if (check.equals(option)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortOptions().get(0);
    }

    public SOption prevOption(SOption option) {
        int i = -1;
        int cpt = 0;
        for (SOption check : getSortOptions()) {
            if (check.equals(option)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortOptions().get(getSortOptions().size() - 1);
        else return getSortOptions().get(cpt - 1);
    }

    public void updateOption(SOption option, GUI gui, boolean isPremiumLoading, boolean next) {
        while (!isPremiumLoading && option.getPremiumOption().contains(option)) {
            if(next) option = nextOption(option);
            else option = prevOption(option);
        }
        value = option;
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 3);
        boolean find = false;
        for (SOption check : getSortOptions()) {
            if (option.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + option));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                if (!isPremiumLoading && option.getPremiumOption().contains(check))
                    lore.add(StringConverter.coloredString("&6✦ &e" + check + " &7Premium"));
                else
                    lore.add(StringConverter.coloredString("&6✦ &e" + check));
            }
        }
        for (SOption check : getSortOptions()) {
            if (lore.size() == 17) break;
            else {
                if (!isPremiumLoading && option.getPremiumOption().contains(check))
                    lore.add(StringConverter.coloredString("&6✦ &e" + check + " &7Premium"));
                else
                    lore.add(StringConverter.coloredString("&6✦ &e" + check));
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

    public SOption getOption(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return builderInstance.getOption(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<SOption> getSortOptions() {
        SortedMap<String, SOption> map = new TreeMap<String, SOption>();
        for (SOption l : builderInstance.getValues()) {
            map.put(l.toString(), l);
        }
        return new ArrayList<>(map.values());
    }

}
