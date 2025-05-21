package com.ssomar.particles.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.numbers.NTools;
import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

import static com.ssomar.particles.commands.ParticleDisplay.edit;

public class Parameters extends ArrayList<Parameter> {

    private boolean requirePlugin;
    private boolean requireDisplay;
    private ParticleDisplay display;

    public void init(Method method) {
        Paranamer info = new CachingParanamer(new AnnotationParanamer(new BytecodeReadingParanamer()));
        java.lang.reflect.Parameter[] parameters = method.getParameters();
        String[] parameterNames = info.lookupParameterNames(method);
        int i = 0;
        for (String parameter : parameterNames) {
            if (parameter.equals("plugin")) {
                requirePlugin = true;
                i++;
                continue;
            }
            if (parameter.equals("display")) {
                requireDisplay = true;
                i++;
                continue;
            }
            //System.out.println("Loading parameter: " + parameter+" type: " + parameters[i].getType().getName());
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
                case "java.lang.String":
                    o = "";
                    break;
            }
            this.add(new com.ssomar.particles.commands.Parameter(parameter, o, ""));
            i++;
        }
    }

    public void load(String[] parameters, Entity entity, Location loc) {
        for (Parameter p : this) {
            p.load(parameters);
        }
        if (requireDisplay) {
            Configuration config = new MemoryConfiguration();
            // Create a new section
            ConfigurationSection section = config.createSection("section");
            for (String s : parameters) {
                String[] sp = s.split(":");
                if (sp.length != 2) continue;
                String value = sp[1];
                Optional<Double> optNumber = NTools.getDouble(value);
                if(optNumber.isPresent()) {
                    section.set(sp[0], optNumber.get());
                }
                else section.set(sp[0], sp[1]);
            }
            display = edit(ParticleDisplay.simple(loc, Particle.FLAME), section);

            if (entity != null) display.withEntity(entity);
        }
    }

    public Object getParameterValue(String name) {
        for (Parameter p : this) {
            if (p.getName().equals(name)) {
                return p.getValue();
            }
        }
        return null;
    }

    public Object[] getParametersValues() {
        int size = this.size();
        if (requirePlugin) size++;
        if (requireDisplay) size++;

        Object[] values = new Object[size];
        int i = 0;
        if (requirePlugin) {
            values[i] = SCore.plugin;
            i++;
        }
        for (Parameter p : this) {
            //SsomarDev.testMsg("Parameter: " + p.getName() + " value: " + p.getValue(), true);
            values[i] = p.getValue();
            i++;
        }
        if (requireDisplay) {
            values[i] = display;
        }
        return values;
    }
}
