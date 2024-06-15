package com.ssomar.score.features.custom.commands.player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommandManager;
import com.ssomar.score.commands.runnable.player.PlayerRunCommandsBuilder;
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
public class PlayerCommandsFeature extends CommandsAbstractFeature<List<String>, PlayerCommandsFeature> implements FeatureRequireSubTextEditorInEditor {

    private List<String> value;

    public PlayerCommandsFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = PlayerCommandManager.getInstance().getCommandsVerified(plugin, config.getStringList(getName()), errors, getParent().getParentInfo());
        return errors;
    }

    public void runCommands(ActionInfo actionInfo, String objectName) {
        List<String> commands = new ArrayList<>(getValue());
        commands = prepareActionbarArgs(commands, objectName);
        for (int i = 0; i < commands.size(); i++) {
            String s1 = commands.get(i);
            /* add blockface for the command setblock (in player cmd) */
            if (s1.startsWith("SETBLOCK") && !s1.startsWith("SETBLOCKPOS")) {
                s1 = s1.replace("SETBLOCK", "SETBLOCK %blockface%");
                commands.set(i, s1);
            }
        }
        PlayerRunCommandsBuilder builder = new PlayerRunCommandsBuilder(commands, actionInfo);
        CommandsExecutor.runCommands(builder);
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), value);
    }

    @Override
    public List<String> getValue() {
        return value;
    }

    @Override
    public PlayerCommandsFeature initItemParentEditor(GUI gui, int slot) {
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
        if (value.size() > 10) {
            for (int i = 0; i < 10; i++) update.add(value.get(i));
            update.add("&6... &e" + value.size() + " &6commands");
        } else update.addAll(value);
        for (int i = 0; i < update.size(); i++) {
            String command = update.get(i);
            if (command.length() > 40) command = command.substring(0, 39) + "...";
            update.set(i, command);
        }
        gui.updateConditionList(getEditorName(), update, "&cEMPTY");
    }

    @Override
    public PlayerCommandsFeature clone(FeatureParentInterface newParent) {
        PlayerCommandsFeature clone = new PlayerCommandsFeature(newParent, getFeatureSettings());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = new ArrayList<>();
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        return PlayerCommandManager.getInstance().verifCommand(message);
    }

    @Override
    public List<String> getCurrentValues() {
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
        for (SCommand command : PlayerCommandManager.getInstance().getCommands()) {
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

        TextComponent variables = new TextComponent(StringConverter.coloredString("&7➤ WIKI of Player commands: &8&l[CLICK HERE]"));
        variables.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://docs.ssomar.com/tools/custom-commands/player-and-target-commands"));
        variables.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&7&oOpen the wiki")).create()));
        p.spigot().sendMessage(variables);

        Map<String, String> commands = new HashMap<>();
        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(p), "Player Commands", false, true, true, true, true, true, false, "", commands);
        editor.generateTheMenuAndSendIt(p);
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        value = (List<String>) manager.currentWriting.get(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
