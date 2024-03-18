package com.ssomar.score.features.custom.cooldowns;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CooldownsManager {

    private static final boolean DEBUG = false;

    private static CooldownsManager instance;

    /* CD_ID Cooldown */
    private final Map<String, List<Cooldown>> cooldowns = new HashMap<>();

    /* Player_UUID Cooldown */
    private final Map<UUID, List<Cooldown>> cooldownsUUID = new HashMap<>();

    public static CooldownsManager getInstance() {
        if (instance == null) instance = new CooldownsManager();
        return instance;
    }

    public Optional<String> onRequestPlaceholder(OfflinePlayer player, String params) {
        if (params.startsWith("cooldown_")) {
            UUID uuid = player.getUniqueId();
            if(cooldownsUUID.containsKey(uuid)){
                boolean castInt = false;
                if(params.contains("_int")){
                    castInt = true;
                    params = params.replaceFirst("_int", "");
                }
                for(Cooldown cd : cooldownsUUID.get(uuid)){
                    SsomarDev.testMsg("CD "+cd.toString(), DEBUG);
                    if(cd.getId().equalsIgnoreCase(params.split("cooldown_")[1])){
                        if(castInt) return Optional.of((int)cd.getTimeLeft()+"");
                        else return Optional.of(cd.getTimeLeft()+"");
                    }
                }
            }
            return Optional.of("0");
        }
        return Optional.empty();
    }

    public void addCooldown(Cooldown cd) {

        if (cd.getCooldown() == 0) return;

        String id = cd.getId();
        SsomarDev.testMsg("ADDDD "+cd.toString(), DEBUG);
        if (cooldowns.containsKey(id)) {
            List<Cooldown> cds = cooldowns.get(id);
            cds.add(cd);
            cooldowns.put(id, cds);
        } else {
            List<Cooldown> cds = new ArrayList<>();
            cds.add(cd);
            cooldowns.put(id, cds);
        }

        UUID id2 = cd.getEntityUUID();
        if (id2 == null) return;
        if (cooldownsUUID.containsKey(id2)) {
            List<Cooldown> cds = cooldownsUUID.get(id2);
            cds.add(cd);
        } else {
            List<Cooldown> cds = new ArrayList<>();
            cds.add(cd);
            cooldownsUUID.put(id2, cds);
        }
    }

    /* FROM DB */
    public void addCooldowns(List<Cooldown> cds) {
        for (Cooldown cd : cds) {
            double timeLeft = cd.getTimeLeft();
            if (timeLeft > 0) this.addCooldown(cd);
        }
    }

    public Optional<Cooldown> getCooldown(SPlugin sPlugin, String id, UUID uuid, boolean onlyGlobal) {

        SsomarDev.testMsg("GET COOLDOWN "+id+" "+uuid, DEBUG);
        if (cooldowns.containsKey(id)) {
            double maxTimeLeft = -1;
            List<Cooldown> cds = cooldowns.get(id);
            if (cds.size() != 0) {
                Cooldown cdMax = null;
                int cptRemoved = 0;
                int size = cds.size();
                //SsomarDev.testMsg("CD size >> "+size, true);
                for (int i = 0; i < size; i++) {
                    Cooldown cd = cds.get(i-cptRemoved);
                    if (cd == null){
                        //SsomarDev.testMsg("CD NULL", true);
                        continue;
                    }
                    if (onlyGlobal && !cd.isGlobal()){
                        //SsomarDev.testMsg("CD NOT GLOBAL", true);
                        continue;
                    }
                    if (!cd.isGlobal() && !cd.getEntityUUID().equals(uuid)){
                        //SsomarDev.testMsg("CD NOT GLOBAL AND NOT EQUAL UUID", true);
                        continue;
                    }

                    double timeLeft = cd.getTimeLeft();

                    //SsomarDev.testMsg("CD valid >> "+timeLeft, true);
                    if (maxTimeLeft < timeLeft && timeLeft > 0) {
                        maxTimeLeft = timeLeft;
                        cdMax = cd;
                    } else {
                        cd.setNull(true);
                        cds.remove(i-cptRemoved);
                        cptRemoved++;
                        try {
                            cooldownsUUID.get(uuid).remove(cd);
                        }catch (Exception ignored){}
                    }
                }
                if(cdMax != null) SsomarDev.testMsg("COOLDOWN "+cdMax.toString(), DEBUG);
                else SsomarDev.testMsg("NO COOLDOWN ", DEBUG);
                return Optional.ofNullable(cdMax);
            }
        }
        SsomarDev.testMsg("NO COOLDOWN ", DEBUG);
        return Optional.empty();
    }

    public List<Cooldown> getCooldownsOf(UUID uuid) {
        if (cooldownsUUID.containsKey(uuid)) {
            return cooldownsUUID.get(uuid);
        } else return new ArrayList<>();
    }

    public List<Cooldown> getAllCooldowns() {
        List<Cooldown> result = new ArrayList<>();

        for (String id : cooldowns.keySet()) {
            result.addAll(cooldowns.get(id));
        }

        return result;
    }

    public void clearCooldowns() {
        cooldowns.clear();
        cooldownsUUID.clear();
    }

    public void removeCooldownsOf(UUID uuid) {
        SsomarDev.testMsg("REMOVE COOLDOWNS OF "+uuid, DEBUG);
        cooldownsUUID.remove(uuid);
        for (String s : cooldowns.keySet()) {
            List<Cooldown> cds = cooldowns.get(s);
            for (int i = 0; i < cds.size(); i++) {
                Cooldown cd = cds.get(i);
                if (cd != null && cd.getEntityUUID() != null && cd.getEntityUUID().equals(uuid)) {
                    cds.set(i, null);
                    break;
                }
            }
        }
    }

    public void clearCooldown(String cooldownId, @Nullable UUID uuid) {
        for (String s : cooldowns.keySet()) {
            List<Cooldown> cds = cooldowns.get(s);
            for (int i = 0; i < cds.size(); i++) {
                Cooldown cd = cds.get(i);
                if (cd != null && cd.getId().equalsIgnoreCase(cooldownId)) {
                    if(uuid != null && (cd.getEntityUUID() == null || !cd.getEntityUUID().equals(uuid))) continue;
                    cooldownsUUID.get(cd.getEntityUUID()).remove(cd);
                    cds.set(i, null);
                    break;
                }
            }
        }
    }

    public List<String> getAllCooldownIds() {
        List<String> result = new ArrayList<>();
        for (String id : cooldowns.keySet()) {
            result.add(id);
        }
        return result;
    }


    public void printInfo() {
        System.out.println("--------^^^^^^^^^^^^^^^^^^^^-------------");


        int total = 0;
        for (String s : cooldowns.keySet()) {
            List<Cooldown> cds = cooldowns.get(s);
            total += cds.size();
        }
        System.out.println(" total : "+total);

        total = 0;
        for (UUID s : cooldownsUUID.keySet()) {
            List<Cooldown> cds = cooldownsUUID.get(s);
            total += cds.size();
        }
        System.out.println(" total : "+total);


        for (String s : cooldowns.keySet()) {
            for (Cooldown cd2 : cooldowns.get(s)) {
                if(cd2 != null) System.out.println(cd2.toString());
                else System.out.println("null");
            }
        }

        System.out.println("----------------------");

        for (UUID uuid2 : cooldownsUUID.keySet()) {
            for (Cooldown cd2 : cooldownsUUID.get(uuid2)) {
                if(cd2 != null) System.out.println(cd2.toString());
                else System.out.println("null");
            }
        }

        System.out.println("---------vvvvvvvvvvvvvvvvv-------------");
    }

}

/* Reader */
//System.out.println("--------^^^^^^^^^^^^^^^^^^^^1-------------");
//
//for(String s : cooldowns.keySet()) {
//	for(Cooldown cd2 : cooldowns.get(s)) {
//		System.out.println(cd2.toString());
//	}
//}
//
//System.out.println("----------------------");
//
//for(UUID uuid2 : cooldownsUUID.keySet()) {
//	for(Cooldown cd2 : cooldownsUUID.get(uuid2)) {
//		System.out.println(cd2.toString());
//	}
//}
//
//System.out.println("---------vvvvvvvvvvv1vvvvvv-------------");
