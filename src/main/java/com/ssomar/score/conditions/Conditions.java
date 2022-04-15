package com.ssomar.score.conditions;

import com.ssomar.score.conditions.condition.Condition;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Conditions<T extends Condition> {

    private Map<String, T> conditions;

    public Conditions(){
        this.conditions = new HashMap<>();
    }

    public T get(T condition){
       return conditions.get(condition.getConfigName());
    }

    public T get(String conditionKey){
        return conditions.get(conditionKey);
    }

    public void add(T condition){
        conditions.put(condition.getConfigName(), condition);
    }

    public boolean contains(T condition){
        return conditions.containsKey(condition.getConfigName());
    }

    public boolean contains(String conditionKey){
        return conditions.containsKey(conditionKey);
    }

    public abstract Conditions createNewInstance();
}
