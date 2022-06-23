package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireOneMessageInEditor;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
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

import static com.ssomar.score.menu.conditions.RequestMessage.space;

@Getter @Setter
public class ColoredStringFeature extends FeatureAbstract<Optional<String>, ColoredStringFeature> implements FeatureRequireOneMessageInEditor {

    private Optional<String> value;
    private Optional<String> defaultValue;
    private boolean notSaveIfEqualsToDefaultValue;

    public ColoredStringFeature(FeatureParentInterface parent, String name, Optional<String> defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String valueStr = config.getString(this.getName(), "<<NULL>>");
        if(valueStr.equals("<<NULL>>")){
            if(defaultValue.isPresent()){
                valueStr = defaultValue.get();
                this.value = defaultValue;
            }
            else{
                valueStr = "";
                this.value = Optional.empty();
            }
        }
        else value = Optional.of(valueStr);
        FeatureReturnCheckPremium<String> checkPremium = checkPremium("Colored String", valueStr, defaultValue, isPremiumLoading);
        if(checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<String> value = getValue();
        if(value.isPresent()) {
            if(notSaveIfEqualsToDefaultValue && defaultValue.isPresent()) {
                if(value.get().equals(defaultValue.get())){
                    config.set(this.getName(), null);
                    return;
                }
            }
            config.set(this.getName(), value.get());
        }
        else {
            config.set(this.getName(), null);
        }
    }

    @Override
    public Optional<String> getValue() {
        return value;
    }

    public Optional<String> getColoreValue() {
        if(value.isPresent()) {
            return Optional.of(StringConverter.coloredString(value.get()));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public ColoredStringFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if(value.isPresent() && !StringConverter.decoloredString(value.get()).isEmpty()) gui.updateActually(getEditorName(), getValue().get(), true);
        else gui.updateActually(getEditorName(), "&cEMPTY STRING", true);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {}

    @Override
    public ColoredStringFeature clone() {
        ColoredStringFeature clone = new ColoredStringFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), notSaveIfEqualsToDefaultValue);
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        if(defaultValue.isPresent()) this.value = Optional.of(defaultValue.get());
        else this.value = Optional.empty();
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        manager.requestWriting.put(editor, getEditorName());
        editor.closeInventory();
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter a string or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI)manager.getCache().get(editor)).getActuallyWithColor(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current string")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new string")).create()));

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
        return Optional.empty();
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        String valueStr = message;
        if(message.contains("EMPTY STRING")) value = Optional.of("");
        else if(valueStr.isEmpty()) value = Optional.empty();
        else value = Optional.of(valueStr);
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
