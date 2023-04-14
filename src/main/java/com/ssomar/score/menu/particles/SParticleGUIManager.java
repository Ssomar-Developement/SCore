package com.ssomar.score.menu.particles;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sparticles.SParticle;
import com.ssomar.score.sparticles.SParticles;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SParticleGUIManager extends GUIManagerSCore<SParticleGUI> {

    private static SParticleGUIManager instance;

    public static SParticleGUIManager getInstance() {
        if (instance == null) instance = new SParticleGUIManager();
        return instance;
    }

    public void startEditing(Player p, SPlugin sPlugin, SParticles sParticles, GUI guiFrom) {
        cache.put(p, new SParticleGUI(sPlugin, sParticles, guiFrom));
        cache.get(p).openGUISync(p);
    }

    public void startEditing(Player p, SPlugin sPlugin, SParticles sParticles, SParticle sParticle, GUI guiFrom) {
        cache.put(p, new SParticleGUI(sPlugin, sParticles, sParticle, guiFrom));
        cache.get(p).openGUISync(p);
    }

    @Override
    public boolean allClicked(InteractionClickedGUIManager<SParticleGUI> i) {
        if (i.name.contains(SParticleGUI.AMOUNT)) {
            requestWriting.put(i.player, SParticleGUI.AMOUNT);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&a&l" + i.sPlugin.getNameDesign() + " &aEnter an amount (min 1):"));
            space(i.player);
        } else if (i.name.contains(SParticleGUI.DELAY)) {
            requestWriting.put(i.player, SParticleGUI.DELAY);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&a&l" + i.sPlugin.getNameDesign() + " &aEnter a delay (min 1, in ticks):"));
            space(i.player);
        } else if (i.name.contains(SParticleGUI.OFFSET)) {
            requestWriting.put(i.player, SParticleGUI.OFFSET);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&a&l" + i.sPlugin.getNameDesign() + " &aEnter the offset (range) of the particle (min 0.000001, Double):"));
            space(i.player);
        } else if (i.name.contains(SParticleGUI.SPEED)) {
            requestWriting.put(i.player, SParticleGUI.SPEED);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&a&l" + i.sPlugin.getNameDesign() + " &aEnter a speed (min 0.000001, Double):"));
            space(i.player);
        }
        if (i.name.contains(SParticleGUI.BLOCK_TYPE)) {
            requestWriting.put(i.player, SParticleGUI.BLOCK_TYPE);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&a&l &aEnter the material of the block particle:"));
            space(i.player);
        } else if (i.name.contains("Save") || i.name.contains("Create this particle")) {
            saveTheConfiguration(i.player);
            SParticles sParticles = cache.get(i.player).getSParticles();
            GUI guiFrom = cache.get(i.player).getGuiFrom();
            cache.remove(i.player);
            requestWriting.remove(i.player);
            SParticlesGUIManager.getInstance().startEditing(i.player, i.sPlugin, sParticles, guiFrom);
        } else if (i.name.contains("Back")) {
            SParticlesGUIManager.getInstance().startEditing(i.player, i.sPlugin, cache.get(i.player).getSParticles(), cache.get(i.player).getGuiFrom());
        } else return false;

        return true;
    }

    public void receivedMessage(Player p, String message) {
        boolean notExit = true;

        SPlugin sPlugin = cache.get(p).getsPlugin();
        //SObject sObject = cache.get(p).getSObject();
        //SActivator sAct = cache.get(p).getSAct();
        String plName = sPlugin.getNameDesign();


        if (requestWriting.get(p).equals(SParticleGUI.AMOUNT)) {

            boolean error = true;
            if (!message.replaceAll(" ", "").isEmpty()) {
                try {
                    if (Integer.parseInt(message) > 0) {
                        cache.get(p).updateInt(SParticleGUI.AMOUNT, Integer.parseInt(message));
                        cache.get(p).openGUISync(p);
                        requestWriting.remove(p);
                        error = false;
                    }
                } catch (Exception ignored) {
                }
            }
            if (error)
                p.sendMessage(StringConverter.coloredString("&c&l" + plName + " &cError invalid amount pls select an amount > 0 !"));
        } else if (requestWriting.get(p).equals(SParticleGUI.DELAY)) {

            boolean error = true;
            if (!message.replaceAll(" ", "").isEmpty()) {
                try {
                    if (Integer.parseInt(message) > 0) {
                        cache.get(p).updateInt(SParticleGUI.DELAY, Integer.parseInt(message));
                        cache.get(p).openGUISync(p);
                        requestWriting.remove(p);
                        error = false;
                    }
                } catch (Exception ignored) {
                }
            }
            if (error)
                p.sendMessage(StringConverter.coloredString("&c&l" + plName + " &cError invalid amount pls select a delay > 0 !"));
        } else if (requestWriting.get(p).equals(SParticleGUI.SPEED)) {

            boolean error = true;
            if (!message.replaceAll(" ", "").isEmpty()) {
                try {
                    if (Double.parseDouble(message) > 0) {
                        cache.get(p).updateDouble(SParticleGUI.SPEED, Double.parseDouble(message));
                        cache.get(p).openGUISync(p);
                        requestWriting.remove(p);
                        error = false;
                    }
                } catch (Exception ignored) {
                }
            }
            if (error)
                p.sendMessage(StringConverter.coloredString("&c&l" + plName + " &cError invalid amount pls select a speed > 0 !"));
        } else if (requestWriting.get(p).equals(SParticleGUI.OFFSET)) {

            boolean error = true;
            if (!message.replaceAll(" ", "").isEmpty()) {
                try {
                    if (Double.parseDouble(message) > 0) {
                        cache.get(p).updateDouble(SParticleGUI.OFFSET, Double.parseDouble(message));
                        cache.get(p).openGUISync(p);
                        requestWriting.remove(p);
                        error = false;
                    }
                } catch (Exception ignored) {
                }
            }
            if (error)
                p.sendMessage(StringConverter.coloredString("&c&l" + plName + " &cError invalid amount pls select an offset (range) > 0 !"));
        } else if (requestWriting.get(p).equals(SParticleGUI.BLOCK_TYPE)) {

            boolean error = true;
            if (!message.replaceAll(" ", "").isEmpty()) {
                try {
                    cache.get(p).updateMaterial(Material.valueOf(StringConverter.decoloredString(message).trim().toUpperCase()));
                    cache.get(p).openGUISync(p);
                    requestWriting.remove(p);
                    error = false;
                } catch (Exception ignored) {
                }
            }
            if (error)
                p.sendMessage(StringConverter.coloredString("&c&l" + plName + " &cError invalid material pls select a good material !  https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html"));
        }

    }

    @Override
    public void saveTheConfiguration(Player p) {
        SParticle sParticle = new SParticle(cache.get(p).getActually(SParticleGUI.ID));
        sParticle.setParticlesAmount(cache.get(p).getInt(SParticleGUI.AMOUNT));
        sParticle.setParticlesDelay(cache.get(p).getInt(SParticleGUI.DELAY));
        sParticle.setParticlesType(cache.get(p).getType());
        sParticle.setParticlesSpeed(cache.get(p).getDouble(SParticleGUI.SPEED));
        sParticle.setParticlesOffSet(cache.get(p).getDouble(SParticleGUI.OFFSET));
        if (sParticle.canHaveBlocktype())
            sParticle.setBlockType(cache.get(p).getMaterial());
        if (sParticle.canHaveRedstoneColor())
            sParticle.setRedstoneColor(cache.get(p).getColor());

        SParticles sParticles = cache.get(p).getSParticles();
        sParticles.updateParticle(sParticle);
        sParticles.save();
    }

    @Override
    public boolean noShiftclicked(InteractionClickedGUIManager<SParticleGUI> i) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(InteractionClickedGUIManager<SParticleGUI> i) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(InteractionClickedGUIManager<SParticleGUI> i) {
        return false;
    }

    @Override
    public boolean shiftClicked(InteractionClickedGUIManager<SParticleGUI> i) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(InteractionClickedGUIManager<SParticleGUI> i) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(InteractionClickedGUIManager<SParticleGUI> i) {
        return false;
    }

    @Override
    public boolean leftClicked(InteractionClickedGUIManager<SParticleGUI> i) {
        if (i.name.contains(SParticleGUI.TYPE)) {
            cache.get(i.player).updateType(cache.get(i.player).nextType(cache.get(i.player).getType()));
            return true;
        } else if (i.name.contains(SParticleGUI.REDSTONE_COLOR)) {
            cache.get(i.player).updateColor(cache.get(i.player).nextColor(cache.get(i.player).getColor()));
            return true;
        }
        return false;
    }

    @Override
    public boolean rightClicked(InteractionClickedGUIManager<SParticleGUI> i) {
        if (i.name.contains(SParticleGUI.TYPE)) {
            cache.get(i.player).updateType(cache.get(i.player).prevType(cache.get(i.player).getType()));
            return true;
        } else if (i.name.contains(SParticleGUI.REDSTONE_COLOR)) {
            cache.get(i.player).updateColor(cache.get(i.player).prevColor(cache.get(i.player).getColor()));
            return true;
        }
        return false;
    }
}
