package com.ssomar.score.features.custom.commands.console;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.console.ConsoleCommandManager;
import com.ssomar.score.commands.runnable.console.ConsoleRunCommandsBuilder;
import com.ssomar.score.commands.runnable.player.PlayerCommandManager;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireSubTextEditorInEditor;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.custom.commands.CommandsAbstractFeature;
import com.ssomar.score.menu.EditorCreator;
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

import java.util.*;

@Getter
@Setter
public class ConsoleCommandsFeature extends CommandsAbstractFeature<List<String>, ConsoleCommandsFeature> implements FeatureRequireSubTextEditorInEditor {

    private List<String> value;
    private boolean notSaveIfEqualsToDefaultValue;

    public ConsoleCommandsFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, featureSettings);
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = PlayerCommandManager.getInstance().getCommandsVerified(plugin, config.getStringList(getName()), errors, getParent().getParentInfo());
        if(value.isEmpty()) value = null;
        return errors;
    }

    public void runCommands(ActionInfo actionInfo, String objectName) {
        if(getValue() == null) return;
        List<String> commands = new ArrayList<>(getValue());
        ConsoleRunCommandsBuilder builder = new ConsoleRunCommandsBuilder(commands, actionInfo);
        CommandsExecutor.runCommands(builder);
    }

    @Override
    public void save(ConfigurationSection config) {
        if(notSaveIfEqualsToDefaultValue && (value == null || value.isEmpty())){
            config.set(this.getName(), null);
            return;
        }
        config.set(this.getName(), value);
    }

    @Override
    public List<String> getValue() {
        if (value == null) return new ArrayList<>();
        return value;
    }

    @Override
    public ConsoleCommandsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Your " + getEditorName() + ": ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        List<String> update = new ArrayList<>();

        if(!(value == null || value.isEmpty())) {
            if (value.size() > 10) {
                for (int i = 0; i < 10; i++) update.add(value.get(i));
                update.add("&6... &e" + value.size() + " &6commands");
            } else update.addAll(value);
            for (int i = 0; i < update.size(); i++) {
                String command = update.get(i);
                if (command.length() > 40) command = command.substring(0, 39) + "...";
                update.set(i, command);
            }
        }
        gui.updateConditionList(getEditorName(), update, "&cEMPTY");
    }

    @Override
    public ConsoleCommandsFeature clone(FeatureParentInterface newParent) {
        ConsoleCommandsFeature clone = new ConsoleCommandsFeature(newParent, getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = null;
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        return ConsoleCommandManager.getInstance().verifCommand(message);
    }

    @Override
    public List<String> getCurrentValues() {
        if (getValue() == null) return new ArrayList<>();
        return getValue();
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        TextComponent component = new TextComponent(StringConverter.coloredString("&7&oClick here for the utility commands (&e&oDELAY, FOR, LOOP, RANDOM, etc.&7&o)"));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://docs.ssomar.com/tools-for-all-plugins/custom-commands/utility-commands"));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&7&oClick here for the utility commands (&e&oDELAY, FOR, LOOP, RANDOM, etc.&7&o)")).create()));
        return Collections.singletonList(component);
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (SCommand command :ConsoleCommandManager.getInstance().getCommands()) {
            Suggestion suggestion = new Suggestion("" + command.getTemplate(), command.getExtraColorNotNull() + "[" + command.getColorNotNull() + command.getNames().get(0) + command.getExtraColorNotNull() + "]", "&7ADD command: &e" + command.getNames().get(0));
            map.put(command.getNames().get(0), suggestion);
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public String getTips() {
        return "&c&oJust type your command if it's a console command";
    }

    @Override
    public void sendBeforeTextEditor(Player p, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your commands: (the '/' is useless)");

        /*TextComponent variables = new TextComponent(StringConverter.coloredString("&7➤ WIKI of Player commands: &8&l[CLICK HERE]"));
        variables.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://docs.ssomar.com/tools/custom-commands/player-and-target-commands"));
        variables.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&7&oOpen the wiki")).create()));
        p.spigot().sendMessage(variables); */

        Map<String, String> commands = new HashMap<>();
        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(p), "Console Commands", false, true, true, true, true, true, false, "", commands);
        editor.generateTheMenuAndSendIt(p);
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        value = (List<String>) manager.currentWriting.get(editor);
        if (value.isEmpty()) value = null;
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
