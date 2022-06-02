package com.ssomar.testRecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.NTools;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireOneMessageInEditor;
import com.ssomar.testRecode.editor.NewGUIManager;
import lombok.Getter;
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

@Getter
public class DoubleFeature extends FeatureAbstract<Optional<Double>, DoubleFeature> implements FeatureRequireOneMessageInEditor {

    private Optional<Double> value;
    private Optional<Double> defaultValue;

    public DoubleFeature(FeatureParentInterface parent, String name, Optional<Double> defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String valueStr = config.getString(getName(), "NULL");
        Optional<Double> valuePotential = NTools.getDouble(valueStr);
        if(valuePotential.isPresent()) {
            this.value = valuePotential;
        }
        else {
            errors.add("&cERROR, Couldn't load the double value of " + getName() + " from config, value: " + valueStr+ " &7&o"+getParent().getParentInfo());
            if (defaultValue.isPresent()) {
                this.value = defaultValue;
            }
            else this.value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        if(value.isPresent()) {
            config.set(getName(), value.get());
        }
    }

    @Override
    public Optional<Double> getValue() {
        return value;
    }

    @Override
    public DoubleFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if(value.isPresent()) gui.updateDouble(getEditorName(), getValue().get());
        else gui.updateDouble(getEditorName(), 0);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        value = Optional.of(((GUI) manager.getCache().get(player)).getDouble(getEditorName()));
    }

    @Override
    public DoubleFeature clone() {
        DoubleFeature clone = new DoubleFeature(getParent(), getName(), defaultValue, getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
        clone.value = value;
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

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter an integer or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI)manager.getCache().get(editor)).getActually(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current integer")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new integer")).create()));

        message.addExtra(new TextComponent(" "));
        message.addExtra(edit);
        message.addExtra(new TextComponent(" "));
        message.addExtra(newName);

        editor.spigot().sendMessage(message);
        space(editor);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        Optional<Double> verify = NTools.getDouble(StringConverter.decoloredString(message).trim());
        if(verify.isPresent()) return Optional.empty();
        else return Optional.of(StringConverter.coloredString("&4&l[ERROR] &cThe message you entered is not a double"));
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        this.value = NTools.getDouble(StringConverter.decoloredString(message).trim());
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
