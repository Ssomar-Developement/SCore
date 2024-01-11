package com.ssomar.score.features.custom.variables.real;

import com.ssomar.executableitems.ExecutableItems;
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
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class VariableRealDouble extends VariableReal<Double> implements Serializable {

    private static final boolean DEBUG = false;

    public VariableRealDouble(VariableFeature<Double> config, ItemStack item, @NotNull DynamicMeta dMeta) {
        super(config, item, dMeta);
    }

    public VariableRealDouble(VariableFeature<Double> config, WriterReaderPersistentDataContainer writerReaderPersistentDataContainer) {
        super(config, writerReaderPersistentDataContainer);
    }

    public VariableRealDouble(VariableFeature<Double> config, ConfigurationSection configurationSection) {
        super(config, configurationSection);
    }

    @Override
    public Optional<Double> readValue(ItemStack item, DynamicMeta dMeta) {
        getItemKeyWriterReader().writeDoubleIfNull((SPlugin) SCore.plugin, item, dMeta, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase(), (Double) getConfig().getDefaultValue());
        Optional<Double> value;
        Optional<Double> potentialOldEIValue;
        if (SCore.hasExecutableItems && (potentialOldEIValue = getItemKeyWriterReader().readDouble(ExecutableItems.plugin, item, dMeta, "EI-" + getConfig().getVariableName().getValue().get().toUpperCase())).isPresent()) {
            value = potentialOldEIValue;
            setValue(value.get());
            writeValue(item, dMeta);
        } else
            value = getItemKeyWriterReader().readDouble(SCore.plugin, item, dMeta, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase());
        return value;
    }

    @Override
    public Optional<Double> readValue(WriterReaderPersistentDataContainer writerReaderPersistentDataContainer) {
        writerReaderPersistentDataContainer.writeDoubleIfNull((SPlugin) SCore.plugin, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase(), (Double) getConfig().getDefaultValue());
        Optional<Double> value;
        Optional<Double> potentialOldEIValue;
        if (SCore.hasExecutableItems && (potentialOldEIValue = writerReaderPersistentDataContainer.readDouble(ExecutableItems.plugin, "EI-" + getConfig().getVariableName().getValue().get().toUpperCase())).isPresent()) {
            value = potentialOldEIValue;
            setValue(value.get());
            writeValue(writerReaderPersistentDataContainer);
        } else
            value = writerReaderPersistentDataContainer.readDouble(SCore.plugin, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase());
        return value;
    }

    @Override
    public Optional<Double> readValue(ConfigurationSection configurationSection) {
        String varUpper = getConfig().getVariableName().getValue().get().toUpperCase();
        if (!configurationSection.contains(varUpper)) writeValue(configurationSection);
        return Optional.ofNullable(configurationSection.getDouble(varUpper));
    }

    @Override
    public void writeValue(ItemStack item, DynamicMeta dMeta) {
        getItemKeyWriterReader().writeDouble(SCore.plugin, item, dMeta, "SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase(), getValue());
    }

    @Override
    public void writeValue(WriterReaderPersistentDataContainer writerReaderPersistentDataContainer) {
        writerReaderPersistentDataContainer.writeDouble(SCore.plugin,"SCORE-" + getConfig().getVariableName().getValue().get().toUpperCase(), getValue());
    }

    @Override
    public void writeValue(ConfigurationSection configurationSection) {
        String varUpper = getConfig().getVariableName().getValue().get().toUpperCase();
        configurationSection.set(varUpper, getValue());
    }

    public void modifVariable(VariableUpdateFeature update, Player p, StringPlaceholder sp) {
        SsomarDev.testMsg("VariableRealDouble.modifVariable", DEBUG);

        UUID uuid = null;
        if (p != null) uuid = p.getUniqueId();

        Optional<Double> optional = update.getDoubleUpdate().getValue(uuid, sp);
        if (!optional.isPresent()) return;
        double modificationDouble = optional.get();

        if (update.getType().getValue().get().equals(VariableUpdateType.MODIFICATION)) {
            // BigDecimal ? To resolve a double issue: https://stackoverflow.com/questions/15625556/adding-and-subtracting-doubles-are-giving-strange-results
            BigDecimal calcul = BigDecimal.valueOf(getValue());
            calcul = calcul.add(BigDecimal.valueOf(modificationDouble));

            setValue(calcul.doubleValue());
        } else setValue(modificationDouble);
        SsomarDev.testMsg("VariableRealDouble.modifVariable: " + getValue(), DEBUG);
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
            s = StringPlaceholder.replaceCalculPlaceholder(s, toReplace, getValue() + "", false);
        }
        String toReplace2 = "%var_" + getConfig().getVariableName().getValue().get() + "_int%";
        if (s.contains(toReplace2)) {
            s = StringPlaceholder.replaceCalculPlaceholder(s, toReplace2, (getValue().intValue()) + "", true);
        }
        String toReplace3 = "%var_" + getConfig().getVariableName().getValue().get() + "_roman%";
        if (s.contains(toReplace3)) {
            s = StringPlaceholder.replaceCalculPlaceholder(s, toReplace3, (getValue().intValue()) + "", true, true);
        }
        return s;
    }
}
