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

    private Optional<Boolean> value;
    private Boolean defaultValue;
    private Optional<String> placeholder;
    private boolean notSaveIfEqualsToDefaultValue;

    public BooleanFeature(FeatureParentInterface parent, String name, boolean defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = Optional.of(defaultValue);
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String valueStr = config.getString(this.getName(), "NULL");
        if (valueStr.contains("%")) {
            placeholder = Optional.of(valueStr);
            value = Optional.empty();
        } else {
            placeholder = Optional.empty();
            this.value = Optional.of(config.getBoolean(this.getName(), this.defaultValue));
            setPremium(isPremiumLoading);
            FeatureReturnCheckPremium<Boolean> checkPremium = checkPremium("Boolean", value.get(), Optional.of(defaultValue), isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        }
        return errors;
    }

    @Override
    public BooleanFeature clone(FeatureParentInterface newParent) {
        BooleanFeature clone = new BooleanFeature(newParent, this.getName(), defaultValue, getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), notSaveIfEqualsToDefaultValue);
        clone.setValue(value);
        clone.setPlaceholder(placeholder);
        return clone;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (notSaveIfEqualsToDefaultValue) {
            if (value.isPresent() && value.get() == defaultValue) {
                config.set(this.getName(), null);
                return;
            }
        }
        if (placeholder.isPresent()) {
            config.set(this.getName(), placeholder.get());
        } else value.ifPresent(aBoolean -> config.set(this.getName(), aBoolean));
    }

    public Boolean getValue(@Nullable StringPlaceholder sp) {
        return getValue(null, sp);
    }

    public Boolean getValue(@Nullable UUID playerUUID, @Nullable StringPlaceholder sp) {
        if (placeholder.isPresent()) {
            String placeholderStr = placeholder.get();
            if (sp != null) {
                placeholderStr = sp.replacePlaceholder(placeholderStr);
            }
            placeholderStr = StringPlaceholder.replacePlaceholderOfPAPI(placeholderStr, playerUUID);
            return Boolean.valueOf(placeholderStr);
        } else if (value.isPresent()) {
            return value.get();
        }
        return defaultValue;
    }

    @Override
    public Boolean getValue() {
        if (value.isPresent()) {
            return value.get();
        } else if (placeholder.isPresent()) {
            String placeholderStr = placeholder.get();
            //SsomarDev.testMsg("Placeholder: " + placeholderStr, true);
            placeholderStr = new StringPlaceholder().replacePlaceholderOfPAPI(placeholderStr);
            return Boolean.valueOf(placeholderStr);
        }
        return defaultValue;
    }
    public boolean isConfigured() {
        return (value.isPresent() && value.get()) || placeholder.isPresent();
    }

    @Override
    public BooleanFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && requirePremium()) {
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
        if(value.isPresent())
         gui.updateBoolean(getEditorName(), value.get());
        else gui.updateCurrently(getEditorName(), placeholder.get());
    }

    @Override
    public void reset() {
        this.value = Optional.of(defaultValue);
        this.placeholder = Optional.empty();
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        if (requirePremium() && !isPremium()) return;
        ((GUI) manager.getCache().get(editor)).changeBoolean(getEditorName());
        value = Optional.of(!getValue());
        this.placeholder = Optional.empty();
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
        this.value = Optional.of(value);
    }
    public void setValue(Optional<Boolean> value) {
        this.value = value;
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
        this.value = Optional.empty();
        this.placeholder = Optional.of(message);
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    @Override
    public void finishEditInEditorNoValue(Player editor, NewGUIManager manager) {
        if(!value.isPresent()) this.value = Optional.of(defaultValue);
        this.placeholder = Optional.empty();
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
