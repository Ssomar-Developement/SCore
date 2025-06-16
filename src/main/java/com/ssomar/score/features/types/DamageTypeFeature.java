package com.ssomar.score.features.types;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.TagKey;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class DamageTypeFeature extends FeatureAbstract<Optional<DamageType>, DamageTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<DamageType> value;
    private Optional<DamageType> defaultValue;

    public DamageTypeFeature(FeatureParentInterface parent, Optional<DamageType> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (colorStr.equals("NULL")) {
            if (defaultValue.isPresent()) value = defaultValue;
            return errors;
        }
        try {
            TypedKey<DamageType> key = TypedKey.create(RegistryKey.DAMAGE_TYPE, colorStr);
            DamageType damageType = RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).get(key);
            value = Optional.ofNullable(damageType);
            FeatureReturnCheckPremium<DamageType> checkPremium = checkPremium("DamageType", damageType, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the DamageType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> DamageType available: https://jd.papermc.io/paper/1.21.5/org/bukkit/damage/DamageType.html");
            if (defaultValue.isPresent()) value = defaultValue;
        }
        return errors;
    }

    public void setValue(TagKey<DamageType> value) {
        if (value == null) {
            this.value = Optional.empty();
            return;
        }
        this.value = Optional.ofNullable(RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).get(value.key()));
    }

    public TagKey<DamageType> getValueTagKey() {
        if (value.isPresent()) {
            return TagKey.create(RegistryKey.DAMAGE_TYPE, getValue().get().getKey());
        }
        return null;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (getValue().isPresent()) {
            if(defaultValue.isPresent() && isSavingOnlyIfDiffDefault() && getValue().get().equals(defaultValue.get())){
                config.set(this.getName(), null);
                return;
            }
            else config.set(this.getName(), getValue().get().getKey().toString());
        }
        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    @Override
    public Optional<DamageType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public DamageTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = TM.g(Text.EDITOR_CURRENTLY_NAME);

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<DamageType> value = getValue();
        DamageType finalValue = value.orElse(DamageType.PLAYER_ATTACK);
        updateOperation(finalValue, gui);
    }

    @Override
    public DamageTypeFeature clone(FeatureParentInterface newParent) {
        DamageTypeFeature clone = new DamageTypeFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        DamageType operation = getOperation((GUI) manager.getCache().get(editor));
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        updateOperation(operation, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        DamageType operation = getOperation((GUI) manager.getCache().get(editor));
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        updateOperation(operation, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateOperation(nextOperation(getOperation((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateOperation(prevOperation(getOperation((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean doubleClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean middleClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    public DamageType nextOperation(DamageType sound) {
        boolean next = false;
        for (DamageType check : RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE)) {
            if (check.equals(sound)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).iterator().next();
    }

    public DamageType prevOperation(DamageType sound) {
        int i = -1;
        int cpt = 0;
        for (DamageType check : RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE)) {
            if (check.equals(sound)) {
                i = cpt;
                break;
            }
            cpt++;
        }

        DamageType[] values = RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).stream().toArray(DamageType[]::new);
        if(i == 0) return values[values.length - 1];
        else return values[cpt - 1];

    }

    public void updateOperation(DamageType operation, GUI gui) {
        this.value = Optional.of(operation);
        ItemStack item = gui.getByIdentifier(getEditorName());
        ItemMeta meta = item.getItemMeta();
        Map<Object, String> map = new HashMap<>();
        for (DamageType check : RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE)) {
            map.put(check, check.getKey().toString());
        }
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += map.size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (Object check : map.keySet()) {
            if (operation == check) {
                lore.add(StringConverter.coloredString("&2➤ &a" + map.get(operation)));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + map.get(check)));
            }
        }
        for (Object check : map.keySet()) {
            if (lore.size() == maxSize) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + map.get(check)));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public DamageType getOperation(GUI gui) {
        ItemStack item = gui.getByIdentifier(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                String value = str.split("➤ ")[1];
                return RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).get(TypedKey.create(RegistryKey.DAMAGE_TYPE, value));
            }
        }
        return null;
    }

}
