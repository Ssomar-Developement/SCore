package com.ssomar.score.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOneMessageInEditor;
import com.ssomar.score.features.FeatureReturnCheckPremium;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.NTools;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class DoubleFeature extends FeatureAbstract<Optional<Double>, DoubleFeature> implements FeatureRequireOneMessageInEditor {

    private Optional<Double> value;
    private Optional<Double> defaultValue;
    private Optional<String> placeholder;

    public DoubleFeature(FeatureParentInterface parent, String name, Optional<Double> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        reset();
    }

    public static DoubleFeature buildNull(){
        return new DoubleFeature(null, null, Optional.empty(), null, null, null, false);
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
            Optional<Double> valuePotential = NTools.getDouble(valueStr);
            if (valuePotential.isPresent()) {
                this.value = valuePotential;
                FeatureReturnCheckPremium<Double> checkPremium = checkPremium("Double", valuePotential.get(), defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.ofNullable(checkPremium.getNewValue());
            } else {
                errors.add("&cERROR, Couldn't load the double value of " + this.getName() + " from config, value: " + valueStr + " &7&o" + getParent().getParentInfo());
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

    public Optional<Double> getValue(@Nullable Player player, @Nullable StringPlaceholder sp) {
        if (placeholder.isPresent()) {
            String placeholderStr = placeholder.get();
            if (sp != null) {
                placeholderStr = sp.replacePlaceholder(placeholderStr);
            }
            if (player != null && SCore.hasPlaceholderAPI) {
                placeholderStr = PlaceholderAPI.setPlaceholders(player, placeholderStr);
            }
            Optional<Double> valuePotential = NTools.getDouble(placeholderStr);
            if (valuePotential.isPresent()) {
                return valuePotential;
            } else {
                return defaultValue;
            }
        } else if (value.isPresent()) {
            return value;
        } else return defaultValue;
    }

    @Override
    public Optional<Double> getValue() {
        if (value.isPresent()) {
            return value;
        } else return defaultValue;
    }

    @Override
    public DoubleFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if (placeholder.isPresent()) gui.updateActually(getEditorName(), placeholder.get());
        else if (value.isPresent()) gui.updateDouble(getEditorName(), getValue().get());
        else gui.updateDouble(getEditorName(), 0);
    }

    @Override
    public DoubleFeature clone(FeatureParentInterface newParent) {
        DoubleFeature clone = new DoubleFeature(newParent, this.getName(), defaultValue, getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
        clone.setValue(getValue());
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
        manager.requestWriting.put(editor, getEditorName());
        editor.closeInventory();
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter an integer or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI) manager.getCache().get(editor)).getActually(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current integer")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new integer")).create()));

        TextComponent noValue = new TextComponent(StringConverter.coloredString("&c&l[NO VALUE / EXIT]"));
        noValue.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "NO VALUE / EXIT"));
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

        Optional<Double> verify = NTools.getDouble(StringConverter.decoloredString(message).trim());
        if (verify.isPresent()) return Optional.empty();
        else return Optional.of(StringConverter.coloredString("&4&l[ERROR] &cThe message you entered is not a double"));
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        message = StringConverter.decoloredString(message).trim();
        if (message.contains("%")) {
            placeholder = Optional.of(message);
            value = Optional.empty();
        } else {
            placeholder = Optional.empty();
            value = NTools.getDouble(message);
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
