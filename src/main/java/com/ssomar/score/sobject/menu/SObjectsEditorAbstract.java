package com.ssomar.score.sobject.menu;

import com.ssomar.score.api.executableitems.events.AddItemInPlayerInventoryEvent;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.*;
import com.ssomar.score.sobject.SObjectWithFileLoader;
import com.ssomar.score.sobject.SObjectManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@Setter
public abstract class SObjectsEditorAbstract<T extends SObject & SObjectEditable> extends GUI{

    public static final int SOBJECT_PER_PAGE = 27;
    public static int index;
    private SPlugin sPlugin;
    private String title;
    private String deleteArg;
    private String createArg;

    private boolean giveButton = true;
    private boolean newButton = true;
    private boolean defaultObjectsButton = true;
    private boolean deleteButton = true;
    private boolean pathButton = true;

    private List<Predicate<T>> predicates = new ArrayList<>();

    private Comparator<T> comparator = new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };

    private SObjectManager<T> manager;

    public SObjectsEditorAbstract(SPlugin sPlugin, String title, SObjectManager<T> manager) {
        super(title, 5 * 9);
        this.sPlugin = sPlugin;
        this.title = title;
        index = 1;
        this.deleteArg = "delete";
        this.createArg = "create";
        this.manager = manager;

        this.initSettings();
    }

    public abstract void initSettings();

    public abstract void load();

    public abstract SObjectWithFileLoader getLoader();

    public void goNextPage() {
        index++;
        load();
    }

    public void goPreviousPage() {
        index--;
        setTitle(title + PAGE + index);
        load();
    }

    abstract void goToFolder(String folder);

    abstract void goBack();

    public void sendMessageCreate(Player p) {
        if (sPlugin.isLotOfWork() && manager.getLoadedObjects().size() >= sPlugin.getMaxSObjectsLimit()) {
            p.sendMessage(StringConverter.coloredString("&4&l" + sPlugin.getNameDesign() + " "+ TM.g(Text.EDITOR_PREMIUM_REQUIREPREMIUMTOADDMORE)));
        } else {
            p.closeInventory();
            p.chat("/" + sPlugin.getShortName().toLowerCase() + " "+createArg);
        }
    }

    public String getCurrentPath() {
        ItemStack item = this.getByName(StringConverter.decoloredString(TM.g(Text.EDITOR_PATH_NAME)));
        return this.getCurrently(item);
    }

    public void sendMessageDelete(String objectID, Player p) {
        p.sendMessage(StringConverter.coloredString(sPlugin.getNameDesign() + " " + TM.g(Text.EDITOR_DELETE_MESSAGE).replace("%object%", manager.getObjectName()) + ": &6" + objectID));
        TextComponent delete = new TextComponent(StringConverter.coloredString("&4&l[&c&l" + GUI.CLICK_TO_REMOVE + "&4&l]"));
        delete.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + sPlugin.getShortName().toLowerCase() + " " + deleteArg + " " + objectID + " confirm"));
        delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString(GUI.CLICK_TO_REMOVE + manager.getObjectName())).create()));
        p.spigot().sendMessage(delete);
        p.updateInventory();
    }


    public void openEditorSObject(String objectID, Player p) {
        Optional<T> optional = manager.getObject(objectID);
        if (optional.isPresent()) {
            SObject sObject = optional.get();
            sObject.openEditor(p);
        }
    }

    public void giveSObject(String objectID, Player p) {
        if(!giveButton) return;
        Optional<T> optional = manager.getLoadedObjectWithID(objectID);
        if (optional.isPresent()) {
            SObject sObject = optional.get();
            if(sObject instanceof SObjectBuildable) {
                SObjectBuildable buildable = (SObjectBuildable) sObject;
                int firstEmptySlot = p.getInventory().firstEmpty();
                ItemStack itemStack = buildable.buildItem(1, Optional.of(p));
                p.getInventory().addItem(itemStack);
                p.sendMessage(StringConverter.coloredString("&2&l" + sPlugin.getNameDesign() + " " + TM.g(Text.EDITOR_GIVE_RECEIVEDMESSAGE) + objectID));

                AddItemInPlayerInventoryEvent eventToCall = new AddItemInPlayerInventoryEvent(p, itemStack, firstEmptySlot);
                Bukkit.getPluginManager().callEvent(eventToCall);
            }
        }
    }
}
