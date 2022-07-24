package com.ssomar.score.commands.runnable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArgumentChecker {

    private boolean valid;
    private String error;

    public ArgumentChecker(){
        valid = true;
        error = "";
    }
}
