package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.NTools;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireOneMessageInEditor;
import com.ssomar.scoretestrecode.features.FeatureReturnCheckPremium;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ColorIntegerFeature extends FeatureAbstract<Optional<Integer>, ColorIntegerFeature> implements FeatureRequireOneMessageInEditor {

    private Optional<Integer> value;
    private Optional<Integer> defaultValue;

    public ColorIntegerFeature(FeatureParentInterface parent, String name, Optional<Integer> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String valueStr = config.getString(this.getName(), "NULL");
        if (!valueStr.equals("NULL")) {
            Optional<Integer> valuePotential = NTools.getInteger(valueStr);
            if (valuePotential.isPresent()) {
                this.value = valuePotential;
                FeatureReturnCheckPremium<Integer> checkPremium = checkPremium("Color in integer", valuePotential.get(), defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } else {
                errors.add("&cERROR, Couldn't load the color integer value of " + this.getName() + " from config, value: " + valueStr + " &7&o" + getParent().getParentInfo());
                if (defaultValue.isPresent()) {
                    this.value = defaultValue;
                } else this.value = Optional.empty();
            }
        } else {
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (value.isPresent()) {
            config.set(this.getName(), value.get());
        }
        else config.set(this.getName(), null);
    }

    @Override
    public Optional<Integer> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public ColorIntegerFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if (value.isPresent()) gui.updateInt(getEditorName(), getValue().get());
        else gui.updateActually(getEditorName(), "&cNO VALUE");
    }

    @Override
    public ColorIntegerFeature clone(FeatureParentInterface newParent) {
        ColorIntegerFeature clone = new ColorIntegerFeature(newParent, this.getName(), defaultValue, getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
        clone.value = value;
        return clone;
    }

    @Override
    public void reset() {
        if (defaultValue.isPresent()) this.value = Optional.of(defaultValue.get());
        else this.value = Optional.empty();
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        manager.requestWriting.put(editor, getEditorName());
        editor.closeInventory();
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter an color in integer or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI) manager.getCache().get(editor)).getActually(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current color integer")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new color integer")).create()));

        TextComponent noValue = new TextComponent(StringConverter.coloredString("&c&l[NO VALUE / EXIT]"));
        noValue.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "NO VALUE / EXIT"));
        noValue.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&cClick here to exit or don't set a value")).create()));

        TextComponent info = new TextComponent(StringConverter.coloredString("&e&l[GET COLOR WEBSITE]"));
        info.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.tydac.ch/color/"));
        info.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here, select your color and copy the mapInfo Color")).create()));

        message.addExtra(new TextComponent(" "));
        message.addExtra(edit);
        message.addExtra(new TextComponent(" "));
        message.addExtra(newName);
        message.addExtra(new TextComponent(" "));
        message.addExtra(noValue);
        message.addExtra(new TextComponent(" "));
        message.addExtra(info);

        editor.spigot().sendMessage(message);
        space(editor);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        Optional<Integer> verify = NTools.getInteger(StringConverter.decoloredString(message).trim());
        if (verify.isPresent()) return Optional.empty();
        else
            return Optional.of(StringConverter.coloredString("&4&l[ERROR] &cThe message you entered is not a color integer"));
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        this.value = NTools.getInteger(StringConverter.decoloredString(message).trim());
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    @Override
    public void finishEditInEditorNoValue(Player editor, NewGUIManager manager) {
        this.value = Optional.empty();
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
