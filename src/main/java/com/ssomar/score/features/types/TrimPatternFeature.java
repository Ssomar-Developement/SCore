package com.ssomar.score.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
@Setter
public class TrimPatternFeature extends FeatureAbstract<Optional<TrimPattern>, TrimPatternFeature> implements FeatureRequireClicksOrOneMessageInEditor {

    private Optional<TrimPattern> value;
    private Optional<TrimPattern> defaultValue;

    public TrimPatternFeature(FeatureParentInterface parent,Optional<TrimPattern> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            TrimPattern material = getTrimPattern(colorStr);
            value = Optional.ofNullable(material);
            FeatureReturnCheckPremium<TrimPattern> checkPremium = checkPremium("TrimPattern", material, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            if(!colorStr.equals("NULL")) errors.add("&cERROR, Couldn't load the TrimPattern value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Patterns available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/meta/trim/TrimPattern.html");
            value = Optional.empty();
        }
        return errors;
    }


    @Override
    public void save(ConfigurationSection config) {
        Optional<TrimPattern> value = getValue();
        value.ifPresent(material ->{
            String fix = material.getKey().toString();
            if(fix.contains("minecraft:")){
                fix = fix.replace("minecraft:", "");
                fix = fix.toUpperCase();
            }
            config.set(this.getName(), fix);
        });
    }

    @Override
    public Optional<TrimPattern> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public TrimPatternFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 3] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 2] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";
        finalDescription[finalDescription.length - 1] = "&8>> &6Type manually: &eMIDDLE &a(Creative only)";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<TrimPattern> value = getValue();
        TrimPattern finalValue = value.orElse(TrimPattern.EYE);
        updatePattern(finalValue, gui);
    }

    @Override
    public TrimPatternFeature clone(FeatureParentInterface newParent) {
        TrimPatternFeature clone = new TrimPatternFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        TrimPattern material = getPattern((GUI) manager.getCache().get(editor));
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        material = nextPattern(material);
        updatePattern(material, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        TrimPattern material = getPattern((GUI) manager.getCache().get(editor));
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        material = prevPattern(material);
        updatePattern(material, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updatePattern(nextPattern(getPattern((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updatePattern(prevPattern(getPattern((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public TrimPattern nextPattern(TrimPattern material) {
        boolean next = false;
        for (TrimPattern check : getSortPatterns()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next){
                return check;
            }
        }
        return getSortPatterns().get(0);
    }

    public TrimPattern prevPattern(TrimPattern material) {
        int i = -1;
        int cpt = 0;
        for (TrimPattern check : getSortPatterns()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        TrimPattern finalMaterial;
        if (i == 0) finalMaterial = getSortPatterns().get(getSortPatterns().size() - 1);
        else finalMaterial = getSortPatterns().get(cpt - 1);

        return finalMaterial;
    }

    public void updatePattern(TrimPattern material, GUI gui) {
        Material mat = getConverter().get(material);
        ItemStack item = gui.getByName(getEditorName());
        value = Optional.of(material);
        item.setType(mat);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 4);
        boolean find = false;
        for (TrimPattern check : getSortPatterns()) {
            if (material.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + getStringValue(material)));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + getStringValue(check)));
            }
        }
        for (TrimPattern check : getSortPatterns()) {
            if (lore.size() == 17) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + getStringValue(check)));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public TrimPattern getPattern(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return getTrimPattern(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<TrimPattern> getSortPatterns() {
        SortedMap<String, TrimPattern> map = new TreeMap<String, TrimPattern>();
        for (TrimPattern l : Registry.TRIM_PATTERN) {
            map.put(l.getKey().getKey(), l);
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        manager.requestWriting.put(editor, getEditorName());
        /* Close inventory sync */
        BukkitRunnable runnable = new BukkitRunnable() {
            public void run() {
                editor.closeInventory();
            }
        };
        runnable.runTask(SCore.plugin);
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter the material or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI) manager.getCache().get(editor)).getCurrently(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current material")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new material")).create()));

        TextComponent noValue = new TextComponent(StringConverter.coloredString("&c&l[NO VALUE / EXIT]"));
        noValue.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/score interact NO VALUE / EXIT"));
        noValue.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&cClick here to exit or don't set a value")).create()));


        message.addExtra(new TextComponent(" "));
        message.addExtra(edit);
        message.addExtra(new TextComponent(" "));
        message.addExtra(newName);
        message.addExtra(new TextComponent(" "));
        message.addExtra(noValue);

        editor.spigot().sendMessage(message);
        space(editor);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        try {
            Material.valueOf(StringConverter.decoloredString(message).trim().toUpperCase());
        } catch (Exception e) {
            return Optional.of(StringConverter.coloredString("&4&l[ERROR] &cThe message you entered is not a material"));
        }
        return Optional.empty();
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        this.value = Optional.of(getTrimPattern(StringConverter.decoloredString(message).trim().toUpperCase()));
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    @Override
    public void finishEditInEditorNoValue(Player editor, NewGUIManager manager) {
        this.value = Optional.empty();
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }


    public Map<TrimPattern, Material> getConverter(){
        Map<TrimPattern, Material> map = new HashMap<>();
        map.put(TrimPattern.EYE, Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.RIB, Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.COAST, Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.DUNE, Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.HOST, Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.RAISER, Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.SENTRY, Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.SHAPER, Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.SILENCE, Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.SNOUT, Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.SPIRE, Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.TIDE, Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.VEX, Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.WARD, Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.WAYFINDER, Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE);
        map.put(TrimPattern.WILD, Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);

        for (TrimPattern mat : Registry.TRIM_PATTERN) {
            if (!map.containsKey(mat)) {
                map.put(mat, Material.BARRIER);
            }
        }
        return map;

    }

    public TrimPattern getTrimPattern(String str) {
        String fix = StringConverter.decoloredString(str);
        if(!fix.contains(":")){
            fix = "minecraft:"+fix;
            fix = fix.toLowerCase();
        }

        TrimPattern material = Registry.TRIM_PATTERN.get(NamespacedKey.fromString(fix));
        return material;
    }

    public String getStringValue(TrimPattern material) {
        String fix = material.getKey().toString();
        if(fix.contains("minecraft:")){
            fix = fix.replace("minecraft:", "");
            fix = fix.toUpperCase();
        }
        return fix;
    }
}
