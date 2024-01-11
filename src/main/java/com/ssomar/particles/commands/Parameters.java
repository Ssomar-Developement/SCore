package com.ssomar.particles.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.emums.CustomColor;
import com.ssomar.score.utils.numbers.NTools;
import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

public class Parameters extends ArrayList<Parameter>{

    private boolean requirePlugin;
    private boolean requireDisplay;
    private ParticleDisplay display;

    public void init(Method method){
        Paranamer info = new CachingParanamer(new AnnotationParanamer(new BytecodeReadingParanamer()));
        java.lang.reflect.Parameter[] parameters = method.getParameters();
        String[] parameterNames = info.lookupParameterNames(method);
        int i = 0;
        for (String parameter : parameterNames) {
            if(parameter.equals("plugin")){
                requirePlugin = true;
                i++;
                continue;
            }
            if(parameter.equals("display")){
                requireDisplay = true;
                i++;
                continue;
            }
            //System.out.println("Loading parameter: " + parameter);
            Object o = null;
            switch (parameters[i].getType().getName()) {
                case "int":
                    o = 0;
                    break;
                case "double":
                    o = 0.0;
                    break;
                case "boolean":
                    o = false;
                    break;
            }
            this.add(new com.ssomar.particles.commands.Parameter(parameter, o, ""));
            i++;
        }
    }

    public void load(String [] parameters, Entity entity, Location loc){
        for(Parameter p : this){
            p.load(parameters);
        }
        if(requireDisplay) {
            boolean found = false;
            for (String s : parameters) {
                if (s.contains("color:")){
                    String colorName = s.split(":")[1];
                    Optional<Integer> optionalInteger = NTools.getInteger(colorName);
                    Color color = null;
                    if(optionalInteger.isPresent()){
                        color = new Color(optionalInteger.get());
                    }else{
                        org.bukkit.Color bukkitColor = CustomColor.valueOf(colorName.toUpperCase());
                        if(bukkitColor != null) {
                            color = new Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
                        }
                    }
                    if(color != null) {
                        display = ParticleDisplay.colored(loc, color, 1);
                        found = true;
                        break;
                    }
                    else{
                        //SsomarDev.testMsg("Color not found: " + colorName, true);
                    }
                    break;
                }
                else if (s.contains("particle:")) {
                    String particleName = s.split(":")[1];
                    Particle particle = XParticle.getParticle(particleName.toUpperCase());
                    display = ParticleDisplay.display(loc, particle);
                    found = true;
                    break;
                }
            }
            // By default its flame particle
            if(display == null || !found) display = ParticleDisplay.display(loc, Particle.FLAME);

            if(display != null) {
                if(entity != null) display.withEntity(entity);
            }
        }
    }

    public Object getParameterValue(String name){
        for(Parameter p : this){
            if(p.getName().equals(name)){
                return p.getValue();
            }
        }
        return null;
    }

    public Object[] getParametersValues(){
        int size = this.size();
        if(requirePlugin) size++;
        if(requireDisplay) size++;

        Object[] values = new Object[size];
        int i = 0;
        if(requirePlugin){
            values[i] = SCore.plugin;
            i++;
        }
        for(Parameter p : this){
            //SsomarDev.testMsg("Parameter: " + p.getName() + " value: " + p.getValue(), true);
            values[i] = p.getValue();
            i++;
        }
        if(requireDisplay){
            values[i] = display;
        }
        return values;
    }
}
