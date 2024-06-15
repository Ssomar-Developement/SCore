package com.ssomar.score.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
@Setter
public class ParticleTypeFeature extends FeatureAbstract<Optional<Particle>, ParticleTypeFeature> implements FeatureRequireClicksOrOneMessageInEditor {

    private Optional<Particle> value;
    private Optional<Particle> defaultValue;

    public ParticleTypeFeature(FeatureParentInterface parent, Optional<Particle> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            Particle particle = Particle.valueOf(colorStr);
            value = Optional.ofNullable(particle);
            FeatureReturnCheckPremium<Particle> checkPremium = checkPremium("Particle", particle, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            if(!colorStr.equals("NULL")) errors.add("&cERROR, Couldn't load the Particle value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Particles available: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html");
            value = Optional.empty();
        }
        return errors;
    }


    @Override
    public void save(ConfigurationSection config) {
        Optional<Particle> value = getValue();
        value.ifPresent(material -> config.set(this.getName(), material.name()));
    }

    @Override
    public Optional<Particle> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public ParticleTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 3] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 2] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";
        finalDescription[finalDescription.length - 1] = "&8>> &6Type manually: &eMIDDLE &a(Creative only)";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<Particle> value = getValue();
        Particle finalValue = value.orElse(Particle.CLOUD);
        updateParticle(finalValue, gui);
    }

    @Override
    public ParticleTypeFeature clone(FeatureParentInterface newParent) {
        ParticleTypeFeature clone = new ParticleTypeFeature(newParent, getDefaultValue(), getFeatureSettings());
        clone.value = value;
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        return;
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
        Particle particle = getParticle((GUI) manager.getCache().get(editor));
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        particle = nextParticle(particle);
        updateParticle(particle, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        Particle particle = getParticle((GUI) manager.getCache().get(editor));
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        particle = prevParticle(particle);
        updateParticle(particle, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateParticle(nextParticle(getParticle((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateParticle(prevParticle(getParticle((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public Particle nextParticle(Particle particle) {
        boolean next = false;
        for (Particle check : getSortParticles()) {
            if (check.equals(particle)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortParticles().get(0);
    }

    public Particle prevParticle(Particle particle) {
        int i = -1;
        int cpt = 0;
        for (Particle check : getSortParticles()) {
            if (check.equals(particle)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortParticles().get(getSortParticles().size() - 1);
        else return getSortParticles().get(cpt - 1);
    }

    public void updateParticle(Particle particle, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        value = Optional.of(particle);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 4);
        boolean find = false;
        for (Particle check : getSortParticles()) {
            if (particle.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + particle.name()));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (Particle check : getSortParticles()) {
            if (lore.size() == 17) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public Particle getParticle(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return Particle.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<Particle> getSortParticles() {
        SortedMap<String, Particle> map = new TreeMap<String, Particle>();
        for (Particle l : Particle.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        manager.requestWriting.put(editor, getEditorName());
        /* Close inventory sync */
        BukkitRunnable runnable = new BukkitRunnable() {
            public void run() {
                editor.closeInventory();
            }
        };
        runnable.runTask(SCore.plugin);
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter the particle or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI) manager.getCache().get(editor)).getCurrently(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current particle")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new particle")).create()));

        TextComponent noValue = new TextComponent(StringConverter.coloredString("&c&l[NO VALUE / EXIT]"));
        noValue.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/score interact NO VALUE / EXIT"));
        noValue.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&cClick here to exit or don't set a value")).create()));


        message.addExtra(new TextComponent(" "));
        message.addExtra(edit);
        message.addExtra(new TextComponent(" "));
        message.addExtra(newName);
        message.addExtra(new TextComponent(" "));
        message.addExtra(noValue);

        editor.spigot().sendMessage(message);
        space(editor);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        try {
            Particle.valueOf(StringConverter.decoloredString(message).trim().toUpperCase());
        } catch (Exception e) {
            return Optional.of(StringConverter.coloredString("&4&l[ERROR] &cThe message you entered is not a particle"));
        }
        return Optional.empty();
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        this.value = Optional.of(Particle.valueOf(StringConverter.decoloredString(message).trim().toUpperCase()));
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    @Override
    public void finishEditInEditorNoValue(Player editor, NewGUIManager manager) {
        this.value = Optional.empty();
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
