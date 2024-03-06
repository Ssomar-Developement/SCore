package com.ssomar.score.features.custom.conditions;

import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class ConditionRequest {

    private List<String> errors;

    private StringPlaceholder sp;

    private @Nullable Event event;

    public ConditionRequest(@Nullable Event event, @NotNull StringPlaceholder sp) {
        this.errors = new ArrayList<>();
        this.event = event;
        this.sp = sp;
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
