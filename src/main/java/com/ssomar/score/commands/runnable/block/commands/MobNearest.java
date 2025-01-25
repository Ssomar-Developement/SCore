package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/* MOB_AROUND {distance} {Your commands here} */
public class MobNearest extends BlockCommand implements FeatureParentInterface {

    public MobNearest(){
        setCanExecuteCommands(true);
    }

    public static void mobAroundExecution(Location location, boolean forceMute, List<String> args, ActionInfo aInfo) {

        List<String> verifyArgs = new ArrayList<>(args);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                double distance = Double.valueOf(verifyArgs.get(0));

                Entity e = location.getWorld().getEntities().stream()
                        .filter(p -> !(p instanceof Player) && p instanceof Mob)
                        .min(Comparator.comparingDouble((p) -> p.getLocation().distanceSquared(location)))
                        .orElse(null);

                if (e == null || e.getLocation().distance(location) > distance) {
                    return;
                }
                List<Entity> targets = new ArrayList<>();
                targets.add(e);

                CommmandThatRunsCommand.runEntityCommands(targets, verifyArgs.subList(1, verifyArgs.size()), aInfo);
            }
        };
        SCore.schedulerHook.runLocationTask(runnable, location, 0);
    }

    @Override
    public void run(@Nullable Player p, @NotNull Block block, SCommandToExec sCommandToExec) {
        mobAroundExecution(block.getLocation(), false, sCommandToExec.getOtherArgs(), sCommandToExec.getActionInfo());
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MOB_NEAREST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MOB_NEAREST {max distance} {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return null;
    }

    @Override
    public FeatureInterface getFeature(FeatureSettingsInterface featureSettings) {
        return null;
    }

    @Override
    public FeatureInterface getFeatureWithName(String featureName) {
        return null;
    }

    @Override
    public String getParentInfo() {
        return null;
    }

    @Override
    public @Nullable FeatureParentInterface getParent() {
        return null;
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return null;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public void reload() {

    }

    @Override
    public void openEditor(@NotNull Player player) {

    }

    @Override
    public void openBackEditor(@NotNull Player player) {

    }

    @Override
    public void save() {

    }

    @Override
    public boolean isPremium() {
        return false;
    }

    @Override
    public FeatureSettingsInterface getFeatureSettings() {
        return null;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        return List.of();
    }

    @Override
    public void save(ConfigurationSection config) {

    }

    @Override
    public void writeInFile(ConfigurationSection config) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getEditorName() {
        return "";
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public FeatureInterface initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void initAndUpdateItemParentEditor(GUI gui, int slot) {

    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean isRequirePremium() {
        return false;
    }

    @Override
    public FeatureInterface clone(FeatureParentInterface newParent) {
        return null;
    }
}
