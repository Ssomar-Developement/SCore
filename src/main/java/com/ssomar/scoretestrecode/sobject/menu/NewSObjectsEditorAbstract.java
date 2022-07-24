package com.ssomar.scoretestrecode.sobject.menu;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.sobject.NewSObject;
import com.ssomar.scoretestrecode.sobject.NewSObjectLoader;
import com.ssomar.scoretestrecode.sobject.NewSObjectManager;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    public NewSObjectsEditorAbstract(SPlugin sPlugin, String title, String path, NewSObjectManager manager, NewSObjectLoader loader) {
        super(title + PAGE + "1", 5 * 9);
        this.sPlugin = sPlugin;
        this.title = title;
        this.index = 1;
        this.defaultPath = path;
        this.path = path;
        this.manager = manager;
        this.loader = loader;
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
                        String[] split = str.split("\\[icon\\-");
                        name = split[0];
                        try {
                            material = Material.valueOf(split[1].split("\\]")[0].toUpperCase());
                        } catch (Exception e) {
                            material = Material.CHEST;
                        }
                    }
                    ItemStack itemStack = new ItemStack(material);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setLocalizedName(str);
                    itemStack.setItemMeta(itemMeta);
                    createItem(itemStack, 1, i, "&2&l✦ FOLDER: &a" + name, false, false, "", "&7(click to open)");
                } else {
                    if (!fileName.contains(".yml"))
                        createItem(Material.BARRIER, 1, i, "&4&l✦ INVALID FILE: &c" + str, false, false, "", "&4(shift + left click to delete)");
                    String id = fileName.split(".yml")[0];


                    Optional<NewSObject> sObjectOpt = manager.getLoadedObjectWithID(id);
                    if (sObjectOpt.isPresent()) {
                        NewSObject sObject = sObjectOpt.get();
                        ItemStack itemS = sObject.buildItem(1, Optional.empty());

                        List<String> desc = new ArrayList<>();
                        desc.add("");
                        desc.add("&2(shift + right click to give to yourself)");
                        desc.add("&4(shift + left click to delete)");
                        desc.add("&7(click to edit)");
                        desc.add("&a&l➤ WORK FINE");
                        desc.addAll(sObject.getDescription());

                        String[] descArray = new String[desc.size()];
                        for (int j = 0; j < desc.size(); j++) {
                            if (desc.get(j).length() > 40) {
                                descArray[j] = desc.get(j).substring(0, 39) + "...";
                            } else {
                                descArray[j] = desc.get(j);
                            }
                        }
                        createItem(itemS, 1, i, COLOR_OBJECT_ID + " &e&o" + id, false, false, descArray);
                    } else {
                        if (sPlugin.isLotOfWork())
                            createItem(Material.BARRIER, 1, i, "&4&l✦ ERROR ID: &c&o" + id, false, false, "", "&7(You should edit the file directly)", "&4(shift + left click to delete)", "&c&l➤ ERROR WITH THIS " + sPlugin.getObjectName(), "&c&l➤ OR THE LIMIT OF " + sPlugin.getMaxSObjectsLimit() + " " + sPlugin.getObjectName() + " IS REACHED");
                        else
                            createItem(Material.BARRIER, 1, i, "&4&l✦ ERROR ID: &c&o" + id, false, false, "", "&7(You should edit the file directly)", "&4(shift + left click to delete)", "&c&l➤ ERROR WITH THIS " + sPlugin.getObjectName());
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

        createItem(Material.ANVIL, 1, 38, "&ePath", false, false, "", "&7actually: &a" + path, "&c&oClick here to come back", "&8&oin previous folder");

        createItem(GREEN, 1, 40, NEW + sPlugin.getObjectName(), false, false);

        if (sPlugin.isLotOfWork())
            createItem(PURPLE, 1, 43, "&5&l✚ &dDefault Premium " + sPlugin.getObjectName(), false, false);
        else createItem(PURPLE, 1, 43, "&5&l✚ &d" + sPlugin.getObjectName() + " from Custom packs", false, false);
    }

    public void goNextPage() {
        index++;
        load();
    }

    public void goPreviousPage() {
        index--;
        load();
    }

    public void goToFolder(String folder) {
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
            p.sendMessage(StringConverter.coloredString("&4&l" + sPlugin.getNameDesign() + " &cREQUIRE PREMIUM: to add more than " + sPlugin.getMaxSObjectsLimit() + " " + sPlugin.getObjectName() + " you need the premium version"));
        } else {
            p.closeInventory();
            p.chat("/" + sPlugin.getShortName().toLowerCase() + " create");
        }
    }

    public String getPath() {
        ItemStack item = this.getByName("Path");
        return this.getActually(item);
    }

    public void sendMessageDelete(String objectID, Player p) {
        p.sendMessage(StringConverter.coloredString("&4[" + sPlugin.getNameDesign() + "] &cHey you want delete the " + sPlugin.getObjectName() + ": &6" + objectID));
        TextComponent delete = new TextComponent(StringConverter.coloredString("&4&l[&c&lCLICK HERE TO DELETE&4&l]"));
        delete.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + sPlugin.getShortName().toLowerCase() + " delete " + objectID + " confirm"));
        delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&4Click here to delete this " + sPlugin.getObjectName())).create()));
        p.spigot().sendMessage(delete);
        p.updateInventory();
    }

    public void giveSObject(String objectID, Player p) {
        Optional<NewSObject> optional = manager.getLoadedObjectWithID(objectID);
        if (optional.isPresent()) {
            NewSObject sObject = optional.get();
            p.getInventory().addItem(sObject.buildItem(1, Optional.of(p)));
            p.sendMessage(StringConverter.coloredString("&2&l[" + sPlugin.getNameDesign() + "] &aYou received &e" + objectID));
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
