package com.ssomar.score.features.custom.ifhas.executableitems.group;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.ifhas.executableitems.attribute.HasExecutableItemFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringCalculation;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class HasExecutableItemGroupFeature extends FeatureWithHisOwnEditor<HasExecutableItemGroupFeature, HasExecutableItemGroupFeature, HasExecutableItemGroupFeatureEditor, HasExecutableItemGroupFeatureEditorManager> implements FeaturesGroup<FeatureInterface> {

    private Map<String, FeatureInterface> hasExecutableItems;
    private boolean notSaveIfNoValue;
    private String multipleChoicesID;

    public HasExecutableItemGroupFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettingsInterface, boolean notSaveIfNoValue) {
        super(parent, featureSettingsInterface);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    /* Multiple choices construtor */
    public HasExecutableItemGroupFeature(FeatureParentInterface parent, String multipleChoicesID) {
        super(parent, FeatureSettingsSCore.multiChoices);
        this.notSaveIfNoValue = true;
        this.multipleChoicesID = multipleChoicesID;
        reset();
    }

    @Override
    public void reset() {
        this.hasExecutableItems = new HashMap<>();
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
                    HasExecutableItemGroupFeature multiChoices = new HasExecutableItemGroupFeature(this, attributeID);
                    multiChoices.load(plugin, enchantmentsSection, isPremiumLoading);
                    hasExecutableItems.put(attributeID, multiChoices);
                }else {
                    HasExecutableItemFeature attribute = new HasExecutableItemFeature(this, attributeID);
                    subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                    hasExecutableItems.put(attributeID, attribute);
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

        for (FeatureInterface feature : hasExecutableItems.values()) {
            if(feature instanceof HasExecutableItemFeature) {
                if(!verifHasExecutableItem(items, heldSlot, (HasExecutableItemFeature) feature)) return false;
            }
            else if(feature instanceof HasExecutableItemGroupFeature){
                HasExecutableItemGroupFeature f = (HasExecutableItemGroupFeature) feature;
                boolean has = false;
                for(FeatureInterface feature1 : f.getHasExecutableItems().values()){
                    if(feature1 instanceof HasExecutableItemFeature) {
                        if(verifHasExecutableItem(items, heldSlot, (HasExecutableItemFeature) feature1)) has = has ||true;
                    }
                    else if(feature1 instanceof HasExecutableItemGroupFeature){
                        if(!verifHas(items, heldSlot)) return false;
                    }
                }
                if(!has) return false;
            }
        }
        return true;
    }

    public boolean verifHasExecutableItem(ItemStack[] items, int heldSlot, HasExecutableItemFeature f){
        boolean valid = false;

        Optional<ExecutableItemInterface> ei = f.getExecutableItem().getValue();
        if (!ei.isPresent()) return true;
        ExecutableItemInterface executableItem = ei.get();

        int needed = f.getAmount().getValue().get();
        int slot = 0;
        for (ItemStack item : items) {
            if (f.getDetailedSlots().verifSlot(slot, slot == heldSlot)) {
                if (item != null) {
                    ExecutableItemObject eiObject = new ExecutableItemObject(item);
                   if (eiObject.isValid()) {
                        if (eiObject.getConfig().getId().equals(executableItem.getId())) {
                            if (f.getUsageCondition().getValue().isPresent()) {
                                eiObject.loadExecutableItemInfos();
                                if (!StringCalculation.calculation(f.getUsageCondition().getValue().get(), eiObject.getUsage())) {
                                    continue;
                                }
                            }
                            needed = needed - item.getAmount();
                        }
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
            if (notSaveIfNoValue && hasExecutableItems.size() == 0) return;
            attributesSection = config.createSection(this.getName());
        }
        else{
            config.set(this.getMultipleChoicesID(), null);
            attributesSection = config.createSection(this.getMultipleChoicesID()+".multi-choices");
        }
        for (String enchantmentID : hasExecutableItems.keySet()) {
            hasExecutableItems.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public HasExecutableItemGroupFeature getValue() {
        return this;
    }

    @Override
    public HasExecutableItemGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oHasExecutableItems(s) added: &e" + hasExecutableItems.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public FeatureInterface getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (FeatureInterface x : hasExecutableItems.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public HasExecutableItemGroupFeature clone(FeatureParentInterface newParent) {
        HasExecutableItemGroupFeature eF = new HasExecutableItemGroupFeature(newParent, getFeatureSettings(), isNotSaveIfNoValue());
        HashMap<String, FeatureInterface> newHasExecutableItems = new HashMap<>();
        for (String key : hasExecutableItems.keySet()) {
            newHasExecutableItems.put(key, hasExecutableItems.get(key).clone(eF));
        }
        eF.setHasExecutableItems(newHasExecutableItems);
        eF.setMultipleChoicesID(getMultipleChoicesID());
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(hasExecutableItems.values());
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
            if (feature instanceof HasExecutableItemGroupFeature) {
                HasExecutableItemGroupFeature eF = (HasExecutableItemGroupFeature) feature;
                eF.setHasExecutableItems(this.getHasExecutableItems());
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
        HasExecutableItemGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "hasExecutableItem";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!hasExecutableItems.containsKey(id)) {
                HasExecutableItemFeature eF = new HasExecutableItemFeature(this, id);
                hasExecutableItems.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, FeatureInterface feature) {
        if(feature instanceof HasExecutableItemFeature){
            HasExecutableItemFeature eF = (HasExecutableItemFeature) feature;
            hasExecutableItems.remove(eF.getId());
        }
        else if (feature instanceof HasExecutableItemGroupFeature) {
            HasExecutableItemGroupFeature eF = (HasExecutableItemGroupFeature) feature;
            hasExecutableItems.remove(eF.getMultipleChoicesID());
        }
    }

}
