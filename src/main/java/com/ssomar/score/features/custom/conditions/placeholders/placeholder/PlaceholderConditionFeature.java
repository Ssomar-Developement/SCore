package com.ssomar.score.features.custom.conditions.placeholders.placeholder;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.commands.console.ConsoleCommandsFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.ComparatorFeature;
import com.ssomar.score.features.types.PlaceholderConditionTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.Comparator;
import com.ssomar.score.utils.emums.PlaceholdersCdtType;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
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

    private final static Boolean DEBUG = false;

    private PlaceholderConditionTypeFeature type;
    private ComparatorFeature comparator;
    private ColoredStringFeature part1;
    private ColoredStringFeature part2;

    private BooleanFeature cancelEventIfNotValid;
    private ColoredStringFeature messageIfNotValid;
    private ColoredStringFeature messageIfNotValidForTarget;
    private BooleanFeature stopCheckingOtherConditionsIfNotValid;
    private ConsoleCommandsFeature consoleCommandsIfError;
    private String id;


    public PlaceholderConditionFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.placeholderCondition);
        this.id = id;
        reset();
    }

    public static PlaceholderConditionFeature buildNull(){
        return new PlaceholderConditionFeature(null, "NULL");
    }

    @Override
    public void reset() {
        this.type = new PlaceholderConditionTypeFeature(this, Optional.of(PlaceholdersCdtType.PLAYER_NUMBER), FeatureSettingsSCore.type);
        this.comparator = new ComparatorFeature(this, Optional.of(Comparator.EQUALS), FeatureSettingsSCore.comparator);
        this.part1 = new ColoredStringFeature(this, Optional.of("%player_health%"), FeatureSettingsSCore.part1, false);
        this.part2 = new ColoredStringFeature(this, Optional.of("10"),FeatureSettingsSCore.part2, false);

        this.cancelEventIfNotValid = new BooleanFeature(this, false, FeatureSettingsSCore.cancelEventIfNotValid, false);
        this.messageIfNotValid = new ColoredStringFeature(this, Optional.of(""), FeatureSettingsSCore.messageIfNotValid, false);
        this.messageIfNotValidForTarget = new ColoredStringFeature(this, Optional.of(""), FeatureSettingsSCore.messageIfNotValidForTarget, false);
        this.stopCheckingOtherConditionsIfNotValid = new BooleanFeature(this,  true, FeatureSettingsSCore.stopCheckingOtherConditionsIfNotValid, false);
        this.consoleCommandsIfError = new ConsoleCommandsFeature(this, FeatureSettingsSCore.placeholderConditionCmds, true);
    }

    public boolean verify(Player player, Player target) {
        return verify(player, target, null);
    }

    public boolean verify(Player player, Player target, @Nullable StringPlaceholder sp) {
        String aPart1 = "";
        String aPart2 = "";

        PlaceholdersCdtType t = type.getValue().get();

        if (sp != null) {
            aPart1 = sp.replacePlaceholder(part1.getValue().get(), false);
            if(t == PlaceholdersCdtType.PLAYER_TARGET || t == PlaceholdersCdtType.PLAYER_PLAYER || t == PlaceholdersCdtType.TARGET_TARGET) aPart2 = sp.replacePlaceholder(part2.getValue().get(), false);
            else aPart2 = part2.getValue().get();
        } else {
            aPart1 = part1.getValue().get();
            aPart2 = part2.getValue().get();
        }

        if (SCore.hasPlaceholderAPI) {
            //SsomarDev.testMsg("PlaceholderConditionFeature verify() hasPlaceholderAPI", true);
            // replace placeholders in first part
            if (PlaceholdersCdtType.getpCdtTypeWithPlayer().contains(type.getValue().get()) && player != null) {
                //SsomarDev.testMsg("PlaceholderConditionFeature verify() getpCdtTypeWithPlayer", true);
                aPart1 = StringPlaceholder.replacePlaceholderOfPAPI(aPart1, player.getUniqueId());
            } else if (target != null){
                //SsomarDev.testMsg("PlaceholderConditionFeature verify() target != null", true);
                aPart1 = StringPlaceholder.replacePlaceholderOfPAPI(aPart1, target.getUniqueId());
            }

            // replace placeholders in second part
            if (PlaceholdersCdtType.PLAYER_PLAYER.equals(type.getValue().get()) && player != null) {
                aPart2 = StringPlaceholder.replacePlaceholderOfPAPI(aPart2, player.getUniqueId());
            } else if ((PlaceholdersCdtType.TARGET_TARGET.equals(type.getValue().get()) || PlaceholdersCdtType.PLAYER_TARGET.equals(type.getValue().get())) && target != null) {
                aPart2 = StringPlaceholder.replacePlaceholderOfPAPI(aPart2, target.getUniqueId());
            }

            /* Second time for the variables contains with papi placeholder in*/
            if (sp != null) {
                aPart1 = sp.replacePlaceholder(aPart1, false);
                if(t == PlaceholdersCdtType.PLAYER_TARGET || t == PlaceholdersCdtType.PLAYER_PLAYER || t == PlaceholdersCdtType.TARGET_TARGET) aPart2 = sp.replacePlaceholder(aPart2, false);
            }

            /* For IF block */
            if(t == PlaceholdersCdtType.PLAYER_PLAYER && player == null) {
                aPart1 = StringPlaceholder.replacePlaceholderOfPAPI(aPart1, null);
                aPart2 = StringPlaceholder.replacePlaceholderOfPAPI(aPart2, null);
            }
        }

        aPart1 = StringConverter.deconvertColor(aPart1);
        //SsomarDev.testMsg("aPart1: "+aPart1, true);
        aPart2 = StringConverter.deconvertColor(aPart2);

        // verification
        switch (t) {

            case PLAYER_NUMBER:
            case TARGET_NUMBER:
                try {
                    if (NTools.isNumber(aPart1)) {
                        double nPart1 = Double.parseDouble(aPart1);
                        double nPart2 = Double.parseDouble(aPart2);
                        if (!comparator.getValue().get().verify(nPart1, nPart2)){
                            SsomarDev.testMsg("false because> "+nPart1+" ?? "+nPart2, DEBUG);
                            return false;
                        }
                    } else return false;
                } catch (Exception e) {
                    Utils.sendConsoleMsg("&cSCore, error with the placeholder condition of the object &6&l"+getParentInfo()+" &ccondition: &7" + e.getMessage());
                    return false;
                }
                break;

            case PLAYER_STRING:
            case TARGET_STRING:
                if(aPart2.contains("<OR>")) {
                    List<String> list = new ArrayList<>(Arrays.asList(aPart2.split("<OR>")));
                    /** Trim strings **/
                    for(int i = 0; i < list.size(); i++) {
                        list.set(i, list.get(i).trim());
                    }
                    if (!comparator.getValue().get().verify(aPart1, list)) return false;
                }
                else if (!comparator.getValue().get().verify(aPart1, aPart2)){
                    //System.out.println("false because> "+aPart1+" ?? "+aPart2);
                    SsomarDev.testMsg("false because> "+aPart1+" ?? "+aPart2, DEBUG);
                    return false;
                }
                break;

            case PLAYER_PLAYER:
            case TARGET_TARGET:
            case PLAYER_TARGET:
                SsomarDev.testMsg(">>>>>>>>>> aPart1: "+aPart1+" aPart2: "+aPart2, DEBUG);
                if (NTools.isNumber(aPart1) && NTools.isNumber(aPart2)) {
                    SsomarDev.testMsg("aPart1: "+aPart1+" aPart2: "+aPart2, DEBUG);
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
                        part2.setValue("0");
                        errors.add("&cERROR, Couldn't load the Part2 Number value of " + this.getName() + " from config, value: " + verifNumber + " &7&o" + getParent().getParentInfo() + " &6>> It must be a placeholder or a number !");
                    }
                }
            }
            errors.addAll(this.messageIfNotValid.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.messageIfNotValidForTarget.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.cancelEventIfNotValid.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.stopCheckingOtherConditionsIfNotValid.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.consoleCommandsIfError.load(plugin, enchantmentConfig, isPremiumLoading));
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
        this.messageIfNotValidForTarget.save(attributeConfig);
        this.stopCheckingOtherConditionsIfNotValid.save(attributeConfig);
        this.consoleCommandsIfError.save(attributeConfig);
    }

    @Override
    public PlaceholderConditionFeature getValue() {
        return this;
    }

    @Override
    public PlaceholderConditionFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 6];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 6] = "&7Type: &e" + type.getValue().get().name();
        finalDescription[finalDescription.length - 5] = "&7Comparator: &e" + comparator.getValue().get();
        finalDescription[finalDescription.length - 4] = "&7Part1: &e" + part1.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Part2: &e" + part2.getValue().get();
        finalDescription[finalDescription.length - 2] = "&7Console commands If Error: &e"+consoleCommandsIfError.getCurrentValues().size();

        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
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
        eF.setMessageIfNotValidForTarget(messageIfNotValidForTarget.clone(eF));
        eF.setStopCheckingOtherConditionsIfNotValid(stopCheckingOtherConditionsIfNotValid.clone(eF));
        eF.setConsoleCommandsIfError(consoleCommandsIfError.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(type, comparator, part1, part2, cancelEventIfNotValid, messageIfNotValid, messageIfNotValidForTarget, stopCheckingOtherConditionsIfNotValid, consoleCommandsIfError));
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
                    aFOF.setMessageIfNotValidForTarget(messageIfNotValidForTarget);
                    aFOF.setStopCheckingOtherConditionsIfNotValid(stopCheckingOtherConditionsIfNotValid);
                    aFOF.setConsoleCommandsIfError(consoleCommandsIfError);
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

    @Override
    public String toString() {
        return "PlaceholderConditionFeature{" +
                "type=" + type +
                ", comparator=" + comparator +
                ", part1=" + part1 +
                ", part2=" + part2 +
                ", cancelEventIfNotValid=" + cancelEventIfNotValid +
                ", messageIfNotValid=" + messageIfNotValid +
                ", messageIfNotValidForTarget=" + messageIfNotValidForTarget +
                ", stopCheckingOtherConditionsIfNotValid=" + stopCheckingOtherConditionsIfNotValid +
                ", id='" + id + '\'' +
                '}';
    }

}
