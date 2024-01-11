package com.ssomar.score.features.custom.variables.real;

import com.ssomar.score.features.custom.variables.base.variable.VariableFeature;
import com.ssomar.score.features.custom.variables.update.variable.VariableUpdateFeature;
import com.ssomar.score.utils.DynamicMeta;
import com.ssomar.score.utils.itemwriter.ItemKeyWriterReader;
import com.ssomar.score.utils.itemwriter.ItemKeyWriterReaderManager;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.writerreader.WriterReaderPersistentDataContainer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Getter
public abstract class VariableReal<T> {

    private VariableFeature config;
    @Setter
    private T value;
    private NamespacedKey key;
    private final ItemKeyWriterReader itemKeyWriterReader;

    public VariableReal(VariableFeature<T> config, ItemStack item, @NotNull DynamicMeta dMeta) {
        this.config = config;
        this.itemKeyWriterReader = ItemKeyWriterReaderManager.getInstance().getItemKeyWriterReader();


        Optional<T> optional = readValue(item, dMeta);
        if (optional.isPresent()) value = optional.get();
        else value = config.getDefaultValue();
    }

    public VariableReal(VariableFeature<T> config, WriterReaderPersistentDataContainer dataContainer) {
        this.config = config;
        this.itemKeyWriterReader = ItemKeyWriterReaderManager.getInstance().getItemKeyWriterReader();

        Optional<T> optional = readValue(dataContainer);
        if (optional.isPresent()) value = optional.get();
        else value = config.getDefaultValue();
    }

    public VariableReal(VariableFeature<T> config, ConfigurationSection configurationSection) {
        this.config = config;
        this.itemKeyWriterReader = ItemKeyWriterReaderManager.getInstance().getItemKeyWriterReader();

        Optional<T> optional = readValue(configurationSection);
        if (optional.isPresent()) value = optional.get();
        else value = config.getDefaultValue();
    }

    public String toString(){
        return value.toString();
    }

    public abstract Optional<T> readValue(ItemStack item, DynamicMeta dMeta);

    public abstract Optional<T> readValue(WriterReaderPersistentDataContainer dataContainer);

    public abstract Optional<T> readValue(ConfigurationSection configurationSection);

    public abstract void writeValue(ItemStack item, DynamicMeta dMeta);

    public abstract void writeValue(WriterReaderPersistentDataContainer dataContainer);

    public abstract void writeValue(ConfigurationSection configurationSection);

    public abstract void modifVariable(ItemStack item, @NotNull DynamicMeta dMeta, VariableUpdateFeature update, @Nullable Player p, @Nullable StringPlaceholder sp);

    public abstract void modifVariable(WriterReaderPersistentDataContainer dataContainer, VariableUpdateFeature update, @Nullable Player p, @Nullable StringPlaceholder sp);

    public abstract void modifVariable(@NotNull ConfigurationSection configurationSection, VariableUpdateFeature update, @Nullable Player p, @Nullable StringPlaceholder sp);

    public abstract String replaceVariablePlaceholder(String s);

}
