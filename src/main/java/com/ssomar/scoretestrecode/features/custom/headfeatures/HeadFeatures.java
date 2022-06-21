package com.ssomar.scoretestrecode.features.custom.headfeatures;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.ssomar.executableitems.usedapi.HeadDB;
import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.HeadDatabase;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.types.UncoloredStringFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

@Getter
@Setter
public class HeadFeatures extends FeatureWithHisOwnEditor<HeadFeatures, HeadFeatures, HeadFeaturesEditor, HeadFeaturesEditorManager> {

    private UncoloredStringFeature headValue;
    private UncoloredStringFeature headDBID;

    public HeadFeatures(FeatureParentInterface parent) {
        super(parent, "--", "Head Features", new String[]{"&7&oTextures for the head"}, Material.PLAYER_HEAD, false);
        reset();
    }

    @Override
    public void reset() {
        this.headValue = new UncoloredStringFeature(this, "headValue", Optional.empty(), "Head Value", new String[]{"&7&oThe value of the head", "&eminecraft-heads.com"}, Material.PLAYER_HEAD, false, false);
        this.headDBID = new UncoloredStringFeature(this, "headDBID", Optional.empty(), "HeadDB ID", new String[]{"&7&oThe HeadDB ID of the head", "&7&oWork with: ", "&7&o- &bHeadDB(Free)", "&7&o- &cHead Database(Prem)"}, Material.PLAYER_HEAD, false, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        errors.addAll(headValue.load(plugin, config, isPremiumLoading));
        errors.addAll(headDBID.load(plugin, config, isPremiumLoading));

        return errors;
    }

    private ItemStack getHeadOrSub(Material or) {
        if (headDBID.getValue().isPresent()) {
            if (SCore.hasHeadDatabase) {
                ItemStack item = HeadDatabase.getInstance().getHead(headDBID.getValue().get());
                if (item != null) return item;
                else {
                    SCore.plugin.getLogger().severe(" Error when creating the Head: " + headDBID + " invalid head database id ! (" + headDBID + ")");
                    SCore.plugin.getLogger().severe(" If you use HeadDB, be sure that the plugin has finish to fetch all the custom head (generally it takes 20-30 seconds after the start of the server) !");
                }
            }
            if (SCore.hasHeadDB) {
                try {
                    ItemStack item = HeadDB.getHead(Integer.valueOf(headDBID.getValue().get()));
                    if (item != null) return item;
                    else {
                        SCore.plugin.getLogger().severe(" Error when creating the Head: " + headDBID + " invalid head database id ! (" + headDBID + ")");
                        SCore.plugin.getLogger().severe(" If you use HeadDD, be sure that the plugin has finish to fetch all the custom head (generally it takes 20-30 seconds after the start of the server) !");
                    }
                } catch (Exception ignored) {
                    SCore.plugin.getLogger().severe(" Error when creating the Head: " + headDBID + " invalid head database id ! (" + headDBID + ")");
                    SCore.plugin.getLogger().severe(" If you use HeadDB, be sure that the plugin has finish to fetch all the custom head (generally it takes 20-30 seconds after the start of the server) !");
                }
            }
        } else if (headValue.getValue().isPresent() && !SCore.is1v12Less()) {
            ItemStack newHead = new ItemStack(Material.PLAYER_HEAD);

            SkullMeta itemMeta = (SkullMeta)newHead.getItemMeta();
            GameProfile profile = getGameProfile(this.headValue.getValue().get());
            Field profileField = null;
            try {
                profileField = itemMeta.getClass().getDeclaredField("profile");
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            }
            profileField.setAccessible(true);
            try {
                profileField.set(itemMeta, profile);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            newHead.setItemMeta((ItemMeta)itemMeta);
            return newHead;
        }

        return new ItemStack(or);
    }

    public ItemStack getHeadOr(Material or){
        ItemStack item = getHeadOrSub(or);
        /*DONT DELETE THIS
         *  the method help to serialize the item if the material is player_head
         *  if you delete this method, player_head with custom headValue won't be give for isDisabledStack true
         */
        Inventory inv = Bukkit.createInventory(null, 18);
        inv.addItem(item);
        for(ItemStack it: inv.getContents()) {
            if(it == null) continue;
            else {
                return it;
            }
        }
        return item;
    }

    public GameProfile getGameProfile(String input) {
        GameProfile profile = new GameProfile(UUID.fromString("b33183ad-e9c0-4d48-8eea-f8c9358d3568"), null);
        profile.getProperties().put("textures", new Property("textures", input));
        return profile;
    }

    @Override
    public void save(ConfigurationSection config) {
        headValue.save(config);
        headDBID.save(config);
    }

    @Override
    public HeadFeatures getValue() {
        return this;
    }

    @Override
    public HeadFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = gui.CLICK_HERE_TO_CHANGE;
        if (headValue.getValue().isPresent())
            finalDescription[finalDescription.length - 2] = "&7Head value: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Head value: &c&l✘";

        if (headDBID.getValue().isPresent())
            finalDescription[finalDescription.length - 1] = "&7Head DB ID: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Head DB ID: &c&l✘";

        gui.createItem(getHeadOr(Material.PLAYER_HEAD), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public HeadFeatures clone() {
        HeadFeatures dropFeatures = new HeadFeatures(getParent());
        dropFeatures.setHeadValue(headValue.clone());
        dropFeatures.setHeadDBID(headDBID.clone());
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(headValue, headDBID));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return getParent().getConfigurationSection();
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof HeadFeatures) {
                HeadFeatures hiders = (HeadFeatures) feature;
                hiders.setHeadValue(headValue.clone());
                hiders.setHeadDBID(headDBID.clone());
                break;
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        HeadFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
