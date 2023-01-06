package com.ssomar.score.features.custom.cooldowns;

import com.ssomar.score.splugin.SPlugin;

import java.util.*;

public class CooldownsManager {

    private static CooldownsManager instance;

    /* CD_ID Cooldown */
    private final Map<String, List<Cooldown>> cooldowns = new HashMap<>();

    /* Player_UUID Cooldown */
    private final Map<UUID, List<Cooldown>> cooldownsUUID = new HashMap<>();

    public static CooldownsManager getInstance() {
        if (instance == null) instance = new CooldownsManager();
        return instance;
    }

    public void addCooldown(Cooldown cd) {

        if (cd.getCooldown() == 0) return;

        String id = cd.getId();
        if (cooldowns.containsKey(id)) {
            List<Cooldown> cds = cooldowns.get(id);
            cds.add(cd);
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
            long current = System.currentTimeMillis();
            long delay = current - cd.getTime();
            int div = 1000;
            if (cd.isInTick()) div = 50;
            int delayInt = (int) (delay / div);

            if (delayInt < cd.getCooldown()) this.addCooldown(cd);
        }
    }

    public Optional<Cooldown> getCooldown(SPlugin sPlugin, String id, UUID uuid, boolean onlyGlobal) {

        if (cooldowns.containsKey(id)) {
            double maxTimeLeft = -1;
            List<Cooldown> cds = cooldowns.get(id);
            if (cds.size() != 0) {
                Cooldown cdMax = null;
                for (int i = 0; i < cds.size(); i++) {
                    Cooldown cd = cds.get(i);
                    if (cd == null) continue;
                    if (onlyGlobal && !cd.isGlobal()) continue;
                    if (!cd.isGlobal() && !cd.getEntityUUID().equals(uuid)) continue;

                    double timeLeft = cd.getTimeLeft();

                    if (maxTimeLeft < timeLeft && timeLeft > 0) {
                        maxTimeLeft = timeLeft;
                        cdMax = cd;
                    } else {
                        cd.setNull(true);
                        cds.remove(i);
                        try {
                            cooldownsUUID.get(uuid).remove(cd);
                        }catch (Exception ignored){}
                    }
                }
                return Optional.ofNullable(cdMax);
            }
        }
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


    public void printInfo() {
        System.out.println("--------^^^^^^^^^^^^^^^^^^^^1-------------");


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

        System.out.println("---------vvvvvvvvvvv1vvvvvv-------------");
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
