package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class IntegerFeature extends FeatureAbstract<Optional<Integer>, IntegerFeature> implements FeatureRequireOneMessageInEditor {

    private Optional<Integer> value;
    private Optional<Integer> defaultValue;
    private Optional<String> placeholder;

    public IntegerFeature(FeatureParentInterface parent,Optional<Integer> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        reset();
    }

    public static IntegerFeature buildNull() {
        return new IntegerFeature(null, Optional.empty(), null);
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
            Optional<Integer> valuePotential = NTools.getInteger(valueStr);
            if (valuePotential.isPresent()) {
                this.value = valuePotential;
                FeatureReturnCheckPremium<Integer> checkPremium = checkPremium("Integer", valuePotential.get(), defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.ofNullable(checkPremium.getNewValue());
            } else {
                if (!valueStr.equals("NULL"))
                    errors.add("&cERROR, Couldn't load the integer value of " + this.getName() + " from config, value: " + valueStr + " &7&o" + getParent().getParentInfo());
                this.value = defaultValue;
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (placeholder.isPresent()) {
            config.set(this.getName(), placeholder.get());
        } else if (getValue().isPresent()) {
            config.set(this.getName(), getValue().get());
        }
    }

    public Optional<Integer> getValue(@Nullable UUID playerUUID, @Nullable StringPlaceholder sp) {
        if (placeholder.isPresent()) {
            String placeholderStr = placeholder.get();
            if (sp != null) {
                placeholderStr = sp.replacePlaceholder(placeholderStr);
            }
            placeholderStr = StringPlaceholder.replacePlaceholderOfPAPI(placeholderStr, playerUUID);

            Optional<Integer> valuePotential = NTools.getInteger(placeholderStr);
            if (valuePotential.isPresent()) return valuePotential;
        } else if (value.isPresent()) {
            return value;
        }
        return defaultValue;
    }

    @Override
    public Optional<Integer> getValue() {
        if (value.isPresent()) {
            return value;
        } else if (placeholder.isPresent()) {
            String placeholderStr = placeholder.get();
            //SsomarDev.testMsg("Placeholder: " + placeholderStr, true);
            placeholderStr = new StringPlaceholder().replacePlaceholderOfPAPI(placeholderStr);
            Optional<Integer> valuePotential = NTools.getInteger(placeholderStr);
            if (valuePotential.isPresent()) return valuePotential;
        }
        return defaultValue;
    }

    @Override
    public IntegerFeature initItemParentEditor(GUI gui, int slot) {
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
        if (placeholder.isPresent()) gui.updateCurrently(getEditorName(), placeholder.get());
        else if (value.isPresent()) gui.updateInt(getEditorName(), getValue().get());
        else gui.updateDouble(getEditorName(), 0);
    }

    @Override
    public IntegerFeature clone(FeatureParentInterface newParent) {
        IntegerFeature clone = new IntegerFeature(newParent, defaultValue, getFeatureSettings());
        clone.setValue(value);
        clone.setPlaceholder(getPlaceholder());
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
        this.placeholder = Optional.empty();
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        if (this.isRequirePremium() && !isPremium()) return;
        manager.requestWriting.put(editor, getEditorName());
        editor.closeInventory();
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter an integer or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI) manager.getCache().get(editor)).getCurrently(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current integer")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new integer")).create()));

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

        if (message.contains("%")) return Optional.empty();

        Optional<Integer> verify = NTools.getInteger(StringConverter.decoloredString(message).trim());
        if (verify.isPresent()) return Optional.empty();
        else
            return Optional.of(StringConverter.coloredString("&4&l[ERROR] &cThe message you entered is not a integer or a placeholder"));
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        message = StringConverter.decoloredString(message).trim();
        if (message.contains("%")) {
            placeholder = Optional.of(message);
            value = Optional.empty();
        } else {
            placeholder = Optional.empty();
            value = NTools.getInteger(message);
        }
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    @Override
    public void finishEditInEditorNoValue(Player editor, NewGUIManager manager) {
        this.value = Optional.empty();
        this.placeholder = Optional.empty();
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
