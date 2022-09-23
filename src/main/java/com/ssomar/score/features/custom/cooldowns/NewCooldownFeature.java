package com.ssomar.score.features.custom.cooldowns;

import com.ssomar.executableitems.configs.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.NewSObject;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
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
import java.util.*;

@Getter
@Setter
public class NewCooldownFeature extends FeatureWithHisOwnEditor<NewCooldownFeature, NewCooldownFeature, NewCooldownFeatureEditor, NewCooldownFeatureEditorManager> {

    /* Cooldowns / delay */
    private IntegerFeature cooldown;
    private ColoredStringFeature cooldownMessage;
    private BooleanFeature displayCooldownMessage;
    private BooleanFeature isCooldownInTicks;
    private BooleanFeature cancelEventIfInCooldown;
    private String cooldownId;
    private SPlugin sPlugin;
    private boolean enableCooldownForOp;

    private boolean isPremium;

    public NewCooldownFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, SPlugin sPlugin, boolean enableCooldownForOp) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.sPlugin = sPlugin;
        this.isPremium = !sPlugin.isLotOfWork();
        if (getParent() instanceof NewSActivator) {
            NewSActivator newSActivator = (NewSActivator) getParent();
            //String id = newSActivator.getParentObjectId();
            //SsomarDev.testMsg("CREATE NEW COOLDOWN FEATURE FOR ACTIVATOR >> "+ newSActivator.getId()+" >>>> item "+id);
            this.cooldownId = sPlugin.getShortName() + ":" + newSActivator.getParentObjectId() + ":" + newSActivator.getId();
        } else this.cooldownId = UUID.randomUUID().toString();
        this.enableCooldownForOp = enableCooldownForOp;
        reset();
    }

    /**
     * @param p  The player
     * @param e  The event
     * @param sp The placeholder associate to the event
     * @return True in No coooldown, false if its on cooldown
     */
    public boolean checkCooldown(Player p, @Nullable Event e, StringPlaceholder sp, NewSObject sObject, boolean global) {
        /* Check if the activator is in cooldown for the player or not  */
        if (!hasNoCDPerm(p, sObject)) {
            Optional<Cooldown> inCooldownOpt = CooldownsManager.getInstance().getCooldown(sPlugin, cooldownId, p.getUniqueId(), global);
            if (inCooldownOpt.isPresent()) {
                if (this.displayCooldownMessage.getValue()) {
                    //SsomarDev.testMsg("DISPLAY COOLDOWN MESSAGE >> "+inCooldownOpt.get().getTimeLeft());
                    displayCooldownMessage(p, inCooldownOpt.get().getTimeLeft(), sp);
                }
                //SsomarDev.testMsg("COOLDOWN cancel >>"+(e != null && e instanceof Cancellable)+  " >>>"+this.cancelEventIfInCooldown.getValue() );
                if (e != null && e instanceof Cancellable && this.cancelEventIfInCooldown.getValue()) {
                    //SsomarDev.testMsg("COOLDOWN cancel event");
                    ((Cancellable) e).setCancelled(true);
                }
                return false;
            }
            //else SsomarDev.testMsg("NO COOLDOWN BECAUSE OF NO COOLDOWN FOUND with id: " + cooldownId);
        }
        //else SsomarDev.testMsg("NO COOLDOWN BECAUSE OF PERMISSION");
        return true;
    }

    public void displayCooldownMessage(Player player, double timeLeft, StringPlaceholder sp) {
        String message = cooldownMessage.getValue().get();
        if (message.isEmpty()) {
            message = MessageMain.getInstance().getMessage(sPlugin.getPlugin(), Message.TIME_LEFT);
        }
        sp.getTimePlch().setTimePlcHldr(timeLeft);
        message = sp.replacePlaceholder(message);
        player.sendMessage(StringConverter.coloredString(message));
    }

    /**
     * @param p The player
     */
    public void addCooldown(Player p, @NotNull NewSObject sObject) {
        if (!hasNoCDPerm(p, sObject) && this.cooldown.getValue().get() != 0) {
            Cooldown cooldown = new Cooldown(sPlugin, cooldownId, p.getUniqueId(), this.cooldown.getValue().get(), isCooldownInTicks.getValue(), System.currentTimeMillis(), false);
            CooldownsManager.getInstance().addCooldown(cooldown);
        }
    }

    public void addGlobalCooldown(@NotNull NewSObject sObject) {
        Cooldown cooldown = new Cooldown(sPlugin, cooldownId, null, this.cooldown.getValue().get(), isCooldownInTicks.getValue(), System.currentTimeMillis(), true);
        CooldownsManager.getInstance().addCooldown(cooldown);
    }

    public boolean hasNoCDPerm(Player p, NewSObject sObject) {
        String id = sObject.getId();

        if (sPlugin.isLotOfWork()) return false;

        if (p.isOp() || p.hasPermission("*")) {
            return !enableCooldownForOp;
        } else {
            return p.hasPermission(sPlugin.getName() + ".nocd." + id) ||
                    p.hasPermission(sPlugin.getShortName().toLowerCase() + ".nocd." + id) ||
                    p.hasPermission(sPlugin.getName() + ".nocd.*") ||
                    p.hasPermission(sPlugin.getShortName().toLowerCase() + ".nocd.*");
        }
    }


    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(cooldown.load(plugin, section, isPremiumLoading));
            errors.addAll(displayCooldownMessage.load(plugin, section, isPremiumLoading));
            errors.addAll(cancelEventIfInCooldown.load(plugin, section, isPremiumLoading));
            errors.addAll(cooldownMessage.load(plugin, section, isPremiumLoading));
            errors.addAll(isCooldownInTicks.load(plugin, section, isPremiumLoading));
        }
        return errors;
    }

    @Override
    public NewCooldownFeature clone(FeatureParentInterface newParent) {
        NewCooldownFeature clone = new NewCooldownFeature(newParent, getName(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), getSPlugin(), isEnableCooldownForOp());
        clone.setCooldown(cooldown.clone(clone));
        clone.setDisplayCooldownMessage(displayCooldownMessage.clone(clone));
        clone.setCancelEventIfInCooldown(cancelEventIfInCooldown.clone(clone));
        clone.setCooldownMessage(cooldownMessage.clone(clone));
        clone.setIsCooldownInTicks(isCooldownInTicks.clone(clone));
        clone.setCooldownId(cooldownId);
        return clone;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.cooldown.save(section);
        this.isCooldownInTicks.save(section);
        this.cooldownMessage.save(section);
        this.displayCooldownMessage.save(section);
        this.cancelEventIfInCooldown.save(section);
    }

    @Override
    public NewCooldownFeature getValue() {
        return this;
    }

    @Override
    public NewCooldownFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 5];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 5] = GUI.CLICK_HERE_TO_CHANGE;

        finalDescription[finalDescription.length - 4] = "&7Cooldown: &e" + getCooldown().getValue().get();

        if (isCooldownInTicks.getValue()) {
            finalDescription[finalDescription.length - 3] = "&7Cooldown in ticks: &a&l✔";
        } else {
            finalDescription[finalDescription.length - 3] = "&7Cooldown in ticks: &c&l✘";
        }
        if (displayCooldownMessage.getValue()) {
            finalDescription[finalDescription.length - 2] = "&7Display cooldown message: &a&l✔";
        } else {
            finalDescription[finalDescription.length - 2] = "&7Display cooldown message: &c&l✘";
        }
        if (cancelEventIfInCooldown.getValue()) {
            finalDescription[finalDescription.length - 1] = "&7Cancel event if in cooldown: &a&l✔";
        } else {
            finalDescription[finalDescription.length - 1] = "&7Cancel event if in cooldown: &c&l✘";
        }

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void reset() {
        this.cooldown = new IntegerFeature(this, "cooldown", Optional.of(0), "Cooldown", new String[]{"&7&oThe cooldown"}, GUI.CLOCK, false);
        this.isCooldownInTicks = new BooleanFeature(this, "isCooldownInTicks", false, "Cooldown in ticks", new String[]{"&7&oIs the cooldown in ticks?"}, Material.LEVER, false, false);
        this.cooldownMessage = new ColoredStringFeature(this, "cooldownMsg", Optional.of("&cYou are in cooldown ! &7(&e%time_H%&6H &e%time_M%&6M &e%time_S%&6S&7)"), "Cooldown Message", new String[]{"&7&oThe cooldown message"}, GUI.WRITABLE_BOOK, false, false);
        this.displayCooldownMessage = new BooleanFeature(this, "displayCooldownMessage", true, "Display Cooldown Message", new String[]{"&7&oDisplay the cooldown message"}, Material.LEVER, false, false);
        this.cancelEventIfInCooldown = new BooleanFeature(this, "cancelEventIfInCooldown", false, "Cancel Event If In Cooldown", new String[]{"&7&oCancel the event if the player is in cooldown?"}, Material.LEVER, false, false);
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(cooldown, isCooldownInTicks, cooldownMessage, displayCooldownMessage, cancelEventIfInCooldown));
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
            if (feature instanceof NewCooldownFeature && feature.getName().equals(getName())) {
                NewCooldownFeature coolodwn = (NewCooldownFeature) feature;
                coolodwn.setCooldown(this.cooldown);
                coolodwn.setIsCooldownInTicks(this.isCooldownInTicks);
                coolodwn.setCooldownMessage(this.cooldownMessage);
                coolodwn.setDisplayCooldownMessage(this.displayCooldownMessage);
                coolodwn.setCancelEventIfInCooldown(this.cancelEventIfInCooldown);
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
        NewCooldownFeatureEditorManager.getInstance().startEditing(player, this);
    }
}
