package com.ssomar.scoretestrecode.features.custom.conditions;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.custom.hiders.HidersEditor;
import com.ssomar.scoretestrecode.features.custom.hiders.HidersEditorManager;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import com.ssomar.scoretestrecode.features.types.ColoredStringFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter@Setter
public abstract class ConditionFeature<Y extends FeatureInterface, T extends ConditionFeature<Y, T>> extends FeatureWithHisOwnEditor<T, T, HidersEditor, HidersEditorManager> {

    private Y condition;
    private ColoredStringFeature errorMessage;
    private BooleanFeature cancelEventIfError;

    public ConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        reset();
    }

    @Override
    public void reset() {
        this.errorMessage = new ColoredStringFeature(this, getEditorName()+"Msg", Optional.ofNullable("&4[ERROR] &cYou can't activate this item > invalid condition: &6"+getEditorName()), "Error Message", new String[]{"&7&oError Message"}, GUI.WRITABLE_BOOK,false);
        this.cancelEventIfError = new BooleanFeature(this, getEditorName()+"Cancel", false, "Cancel Event If Error", new String[]{"&7&oCancel Event If Error"}, Material.LEVER,false);
        subReset();
    }

    public abstract void subReset();

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        error.addAll(condition.load(plugin, config, isPremiumLoading));
        error.addAll(errorMessage.load(plugin, config, isPremiumLoading));
        error.addAll(cancelEventIfError.load(plugin, config, isPremiumLoading));

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        condition.save(config);
        errorMessage.save(config);
        cancelEventIfError.save(config);
    }

    public void sendErrorMsg(Optional<Player> playerOpt, SendMessage messageSender){
        if(playerOpt.isPresent() && hasErrorMsg()) messageSender.sendMessage(playerOpt.get(), errorMessage.getValue().get());
    }

    public boolean hasErrorMsg(){
        return errorMessage.getValue().isPresent() && StringConverter.decoloredString(errorMessage.getValue().get().trim()).length() > 0;
    }

    public void cancelEvent(@Nullable Event event){
        if(event != null && cancelEventIfError.getValue() && event instanceof Cancellable) ((Cancellable)event).setCancelled(true);
    }

    @Override
    public T initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 3];
        finalDescription[0] = gui.CLICK_HERE_TO_CHANGE;
        System.arraycopy(getEditorDescription(), 0, finalDescription, 1, getEditorDescription().length);

        if(errorMessage.getValue().isPresent()) {
            finalDescription[finalDescription.length - 2] = "&7Error Message: &e" + getErrorMessage().getValue();
        } else {
            finalDescription[finalDescription.length - 2] = "&7Error Message: &cNO MESSAGE" ;
        }

        if(cancelEventIfError.getValue())
            finalDescription[finalDescription.length - 5] = "&7Cancel Event If Error: &a&l✔";
        else
            finalDescription[finalDescription.length - 5] = "&7Cancel Event If Error: &c&l✘";


        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return (T) this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public T clone() {
        T clone = getNewInstance();
        clone.setCondition((Y) condition.clone());
        clone.setErrorMessage(errorMessage.clone());
        clone.setCancelEventIfError(cancelEventIfError.clone());
        return clone;
    }

    public abstract T getNewInstance();

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(condition, errorMessage, cancelEventIfError));
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
            if(feature.getClass() == getNewInstance().getClass()) {
                T cdt = (T) feature;
                cdt.setCondition(cdt.getCondition());
                cdt.setErrorMessage(cdt.getErrorMessage());
                cdt.setCancelEventIfError(cdt.getCancelEventIfError());
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
        ConditionFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
