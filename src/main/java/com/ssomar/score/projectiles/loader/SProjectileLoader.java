package com.ssomar.score.projectiles.loader;

import com.ssomar.score.SCore;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import com.ssomar.score.sobject.NewSObjectLoader;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.util.*;

public class SProjectileLoader extends NewSObjectLoader<SProjectile> {

    private static SProjectileLoader instance;

    private static final String DEFAULT = "Default";


    public SProjectileLoader() {
        super(SCore.plugin, "/com/ssomar/score/configs/projectiles/", SProjectilesManager.getInstance(), 1000);
    }

    @Override
    public void load() {
        SProjectilesManager.getInstance().setDefaultObjects(new ArrayList<>());
        /* // TODO if (!GeneralConfig.getInstance().isDisableTestItems()) {*/
        //if (PlaceholderAPI.isLotOfWork()) {
            this.loadDefaultPremiumObjects(this.getPremiumDefaultObjectsName());
        //}
        //this.loadDefaultEncodedPremiumObjects(this.getPremiumPackObjectsName());
        //}

        // ITEMS CONFIG
        SProjectilesManager.getInstance().setLoadedObjects(new ArrayList<>());

        this.resetCpt();

        File itemsDirectory;
        if ((itemsDirectory = new File(SCore.plugin.getDataFolder() + "/projectiles")).exists()) {
            this.loadObjectsInFolder(itemsDirectory, true);
        } else {
            this.createDefaultObjectsFile(true);
            this.load();
        }

        createNotEditableProjFiles();
        File notEditProjDirectory;
        notEditProjDirectory = new File(SCore.plugin.getDataFolder() + "/projectiles/projectiles_not_editable");
        this.loadObjectsInFolder(notEditProjDirectory, true);

    }

