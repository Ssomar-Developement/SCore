package com.ssomar.score.sobject.menu;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableitems.events.AddItemInPlayerInventoryEvent;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.NewSObject;
import com.ssomar.score.sobject.NewSObjectLoader;
import com.ssomar.score.sobject.NewSObjectManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.DynamicMeta;
import com.ssomar.score.utils.strings.StringConverter;
import com.ssomar.score.utils.itemwriter.ItemKeyWriterReader;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

@Getter
@Setter
public abstract class NewSObjectsEditorAbstract extends GUI {

    public static final String NEW = "&2&l✚ &aNew ";
    private static final int SOBJECT_PER_PAGE = 27;
    private static int index;
    private SPlugin sPlugin;
    private String title;
    private String defaultPath;
    private String path;
    private NewSObjectManager manager;
    private NewSObjectLoader loader;
    private String deleteArg;
    private String objectName;
    private boolean noObject;

    public NewSObjectsEditorAbstract(SPlugin sPlugin, String title, String path, NewSObjectManager manager, NewSObjectLoader loader) {
        super(title, 5 * 9);
        this.sPlugin = sPlugin;
        this.title = title;
        index = 1;
        this.defaultPath = path;
        this.path = path;
        this.manager = manager;
        this.loader = loader;
        this.deleteArg = "delete";

        objectName = sPlugin.getObjectName();
        /* For plugins that have multiple object splugin.getOjectName can't work */
        if(objectName == null) objectName = manager.getObjectName();
        objectName = objectName.toLowerCase();

        this.noObject = false;

        this.load();
    }

    public NewSObjectsEditorAbstract(SPlugin sPlugin, String title, String path, NewSObjectManager manager, NewSObjectLoader loader, boolean noObject) {
        super(title, 5 * 9);
        this.sPlugin = sPlugin;
        this.title = title;
        index = 1;
        this.defaultPath = path;
        this.path = path;
        this.manager = manager;
        this.loader = loader;
        this.deleteArg = "delete";

        objectName = sPlugin.getObjectName();
        /* For plugins that have multiple object splugin.getOjectName can't work */
        if(objectName == null) objectName = manager.getObjectName();
        objectName = objectName.toLowerCase();

        this.noObject = noObject;

        this.load();
    }


