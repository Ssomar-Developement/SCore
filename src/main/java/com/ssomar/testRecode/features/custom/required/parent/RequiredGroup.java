package com.ssomar.testRecode.features.custom.required.parent;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.custom.required.RequiredPlayerInterface;
import com.ssomar.testRecode.features.custom.required.executableitems.group.RequiredExecutableItemGroupFeature;
import com.ssomar.testRecode.features.custom.required.executableitems.item.RequiredExecutableItemFeature;
import com.ssomar.testRecode.features.custom.required.items.group.RequiredItemGroupFeature;
import com.ssomar.testRecode.features.custom.required.items.item.RequiredItemFeatureEditorManager;
import com.ssomar.testRecode.features.custom.required.level.RequiredLevel;
import com.ssomar.testRecode.features.custom.required.money.RequiredMoney;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class RequiredGroup extends FeatureWithHisOwnEditor<RequiredGroup, RequiredGroup, RequiredGroupEditor, RequiredGroupEditorManager>  implements RequiredPlayerInterface{

    private RequiredLevel requiredLevel;
    private RequiredMoney requiredMoney;
    private RequiredItemGroupFeature requiredItems;
    private RequiredExecutableItemGroupFeature requiredExecutableItems;

    public RequiredGroup(FeatureParentInterface parent) {
        super(parent, "requiredGroup", "Required Things", new String[]{"&7&oRequired things"}, Material.ANVIL, false);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        error.addAll(requiredLevel.load(plugin, config, isPremiumLoading));
        error.addAll(requiredMoney.load(plugin, config, isPremiumLoading));
        error.addAll(requiredItems.load(plugin, config, isPremiumLoading));
        error.addAll(requiredExecutableItems.load(plugin, config, isPremiumLoading));
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        requiredLevel.save(config);
        requiredMoney.save(config);
    }

    @Override
    public boolean verify(Player player, Event event) {
        if(!requiredLevel.verify(player, event)){
            return false;
        }
        if(!requiredMoney.verify(player, event)){
            return false;
        }
        if(!requiredItems.verify(player, event)){
            return false;
        }
        if(!requiredExecutableItems.verify(player, event)){
            return false;
        }
        return true;
    }

    @Override
    public void take(Player player) {
        requiredLevel.take(player);
        requiredMoney.take(player);
        requiredItems.take(player);
        requiredExecutableItems.take(player);
    }

    @Override
    public RequiredGroup getValue() {
        return this;
    }

    @Override
    public RequiredGroup initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 5];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 5] = gui.CLICK_HERE_TO_CHANGE;
        if(requiredLevel.getValue().getLevel().getValue().get() > 0)
            finalDescription[finalDescription.length - 4] = "&7Required level: &a&l✔";
        else
            finalDescription[finalDescription.length - 4] = "&7Required level: &c&l✘";

        if(requiredMoney.getValue().getMoney().getValue().get() > 0)
            finalDescription[finalDescription.length - 3] = "&7Required money: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Required money: &c&l✘";

        if(requiredItems.getValue().getRequiredItems().size() > 0)
            finalDescription[finalDescription.length - 2] = "&7Required items: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Required items: &c&l✘";

        if(requiredExecutableItems.getValue().getRequiredExecutableItems().size() > 0)
            finalDescription[finalDescription.length - 1] = "&7Required executable items: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Required executable items: &c&l✘";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }


    @Override
    public void updateItemParentEditor(GUI gui) {

    }


    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        return;
    }


    @Override
    public RequiredGroup clone() {
        RequiredGroup requiredLevel = new RequiredGroup(getParent());
        requiredLevel.setRequiredLevel(getRequiredLevel().clone());
        requiredLevel.setRequiredMoney(getRequiredMoney().clone());
        requiredLevel.setRequiredItems(getRequiredItems().clone());
        requiredLevel.setRequiredExecutableItems(getRequiredExecutableItems().clone());
        return requiredLevel;
    }

    @Override
    public void reset() {
        this.requiredLevel = new RequiredLevel(this);
        this.requiredMoney = new RequiredMoney(this);
        this.requiredItems = new RequiredItemGroupFeature(this);
        this.requiredExecutableItems = new RequiredExecutableItemGroupFeature(this);
    }

    @Override
    public void openEditor(Player player) {
        RequiredGroupEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return Arrays.asList(requiredLevel, requiredMoney, requiredItems, requiredExecutableItems);
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
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof RequiredGroup) {
                RequiredGroup requiredgroup = (RequiredGroup) feature;
                requiredgroup.setRequiredLevel(requiredLevel);
                requiredgroup.setRequiredMoney(requiredMoney);
                requiredgroup.setRequiredItems(requiredItems);
                requiredgroup.setRequiredExecutableItems(requiredExecutableItems);
                break;
            }
        }
    }
}
