package com.ssomar.testRecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireOneMessageInEditor;
import com.ssomar.testRecode.features.FeatureReturnCheckPremium;
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
public class NumberConditionFeature extends FeatureAbstract<Optional<String>, NumberConditionFeature> implements FeatureRequireOneMessageInEditor {

    private Optional<String> value;

    public NumberConditionFeature(FeatureParentInterface parent, String name, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String valueStr = config.getString(this.getName(), "");
        if(valueStr.isEmpty()) {
            value = Optional.empty();
        }
        else{
            if(StringCalculation.isStringCalculation(valueStr)){
                value = Optional.of(valueStr);
            }
            else{
                errors.add("&cERROR, Couldn't load the Number Condition value of " + this.getName() + " from config, value: " + valueStr+ " &7&o"+getParent().getParentInfo()+" &6>> Number Condition example: CONDITION<=6 ");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        if(value.isPresent())config.set(this.getName(), value.get());
    }

    @Override
    public Optional<String> getValue() {
        if(value.isPresent() && !value.get().isEmpty()) return value;
        else return Optional.empty();
    }

    @Override
    public NumberConditionFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if(value.isPresent()) gui.updateActually(getEditorName(), getValue().get(), true);
        else gui.updateCondition(getEditorName(), "");
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {}

    @Override
    public NumberConditionFeature clone() {
        NumberConditionFeature clone = new NumberConditionFeature(getParent(), this.getName(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = Optional.empty();
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        manager.requestWriting.put(editor, getEditorName());
        editor.closeInventory();
        space(editor);

        editor.sendMessage(StringConverter.coloredString("&8➤ &7&oReplace {number} by the number of your choice !"));
        TextComponent message = new TextComponent( StringConverter.coloredString("&8➤ &7Choose an option: "));

        if(value.isPresent()) {
            TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
            edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, value.get()));
            edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current condition")).create()));

            message.addExtra(edit);
        }
        message.addExtra(new TextComponent(StringConverter.coloredString(" &7Or create new condition: ")));

        TextComponent inf = new TextComponent( StringConverter.coloredString("&a&l[<]"));
        inf.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "<{number}"));
        inf.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+getEditorName()+" < {number}") ).create()));

        TextComponent infE = new TextComponent( StringConverter.coloredString("&a&l[<=]"));
        infE.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "<={number}"));
        infE.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+getEditorName()+" <= {number}") ).create()));

        TextComponent egal = new TextComponent( StringConverter.coloredString("&a&l[==]"));
        egal.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "=={number}"));
        egal.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+getEditorName()+" == {number}") ).create()));

        TextComponent sup = new TextComponent( StringConverter.coloredString("&a&l[>]"));
        sup.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, ">{number}"));
        sup.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+getEditorName()+" > {number}") ).create()));

        TextComponent supE = new TextComponent( StringConverter.coloredString("&a&l[>=]"));
        supE.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, ">={number}"));
        supE.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+getEditorName()+" >= {number}") ).create()));

        TextComponent noC = new TextComponent( StringConverter.coloredString("&c&l[NO CONDITION]"));
        noC.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "exit with delete"));
        noC.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to don't set condition") ).create()));

        message.addExtra(inf);
        message.addExtra(new TextComponent(" "));
        message.addExtra(infE);
        message.addExtra(new TextComponent(" "));
        message.addExtra(egal);
        message.addExtra(new TextComponent(" "));
        message.addExtra(sup);
        message.addExtra(new TextComponent(" "));
        message.addExtra(supE);
        message.addExtra(new TextComponent(" "));
        message.addExtra(noC);

        editor.spigot().sendMessage(message);
        space(editor);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        if(!StringCalculation.isStringCalculation(StringConverter.decoloredString(message))){
            return Optional.of(StringConverter.coloredString("&c&l[ERROR] &7&oYou must define a correct condition ! 8&7&oExample: &eCONDITION>=10"));
        }
        else return Optional.empty();
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        String valueStr = StringConverter.decoloredString(message);
        if(valueStr.isEmpty()) value = Optional.empty();
        else value = Optional.of(valueStr);
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
