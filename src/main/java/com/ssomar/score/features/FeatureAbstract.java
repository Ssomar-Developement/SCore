package com.ssomar.score.features;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.scheduler.RunnableManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.Optional;

@Getter
public abstract class FeatureAbstract<T, Y extends FeatureInterface<T, Y>> implements FeatureInterface<T, Y>, Serializable {

    private final String name;
    private final String editorName;
    private final String[] editorDescription;
    private final Material editorMaterial;
    private final boolean requirePremium;
    @Setter
    private transient FeatureParentInterface parent;
    @Setter
    private boolean isPremium;

    private boolean saveLaunched;

    public FeatureAbstract(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        this.parent = parent;
        if (parent == null && (this instanceof FeatureParentInterface)) {
            this.parent = (FeatureParentInterface) this;
        }
        this.name = name;
        this.editorName = editorName;
        this.editorDescription = editorDescription;
        this.editorMaterial = editorMaterial;
        this.requirePremium = requirePremium;
        if (parent != null) {
            this.isPremium = parent.isPremium();
        } else this.isPremium = true;
    }

    public static void space(Player p) {
        p.sendMessage("");
    }

    public <M> FeatureReturnCheckPremium<M> checkPremium(String featureTypeName, M value, Optional<M> defaultValue, boolean isPremiumLoading) {
        if (requirePremium() && !isPremiumLoading) {
            if (defaultValue.isPresent()) {
                if (!defaultValue.get().equals(value)) {
                    return new FeatureReturnCheckPremium<>("&cERROR, Couldn't load the " + featureTypeName + " value of " + this.getName() + " from config, value: " + value + " &7&o" + getParent().getParentInfo() + " &6>> Because it's a premium feature ! (reset to default: " + defaultValue.get() + ")", defaultValue.get());
                }
            } else {
                return new FeatureReturnCheckPremium<>("&cERROR, Couldn't load the " + featureTypeName + " value of " + this.getName() + " from config, value: " + value + " &7&o" + getParent().getParentInfo() + " &6>> Because it's a premium feature !", null);
            }
        }
        return new FeatureReturnCheckPremium<>();
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.equals(editorName);
    }

    public void initAndUpdateItemParentEditor(GUI gui, int slot) {
        initItemParentEditor(gui, slot).updateItemParentEditor(gui);
    }

    /* Save will be make with a delay of 2 minutes minimum */
    public void save(boolean saveInstantly) {
        long delay = 20 * 120;
        if (saveInstantly) delay = 0;

        if (!saveLaunched || saveInstantly) {
            saveLaunched = true;
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    ConfigurationSection config = parent.getConfigurationSection();
                    save(config);
                    while (config.getParent() != null) {
                        config = config.getParent();
                    }
                    writeInFile(config);
                    saveLaunched = false;
                    RunnableManager.getInstance().removeTask(this);
                }
            };
            if (delay == 0) runnable.run();
            else {
                RunnableManager.getInstance().addTask(runnable);
                SCore.schedulerHook.runTask(runnable, delay);
            }
        }
    }

    public void save() {
        save(true);
    }


    public void writeInFile(ConfigurationSection config) {
        try {
            File file = parent.getFile();
            if (file.exists()) {
                Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

                try {
                    writer.write(((FileConfiguration) config).saveToString());
                } finally {
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean requirePremium() {
        return requirePremium;
    }

    public abstract Y clone(FeatureParentInterface newParent);

    public FeatureParentInterface getParent() {
        //System.out.println("passe getParent > "+(this instanceof FeatureParentInterface)+ " >> "+(parent == this));
        if (this instanceof FeatureParentInterface && parent == this) return (FeatureParentInterface) this;
        else return parent;
    }

    public <B> Optional<B> getParent(B clazz) {
        FeatureParentInterface parent = getParent();
        while (parent != null && parent instanceof FeatureInterface && ((FeatureAbstract) parent).getParent() != parent) {
            Class classs = ((Object) clazz).getClass();
            if (parent.getClass() == classs) {
                return Optional.of((B) parent);
            } else {
                parent = ((FeatureAbstract) parent).getParent();
                parent.reload();
            }
        }
        return Optional.empty();
    }
}
