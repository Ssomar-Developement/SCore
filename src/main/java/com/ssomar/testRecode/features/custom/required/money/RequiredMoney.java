package com.ssomar.testRecode.features.custom.required.money;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.VaultAPI;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.custom.required.RequiredPlayerInterface;
import com.ssomar.testRecode.features.types.BooleanFeature;
import com.ssomar.testRecode.features.types.ColoredStringFeature;
import com.ssomar.testRecode.features.types.IntegerFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.menu.GUI.WRITABLE_BOOK;

@Getter
@Setter
public class RequiredMoney extends FeatureWithHisOwnEditor<RequiredMoney, RequiredMoney, RequiredMoneyGUI, RequiredMoneyGUIManager>  implements RequiredPlayerInterface{

    private IntegerFeature money;
    private ColoredStringFeature errorMessage;
    private BooleanFeature cancelEventIfError;

    public RequiredMoney(FeatureParentInterface parent) {
        super(parent, "requiredMoney", "Required Money", new String[]{"&7&oRequired money"}, Material.GOLD_BLOCK, true);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.contains("requiredLevel")) {
            if (!isPremiumLoading) {
                error.add("&cERROR, Couldn't load the Required Level value of " + this.getName() + " from config, &7&o"+getParent().getParentInfo()+" &6>> Because it's a premium feature !");
                return error;
            }
            money.load(plugin, config, isPremiumLoading);
            errorMessage.load(plugin, config, isPremiumLoading);
            cancelEventIfError.load(plugin, config, isPremiumLoading);
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        if(money.getValue().isPresent() && money.getValue().get() > 0) {
            money.save(config);
            errorMessage.save(config);
            cancelEventIfError.save(config);
        }
    }

    @Override
    public boolean verify(Player player, Event event) {
        if (money.getValue().isPresent()) {
            VaultAPI v = new VaultAPI();
            v.verifEconomy(player);
            return v.hasMoney(player, money.getValue().get(), errorMessage.getValue().get());
        }
        return true;
    }

    @Override
    public void take(Player player) {
        if (money.getValue().isPresent()){
            VaultAPI v = new VaultAPI();
            v.takeMoney(player, money.getValue().get());
        }
    }

    @Override
    public RequiredMoney getValue() {
        return this;
    }

    @Override
    public RequiredMoney initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

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
    public RequiredMoney clone() {
        RequiredMoney requiredLevel = new RequiredMoney(getParent());
        requiredLevel.setMoney(money.clone());
        requiredLevel.setErrorMessage(errorMessage.clone());
        requiredLevel.setCancelEventIfError(cancelEventIfError.clone());
        return requiredLevel;
    }

    @Override
    public void reset() {
        this.money = new IntegerFeature(getParent(), "requiredLevel", Optional.of(0), "Required Level", new String[]{"&7&oRequired level"}, Material.ANVIL, false);
        this.errorMessage = new ColoredStringFeature(getParent(), "requiredLevelMsg", Optional.of("&4&l>> &cError you don't have the required levels"), "Error message", new String[]{"&7&oEdit the error message"}, WRITABLE_BOOK, false);
        this.cancelEventIfError = new BooleanFeature(getParent(), "cancelEventIfInvalidRequiredLevel", false, "cancelEventIfInvalidRequiredLevel", new String[]{"&7&oCancel the vanilla event"}, Material.LEVER, false);
    }

    @Override
    public void openEditor(Player player) {
        RequiredMoneyGUIManager.getInstance().startEditing(player, this);
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return Arrays.asList(money, errorMessage, cancelEventIfError);
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo()+".(requiredLevel)";
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
            if(feature instanceof RequiredMoney) {
                RequiredMoney requiredLevel = (RequiredMoney) feature;
                requiredLevel.setMoney(money);
                requiredLevel.setErrorMessage(errorMessage);
                requiredLevel.setCancelEventIfError(cancelEventIfError);
                break;
            }
        }
    }
}
