package com.ssomar.score.sobject;

import com.google.common.io.ByteStreams;
import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

public abstract class SObjectLoader<T extends SObject> {

    private SPlugin sPlugin;
    private String defaultObjectsPath;
    @Getter
    private Map<String, String> randomIdsDefaultObjects;
    private SObjectManager sObjectManager;
    private int maxFreeObjects;
    private int cpt;
    private Logger logger;

    public SObjectLoader(SPlugin sPlugin, String defaultObjectsPath, SObjectManager<T> sObjectManager, int maxFreeObjects) {
        this.sPlugin = sPlugin;
        this.logger = sPlugin.getPlugin().getServer().getLogger();
        this.defaultObjectsPath = defaultObjectsPath;
        this.sObjectManager = sObjectManager;
        this.maxFreeObjects = maxFreeObjects;
        this.cpt = 0;
    }

    public abstract void load();

    public abstract Map<String, List<String>> getPremiumDefaultObjectsName();

    public abstract Map<String, List<String>> getFreeDefaultObjectsName();

    public Map<String, List<String>> getAllDefaultObjectsName() {
        Map<String, List<String>> defaultObjects = getFreeDefaultObjectsName();
        Map<String, List<String>> premDefaultObjects = getPremiumDefaultObjectsName();
        for (String key : defaultObjects.keySet()) {
            List<String> mergeList = defaultObjects.get(key);
            mergeList.addAll(premDefaultObjects.get(key));
            defaultObjects.put(key, mergeList);
        }
        return defaultObjects;
    }


