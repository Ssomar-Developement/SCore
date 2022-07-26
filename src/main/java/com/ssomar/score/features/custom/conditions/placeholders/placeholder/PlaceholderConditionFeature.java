package com.ssomar.score.features.custom.conditions.placeholders.placeholder;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.Comparator;
import com.ssomar.score.utils.NTools;
import com.ssomar.score.utils.PlaceholdersCdtType;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.ComparatorFeature;
import com.ssomar.score.features.types.PlaceholderConditionTypeFeature;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class PlaceholderConditionFeature extends FeatureWithHisOwnEditor<PlaceholderConditionFeature, PlaceholderConditionFeature, PlaceholderConditionFeatureEditor, PlaceholderConditionFeatureEditorManager> {

    private PlaceholderConditionTypeFeature type;
    private ComparatorFeature comparator;
    private ColoredStringFeature part1;
    private ColoredStringFeature part2;

    private BooleanFeature cancelEventIfNotValid;
    private ColoredStringFeature messageIfNotValid;
    private String id;

    public PlaceholderConditionFeature(FeatureParentInterface parent, String id) {
        super(parent, "placeholderCondition", "Placeholder Condition", new String[]{"&7&oA Placeholder condition with its options"}, GUI.WRITABLE_BOOK, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.type = new PlaceholderConditionTypeFeature(this, "type", Optional.of(PlaceholdersCdtType.PLAYER_NUMBER), "Type", new String[]{"&7&oThe type of the condition"}, Material.COMPASS, false);
        this.comparator = new ComparatorFeature(this, "comparator", Optional.of(Comparator.EQUALS), "Comparator", new String[]{"&7&oThe comparator of the condition"}, Material.COMPASS, false);
        this.part1 = new ColoredStringFeature(this, "part1", Optional.of("%player_health%"), "Part 1", new String[]{"&7&oThe first part of the condition"}, GUI.WRITABLE_BOOK, false, false);
        this.part2 = new ColoredStringFeature(this, "part2", Optional.of("10"), "Part 2", new String[]{"&7&oThe second part of the condition"}, GUI.WRITABLE_BOOK, false, false);

        this.cancelEventIfNotValid = new BooleanFeature(this, "cancelEventIfNotValid", false, "Cancel Event If Not Valid", new String[]{"&7&oCancel the event if the condition is not valid"}, Material.COMPASS, false, false);
        this.messageIfNotValid = new ColoredStringFeature(this, "messageIfNotValid", Optional.of(""), "Message If Not Valid", new String[]{"&7&oThe message to display if", "&7&othe condition is not valid"}, GUI.WRITABLE_BOOK, false, false);
    }

    public boolean verify(Player player, Player target) {
        return verify(player, target, null);
    }

    public boolean verify(Player player, Player target, @Nullable StringPlaceholder sp) {
        String aPart1 = "";
        String aPart2 = "";

        if (sp != null) {
            aPart1 = sp.replacePlaceholder(part1.getValue().get(), false);
            aPart2 = sp.replacePlaceholder(part2.getValue().get(), false);
        } else {
            aPart1 = part1.getValue().get();
            aPart2 = part2.getValue().get();
        }

        if (SCore.hasPlaceholderAPI) {
            // replace placeholders in first part
            if (PlaceholdersCdtType.getpCdtTypeWithPlayer().contains(type.getValue().get()) && player != null) {
                aPart1 = PlaceholderAPI.setPlaceholders(player, aPart1);
            } else if (target != null) aPart1 = PlaceholderAPI.setPlaceholders(target, aPart1);

            // replace placeholders in second part
            if (PlaceholdersCdtType.PLAYER_PLAYER.equals(type.getValue().get()) && player != null) {
                aPart2 = PlaceholderAPI.setPlaceholders(player, aPart2);
            } else if ((PlaceholdersCdtType.TARGET_TARGET.equals(type.getValue().get()) || PlaceholdersCdtType.PLAYER_TARGET.equals(type.getValue().get())) && target != null) {
                aPart2 = PlaceholderAPI.setPlaceholders(target, aPart2);
            }
        }

        // verification
        switch (type.getValue().get()) {

            case PLAYER_NUMBER:
            case TARGET_NUMBER:
                if (NTools.isNumber(aPart1)) {
                    double nPart1 = Double.parseDouble(aPart1);
                    double nPart2 = Double.parseDouble(aPart2);
                    if (!comparator.getValue().get().verify(nPart1, nPart2)) return false;
                } else return false;
                break;

            case PLAYER_STRING:
            case TARGET_STRING:
                if (!comparator.getValue().get().verify(aPart1, aPart2)) return false;
                break;

            case PLAYER_PLAYER:
            case TARGET_TARGET:
            case PLAYER_TARGET:
                if (NTools.isNumber(aPart1) && NTools.isNumber(aPart2)) {
                    double nPart1 = Double.parseDouble(aPart1);
                    double nPart2 = Double.parseDouble(aPart2);
                    if (!comparator.getValue().get().verify(nPart1, nPart2)) return false;
                } else if (!comparator.getValue().get().verify(aPart1, aPart2)) return false;
                break;

            default:
                break;
        }

        return true;

    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.type.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.comparator.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.part1.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.part2.load(plugin, enchantmentConfig, isPremiumLoading));
            if (PlaceholdersCdtType.getpCdtTypeWithNumber().contains(this.type.getValue().get())) {
                String verifNumber = part2.getValue().get();
                if (!verifNumber.contains("%")) {
                    if (!NTools.isNumber(verifNumber)) {
                        part2.setValue(Optional.of("0"));
                        errors.add("&cERROR, Couldn't load the Part2 Number value of " + this.getName() + " from config, value: " + verifNumber + " &7&o" + getParent().getParentInfo() + " &6>> It must be a placeholder or a number !");
                    }
                }
            }
            errors.addAll(this.messageIfNotValid.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.cancelEventIfNotValid.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Placeholder Condition with its options because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.type.save(attributeConfig);
        this.comparator.save(attributeConfig);
        this.part1.save(attributeConfig);
        this.part2.save(attributeConfig);
        this.cancelEventIfNotValid.save(attributeConfig);
        this.messageIfNotValid.save(attributeConfig);
    }

    @Override
    public PlaceholderConditionFeature getValue() {
        return this;
    }

    @Override
    public PlaceholderConditionFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 5];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 5] = "&7Type: &e" + type.getValue().get().name();
        finalDescription[finalDescription.length - 4] = "&7Comparator: &e" + comparator.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Part1: &e" + part1.getValue().get();
        finalDescription[finalDescription.length - 2] = "&7Part2: &e" + part2.getValue().get();
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public PlaceholderConditionFeature clone(FeatureParentInterface newParent) {
        PlaceholderConditionFeature eF = new PlaceholderConditionFeature(newParent, id);
        eF.setType(type.clone(eF));
        eF.setComparator(comparator.clone(eF));
        eF.setPart1(part1.clone(eF));
        eF.setPart2(part2.clone(eF));
        eF.setCancelEventIfNotValid(cancelEventIfNotValid.clone(eF));
        eF.setMessageIfNotValid(messageIfNotValid.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(type, comparator, part1, part2, cancelEventIfNotValid, messageIfNotValid));
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
            if (feature instanceof PlaceholderConditionFeature) {
                PlaceholderConditionFeature aFOF = (PlaceholderConditionFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setType(type);
                    aFOF.setComparator(comparator);
                    aFOF.setPart1(part1);
                    aFOF.setPart2(part2);
                    aFOF.setCancelEventIfNotValid(cancelEventIfNotValid);
                    aFOF.setMessageIfNotValid(messageIfNotValid);
                    break;
                }
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        PlaceholderConditionFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
