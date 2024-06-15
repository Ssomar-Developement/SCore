package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class EntityTypeFeature extends FeatureAbstract<Optional<EntityType>, EntityTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<EntityType> value;
    private Optional<EntityType> defaultValue;

    public EntityTypeFeature(FeatureParentInterface parent, Optional<EntityType> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            EntityType entityType = EntityType.valueOf(colorStr);
            value = Optional.ofNullable(entityType);
            FeatureReturnCheckPremium<EntityType> checkPremium = checkPremium("EntityType", entityType, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the EntityType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> EntityTypes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html");
            value = Optional.empty();
        }
        return errors;
    }

    public List<String> load(SPlugin plugin, String config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.toUpperCase();
        try {
            EntityType entityType = EntityType.valueOf(colorStr);
            value = Optional.ofNullable(entityType);
            FeatureReturnCheckPremium<EntityType> checkPremium = checkPremium("EntityType", entityType, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the EntityType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> EntityTypes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<EntityType> value = getValue();
        value.ifPresent(entityType -> config.set(this.getName(), entityType.name()));
    }

    @Override
    public Optional<EntityType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public EntityTypeFeature initItemParentEditor(GUI gui, int slot) {
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
        Optional<EntityType> value = getValue();
        EntityType finalValue = value.orElse(EntityType.ZOMBIE);
        updateEntityType(finalValue, gui);
    }

    @Override
    public EntityTypeFeature clone(FeatureParentInterface newParent) {
        EntityTypeFeature clone = new EntityTypeFeature(newParent,getDefaultValue(), getFeatureSettings());
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
        EntityType entityType = getEntityType((GUI) manager.getCache().get(editor));
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        entityType = nextEntityType(entityType);
        updateEntityType(entityType, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        EntityType entityType = getEntityType((GUI) manager.getCache().get(editor));
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        entityType = prevEntityType(entityType);
        updateEntityType(entityType, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateEntityType(nextEntityType(getEntityType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateEntityType(prevEntityType(getEntityType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public EntityType nextEntityType(EntityType material) {
        boolean next = false;
        for (EntityType check : getSortEntityTypes()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortEntityTypes().get(0);
    }

    public EntityType prevEntityType(EntityType material) {
        int i = -1;
        int cpt = 0;
        for (EntityType check : getSortEntityTypes()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortEntityTypes().get(getSortEntityTypes().size() - 1);
        else return getSortEntityTypes().get(cpt - 1);
    }

    public void updateEntityType(EntityType entityType, GUI gui) {
        this.value = Optional.of(entityType);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 3);
        boolean find = false;
        for (EntityType check : getSortEntityTypes()) {
            if (entityType.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + entityType.name()));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (EntityType check : getSortEntityTypes()) {
            if (lore.size() == 17) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public EntityType getEntityType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return EntityType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<EntityType> getSortEntityTypes() {
        SortedMap<String, EntityType> map = new TreeMap<String, EntityType>();
        for (EntityType l : EntityType.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
