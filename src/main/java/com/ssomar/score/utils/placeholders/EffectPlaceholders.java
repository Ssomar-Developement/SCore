package com.ssomar.score.utils.placeholders;

import lombok.Getter;
import org.bukkit.potion.PotionEffect;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EffectPlaceholders extends PlaceholdersInterface implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Getter
    private Map<String, String> placeholders;

    /* placeholders of the player */
    @Getter
    private PotionEffect effect;

    public EffectPlaceholders() {
        this.placeholders = new HashMap<>();
    }

    public void setEffectPlcHldr(PotionEffect effect) {
        this.effect = effect;
        this.reloadEffectPlcHldr();
    }

    public void setPlayerPlcHldr(PotionEffect effect) {
        this.effect = effect;
        this.reloadEffectPlcHldr();
    }

    public void reloadEffectPlcHldr() {
        if (this.effect != null) {
            /* Pre save placeholders without calcul */

            placeholders.put("%effect_received%", effect.getType().getKey().getKey());
            placeholders.put("%effect_received_lower%", effect.getType().getKey().getKey().toLowerCase());
            placeholders.put("%effect_received_level%", effect.getAmplifier()+"");
            placeholders.put("%effect_received_duration%", effect.getDuration()+"");
        }
    }

    public String replacePlaceholder(String s) {
        String toReplace = s;
        return toReplace;
    }
}
