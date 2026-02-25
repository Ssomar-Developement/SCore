package com.ssomar.score.commands.runnable.mixed_player_entity.commands.addtempattribute;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddTemporaryAttributeObject {
    /**
     * Owner of the temporary attribute. The reason why the 4th constructor argument asks for entity uuid because
     * both players and entities can receive this buff.
     * <br/><br/>
     * But for now, only buffs given to players are recorded to mysql because if the server suddenly restarts or crashes,
     * there's a super high chance that undeleted buffs are negligible.
     * <br/><br/>
     * <b>But this fact must be properly written in the plugin's wiki just in case the edge case occurs</b>
     */
    private final String attribute_key;
    private final String attribute_type;
    private final double amount;
    private final String entity_uuid;
    private final long expiry_time;
    public AddTemporaryAttributeObject(String attribute_key, String attribute_type, double amount, String entity_uuid, long expiry_time ) {
        this.attribute_key = attribute_key;
        this.attribute_type = attribute_type;
        this.amount = amount;
        this.entity_uuid = entity_uuid;
        this.expiry_time = expiry_time;
    }
}
