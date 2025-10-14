package com.ssomar.score.commands.runnable.mixed_player_entity.commands.addtempattribute;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddTemporaryAttributeObject {
    /**
     * Owner of the temporary attribute
     */
    final String attribute_key;
    final String attribute_type;
    final double amount;
    final String entity_uuid;
    final long expiry_time;
    public AddTemporaryAttributeObject(String attribute_key, String attribute_type, double amount, String entity_uuid, long expiry_time ) {
        this.attribute_key = attribute_key;
        this.attribute_type = attribute_type;
        this.amount = amount;
        this.entity_uuid = entity_uuid;
        this.expiry_time = expiry_time;
    }
}
