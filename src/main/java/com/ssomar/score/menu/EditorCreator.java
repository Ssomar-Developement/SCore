package com.ssomar.score.menu;

import com.ssomar.score.utils.strings.StringConverter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class EditorCreator {

    private static StringConverter sc = new StringConverter();
    private List<String> beforeMenu = new ArrayList<>();
    private List<String> objects = new ArrayList<>();
    private String objectTitle = "";
    private boolean isLoreColor = false;
    private boolean displayEdit = false;
    private boolean displayUpDown = false;
    private boolean displayDelete = false;
    private boolean displayFinish = false;
    private boolean displayAddLine = false;
    private boolean haveSuggestions = false;
    private String suggestionTitle = "";
    /**
     * Key is the command, value is the visual command so you can add extra things like colors
     **/
    private Map<String, String> suggestions = new HashMap<>();
    private ClickEvent.Action clickAction;


    public EditorCreator(List<String> beforeMenu, List<String> objects, String objectTitle, boolean isLoreColor,
                         boolean displayEdit, boolean displayUpDown, boolean displayDelete, boolean displayFinish,
                         boolean displayAddLine, boolean haveSuggestions, String suggestionTitle, Map<String, String> suggestions) {
        super();
        this.beforeMenu = beforeMenu;
        this.objects = objects;
        this.objectTitle = objectTitle;
        this.isLoreColor = isLoreColor;
        this.displayEdit = displayEdit;
        this.displayUpDown = displayUpDown;
        this.displayDelete = displayDelete;
        this.displayFinish = displayFinish;
        this.displayAddLine = displayAddLine;
        this.haveSuggestions = haveSuggestions;
        this.suggestionTitle = suggestionTitle;
        this.suggestions = suggestions;
        this.clickAction = ClickEvent.Action.RUN_COMMAND;
    }

    public static StringConverter getSc() {
        return sc;
    }

    public static void setSc(StringConverter sc) {
        EditorCreator.sc = sc;
    }

    public void generateTheMenuAndSendIt(Player target) {

        this.sendFirstPart(target);

        this.sendObjects(target);

        this.sendOptions(target);

        this.sendSuggestions(target);

    }

    public void sendFirstPart(Player target) {
        for (String s : beforeMenu) {
            target.sendMessage(StringConverter.coloredString(s));
        }
        if (beforeMenu.size() != 0) space(target);
    }

    public void sendObjects(Player target) {
        int cpt = 0;
        TextComponent toSend = null;
        if (objects.size() == 0) {
            toSend = new TextComponent(StringConverter.coloredString("&7EMPTY"));
        } else {
            for (String s : objects) {
                if (isLoreColor)
                    toSend = new TextComponent(TextComponent.fromLegacyText(StringConverter.coloredString("&7" + cpt + ".&5" + s)));
                else
                    toSend = new TextComponent(TextComponent.fromLegacyText(StringConverter.coloredString("&7" + cpt + "." + s)));

                if (displayEdit) {
                    TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
                    edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "edit line <" + cpt + "> ->" + StringConverter.deconvertColor(s)));
                    edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick Here to edit line: " + cpt)).create()));

                    toSend.addExtra(new TextComponent(" "));
                    toSend.addExtra(edit);
                }

                if (displayDelete) {
                    TextComponent delete = new TextComponent(StringConverter.coloredString("&c&l[X]"));
                    delete.setClickEvent(new ClickEvent(clickAction, "/score interact delete line <" + cpt + ">"));
                    delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&cClick here to delete the line: " + cpt)).create()));

                    toSend.addExtra(new TextComponent(" "));
                    toSend.addExtra(delete);
                }

                if (displayUpDown) {
                    TextComponent downLine = new TextComponent(StringConverter.coloredString("&a&l[v]"));
                    downLine.setClickEvent(new ClickEvent(clickAction, "/score interact down line <" + cpt + ">"));
                    downLine.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&cClick here to go down the line: " + cpt)).create()));

                    TextComponent upLine = new TextComponent(StringConverter.coloredString("&a&l[^]"));
                    upLine.setClickEvent(new ClickEvent(clickAction, "/score interact up line <" + cpt + ">"));
                    upLine.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&cClick here to go up the line: " + cpt)).create()));

                    toSend.addExtra(new TextComponent(" "));
                    toSend.addExtra(downLine);

                    toSend.addExtra(new TextComponent(" "));
                    toSend.addExtra(upLine);
                }
                target.spigot().sendMessage(toSend);
                cpt++;
            }
            space(target);
        }
    }

    public void sendOptions(Player target) {
        if (displayAddLine || displayFinish) {
            TextComponent toSend = new TextComponent(StringConverter.coloredString("&7➤Options: "));
            if (displayFinish) {
                TextComponent finish = new TextComponent(StringConverter.coloredString("&4&l[FINISH]"));
                finish.setClickEvent(new ClickEvent(clickAction, "/score interact exit"));
                finish.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&4Click Here when you have finish to edit the " + objectTitle)).create()));

                toSend.addExtra(new TextComponent(" "));
                toSend.addExtra(finish);
            }

            if (displayAddLine) {
                TextComponent addLine = new TextComponent(StringConverter.coloredString("&2&l[ADD LINE]"));
                addLine.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type new line here.."));
                addLine.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&2Click Here if you want add new line " + objectTitle)).create()));

                toSend.addExtra(new TextComponent(" "));
                toSend.addExtra(addLine);
            }

            target.spigot().sendMessage(toSend);
        }
    }

    public void sendSuggestions(Player target) {
        if (haveSuggestions) {
            target.sendMessage(StringConverter.coloredString(suggestionTitle));

            space(target);

            List<TextComponent> listItems = new ArrayList<>();
            for (String s : suggestions.keySet()) {
                TextComponent newText;

                newText = new TextComponent(StringConverter.coloredString(s));
                newText.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestions.get(s)));
                newText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aAdd the command: &e" + s)).create()));

                listItems.add(newText);
            }

            for (int i = 0; i < listItems.size(); i++) {
                TextComponent result;
                if (i + 1 != listItems.size()) {
                    (result = listItems.get(i)).addExtra("  ");
                    result.addExtra(listItems.get(i + 1));
                    i++;
                } else {
                    result = listItems.get(i);
                }
                target.spigot().sendMessage(result);
            }
        }
    }

    public void space(Player p) {
        p.sendMessage("");
    }

    public List<String> getBeforeMenu() {
        return beforeMenu;
    }

    public void setBeforeMenu(List<String> beforeMenu) {
        this.beforeMenu = beforeMenu;
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    public boolean isLoreColor() {
        return isLoreColor;
    }

    public void setLoreColor(boolean isLoreColor) {
        this.isLoreColor = isLoreColor;
    }

    public boolean isDisplayEdit() {
        return displayEdit;
    }

    public void setDisplayEdit(boolean displayEdit) {
        this.displayEdit = displayEdit;
    }

    public boolean isDisplayUpDown() {
        return displayUpDown;
    }

    public void setDisplayUpDown(boolean displayUpDown) {
        this.displayUpDown = displayUpDown;
    }

    public boolean isDisplayDelete() {
        return displayDelete;
    }

    public void setDisplayDelete(boolean displayDelete) {
        this.displayDelete = displayDelete;
    }

    public boolean isDisplayFinish() {
        return displayFinish;
    }

    public void setDisplayFinish(boolean displayFinish) {
        this.displayFinish = displayFinish;
    }

    public boolean isDisplayAddLine() {
        return displayAddLine;
    }

    public void setDisplayAddLine(boolean displayAddLine) {
        this.displayAddLine = displayAddLine;
    }

    public boolean isHaveSuggestions() {
        return haveSuggestions;
    }

    public void setHaveSuggestions(boolean haveSuggestions) {
        this.haveSuggestions = haveSuggestions;
    }

    public String getSuggestionTitle() {
        return suggestionTitle;
    }

    public void setSuggestionTitle(String suggestionTitle) {
        this.suggestionTitle = suggestionTitle;
    }

    public Map<String, String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(Map<String, String> suggestions) {
        this.suggestions = suggestions;
    }

}
