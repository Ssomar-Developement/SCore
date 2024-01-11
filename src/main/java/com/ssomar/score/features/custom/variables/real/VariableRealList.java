package com.ssomar.score.features.custom.variables.real;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.custom.variables.base.variable.VariableFeature;
import com.ssomar.score.features.custom.variables.update.variable.VariableUpdateFeature;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.DynamicMeta;
import com.ssomar.score.utils.emums.VariableUpdateType;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.writerreader.WriterReaderPersistentDataContainer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VariableRealList extends VariableReal<List<String>> implements Serializable {

    private static final boolean DEBUG = false;

    public VariableRealList(VariableFeature<List<String>> config, ItemStack item, @NotNull DynamicMeta dMeta) {
        super(config, item, dMeta);
    }

    public VariableRealList(VariableFeature<List<String>> config, WriterReaderPersistentDataContainer writerReaderPersistentDataContainer) {
        super(config, writerReaderPersistentDataContainer);
    }

    public VariableRealList(VariableFeature<List<String>> config, ConfigurationSection configurationSection) {
        super(config, configurationSection);
    }

    @Override
    public Optional<List<String>> readValue(ItemStack item, DynamicMeta dMeta) {
        getItemKeyWriterReader().writeListIfNull((SPlugin) SCore.plugin, item, dMeta, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase(), (List<String>) getConfig().getDefaultValue());
        Optional<List<String>> value;
        value = getItemKeyWriterReader().readList(SCore.plugin, item, dMeta, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase());
        return value;
    }

    @Override
    public Optional<List<String>> readValue(WriterReaderPersistentDataContainer writerReaderPersistentDataContainer) {
        writerReaderPersistentDataContainer.writeListIfNull((SPlugin) SCore.plugin, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase(), (List<String>) getConfig().getDefaultValue());
        Optional<List<String>> value;
        value = writerReaderPersistentDataContainer.readList(SCore.plugin, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase());
        return value;
    }

    @Override
    public Optional<List<String>> readValue(ConfigurationSection configurationSection) {
        String varUpper = getConfig().getVariableName().getValue().get().toUpperCase();
        if (!configurationSection.contains(varUpper)) writeValue(configurationSection);
        return Optional.of(configurationSection.getStringList(varUpper));
    }

    @Override
    public void writeValue(ItemStack item, DynamicMeta dMeta) {
        getItemKeyWriterReader().writeList(SCore.plugin, item, dMeta, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase(), getValue());
    }

    @Override
    public void writeValue(WriterReaderPersistentDataContainer writerReaderPersistentDataContainer) {
        writerReaderPersistentDataContainer.writeList(SCore.plugin, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase(), getValue());
    }

    @Override
    public void writeValue(ConfigurationSection configurationSection) {
        String varUpper = getConfig().getVariableName().getValue().get().toUpperCase();
        configurationSection.set(varUpper, getValue());
    }

    public void modifVariable(VariableUpdateFeature update, Player p, StringPlaceholder sp) {
        SsomarDev.testMsg("VariableRealList.modifVariable", DEBUG);

        Optional<String> optional = update.getStringUpdate().getValue();
        if (!optional.isPresent()) return;
        String modificationString = optional.get();
        modificationString = sp.replacePlaceholder(modificationString, true);

        if (update.getType().getValue().get().equals(VariableUpdateType.LIST_ADD)) {
            List<String> list = getValue();
            list.add(modificationString);

            setValue(list);
        }
        else if (update.getType().getValue().get().equals(VariableUpdateType.LIST_REMOVE)) {
            List<String> list = getValue();
            list.remove(modificationString);
            setValue(list);
        } else if (update.getType().getValue().get().equals(VariableUpdateType.LIST_CLEAR)) {
            setValue(new ArrayList<>());
        }
        SsomarDev.testMsg("VariableRealList.modifVariable: " + getValue(), DEBUG);
    }

    @Override
    public void modifVariable(ItemStack item, @NotNull DynamicMeta dMeta, VariableUpdateFeature update, @Nullable Player p, @Nullable StringPlaceholder sp) {
        modifVariable(update, p, sp);
        writeValue(item, dMeta);
    }

    @Override
    public void modifVariable(WriterReaderPersistentDataContainer writerReaderPersistentDataContainer, VariableUpdateFeature update, @Nullable Player p, @Nullable StringPlaceholder sp) {
        modifVariable(update, p, sp);
        writeValue(writerReaderPersistentDataContainer);
    }

    @Override
    public void modifVariable(@NotNull ConfigurationSection configurationSection, VariableUpdateFeature update, @Nullable Player p, @Nullable StringPlaceholder sp) {
        modifVariable(update, p, sp);
        writeValue(configurationSection);
    }

    @Override
    public String replaceVariablePlaceholder(String s) {
        String toReplace = "%var_" + getConfig().getVariableName().getValue().get() + "%";
        if (s.contains(toReplace)) {
            while (getValue().contains("")){
                getValue().remove("");
            }
            s = StringPlaceholder.replaceCalculPlaceholder(s, toReplace, getValue() + "", false);
        }

        toReplace = "%var_" + getConfig().getVariableName().getValue().get() + "_contains_";
        if (s.contains(toReplace)) {
           String contains = s.split(toReplace)[1];
           if (contains.length() > 0 && contains.charAt(0) != '%'){
               contains = contains.split("%")[0];
                s = s.replace(toReplace+contains+"%", getValue().contains(contains) + "");
                //SsomarDev.testMsg("VariableRealList.replaceVariablePlaceholder:" + s+":", true);
           }
        }

        toReplace = "%var_" + getConfig().getVariableName().getValue().get() + "_size%";
        if (s.contains(toReplace)) {
            s = s.replace(toReplace, getValue().size() + "");
        }

        return s;
    }
}
