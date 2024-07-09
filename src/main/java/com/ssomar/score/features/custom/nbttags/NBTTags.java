package com.ssomar.score.features.custom.nbttags;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireSubTextEditorInEditor;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class NBTTags extends FeatureAbstract<Optional<List<String>>, NBTTags> implements FeatureRequireSubTextEditorInEditor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<NBTTag> tags = new ArrayList<>();

    public NBTTags(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.nbt);
    }

    public static List<String> blackListedTags() {
        List<String> blackListedTags = new ArrayList<>();
        blackListedTags.add("Enchantments");
        blackListedTags.add("Unbreakable");
        blackListedTags.add("Damage");
        blackListedTags.add("display");
        blackListedTags.add("Lore");
        blackListedTags.add("HideFlags");
        blackListedTags.add("score:usage");
        blackListedTags.add("executableitems:ei-id");
        if (!SCore.is1v12Less()) {
            blackListedTags.add("AttributeModifiers");
            blackListedTags.add("CustomModelData");
            // Name is needed in 1.12 for attributeName
            blackListedTags.add("Name");
        }
        return blackListedTags;
    }

    public void load(ItemStack item) {
        if (SCore.hasNBTAPI) {
            NBTItem nbti = new NBTItem(item);
            for (String s : nbti.getKeys()) {
                if (blackListedTags().contains(s)) continue;
                NBTType type = nbti.getType(s);
                switch (type) {
                    case NBTTagInt:
                        tags.add(new IntNBTTag(s, nbti.getInteger(s).intValue()));
                        break;
                    case NBTTagByte:
                        tags.add(new ByteNBTTag(s, nbti.getByte(s).byteValue()));
                        break;
                    case NBTTagCompound:
                        tags.add(new CompoundNBTTag(s, nbti.getCompound(s)));
                        break;
                    case NBTTagDouble:
                        tags.add(new DoubleNBTTag(s, nbti.getDouble(s).doubleValue()));
                        break;
                    case NBTTagList:
                        /* Important for attributes in 1.12 */
                        //SsomarDev.testMsg(" >>>>>>> tag list: "+ nbti.getListType(s), false);
                        switch (nbti.getListType(s)) {
                            case NBTTagString:
                                tags.add(new ListStringNBTTag(s, (List<String>) nbti.getStringList(s)));
                                break;
                            default:
                                tags.add(new ListCompoundNBTTag(s, nbti.getCompoundList(s)));
                                break;
                        }
                        break;
                    case NBTTagString:
                        tags.add(new StringNBTTag(s, nbti.getString(s)));
                        break;
                }
            }
        }
    }

    @Override
    public List<String> load(SPlugin sPlugin, ConfigurationSection configurationSection, boolean isPremiumLoading) {
        tags.clear();
        ArrayList<String> error = new ArrayList<>();
        if (configurationSection.contains("nbt"))
            if (!isPremiumLoading) {
                error.add(StringConverter.coloredString("&cREQUIRE PREMIUM: to edit NBT you need the premium version"));
            } else {
                ConfigurationSection nbtSection = configurationSection.getConfigurationSection("nbt");
                //SsomarDev.testMsg(" >>>>>>> nbtSection: " + nbtSection, true);
                for (String nbtId : nbtSection.getKeys(false)) {
                    ConfigurationSection tagSection = nbtSection.getConfigurationSection(nbtId);
                    String type = tagSection.getString("type").toUpperCase();
                    String key = tagSection.getString("key");
                    //SsomarDev.testMsg(" >>>>>>> tag type: " + type+" key: "+ key, true);
                    NBTTag tag = null;
                    switch (type) {
                        case "BOOLEAN":
                        case "BOOL":
                            tag = new BooleanNBTTag(tagSection);
                            break;
                        case "STRING":
                        case "STR":
                            tag = new StringNBTTag(tagSection);
                            break;
                        case "DOUBLE":
                            tag = new DoubleNBTTag(tagSection);
                            break;
                        case "INTEGER":
                        case "INT":
                            tag = new IntNBTTag(tagSection);
                            break;
                        case "BYTE":
                            tag = new ByteNBTTag(tagSection);
                            break;
                        case "COMPOUND":
                            tag = new CompoundNBTTag(tagSection);
                            break;
                        case "STRING_LIST":
                            tag = new ListStringNBTTag(tagSection);
                            break;
                        case "COMPOUND_LIST":
                            tag = new ListCompoundNBTTag(tagSection);
                            break;
                        default:
                            error.add("&cInvalid nbt type for the nbt with the id: " + nbtId + ", look the wiki !");
                            continue;
                    }
                    tags.add(tag);
                }
            }
        return error;
    }

    public List<String> load(SPlugin sPlugin, List<String> list, boolean isPremiumLoading){
        File file = new File(sPlugin.getPlugin().getDataFolder(), "nbt.yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        int i = 0;
        for (String s : list){
            String[] split = s.split("::");
            config.set("nbt."+i+".type", split[0]);
            config.set("nbt."+i+".key", split[1]);
            config.set("nbt."+i+".value", split[2]);
        }
        file.delete();

        return load(sPlugin, config, isPremiumLoading);
    }

    public void save(ConfigurationSection config) {
        config.set("nbt", null);
        int i = 0;
        for (NBTTag tag : tags) {
            //SsomarDev.testMsg(" >>>>>>> tag save: " + tag.toString(), true);
            tag.saveInConfig(config, Integer.valueOf(i));
            i++;
        }
    }

    @Override
    public Optional<List<String>> getValue() {
        List<String> list = new ArrayList<>();
        for (NBTTag tag : tags) {
            if (tag instanceof StringNBTTag || tag instanceof BooleanNBTTag || tag instanceof DoubleNBTTag || tag instanceof IntNBTTag)
                list.add(tag.toString());
        }
        return Optional.of(list);
    }

    @Override
    public NBTTags initItemParentEditor(GUI gui, int i) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, i, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getCurrentValues(), "&cEMPTY");
    }

    @Override
    public void reset() {
        List<NBTTag> toRemove = new ArrayList<>();
        for (NBTTag tag : tags) {
            if (tag instanceof StringNBTTag || tag instanceof BooleanNBTTag || tag instanceof DoubleNBTTag || tag instanceof IntNBTTag)
                toRemove.add(tag);
        }
        tags.removeAll(toRemove);
    }

    public ItemStack writeNBTTags(ItemStack item) {
        if (SCore.hasNBTAPI) {
            // Creating a new empty NBT tag
            // Optional to remove Error occurred while enabling ExecutableItems v7.24.6.23 (Is it up to date?)
            //java.lang.NoClassDefFoundError: de/tr7zw/nbtapi/iface/ReadableNBT
            NBT.modify(item, nbtItem -> {
                for (NBTTag nbtTag : tags) {
                    nbtTag.applyTo(nbtItem, true);
                }
            });
        }
        return item;
    }

    @Override
    public NBTTags clone(FeatureParentInterface featureParentInterface) {
        NBTTags clone = new NBTTags(featureParentInterface);
        clone.tags = this.tags;
        return clone;
    }

    @Override
    public Optional<String> verifyMessageReceived(String s) {
        String[] split = s.split("::");
        if (split.length < 3)
            return Optional.of("&cInvalid format ! &7TYPE::KEY::VALUE");
        String type = split[0].toUpperCase();
        String [] acceptedTypes = new String[]{"BOOLEAN", "STRING", "DOUBLE", "INTEGER"};
        if (!Arrays.asList(acceptedTypes).contains(type))
            return Optional.of("&cInvalid type ! &7BOOLEAN, STRING, DOUBLE, INTEGER");
        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        if (getValue().isPresent())
            return getValue().get();
        return new ArrayList<>();
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        Suggestion suggestion = new Suggestion("BOOLEAN::MY_KEY::MY_VALUE", "&6[&7Boolean&6]", "&7&oClick here to add a &eboolean &7nbt");
        Suggestion suggestion1 = new Suggestion("STRING::MY_KEY::MY_VALUE", "&6[&7String&6]", "&7&oClick here to add a &estring &7nbt");
        Suggestion suggestion2 = new Suggestion("DOUBLE::MY_KEY::MY_VALUE", "&6[&7Double&6]", "&7&oClick here to add a &edouble &7nbt");
        Suggestion suggestion3 = new Suggestion("INTEGER::MY_KEY::MY_VALUE", "&6[&7Integer&6]", "&7&oClick here to add a &einteger &7nbt");
        ArrayList<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(suggestion);
        suggestions.add(suggestion1);
        suggestions.add(suggestion2);
        suggestions.add(suggestion3);
        return suggestions;
    }

    @Override
    public String getTips() {
        return "&7&o Type &eTYPE&a::&eKEY&a::&eVALUE";
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

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        reset();
        List<NBTTag> saveOthers = new ArrayList<>(tags);
        List<String> values = StringConverter.decoloredString((List<String>) manager.currentWriting.get(editor));
        load(ExecutableItems.plugin, values, isRequirePremium());
        tags.addAll(saveOthers);
        manager.requestWriting.remove(editor);
        manager.activeTextEditor.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

}