    public void createDefaultObjectsFile(Boolean isPremiumLoading) {

        String objectName = sPlugin.getObjectName().toLowerCase();

        logger.severe(sPlugin.getNameDesign() + " CANT LOAD YOUR " + objectName.toUpperCase() + ", FOLDER '" + objectName + "' not found !");
        logger.severe(sPlugin.getNameDesign() + " DEFAULT " + objectName.toUpperCase() + " CREATED !");

        Map<String, List<String>> defaultObjects;
        if (!isPremiumLoading) defaultObjects = this.getFreeDefaultObjectsName();
        else defaultObjects = this.getAllDefaultObjectsName();


        for (String folder : defaultObjects.keySet()) {

            File fileFolder = new File(sPlugin.getPlugin().getDataFolder() + "/" + objectName + "/" + folder);

            fileFolder.mkdirs();

            for (String id : defaultObjects.get(folder)) {
                try {
                    File pdfile = new File(sPlugin.getPlugin().getDataFolder() + "/" + objectName + "/" + folder + "/" + id + ".yml");
                    InputStream in = this.getClass().getResourceAsStream(defaultObjectsPath + folder + "/" + id + ".yml");

                    if (!pdfile.exists()) {
                        sPlugin.getPlugin().getDataFolder().mkdirs();
                        pdfile.getParentFile().mkdirs();
                        pdfile.createNewFile();
                    } else
                        return;

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

    public void loadDefaultPremiumObjects(Map<String, List<String>> defaultItemsName) {

        /* SET RANDOM ID TO NOT INTERFER WITH OTHER EI and to make it, One time session (will not work after a restart) because only for test*/
        randomIdsDefaultObjects = new HashMap<>();
        for (String folder : defaultItemsName.keySet()) {
            for (String id : defaultItemsName.get(folder)) {
                randomIdsDefaultObjects.put(id, UUID.randomUUID().toString());
            }
        }

        for (String folder : defaultItemsName.keySet()) {
            for (String id : defaultItemsName.get(folder)) {

                InputStream in = this.getClass().getResourceAsStream(defaultObjectsPath + folder + "/" + id + ".yml");
                BufferedReader reader = null;
                reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                T o;
                if ((o = this.getObjectByReader(reader, id, false, randomIdsDefaultObjects)) == null) continue;

                o.setId(randomIdsDefaultObjects.get(o.getId()));
                sObjectManager.addDefaultLoadedItems(o);
            }
        }
    }

    public void loadObjectByFile(String filePath, boolean isPremiumLoading) {
        File fileEntry = new File(filePath);
        if (!fileEntry.getName().contains(".yml") || fileEntry.getName().contains(".txt")) return;
        String id = fileEntry.getName().split(".yml")[0];

        if (isPremiumLoading && cpt >= 25) {
            logger.severe(sPlugin.getNameDesign() + " REQUIRE PREMIUM: to add more than 25 items you need the premium version");
            return;
        }

        T o;
        if ((o = this.getObjectByFile(fileEntry, id, true)) == null) {
            logger.severe(sPlugin.getNameDesign() + filePath);
            return;
        }
        sObjectManager.addLoadedObject(o);
        cpt++;
        logger.fine(sPlugin.getNameDesign() + " " + id + " was loaded !");
    }

    public void loadObjectsInFolder(File folder, boolean isPremiumLoading) {
        List<String> listFiles = Arrays.asList(folder.list());
        Collections.sort(listFiles);

        for (String s : listFiles) {
            File fileEntry = new File(folder.getPath() + "/" + s);
            if (fileEntry.isDirectory()) {
                loadObjectsInFolder(fileEntry, isPremiumLoading);
            } else {
                loadObjectByFile(folder.getPath() + "/" + s, isPremiumLoading);
            }
        }
    }

    public static File searchFileOfObject(String id) {

        List<String> listFiles = Arrays.asList(new File(ExecutableItems.getPluginSt().getDataFolder() + "/items").list());
        Collections.sort(listFiles);

        for (String s : listFiles) {
            File fileEntry = new File(ExecutableItems.getPluginSt().getDataFolder() + "/items/" + s);
            if (fileEntry.isDirectory()) {
                File result = null;
                if ((result = searchFileOfObjectInFolder(id, fileEntry)) == null)
                    continue;
                else
                    return result;
            } else {
                if (!fileEntry.getName().contains(".yml") || fileEntry.getName().contains(".txt"))
                    continue;
                String currentId = fileEntry.getName().split(".yml")[0];
                if (id.equals(currentId))
                    return fileEntry;

            }
        }
        return null;
    }

    public static File searchFileOfObjectInFolder(String id, File folder) {
        List<String> listFiles = Arrays.asList(folder.list());
        Collections.sort(listFiles);

        for (String s : listFiles) {
            File fileEntry = new File(folder + "/" + s);
            if (fileEntry.isDirectory()) {
                File result = null;
                if ((result = searchFileOfObjectInFolder(id, fileEntry)) == null)
                    continue;
                else
                    return result;
            } else {
                if (!fileEntry.getName().contains(".yml") || fileEntry.getName().contains(".txt"))
                    continue;
                String currentId = fileEntry.getName().split(".yml")[0];
                if (id.equals(currentId))
                    return fileEntry;
            }
        }
        return null;
    }

    public T getObjectByFile(File file, String id, boolean showError) {
        try {
            if (this.CreateBackupFilIfNotValid(file)) return null;
            configVersionsConverter(file);
            FileConfiguration objectConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
            return getObject(objectConfig, id, showError);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T getObjectByReader(Reader reader, String id, boolean showError, Map<String, String> wordsToReplace) {
        try {
            FileConfiguration firstConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(reader);
            String toStr = firstConfig.saveToString();
            for (String word : wordsToReplace.keySet()) {
                toStr = toStr.replaceAll(word, wordsToReplace.get(word));
            }
            YamlConfiguration config = new YamlConfiguration();
            config.loadFromString(toStr);
            FileConfiguration objectConfig = (FileConfiguration) config;

            return getDefaultObject(objectConfig, id, showError);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract void configVersionsConverter(File file);

    public T getObject(FileConfiguration objectConfig, String id, boolean showError) {
        return getObject(objectConfig, id, showError, !sPlugin.isLotOfWork(), searchFileOfObject(id).getPath());
    }

    public T getDefaultObject(FileConfiguration objectConfig, String id, boolean showError) {
        return getObject(objectConfig, id, showError, true, "");
    }

    public abstract T getObject(FileConfiguration objectConfig, String id, boolean showError, boolean isPremiumLoading, String path);


    public boolean CreateBackupFilIfNotValid(File file) {

        YamlConfiguration loader = new YamlConfiguration();

        @SuppressWarnings("unused")
        FileConfiguration config;
        try {
            loader.load(file);
            config = (FileConfiguration) loader;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            File fileBackup = new File(file.getParent() + "/" + file.getName() + ".txt");

            int i = 1;
            while (fileBackup.exists()) {
                fileBackup = new File(file.getParent() + "/" + file.getName() + i + ".txt");
                i++;
            }

            if (!fileBackup.exists()) {
                try {
                    fileBackup.getParentFile().mkdir();
                    fileBackup.createNewFile();
                    InputStream is = new FileInputStream(file);
                    OutputStream os = new FileOutputStream(fileBackup);
                    ByteStreams.copy(is, os);
                    is.close();
                    os.close();
                } catch (Exception e2) {
                    throw new RuntimeException("Unable to create the backup file: " + file.getName(), e2);
                }

            }
            return true;
        }
    }

    public List<String> getAllObjects() {
        ArrayList<String> result = new ArrayList<>();
        if (new File(sPlugin.getPlugin().getDataFolder() + "/" + sPlugin.getObjectName()).exists()) {
            List<String> listFiles = Arrays.asList(new File(sPlugin.getPlugin().getDataFolder() + "/" + sPlugin.getObjectName()).list());
            Collections.sort(listFiles);

            for (String s : listFiles) {
                File fileEntry = new File(sPlugin.getPlugin().getDataFolder() + "/" + sPlugin.getObjectName() + "/" + s);
                if (fileEntry.isDirectory()) result.addAll(getAllObjectsOfFolder(fileEntry));
                else {
                    if (!fileEntry.getName().contains(".yml")) continue;
                    String id = fileEntry.getName().split(".yml")[0];

                    result.add(id);
                }
            }
        }
        return result;
    }

    public List<String> getAllObjectsLowerCases() {
        ArrayList<String> result = new ArrayList<>();
        for (String s : getAllObjects()) {
            result.add(s.toLowerCase());
        }
        return result;
    }

    public List<String> getAllObjectsOfFolder(File folder) {
        ArrayList<String> result = new ArrayList<>();

        List<String> listFiles = Arrays.asList(folder.list());
        Collections.sort(listFiles);

        for (String s : listFiles) {
            File fileEntry = new File(folder + "/" + s);
            if (fileEntry.isDirectory()) result.addAll(getAllObjectsOfFolder(fileEntry));
            else {
                if (!fileEntry.getName().contains(".yml")) continue;
                String id = fileEntry.getName().split(".yml")[0];

                result.add(id);
            }
        }

        return result;
    }

    public void reload() {
        load();
    }

    public void resetCpt(){
        this.cpt = 0;
    }
}
