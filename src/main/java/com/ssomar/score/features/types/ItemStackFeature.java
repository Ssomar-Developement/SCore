package com.ssomar.score.features.types;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Getter
@Setter
public class ItemStackFeature extends FeatureAbstract<Optional<ItemStack>, ItemStackFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<ItemStack> value;
    private Optional<ItemStack> defaultValue;
    private Optional<String> placeholder;

    public ItemStackFeature(FeatureParentInterface parent, Optional<ItemStack> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        reset();
    }

    public static ItemStackFeature buildNull() {
        return new ItemStackFeature(null, Optional.empty(), null);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String valueStr = config.getString(this.getName(), "NULL");
        if (valueStr.contains("%")) {
            placeholder = Optional.of(valueStr);
            value = Optional.empty();
        } else {
            placeholder = Optional.empty();
            try {
                value = Optional.of(Bukkit.getServer().getItemFactory().createItemStack(valueStr));
            } catch (Exception e) {
                if (!valueStr.equals("NULL"))
                    errors.add("&cERROR, Couldn't load the ItemStack value of " + this.getName() + " from config, value: " + valueStr + " &7&o" + getParent().getParentInfo());
                this.value = defaultValue;
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (placeholder.isPresent()) {
            config.set(this.getName(), placeholder.get());
        } else if (getValue().isPresent()) {
            if(defaultValue.isPresent() && isSavingOnlyIfDiffDefault() && value.get().equals(defaultValue.get())){
                config.set(this.getName(), null);
                return;
            }
            else {
                ItemStack item = getValue().get();
                String valStr = itemStackToString(item);
                config.set(this.getName(), valStr);
            }
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));
    }

    public String itemStackToString(ItemStack item){
        String valStr = "";
        //System.out.println("item: " + item);
        if(item.hasItemMeta()) valStr = "minecraft:"+item.getType().toString().toLowerCase()+item.getItemMeta().getAsString();
        else valStr = "minecraft:"+item.getType().toString().toLowerCase();
        return valStr;
    }

    public Optional<ItemStack> getValue(@Nullable UUID playerUUID, @Nullable StringPlaceholder sp) {
        if (placeholder.isPresent()) {
            String placeholderStr = placeholder.get();
            if (sp != null) {
                placeholderStr = sp.replacePlaceholder(placeholderStr);
            }
            placeholderStr = StringPlaceholder.replacePlaceholderOfPAPI(placeholderStr, playerUUID);

            ItemStack item ;
            try {
                item = Bukkit.getServer().getItemFactory().createItemStack(placeholderStr);
                return Optional.of(item);
            } catch (Exception e) {}
        } else if (value.isPresent()) {
            return value;
        }
        return defaultValue;
    }

    @Override
    public Optional<ItemStack> getValue() {
        if (value.isPresent()) {
            return Optional.of(value.get().clone());
        } else if (placeholder.isPresent()) {
            String placeholderStr = placeholder.get();
            //SsomarDev.testMsg("Placeholder: " + placeholderStr, true);
            placeholderStr = new StringPlaceholder().replacePlaceholderOfPAPI(placeholderStr);
            ItemStack item ;
            try {
                item = Bukkit.getServer().getItemFactory().createItemStack(placeholderStr);
                return Optional.of(item);
            } catch (Exception e) {}
        }
        return defaultValue;
    }

    @Override
    public ItemStackFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 2] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = TM.g(Text.EDITOR_CURRENTLY_NAME);

        Optional<ItemStack> value = getValue();
        if (value.isPresent()) {
            gui.createItem(value.get(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        } else gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        if (placeholder.isPresent()) gui.updateCurrently(getEditorName(), placeholder.get());
        else if (value.isPresent()){
            gui.updateCurrently(getEditorName(), itemStackToString(value.get()));
        }
        else gui.updateCurrently(getEditorName(), "&8&oNo item");
    }

    @Override
    public ItemStackFeature clone(FeatureParentInterface newParent) {
        ItemStackFeature clone = new ItemStackFeature(newParent, defaultValue, getFeatureSettings());
        clone.setValue(value);
        clone.setPlaceholder(getPlaceholder());
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
        this.placeholder = Optional.empty();
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {

    }

    @Override
    public boolean noShiftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        ItemStack stack = editor.getItemOnCursor().clone();
        //SsomarDev.testMsg("stack: " + stack, true);
        if(stack.getType().isAir()) {
           editor.sendMessage("&cYou must have an item in your hand to set it as the value");
        }
        else {
            value = Optional.of(stack);
            placeholder = Optional.empty();
            //SsomarDev.testMsg("stack2: " + stack, true);
            ((GUI)manager.getCache().get(editor)).updateCurrently(getEditorName(), itemStackToString(stack));
        }
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        ((GUI)manager.getCache().get(editor)).updateCurrently(getEditorName(), "&8&oNo item");
        value = Optional.empty();
        placeholder = Optional.empty();
        return true;
    }

    @Override
    public boolean doubleClicked(Player editor, NewGUIManager manager) {
        return false;
    }
}
