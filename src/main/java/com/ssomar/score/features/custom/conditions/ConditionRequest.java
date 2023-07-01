package com.ssomar.score.features.custom.conditions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class ConditionRequest {

    private List<String> errors;

    private @Nullable Event event;

    public ConditionRequest(@Nullable Event event) {
        this.errors = new ArrayList<>();
        this.event = event;
    }

    public List<String> getErrorsFinal() {
        List<String> errorsFinal = new ArrayList<>();
        for (String s : errors) {
            String[] split = s.split("\\\\n");
            for (String s2 : split) {
                errorsFinal.add(s2);
            }
        }
        errors = errorsFinal;
        return errorsFinal;
    }
}