    public void load() {
        clearAndSetBackground();
        int i = 0;
        int total = 0;
        Plugin plugin = sPlugin.getPlugin();
        List<String> listFiles = Arrays.asList(new File(plugin.getDataFolder() + path).list());
        Collections.sort(listFiles);

        for (String str : listFiles) {
            if ((index - 1) * SOBJECT_PER_PAGE <= total && total < index * SOBJECT_PER_PAGE) {
                File fileEntry = new File(plugin.getDataFolder() + path + "/" + str);
                String fileName = fileEntry.getName();
                if (fileName.contains(".txt")) continue;
                String name = str;
                if (fileEntry.isDirectory()) {
                    Material material = Material.CHEST;
                    if (str.contains("[icon-")) {
                        String[] split = str.split("\\[icon-");
                        name = split[0];
                        try {
                            material = Material.valueOf(split[1].split("]")[0].toUpperCase());
                        } catch (Exception e) {
                            material = Material.CHEST;
                        }
                    }
                    ItemStack itemStack = new ItemStack(material);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    DynamicMeta dynamicMeta = new DynamicMeta(itemMeta);
                    ItemKeyWriterReader.init().writeString(SCore.plugin, itemStack, dynamicMeta, "folderInfo", str);
                    itemStack.setItemMeta(dynamicMeta.getMeta());
                    createItem(itemStack, 1, i, "&2&l✦ FOLDER: &a" + name, false, false, "", "&7(click to open)");
                } else {
                    if (!fileName.contains(".yml"))
                        createItem(Material.BARRIER, 1, i, "&4&l✦ INVALID FILE: &c" + str, false, false, "", GUI.SHIFT_LEFT_CLICK_TO_REMOVE);
                    String id = fileName.split(".yml")[0];


                    Optional<NewSObject> sObjectOpt = manager.getLoadedObjectWithID(id);
                    if (sObjectOpt.isPresent()) {
                        NewSObject sObject = sObjectOpt.get();
                        ItemStack itemS = sObject.buildItem(1, Optional.empty());

                        /* Remove useless tags */
                        ItemMeta meta = itemS.getItemMeta();
                        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
                        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
                        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
                        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_UNBREAKABLE});
                        if (SCore.is1v17Plus())
                            meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_DYE});
                        if(SCore.is1v20Plus())
                            meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ARMOR_TRIM});
                        itemS.setItemMeta(meta);

                        List<String> desc = new ArrayList<>();
                        desc.add("");
                        if(!noObject) desc.add("&2(shift + right click to give to yourself)");
                        desc.add(GUI.SHIFT_LEFT_CLICK_TO_REMOVE);
                        desc.add(GUI.CLICK_HERE_TO_CHANGE);
                        desc.addAll(sObject.getDescription());

                        String[] descArray = new String[desc.size()];
                        for (int j = 0; j < desc.size(); j++) {
                            if (desc.get(j).length() > 40) {
                                descArray[j] = desc.get(j).substring(0, 39) + "...";
                            } else {
                                descArray[j] = desc.get(j);
                            }
                        }
                        createItem(itemS, 1, i, CREATION_ID + " &e&o" + id, false, false, descArray);
                    } else {
                        if (sPlugin.isLotOfWork())
                            createItem(Material.BARRIER, 1, i, "&4&l✦ ERROR ID: &c&o" + id, false, false, "", "&7(You should edit the file directly)", GUI.SHIFT_LEFT_CLICK_TO_REMOVE, "&c&l➤ ERROR WITH THIS " + objectName, "&c&l➤ OR THE LIMIT OF " + sPlugin.getMaxSObjectsLimit() + " " + objectName + " IS REACHED");
                        else
                            createItem(Material.BARRIER, 1, i, "&4&l✦ ERROR ID: &c&o" + id, false, false, "", "&7(You should edit the file directly)", GUI.SHIFT_LEFT_CLICK_TO_REMOVE, "&c&l➤ ERROR WITH THIS " + objectName);
                    }
                }
                i++;

            }
            total++;
        }
        //other button
        if (listFiles.size() > SOBJECT_PER_PAGE && index * SOBJECT_PER_PAGE < listFiles.size()) {
            createItem(NEXT_PAGE_MAT, 1, 44, NEXT_PAGE, false, false);
        }
        if (index > 1) {
            createItem(PREVIOUS_PAGE_MAT, 1, 37, PREVIOUS_PAGE, false, false);
        }
        createItem(RED, 1, 36, EXIT, false, false);

        createItem(Material.ANVIL, 1, 38, "&ePath", false, false, "", "&7Currently: &a" + path, "&c&oClick here to come back", "&8&oin previous folder");

        createItem(GREEN, 1, 40, NEW + objectName, false, false);

        if (sPlugin.isLotOfWork())
            createItem(PURPLE, 1, 43, "&5&l✚ &dDefault Premium " + objectName, false, false);
        else createItem(PURPLE, 1, 43, "&5&l✚ &d" + objectName + " from Custom packs", false, false);
    }

    public void goNextPage() {
        index++;

        load();
    }

    public void goPreviousPage() {
        index--;
        setTitle(title + PAGE + index);
        load();
    }

    public void goToFolder(String folder) {
        index = 1;
        path = this.getPath() + "/" + StringConverter.decoloredString(folder).trim();
        load();
    }

    public void goBack() {
        if (!this.path.equals(defaultPath)) {
            String[] split = this.getPath().split("/");
            StringBuilder newPathBuilder = new StringBuilder("/");
            for (int i = 0; i < split.length - 1; i++) {
                if (i == 0) continue;
                newPathBuilder.append(split[i]).append("/");
            }
            String newPath = newPathBuilder.toString();
            newPath = newPath.substring(0, newPath.length() - 1);
            this.path = newPath;
        }
        load();
    }

    public void sendMessageCreate(Player p) {
        if (sPlugin.isLotOfWork() && manager.getLoadedObjects().size() >= sPlugin.getMaxSObjectsLimit()) {
            p.sendMessage(StringConverter.coloredString("&4&l" + sPlugin.getNameDesign() + " &cREQUIRE PREMIUM: to add more than " + sPlugin.getMaxSObjectsLimit() + " " + objectName + " you need the premium version"));
        } else {
            p.closeInventory();
            p.chat("/" + sPlugin.getShortName().toLowerCase() + " create");
        }
    }

    public String getPath() {
        ItemStack item = this.getByName("Path");
        return this.getCurrently(item);
    }

    public void sendMessageDelete(String objectID, Player p) {
        p.sendMessage(StringConverter.coloredString("&4[" + sPlugin.getNameDesign() + "] &cHey you want delete the " + objectName + ": &6" + objectID));
        TextComponent delete = new TextComponent(StringConverter.coloredString("&4&l[&c&l"+GUI.CLICK_TO_REMOVE+"&4&l]"));
        delete.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + sPlugin.getShortName().toLowerCase() + " "+deleteArg+" " + objectID + " confirm"));
        delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString(GUI.CLICK_TO_REMOVE + objectName)).create()));
        p.spigot().sendMessage(delete);
        p.updateInventory();
    }

    public void giveSObject(String objectID, Player p) {
        if(noObject) return;
        Optional<NewSObject> optional = manager.getLoadedObjectWithID(objectID);
        if (optional.isPresent()) {
            NewSObject sObject = optional.get();
            int firstEmptySlot = p.getInventory().firstEmpty();
            ItemStack itemStack = sObject.buildItem(1, Optional.of(p));
            p.getInventory().addItem(itemStack);
            p.sendMessage(StringConverter.coloredString("&2&l" + sPlugin.getNameDesign() + " &aYou received &e" + objectID));

            AddItemInPlayerInventoryEvent eventToCall = new AddItemInPlayerInventoryEvent(p, itemStack, firstEmptySlot);
            Bukkit.getPluginManager().callEvent(eventToCall);
        }
    }

    public void openEditorSObject(String objectID, Player p) {
        Optional<NewSObject> optional = manager.getLoadedObjectWithID(objectID);
        if (optional.isPresent()) {
            NewSObject sObject = optional.get();
            sObject.openEditor(p);
        }
    }
}
