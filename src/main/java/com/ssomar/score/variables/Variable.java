package com.ssomar.score.variables;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.custom.activators.group.ActivatorsFeature;
import com.ssomar.score.features.types.VariableForFeature;
import com.ssomar.score.features.types.VariableTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.SProjectileEditor;
import com.ssomar.score.projectiles.SProjectileEditorManager;
import com.ssomar.score.sobject.NewSObject;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.VariableType;
import com.ssomar.score.variables.loader.VariablesLoader;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Getter
@Setter
public class Variable extends NewSObject<Variable, SProjectileEditor, SProjectileEditorManager> {

    private String id;
    private String path;

    private VariableTypeFeature type;

    private VariableForFeature forFeature;

    private Map<String, String> values;

    public Variable(FeatureParentInterface parent, String id, String path) {
        super(parent, "VAR", "VAR", new String[]{}, GUI.WRITABLE_BOOK);
        this.id = id;
        this.path = path;
        reset();
    }

    public Variable(String id, String path) {
        super("VAR", "VAR", new String[]{}, GUI.WRITABLE_BOOK);
        this.id = id;
        this.path = path;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();

        for (FeatureInterface featureInterface : this.getFeatures()) {
            errors.addAll(featureInterface.load(plugin, config, isPremiumLoading));
        }

        if(config.isConfigurationSection("values")) {
            for (String key : config.getConfigurationSection("values").getKeys(false)) {
                this.values.put(key, config.getString("values."+key));
            }
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        for (String s : config.getKeys(false)) {
            config.set(s, null);
        }
        for (FeatureInterface feature : getFeatures()) {
            feature.save(config);
        }
        /* clear values*/
        config.set("values", null);

        for (String key : this.values.keySet()) {
            config.set("values."+key, this.values.get(key));
        }
    }

    @Override
    public Variable getValue() {
        return this;
    }

    @Override
    public Variable initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public void reset() {

        type = new VariableTypeFeature(this, "type", Optional.of(VariableType.NUMBER), "Type", new String[]{}, GUI.COMPARATOR, false);
        forFeature = new VariableForFeature(this, "for", Optional.empty(), "For", new String[]{}, GUI.COMPARATOR, false);
        values = new HashMap<>();
    }

    @Override
    public Variable clone(FeatureParentInterface newParent) {
        Variable clone = new Variable(this, id, path);
        clone.setType(type.clone(clone));
        clone.setForFeature(forFeature.clone(clone));
        clone.setValues(new HashMap<>(values));
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<FeatureInterface>();
        features.add(type);
        features.add(forFeature);
        return features;
    }

    @Override
    public String getParentInfo() {
        return "Variable: " + id;
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        File file = getFile();

        FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        return config;
    }

    @Override
    public File getFile() {
        File file = new File(path);
        if (!file.exists()) {
            try {
                new File(path).createNewFile();
                file = VariablesLoader.getInstance().searchFileOfObject(id);
            } catch (IOException ignored) {
                return null;
            }
        }
        return file;
    }

    @Override
    public void reload() {
        if (getParent() instanceof Variable) {
            Variable variable = (Variable) getParent();
            variable.setType(type);
            variable.setForFeature(forFeature);
            //SsomarDev.testMsg("RELOAD INTO "+variable.hashCode());
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        NewSObjectsManagerEditor.getInstance().startEditing(player, new VariablesEditor());
    }

    @Override
    public void openEditor(@NotNull Player player) {
        VariableEditorManager.getInstance().startEditing(player, this);
    }


    @Override
    public ActivatorsFeature getActivators() {
        return null;
    }

    @Override
    public Item dropItem(Location location, int amount) {
        return null;
    }

    @Override
    public ItemStack buildItem(int quantity, Optional<Player> creatorOpt) {
        // Useless here
        return new ItemStack(Material.STONE);
    }

    @Nullable
    @Override
    public NewSActivator getActivator(String actID) {
        return null;
    }

    @Override
    public List<String> getDescription() {
        List<String> description = new ArrayList<>();
        description.add("§7ID: §f" + id);
        description.add("§7Path: §f" + path);
        return description;
    }

    public String getValuesStr(){
        StringBuilder sb = new StringBuilder();
        for (String key : this.values.keySet()) {
            sb.append(key).append(":").append(this.values.get(key)).append("\n");
        }
        return sb.toString();
    }

    public String getValue(Optional<Player> optPlayer){
        if(forFeature.getValue().get().equals(VariableForEnum.PLAYER)){
            if(optPlayer.isPresent()){
                if(values.containsKey(optPlayer.get().getUniqueId()+"")){
                    return values.get(optPlayer.get().getUniqueId()+"");
                }
            }
        }
        else {
            if(values.containsKey("global")){
                return values.get("global");
            }
        }
        return "";
    }

    public Optional<String> modifValue(Optional<Player> optPlayer, String value){
        String actualValue = getValue(optPlayer);
        if(type.getValue().get().equals(VariableType.NUMBER)){
            try{
                if (actualValue.isEmpty()) {
                    actualValue = "0";
                }
                double actual = Double.parseDouble(actualValue);
                double modif = Double.parseDouble(value);
                double result = actual + modif;
                actualValue = String.valueOf(result);
            }
            catch (NumberFormatException e){
                return Optional.of("§cThe value is not a number");
            }
        }
        else actualValue = actualValue + value;

        Optional<String> setErr = setValue(optPlayer, actualValue);
        if (setErr.isPresent()) return setErr;

        return Optional.empty();
    }

    public Optional<String> setValue(Optional<Player> optPlayer, String value){

        if(type.getValue().get().equals(VariableType.NUMBER)){
            try{
                Double.parseDouble(value);
            }catch (Exception e){
                return Optional.of("§cThe value must be a number");
            }
        }

        if(forFeature.getValue().get().equals(VariableForEnum.PLAYER)){
            if(optPlayer.isPresent()){
                values.put(optPlayer.get().getUniqueId()+"", value);
            }
            else {
                return Optional.of("§cYou attribute the value of this variable to a player");
            }
        }
        else {
            values.put("global", value);
        }

        this.save();

        return Optional.empty();
    }
}
