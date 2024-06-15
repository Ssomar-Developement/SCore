package com.ssomar.score.features.types.list;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListExecutableItemsFeature extends ListFeatureAbstract<String, ListExecutableItemsFeature> {

    public ListExecutableItemsFeature(FeatureParentInterface parent, List<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, "List of ExecutableItems", defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<String> loadValues(List<String> entries, List<String> errors) {
        List<String> result = new ArrayList<>();
        result = entries;
        if (!SCore.hasExecutableItems && entries.size() > 0) {
            errors.add("&cERROR, Couldn't load the List of ExecutableItems of " + this.getName() + " from config &7&o" + getParent().getParentInfo() + " &6>> You haven't ExecutableItems !");
            return errors;
        }
        for (int i = 0; i < result.size(); i++) {
            result.set(i, StringConverter.decoloredString(result.get(i)));
        }
        return result;
    }

    @Override
    public String transfromToString(String value) {
        return value;
    }

    @Override
    public ListExecutableItemsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!SCore.hasExecutableItems)
            finalDescription[finalDescription.length - 2] = "&4&l● &c&lRequire ExecutableItems";
        else finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }


    @Override
    public ListExecutableItemsFeature clone(FeatureParentInterface newParent) {
        ListExecutableItemsFeature clone = new ListExecutableItemsFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    public boolean isValid(ItemStack itemStack) {
        if (getValues().size() == 0) return true;
        if (SCore.hasExecutableItems) {
            Optional<ExecutableItemInterface> executableItem = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(itemStack);
            return executableItem.filter(executableItemInterface -> getValues().contains(executableItemInterface.getId())).isPresent();
        } else return true;
    }

    // hey :D

    @Override
    public Optional<String> verifyMessage(String message) {
        if (SCore.hasExecutableItems) {
            message = StringConverter.decoloredString(message.trim());
            if (ExecutableItemsAPI.getExecutableItemsManager().isValidID(message)) {
                return Optional.empty();
            } else
                return Optional.of("&4&l[ERROR] &cThe message you entered is not a valid ExecutableItems ID ! (The id is word before the .yml) ");
        }
        return Optional.empty();
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        List<Suggestion> suggestions = new ArrayList<>();
        if (SCore.hasExecutableItems) {
            for (String s : ExecutableItemsAPI.getExecutableItemsManager().getExecutableItemIdsList()) {
                suggestions.add(new Suggestion(s, "&6[&e" + s + "&6]", "&7Add ExecutableItem : &e" + s));
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String getTips() {
        return "";
    }


    @Override
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