    public void createNotEditableProjFiles() {

        Map<String, List<String>> defaultObjects = this.getNotEditableProjectilesName();


        for (String folder : defaultObjects.keySet()) {

            File fileFolder = new File(SCore.plugin.getPlugin().getDataFolder() + "/projectiles/projectiles_not_editable/" + folder);

            fileFolder.mkdirs();

            for (String id : defaultObjects.get(folder)) {
                try {
                    File pdfile = new File(SCore.plugin.getPlugin().getDataFolder() + "/projectiles/projectiles_not_editable/" + folder + "/" + id + ".yml");
                    InputStream in = this.getClass().getResourceAsStream("/com/ssomar/score/configs/projectiles_not_editable/" + folder + "/" + id + ".yml");

                    if (!pdfile.exists()) {
                        SCore.plugin.getPlugin().getDataFolder().mkdirs();
                        pdfile.getParentFile().mkdirs();
                        pdfile.createNewFile();
                    } else
                        continue;

                    OutputStream out = new FileOutputStream(pdfile);
                    byte[] buffer = new byte[1024];
                    int current = 0;

                    while ((current = in.read(buffer)) > -1)
                        out.write(buffer, 0, current);

                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Map<String, List<String>> getPremiumPackObjectsName() {
        Map<String, List<String>> defaultItems = new HashMap<>();

        return defaultItems;
    }

    @Override
    public Map<String, List<String>> getPremiumDefaultObjectsName() {
        Map<String, List<String>> defaultBlocks = new HashMap<>();

        List<String> defaultProj = new ArrayList<>();
        defaultProj.add("arrow1");
        defaultProj.add("egg1");
        defaultProj.add("enderpearl1");
        defaultProj.add("fireball1");
        defaultProj.add("lingering1");
        defaultProj.add("shulkerbullet1");
        defaultProj.add("snowball1");
        defaultProj.add("tridentCustom1");
        defaultProj.add("witherskull1");

        defaultBlocks.put(DEFAULT, defaultProj);

        return defaultBlocks;
    }


    public Map<String, List<String>> getNotEditableProjectilesName() {
        Map<String, List<String>> defaultBlocks = new HashMap<>();

        List<String> defaultProj = new ArrayList<>();
        defaultProj.add("DEFAULT_INVISIBLE_ARROW");
        defaultProj.add("DEFAULT_INVISIBLE_ARROW_NO_GRAVITY");
        defaultProj.add("DEFAULT_INVISIBLE_ARROW_NO_GRAVITY_SPEED");
        defaultProj.add("FAIRYTAIL_GRAY_FULLBUSTER_1");
        defaultProj.add("FAIRYTAIL_NATSU_DRAGNEEL_1");
        defaultProj.add("FATE_ARCHER_1");
        defaultProj.add("FATE_ARCHER_2");
        defaultProj.add("FATE_CASTER_1");
        defaultProj.add("FATE_CASTER_2");
        defaultProj.add("FATE_CASTER_3");
        defaultProj.add("FATE_GILGAMESH_1");
        defaultProj.add("FATE_GILGAMESH_2");
        defaultProj.add("FATE_GILGAMESH_3");
        defaultProj.add("FATE_RIDER_1");
        defaultProj.add("HUNTERXHUNTER_GON_FREECSS_1");
        defaultProj.add("HUNTERXHUNTER_KURAPIKA_1");
        defaultProj.add("MADOKA_HOMURA_AKEMI_1");
        defaultProj.add("MADOKA_MADOKA_KANAME_1");
        defaultProj.add("MADOKA_MAMI_TOMOE_1");
        defaultProj.add("MHA_DENKI_KAMINARI_1");
        defaultProj.add("MHA_IZUKU_MIDORIYA_1");
        defaultProj.add("MHA_MINA_ASHIDO_ACID_1");
        defaultProj.add("MHA_MINA_ASHIDO_ACID_2");
        defaultProj.add("MHA_SHOTO_TODOROKI_2");
        defaultProj.add("ONEPIECE_001_1_FIRE_FIST");
        defaultProj.add("ONEPIECE_001_2_FIRE_GUN");
        defaultProj.add("ONEPIECE_001_3_FIREFLY");
        defaultProj.add("ONEPIECE_002_1_FREEZE_BEAM");
        defaultProj.add("ONEPIECE_002_2_FREEZE_BOMB");
        defaultProj.add("ONEPIECE_003_1_TELEPORT");
        defaultProj.add("ONEPIECE_003_2_LIGHTNING_STRIKE");
        defaultProj.add("ONEPIECE_004_1_BLACK_HOLE");
        defaultProj.add("ONEPIECE_005_1_FLIGHT");
        defaultProj.add("ONEPIECE_005_2_FLAME_WALL");
        defaultProj.add("ONEPIECE_005_3_FIREBALL");
        defaultProj.add("OPM_GENOS_1");
        defaultProj.add("OPM_SPEED_O_SONIC_1");
        defaultProj.add("PACK_2_06");
        defaultProj.add("PACK_2_12");
        defaultProj.add("proj_rainbow_projectile");
        defaultProj.add("proj_tornado_blade");
        defaultProj.add("proj_pumpkin");


        defaultBlocks.put(DEFAULT, defaultProj);

        return defaultBlocks;
    }

    @Override
    public Map<String, List<String>> getFreeDefaultObjectsName() {
        Map<String, List<String>> defaultBlocks = new HashMap<>();

        List<String> defaultProj = new ArrayList<>();
        defaultProj.add("arrow1");
        defaultProj.add("egg1");
        defaultProj.add("enderpearl1");
        defaultProj.add("fireball1");
        defaultProj.add("lingering1");
        defaultProj.add("shulkerbullet1");
        defaultProj.add("snowball1");
        defaultProj.add("tridentCustom1");
        defaultProj.add("witherskull1");

        defaultBlocks.put(DEFAULT, defaultProj);

        return defaultBlocks;
    }

    @Override
    public void configVersionsConverter(File file) {
        ConfigConverter.update(file);
    }

    @Override
    public Optional<SProjectile> getObject(FileConfiguration itemConfig, String id, boolean showError, boolean isPremiumLoading, String path) {

        List<String> errors = new ArrayList<>();
        SProjectile item = new SProjectile(id, path);
        errors.addAll(item.load(SCore.plugin, itemConfig, isPremiumLoading));

        if (showError) {
            for (String s : errors) {
                SCore.plugin.getServer().getLogger().severe(s);
            }
        }
        return Optional.ofNullable(item);
    }

    public static SProjectileLoader getInstance() {
        if (instance == null) instance = new SProjectileLoader();
        return instance;
    }

}
