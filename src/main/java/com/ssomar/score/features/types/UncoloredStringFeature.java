package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class UncoloredStringFeature extends FeatureAbstract<Optional<String>, UncoloredStringFeature> implements FeatureRequireOneMessageInEditor {

    private Optional<String> value;
    private Optional<String> defaultValue;
    private boolean notSaveIfEqualsToDefaultValue;

    public UncoloredStringFeature(FeatureParentInterface parent, Optional<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        reset();
    }

    public static UncoloredStringFeature buildNull(){
        return new UncoloredStringFeature(null,  Optional.empty(), null,  false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();

        String valueStr = config.getString(this.getName(), "<<NULL>>");
        if (valueStr.equals("<<NULL>>")) {
            if (defaultValue.isPresent()) {
                valueStr = defaultValue.get();
                this.value = defaultValue;
            } else {
                valueStr = "";
                this.value = Optional.empty();
            }
        } else {
            valueStr = StringConverter.decoloredString(valueStr);
            if (valueStr.isEmpty()) value = Optional.empty();
            else value = Optional.of(valueStr);
        }
        FeatureReturnCheckPremium<String> checkPremium = checkPremium("Uncolored String", valueStr, defaultValue, isPremiumLoading);
        if (checkPremium.isHasError()) value = Optional.ofNullable(checkPremium.getNewValue());
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<String> value = getValue();
        if (value.isPresent()) {
            if (notSaveIfEqualsToDefaultValue && defaultValue.isPresent()) {
                if (value.get().equals(defaultValue.get())) {
                    config.set(this.getName(), null);
                    return;
                }
            }
            config.set(this.getName(), value.get());
        } else {
            config.set(this.getName(), null);
        }
    }

    @Override
    public Optional<String> getValue() {
        return value;
    }

    @Override
    public UncoloredStringFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 2] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if (value.isPresent() && !StringConverter.decoloredString(value.get()).isEmpty())
            gui.updateCurrently(getEditorName(), getValue().get(), true);
        else gui.updateCurrently(getEditorName(), "&cEMPTY STRING", true);
    }

    @Override
    public UncoloredStringFeature clone(FeatureParentInterface newParent) {
        UncoloredStringFeature clone = new UncoloredStringFeature(newParent, getDefaultValue(), getFeatureSettings(), notSaveIfEqualsToDefaultValue);
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        if (this.isRequirePremium() && !isPremium()) return;
        manager.requestWriting.put(editor, getEditorName());
        editor.closeInventory();
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter a string or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI) manager.getCache().get(editor)).getCurrentlyWithColor(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current string")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new string")).create()));

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
        return Optional.empty();
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        String valueStr = StringConverter.decoloredString(message);
        if (message.contains("EMPTY STRING")) value = Optional.of("");
        else if (valueStr.isEmpty()) value = Optional.empty();
        else value = Optional.of(valueStr);
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    @Override
    public void finishEditInEditorNoValue(Player editor, NewGUIManager manager) {
        value = Optional.empty();
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
