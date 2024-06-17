package com.ssomar.score.features.custom.othereicooldowns.cooldown;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.activators.activator.SActivator;
import com.ssomar.score.features.custom.cooldowns.Cooldown;
import com.ssomar.score.features.custom.cooldowns.CooldownsManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ExecutableItemFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.SObjectWithActivators;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class OtherEICooldown extends FeatureWithHisOwnEditor<OtherEICooldown, OtherEICooldown, OtherEICooldownEditor, OtherEICooldownEditorManager> {

    private ExecutableItemFeature executableItemFeature;
    private ListUncoloredStringFeature activatorsList;
    private IntegerFeature cooldown;
    private BooleanFeature isCooldownInTicks;
    private String id;

    public OtherEICooldown(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.otherEICooldowns);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.executableItemFeature = new ExecutableItemFeature(this, FeatureSettingsSCore.executableItem);
        this.activatorsList = new ListUncoloredStringFeature(this,  new ArrayList<>(), FeatureSettingsSCore.cooldown_activators, false, Optional.empty());
        this.cooldown = new IntegerFeature(this,  Optional.of(0), FeatureSettingsSCore.cooldown);
        this.isCooldownInTicks = new BooleanFeature(this, false, FeatureSettingsSCore.isCooldownInTicks, false);
    }

    public void addOtherCooldowns(@NotNull Player player, @Nullable StringPlaceholder sp) {
        if (executableItemFeature.getValue().isPresent()) {
            SObject ei = (SObject) executableItemFeature.getValue().get();
            if (!(ei instanceof SObjectWithActivators)) return;
            SObjectWithActivators sObjectWithActivators = (SObjectWithActivators) ei;
            if (activatorsList.getValue().contains("ALL")) {
                for (Object act : sObjectWithActivators.getActivators().getActivators().values()) {
                    SActivator sActivator = (SActivator) act;
                    Cooldown cooldown = new Cooldown(ExecutableItems.plugin, ei, sActivator, player.getUniqueId(), this.cooldown.getValue(player.getUniqueId(), sp).get(), isCooldownInTicks.getValue(), System.currentTimeMillis(), false);
                    CooldownsManager.getInstance().addCooldown(cooldown);
                    //SsomarDev.testMsg("Added cooldown " + cooldown.getId() + " to " + player.getName(), true);
                }
            } else {
                for (String activator : activatorsList.getValue()) {
                    for (Object act : sObjectWithActivators.getActivators().getActivators().values()) {
                        SActivator sActivator = (SActivator) act;
                        if (activator.equals(sActivator.getId())) {
                            Cooldown cooldown = new Cooldown(ExecutableItems.plugin, ei, sActivator, player.getUniqueId(), this.cooldown.getValue(player.getUniqueId(), sp).get(), isCooldownInTicks.getValue(), System.currentTimeMillis(), false);
                            CooldownsManager.getInstance().addCooldown(cooldown);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<FeatureInterface>() {{
            add(executableItemFeature);
            add(activatorsList);
            add(cooldown);
            add(isCooldownInTicks);
        }};
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return null;
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof OtherEICooldown) {
                OtherEICooldown c = (OtherEICooldown) feature;
                if (c.getId().equals(id)) {
                    c.setExecutableItemFeature(executableItemFeature);
                    c.setActivatorsList(activatorsList);
                    c.setCooldown(cooldown);
                    c.setIsCooldownInTicks(isCooldownInTicks);
                    c.reloadActivatorsSuggestions();
                    break;
                }
            }
        }
    }

    @Override
    public void openEditor(@NotNull Player player) {
        OtherEICooldownEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public List<String> load(SPlugin sPlugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.executableItemFeature.load(sPlugin, enchantmentConfig, isPremiumLoading));

            errors.addAll(this.activatorsList.load(sPlugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.cooldown.load(sPlugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.isCooldownInTicks.load(sPlugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the OtherEICooldown with its options because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    public void reloadActivatorsSuggestions() {
        if (executableItemFeature.getValue().isPresent()) {
            List<Suggestion> suggestions = new ArrayList<>();
            for (SActivator act : executableItemFeature.getValue().get().getActivators().getActivators().values()) {
                suggestions.add(new Suggestion(act.getId(), "&6[&e" + act.getId() + "&6]", "&7Add &e" + act.getId()));
            }
            suggestions.add(new Suggestion("ALL", "&8[&7Add ALL&8]", "&7Add &eALL"));
            activatorsList.setSuggestions(Optional.of(suggestions));
        }
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.executableItemFeature.save(attributeConfig);
        this.activatorsList.save(attributeConfig);
        this.cooldown.save(attributeConfig);
        this.isCooldownInTicks.save(attributeConfig);
    }

    @Override
    public OtherEICooldown getValue() {
        return this;
    }

    @Override
    public OtherEICooldown initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 5];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (executableItemFeature.getValue().isPresent())
            finalDescription[finalDescription.length - 5] = "&7EI ID: &e" + executableItemFeature.getValue().get().getId();
        else
            finalDescription[finalDescription.length - 5] = "&7EI ID: &cNone";

        finalDescription[finalDescription.length - 4] = "&7Activators: &e" + activatorsList.getValue().size();
        finalDescription[finalDescription.length - 3] = "&7Cooldown: &e" + cooldown.getValue().get();
        finalDescription[finalDescription.length - 2] = "&7Cooldown in ticks: &e" + isCooldownInTicks.getValue();
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public OtherEICooldown clone(FeatureParentInterface newParent) {
        reloadActivatorsSuggestions();
        OtherEICooldown otherEICooldown = new OtherEICooldown(newParent, id);
        otherEICooldown.executableItemFeature = executableItemFeature.clone(otherEICooldown);
        otherEICooldown.activatorsList = activatorsList.clone(otherEICooldown);
        otherEICooldown.cooldown = cooldown.clone(otherEICooldown);
        otherEICooldown.isCooldownInTicks = isCooldownInTicks.clone(otherEICooldown);
        return otherEICooldown;
    }
}
