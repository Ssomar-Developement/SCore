package com.ssomar.score.menu.commands;

import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.util.UtilCommandsManager;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.messages.CenteredMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandsEditor {

    private Map<Player, List<String>> commands;
    private Map<Player, List<SCommand>> suggestions;
    private static CommandsEditor instance;
    private Map<Player, Boolean> isAsking;
    private int commandsPerColumn;
    private Map<Player, Integer> commandPage;

    public CommandsEditor(){
        commands = new HashMap<>();
        suggestions = new HashMap<>();
        isAsking = new HashMap<>();
        commandsPerColumn = 12;
        commandPage = new HashMap<>();
    }

    public void start(@NotNull Player p, List<String> commands, List<SCommand> suggestions){
        p.closeInventory();
        this.commandPage.put(p, 0);
        this.commands.put(p, commands);
        this.suggestions.put(p, this.sortSuggestions(suggestions));
        this.isAsking.put(p, true);
    }

    public List<String> finish(Player p){
        List<String> commandsFinal = new ArrayList<>(commands.get(p));
        commands.remove(p);
        suggestions.remove(p);
        commandPage.remove(p);
        isAsking.remove(p);
        return commandsFinal;
    }

    public void receiveMessage(Player p, String message){

        if(message.equals("COMMANDS PREVIOUS PAGE")) {
            this.previousPageSuggestion(p);
        }
        else if(message.equals("COMMANDS NEXT PAGE")) {
            this.nextPageSuggestion(p);
        }
        else if(message.contains("delete line <")) {
            this.deleteCommand(p, message);
        }
        else if(message.contains("up line <")) {
            this.upCommand(p, message);
        }
        else if(message.contains("down line <")) {
            this.downCommand(p, message);
        }
        else if(!StringConverter.decoloredString(message).equals("exit")) {
            commands.get(p).add(message);
        }

        this.sendEditor(p);

    }

    public void sendEditor(@NotNull Player p){
        this.showCommandsEditor(p);
        this.sendSuggestions(p);
    }

    public void sendSuggestions(Player p){
        space(p);
        p.sendMessage(StringConverter.coloredString("&5&oExecutableItems &dChoose a command below:"));
        space(p);

        List<TextComponent> listCommands = new ArrayList<>();

        int y = commandPage.get(p) * commandsPerColumn;
        while(y < (commandPage.get(p)+1) * commandsPerColumn && suggestions.get(p).size()-1 >= y ) {

            SCommand command = suggestions.get(p).get(y);
            TextComponent cmd1Cpnt = new TextComponent("");
            TextComponent cmd2Cpnt = new TextComponent("");

            ChatColor color = ChatColor.LIGHT_PURPLE;
            ChatColor extraColor = ChatColor.DARK_PURPLE;

            if(command.getColor() != null) color = command.getColor();
            if(command.getExtraColor() != null) extraColor = command.getExtraColor();

            String commandStr1 = StringConverter.coloredString(extraColor+"["+color+command.getNames().get(0)+extraColor+"]");
            String commandStr2 = " ";

            cmd1Cpnt.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, ""+command.getTemplate() ));
            cmd1Cpnt.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aCommand: &e"+command.getTemplate()) ).create() ) );


            if(y+1 != suggestions.get(p).size()) {

                SCommand command2 = suggestions.get(p).get(y+1);

                ChatColor color2 = ChatColor.LIGHT_PURPLE;
                ChatColor extraColor2 = ChatColor.DARK_PURPLE;

                if(command2.getColor() != null) color2 = command2.getColor();
                if(command2.getExtraColor() != null) extraColor2 = command2.getExtraColor();

                commandStr2 = StringConverter.coloredString(extraColor2+"["+color2+command2.getNames().get(0)+extraColor2+"]");

                /* Try to improve a bit the display in clean column */
                int charDiff = commandStr1.length()-commandStr2.length();
                StringBuilder commandStr1Builder = new StringBuilder(commandStr1);
                StringBuilder commandStr2Builder = new StringBuilder(commandStr2);
                while(charDiff != 0) {
                    if(charDiff > 0) {
                        commandStr2Builder.append(" ");
                        charDiff--;
                    }
                    else {
                        commandStr1Builder.insert(0, " ");
                        charDiff++;
                    }
                }
                commandStr2 = commandStr2Builder.toString();
                commandStr1 = commandStr1Builder.toString();

                cmd2Cpnt.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, ""+command2.getTemplate() ));
                cmd2Cpnt.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aCommand: &e"+command2.getTemplate()) ).create() ) );

                y = y+2;
            }
            else {
                y++;
            }
            String commandsStr = commandStr1 + "     |     "+commandStr2;
            commandsStr = CenteredMessage.convertIntoCenteredMessage(commandsStr);

            cmd1Cpnt.setText(commandsStr.split("\\|")[0]);
            cmd2Cpnt.setText(commandsStr.split("\\|")[1]);
            cmd1Cpnt.addExtra(cmd2Cpnt);
            listCommands.add(cmd1Cpnt);
        }



        for(int i = 0 ; i < listCommands.size(); i++) {
            p.spigot().sendMessage(listCommands.get(i));
        }

        CenteredMessage.sendCenteredMessage(p, "&c&oJust type your command if it's console command");

        space(p);
        String changementPage;
        if(commandPage.get(p) == 0) {
            changementPage = " | &d&lNext page &5&l>>>>>";
        }
        else if (((commandPage.get(p)+1) * commandsPerColumn) > suggestions.get(p).size()) {
            changementPage = "&5&l<<<<< &d&lPrevious page | ";
        }
        else  changementPage = "&5&l<<<<< &d&lPrevious page | &d&lNext page &5&l>>>>>";

        changementPage = CenteredMessage.convertIntoCenteredMessage(changementPage);

        String previousStr = changementPage.split("\\|")[0];
        String nextStr = changementPage.split("\\|")[1];

        TextComponent previousText = new TextComponent( previousStr );
        previousText.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "COMMANDS PREVIOUS PAGE" ));
        previousText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&5&l<<<<< &d&lPrevious page") ).create() ) );

        TextComponent nextText = new TextComponent( nextStr );
        nextText.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "COMMANDS NEXT PAGE" ));
        nextText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&d&lNext page &5&l>>>>>") ).create() ) );

        previousText.addExtra(nextText);

        p.spigot().sendMessage(previousText);

        space(p);
    }

    public void nextPageSuggestion(Player p){
        commandPage.put(p, commandPage.get(p)+1);
    }

    public void previousPageSuggestion(Player p){
        commandPage.put(p, commandPage.get(p)-1);
    }

    public void upCommand(Player p, String message){
        space(p);
        space(p);
        int line = Integer.valueOf(message.split("up line <")[1].split(">")[0]);
        if (line != 0) {
            String current = commands.get(p).get(line);
            commands.get(p).set(line, commands.get(p).get(line-1));
            commands.get(p).set(line-1, current);
        }
        p.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION &aYou have up the line: "+line+" to "+(line-1)+" !"));
        space(p);
        space(p);
    }

    public void downCommand(Player p, String message){
        space(p);
        space(p);
        int line = Integer.valueOf(message.split("down line <")[1].split(">")[0]);
        if (line != 0) {
            String current = commands.get(p).get(line);
            commands.get(p).set(line, commands.get(p).get(line+1));
            commands.get(p).set(line+1, current);
        }
        p.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION &aYou have down the line: "+line+" to "+(line+1)+" !"));
        space(p);
        space(p);
    }

    public void deleteCommand(Player p, String message){
        space(p);
        space(p);
        int line = Integer.parseInt(message.split("delete line <")[1].split(">")[0]);
        commands.get(p).remove(line);
        p.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION &aYou have delete the line: "+line+" !"));
        space(p);
        space(p);
    }

    public boolean isAsking(Player player){
        for(Player p : isAsking.keySet()){
            if(p.getUniqueId().equals(player.getUniqueId()) && isAsking.get(p)) return true;
        }
        return false;
    }

    public List<SCommand> sortSuggestions(List<SCommand> suggestions){
        /* Create the sorted list */
        List<SCommand> commandsSorted = new ArrayList<>();

        /* Create new list with suggestions + util commands */
        List<SCommand> commands = new ArrayList<>(suggestions);
        commands.addAll(UtilCommandsManager.getInstance().getCommands());

        for(SCommand cmd : commands) {
            String key = cmd.getNames().get(0);

            int k = 0;
            boolean insert = false;
            for(int g = 0; g < commandsSorted.size(); g++) {
                String key2;
                SCommand cmd2 = commandsSorted.get(g);
                key2 = cmd2.getNames().get(0);

                if(key.compareToIgnoreCase(key2) <= 0) {
                    commandsSorted.add(k, cmd);
                    insert = true;
                    break;
                }
                k++;
            }
            if(!insert) {
                commandsSorted.add(cmd);
            }
        }

        return commandsSorted;
    }

    public void showCommandsEditor(Player p) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your commands: (the '/' is useless)");

        TextComponent variables = new TextComponent( StringConverter.coloredString("&7➤ Variables / placeholders: &8&l[CLICK HERE]"));
        variables.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://github.com/ssomar1607/ExecutableItems/wiki/%E2%9E%A4-Commands" ));
        variables.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&7&oOpen the wiki") ).create() ) );
        p.spigot().sendMessage(variables);

        Map<String, String> commands = new HashMap<>();
        EditorCreator editor = new EditorCreator(beforeMenu, this.commands.get(p), "Commands", false, true, true, true, true, true, false, "", commands);
        editor.generateTheMenuAndSendIt(p);
    }

    public static CommandsEditor getInstance(){
        if(instance == null) instance = new CommandsEditor();
        return instance;
    }

    public void space(Player p) {
        p.sendMessage("");
    }
}
