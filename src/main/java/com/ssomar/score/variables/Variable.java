package com.ssomar.score.variables;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.MaterialFeature;
import com.ssomar.score.features.types.VariableForFeature;
import com.ssomar.score.features.types.VariableTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.SProjectileEditor;
import com.ssomar.score.projectiles.SProjectileEditorManager;
import com.ssomar.score.sobject.SObjectWithFileEditable;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.VariableType;
import com.ssomar.score.variables.loader.VariablesLoader;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
@Setter
public class Variable extends SObjectWithFileEditable<Variable, SProjectileEditor, SProjectileEditorManager> {

    private VariableTypeFeature type;

    private VariableForFeature forFeature;

    private Map<String, List<String>> values;

    private ColoredStringFeature defaultValue;

    private MaterialFeature icon;

    public Variable(FeatureParentInterface parent, String id, String path) {
        super(id, parent, FeatureSettingsSCore.VARIABLE, path, VariablesLoader.getInstance());
        reset();
    }

    public Variable(String id, String path) {
        super(id, FeatureSettingsSCore.VARIABLE, path, VariablesLoader.getInstance());
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();

        for (FeatureInterface featureInterface : this.getFeatures()) {
            errors.addAll(featureInterface.load(plugin, config, isPremiumLoading));
        }

        if (config.isConfigurationSection("values")) {
            for (String key : config.getConfigurationSection("values").getKeys(false)) {
                if (type.getValue().get().equals(VariableType.LIST)) {
                    this.values.put(key, config.getStringList("values." + key));
                } else this.values.put(key, Arrays.asList(config.getString("values." + key)));
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
            //SsomarDev.testMsg("key: " + key, true);
            if (type.getValue().get().equals(VariableType.LIST) && !this.values.get(key).isEmpty()) {
                config.set("values." + key, this.values.get(key));
            } else if (!this.values.get(key).isEmpty())
                config.set("values." + key, this.values.get(key).get(0));
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
        defaultValue = new ColoredStringFeature(this, Optional.empty(), FeatureSettingsSCore.default_string, true);
        type = new VariableTypeFeature(this, Optional.of(VariableType.NUMBER), FeatureSettingsSCore.type, false);
        forFeature = new VariableForFeature(this, Optional.empty(), FeatureSettingsSCore.for_);
        values = new HashMap<>();
        icon = new MaterialFeature(this, Optional.of(Material.PAPER), FeatureSettingsSCore.icon);
    }

    @Override
    public Variable clone(FeatureParentInterface newParent) {
        Variable clone = new Variable(this, getId(), getPath());
        clone.setType(type.clone(clone));
        clone.setForFeature(forFeature.clone(clone));
        clone.setDefaultValue(defaultValue.clone(clone));
        clone.setValues(new HashMap<>(values));
        clone.setIcon(icon.clone(clone));
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<FeatureInterface>();
        features.add(type);
        features.add(forFeature);
        features.add(defaultValue);
        features.add(icon);
        return features;
    }

    @Override
    public String getParentInfo() {
        return "Variable: " + getId();
    }

    @Override
    public void reload() {
        if (getParent() instanceof Variable) {
            Variable variable = (Variable) getParent();
            variable.setType(type);
            variable.setForFeature(forFeature);
            variable.setDefaultValue(defaultValue);
            variable.setIcon(icon);
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


    public String getValuesStr() {
        StringBuilder sb = new StringBuilder();
        if (defaultValue.getValue().isPresent()) {
            sb.append("&7Default value: &f\n");
            sb.append("&e").append(defaultValue.getValue().get()).append("\n");
        }
        sb.append("&7Values: &f\n");
        for (String key : this.values.keySet()) {
            if (!key.equals("global")) {
                try {
                    UUID uuid = UUID.fromString(key);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                    sb.append("&e").append(player.getName()).append("&7: &a").append(this.values.get(key)).append("\n");
                } catch (Exception ignored) {
                }
            } else sb.append("&e").append(key).append("&7: &a").append(this.values.get(key)).append("\n");
        }
        return sb.toString();
    }

    public String getValue(Optional<OfflinePlayer> optPlayer, Optional<Integer> indexOpt) {
        if (forFeature.getValue().get().equals(VariableForEnum.PLAYER)) {
            if (optPlayer.isPresent()) {
                List<String> playerValues;
                if (values.containsKey(optPlayer.get().getUniqueId() + "") && (playerValues = values.get(optPlayer.get().getUniqueId() + "")).size() > indexOpt.orElse(0)) {
                    if (type.getValue().get().equals(VariableType.LIST)) {
                        if (indexOpt.isPresent()) {
                            return playerValues.get(indexOpt.get());
                        } else return playerValues.toString();
                    } else return playerValues.get(0);
                }
            }
        } else {
            List<String> globalValues;
            if (values.containsKey("global") && (globalValues = values.get("global")).size() > indexOpt.orElse(0)) {
                if (type.getValue().get().equals(VariableType.LIST)) {
                    if (indexOpt.isPresent()) {
                        return globalValues.get(indexOpt.get());
                    } else return globalValues.toString();
                } else return globalValues.get(0);
            }
        }
        if (defaultValue.getValue().isPresent()) return defaultValue.getValue().get();
        return "";
    }

    public int sizeValue(Optional<OfflinePlayer> optPlayer, Optional<Integer> indexOpt) {
        if (forFeature.getValue().get().equals(VariableForEnum.PLAYER)) {
            if (optPlayer.isPresent()) {
                List<String> playerValues;
                if (values.containsKey(optPlayer.get().getUniqueId() + "") && (playerValues = values.get(optPlayer.get().getUniqueId() + "")).size() > indexOpt.orElse(0)) {
                    if (type.getValue().get().equals(VariableType.LIST)) {
                        if (indexOpt.isPresent()) {
                            return playerValues.get(indexOpt.get()).length();
                        } else return playerValues.size();
                    } else return playerValues.get(0).length();
                }
            }
        } else {
            List<String> globalValues;
            if (values.containsKey("global") && (globalValues = values.get("global")).size() > indexOpt.orElse(0)) {
                if (type.getValue().get().equals(VariableType.LIST)) {
                    if (indexOpt.isPresent()) {
                        return globalValues.get(indexOpt.get()).length();
                    } else return globalValues.size();
                } else return globalValues.get(0).length();
            }
        }
        return 0;
    }

    public boolean containsValue(Optional<OfflinePlayer> optPlayer, String value) {
        if (forFeature.getValue().get().equals(VariableForEnum.PLAYER)) {
            if (optPlayer.isPresent()) {
                List<String> playerValues;
                if (values.containsKey(optPlayer.get().getUniqueId() + "") && (playerValues = values.get(optPlayer.get().getUniqueId() + "")).size() > 0) {
                    if (type.getValue().get().equals(VariableType.LIST)) {
                        return playerValues.contains(value);
                    } else return playerValues.get(0).contains(value);
                }
            }
        } else {
            List<String> globalValues;
            if (values.containsKey("global") && (globalValues = values.get("global")).size() > 0) {
                if (type.getValue().get().equals(VariableType.LIST)) {
                    return globalValues.contains(value);
                } else return globalValues.get(0).contains(value);
            }
        }
        return false;
    }

    public Optional<String> modifValue(Optional<OfflinePlayer> optPlayer, String value) {
        String actualValue = getValue(optPlayer, Optional.empty());
        if (type.getValue().get().equals(VariableType.NUMBER)) {
            try {
                if (actualValue.isEmpty()) {
                    actualValue = "0";
                }
                double actual = Double.parseDouble(actualValue);
                double modif = Double.parseDouble(value);
                double result = actual + modif;
                actualValue = String.valueOf(result);
            } catch (NumberFormatException e) {
                return Optional.of("§cThe value is not a number");
            }
        } else actualValue = actualValue + value;

        Optional<String> setErr = setValue(optPlayer, actualValue);
        if (setErr.isPresent()) return setErr;

        return Optional.empty();
    }

    public Optional<String> setValue(Optional<OfflinePlayer> optPlayer, String value) {

        if (type.getValue().get().equals(VariableType.NUMBER)) {
            try {
                Double.parseDouble(value);
            } catch (Exception e) {
                return Optional.of("§cThe value must be a number");
            }
        }

        if (forFeature.getValue().get().equals(VariableForEnum.PLAYER)) {
            if (optPlayer.isPresent()) {
                values.put(optPlayer.get().getUniqueId() + "", Arrays.asList(value));
            } else {
                return Optional.of("§cYou need to attribute the value of this variable to a player");
            }
        } else {
            values.put("global", Arrays.asList(value));
        }

        this.save(false);

        return Optional.empty();
    }

    public Optional<String> addValue(Optional<OfflinePlayer> optPlayer, String value, Optional<Integer> indexOpt) {

        if (type.getValue().get().equals(VariableType.LIST)) {
            if (forFeature.getValue().get().equals(VariableForEnum.PLAYER)) {
                if (optPlayer.isPresent()) {
                    List<String> actualValues = values.get(optPlayer.get().getUniqueId() + "");
                    if (actualValues == null) actualValues = new ArrayList<>();
                    actualValues.add(indexOpt.orElse(actualValues.size()), value);
                    values.put(optPlayer.get().getUniqueId() + "", actualValues);
                } else {
                    return Optional.of("§cYou need to attribute the value of this variable to a player");
                }
            } else {
                List<String> actualValues = values.get("global");
                if (actualValues == null) actualValues = new ArrayList<>();
                actualValues.add(indexOpt.orElse(actualValues.size()), value);
                values.put("global", actualValues);
            }
        } else {
            return Optional.of("§cTo add something your variable must be a list");
        }

        this.save(false);

        return Optional.empty();
    }

    public Optional<String> removeValue(Optional<OfflinePlayer> optPlayer, Optional<Integer> indexOpt, Optional<String> valueOpt) {

        if (type.getValue().get().equals(VariableType.LIST)) {
            if (forFeature.getValue().get().equals(VariableForEnum.PLAYER)) {
                if (optPlayer.isPresent()) {
                    List<String> actualValues = values.get(optPlayer.get().getUniqueId() + "");
                    try {
                        if (valueOpt.isPresent()) actualValues.remove(valueOpt.get());
                        else actualValues.remove((int) indexOpt.orElse(actualValues.size() - 1));
                    } catch (Exception ignored) {
                    }
                    values.put(optPlayer.get().getUniqueId() + "", actualValues);
                } else {
                    return Optional.of("§cYou need to attribute the remove of this variable to a player");
                }
            } else {
                List<String> actualValues = values.get("global");
                try {
                    if (valueOpt.isPresent()) actualValues.remove(valueOpt.get());
                    else actualValues.remove((int) indexOpt.orElse(actualValues.size() - 1));
                } catch (Exception ignored) {
                }
                values.put("global", actualValues);
            }
        } else {
            return Optional.of("§cTo remove something your variable must be a list");
        }

        this.save(false);

        return Optional.empty();
    }

    public Optional<String> clearValue(Optional<OfflinePlayer> optPlayer) {

        if (forFeature.getValue().get().equals(VariableForEnum.PLAYER)) {
            if (optPlayer.isPresent()) {
                values.remove(optPlayer.get().getUniqueId() + "");
            } else {
                return Optional.of("§cYou need to attribute the remove of this variable to a player");
            }
        } else {
            values.remove("global");
        }

        this.save(false);

        return Optional.empty();
    }

    @Override
    public ItemStack getIconItem() {
        return new ItemStack(icon.getValue().get());
    }
}
