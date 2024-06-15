package com.ssomar.score.features.custom.ifhas.items.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.ifhas.items.attribute.HasItemFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HasItemGroupFeature extends FeatureWithHisOwnEditor<HasItemGroupFeature, HasItemGroupFeature, HasItemGroupFeatureEditor, HasItemGroupFeatureEditorManager> implements FeaturesGroup<FeatureInterface> {

    private Map<String, FeatureInterface> hasItems;
    private boolean notSaveIfNoValue;
    private String multipleChoicesID;

    public HasItemGroupFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings, boolean notSaveIfNoValue) {
        super(parent, featureSettings);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    /* Multiple choices construtor */
    public HasItemGroupFeature(FeatureParentInterface parent, String multipleChoicesID) {
        super(parent, FeatureSettingsSCore.multiChoices);
        this.notSaveIfNoValue = true;
        this.multipleChoicesID = multipleChoicesID;
        reset();
    }


    @Override
    public void reset() {
        this.hasItems = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();

        String section;
        if(multipleChoicesID == null || multipleChoicesID.isEmpty()){
            section = this.getName();
        }
        else{
            section = multipleChoicesID+".multi-choices";
        }


        if (config.isConfigurationSection(section)) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(section);
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                ConfigurationSection cdtSection = enchantmentsSection.getConfigurationSection(attributeID);

                List<String> subErrors = new ArrayList<>();
                if(cdtSection.isConfigurationSection("multi-choices")){
                    HasItemGroupFeature multiChoices = new HasItemGroupFeature(this, attributeID);
                    multiChoices.load(plugin, enchantmentsSection, isPremiumLoading);
                    hasItems.put(attributeID, multiChoices);
                }else {
                    HasItemFeature attribute = new HasItemFeature(this, attributeID);
                    subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                    hasItems.put(attributeID, attribute);
                }

                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
            }
        }
        return error;
    }

    public boolean verifHas(ItemStack[] items, int heldSlot) {

        for (FeatureInterface feature : hasItems.values()) {
            if(feature instanceof HasItemFeature) {
                if(!verifHasItem(items, heldSlot, (HasItemFeature) feature)) return false;
            }
            else if(feature instanceof HasItemGroupFeature){
                HasItemGroupFeature f = (HasItemGroupFeature) feature;
                boolean has = false;
                for(FeatureInterface feature1 : f.getHasItems().values()){
                    if(feature1 instanceof HasItemFeature) {
                        if(verifHasItem(items, heldSlot, (HasItemFeature) feature1)) has = has ||true;
                    }
                    else if(feature1 instanceof HasItemGroupFeature){
                        if(!verifHas(items, heldSlot)) return false;
                    }
                }
                if(!has) return false;
            }
        }
        return true;
    }

    public boolean verifHasItem(ItemStack[] items, int heldSlot, HasItemFeature f) {
            boolean valid = false;

            int needed = f.getAmount().getValue().get();
            int slot = 0;
            for (ItemStack item : items) {
                if (f.getDetailedSlots().verifSlot(slot, slot == heldSlot)) {
                    if (item != null) {
                        if (item.getType().equals(f.getMaterial().getValue().get())) {
                            needed = needed - item.getAmount();
                        }
                    }
                }
                if (needed <= 0) {
                    valid = true;
                    break;
                }
                slot++;
            }
             return valid;
    }

    public boolean verifHasNot(ItemStack[] items, int heldSlot) {
       return !verifHas(items, heldSlot);
    }


    @Override
    public void save(ConfigurationSection config) {
        ConfigurationSection attributesSection;
        //SsomarDev.testMsg("SAVE : "+multipleChoicesID);
        if(multipleChoicesID == null || multipleChoicesID.isEmpty()) {
            config.set(this.getName(), null);
            if (notSaveIfNoValue && hasItems.size() == 0) return;
            attributesSection = config.createSection(this.getName());
        }
        else{
            config.set(this.getMultipleChoicesID(), null);
            attributesSection = config.createSection(this.getMultipleChoicesID()+".multi-choices");
        }
        for (String enchantmentID : hasItems.keySet()) {
            hasItems.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public HasItemGroupFeature getValue() {
        return this;
    }

    @Override
    public HasItemGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oHasItems(s) added: &e" + hasItems.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public FeatureInterface getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (FeatureInterface x : hasItems.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public HasItemGroupFeature clone(FeatureParentInterface newParent) {
        HasItemGroupFeature eF = new HasItemGroupFeature(newParent, getFeatureSettings(), isNotSaveIfNoValue());
        HashMap<String, FeatureInterface> newHasExecutableItems = new HashMap<>();
        for (String key : hasItems.keySet()) {
            newHasExecutableItems.put(key, hasItems.get(key).clone(eF));
        }
        eF.setHasItems(newHasExecutableItems);
        eF.setMultipleChoicesID(getMultipleChoicesID());
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(hasItems.values());
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if(multipleChoicesID == null || multipleChoicesID.isEmpty()) {
            if (section.isConfigurationSection(this.getName())) {
                return section.getConfigurationSection(this.getName());
            } else return section.createSection(this.getName());
        }
        else{
            if (section.isConfigurationSection(this.getMultipleChoicesID()+".multi-choices")) {
                return section.getConfigurationSection(this.getMultipleChoicesID()+".multi-choices");
            } else return section.createSection(this.getMultipleChoicesID()+".multi-choices");
        }
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof HasItemGroupFeature) {
                HasItemGroupFeature eF = (HasItemGroupFeature) feature;
                eF.setHasItems(this.getHasItems());
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
        HasItemGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "hasItem";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!hasItems.containsKey(id)) {
                HasItemFeature eF = new HasItemFeature(this, id);
                hasItems.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, FeatureInterface feature) {
        if(feature instanceof HasItemFeature){
            HasItemFeature eF = (HasItemFeature) feature;
            hasItems.remove(eF.getId());
        }
        else if (feature instanceof HasItemGroupFeature) {
            HasItemGroupFeature eF = (HasItemGroupFeature) feature;
            hasItems.remove(eF.getMultipleChoicesID());
        }
    }

}
