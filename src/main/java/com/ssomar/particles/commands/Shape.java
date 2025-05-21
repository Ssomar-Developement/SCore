package com.ssomar.particles.commands;

import com.ssomar.score.utils.scheduler.ScheduledTask;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

@Getter
public class Shape {

    String name;
    Parameters parameters;
    Method method;


    public Shape(String name, Method method) {
        this.name = name;
        this.method = method;
        this.parameters = new Parameters();
        parameters.init(method);
    }

    public void run(Parameters parameters, @Nullable Entity targetEntity){
        try {
            ScheduledTask task = (ScheduledTask) method.invoke(this, parameters.getParametersValues());
            if(targetEntity!= null) ShapesManager.getInstance().addRunningShape(targetEntity, task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
