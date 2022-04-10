package com.ssomar.score.sobject.sactivator.conditions;

import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public abstract class NewConditions<T extends Condition> {

    private Map<String, T> conditions;

    public NewConditions(){
        this.conditions = new HashMap<>();
    }

    public T get(T condition){
       return conditions.get(condition.getConfigName());
    }

    public void add(T condition){
        conditions.put(condition.getConfigName(), condition);
    }

    public boolean contains(T condition){
        return conditions.containsKey(condition.getConfigName());
    }

    public abstract NewConditions createNewInstance();
}
