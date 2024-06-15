package com.ssomar.score.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class BooleanFeature extends FeatureAbstract<Boolean, BooleanFeature> implements FeatureRequireClicksOrOneMessageInEditor, Serializable {

    private boolean value;
    private boolean noValueUsePlaceholder;
    private boolean defaultValue;
    private String placeholder;
    private boolean notSaveIfEqualsToDefaultValue;

    public BooleanFeature(FeatureParentInterface parent, Boolean defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.noValueUsePlaceholder = false;
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        reset();

        //System.out.println(ClassLayout.parseClass(BooleanFeature.class).toPrintable());
    }

    @Override
    public Material getEditorMaterial() {
        return super.getEditorMaterial() == null ? Material.LEVER : super.getEditorMaterial();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String valueStr = config.getString(this.getName(), "NULL");
        if (valueStr.contains("%")) {
            placeholder = valueStr;
            value = false;
            noValueUsePlaceholder = true;
        } else {
            placeholder = null;
            this.value = config.getBoolean(this.getName(), this.defaultValue);
            noValueUsePlaceholder = false;
            setPremium(isPremiumLoading);
            FeatureReturnCheckPremium<Boolean> checkPremium = checkPremium("Boolean", value, Optional.of(defaultValue), isPremiumLoading);
            if (checkPremium.isHasError()) value = checkPremium.getNewValue();
        }
        return errors;
    }

    @Override
    public BooleanFeature clone(FeatureParentInterface newParent) {
        BooleanFeature clone = new BooleanFeature(newParent, defaultValue, getFeatureSettings(), notSaveIfEqualsToDefaultValue);
        clone.setValue(value);
        clone.setPlaceholder(placeholder);
        clone.setNoValueUsePlaceholder(noValueUsePlaceholder);
        return clone;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (notSaveIfEqualsToDefaultValue) {
            if (!noValueUsePlaceholder && value == defaultValue) {
                config.set(this.getName(), null);
                return;
            }
        }
        if (placeholder != null) {
            config.set(this.getName(), placeholder);
        } else if(!noValueUsePlaceholder) config.set(this.getName(), value);
    }

    public Boolean getValue(@Nullable StringPlaceholder sp) {
        return getValue(null, sp);
    }

    public Boolean getValue(@Nullable UUID playerUUID, @Nullable StringPlaceholder sp) {
        if (placeholder != null) {
            String placeholderStr = placeholder;
            if (sp != null) {
                placeholderStr = sp.replacePlaceholder(placeholderStr);
            }
            placeholderStr = StringPlaceholder.replacePlaceholderOfPAPI(placeholderStr, playerUUID);
            return Boolean.valueOf(placeholderStr);
        } else if (!noValueUsePlaceholder) {
            return value;
        }
        return defaultValue;
    }

    @Override
    public Boolean getValue() {
        if (!noValueUsePlaceholder) {
            return value;
        } else if (placeholder != null) {
            String placeholderStr = placeholder;
            //SsomarDev.testMsg("Placeholder: " + placeholderStr, true);
            placeholderStr = new StringPlaceholder().replacePlaceholderOfPAPI(placeholderStr);
            return Boolean.valueOf(placeholderStr);
        }
        return defaultValue;
    }
    public boolean isConfigured() {
        return (!noValueUsePlaceholder && value) || placeholder != null;
    }

    @Override
    public BooleanFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 4] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 3] = "&8>> &6Enter placeholder: &eMIDDLE &a(Creative only)";
        finalDescription[finalDescription.length - 2] = "&7(Be sure your placeholder return &atrue &7or &cfalse&7)";
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if(!noValueUsePlaceholder) gui.updateBoolean(getEditorName(), value);
        else gui.updateCurrently(getEditorName(), placeholder);
    }

    @Override
    public void reset() {
        this.value = defaultValue;
        this.noValueUsePlaceholder = false;
        this.placeholder = null;
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        if (this.isRequirePremium() && !isPremium()) return;
        ((GUI) manager.getCache().get(editor)).changeBoolean(getEditorName());
        value = !getValue();
        this.noValueUsePlaceholder = false;
        this.placeholder = null;
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
        return false;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
    public void setValue(Optional<Boolean> value) {
        this.value = value.orElse(defaultValue);
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
        SCore.schedulerHook.runTask(runnable, 0);
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter the placeholder or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI) manager.getCache().get(editor)).getCurrently(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current placeholder")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new placeholder boolean")).create()));

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
        if(message.contains("%")) return Optional.empty();
        else return Optional.of(StringConverter.coloredString("&4&l[ERROR] &cThe message you entered is not a placeholder"));
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        this.value = false;
        this.noValueUsePlaceholder = true;
        this.placeholder = message;
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    @Override
    public void finishEditInEditorNoValue(Player editor, NewGUIManager manager) {
        if(noValueUsePlaceholder) this.value = defaultValue;
        this.noValueUsePlaceholder = false;
        this.placeholder = null;
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
