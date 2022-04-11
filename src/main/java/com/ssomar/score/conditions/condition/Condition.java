package com.ssomar.score.conditions.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Optional;

@Getter@Setter
public abstract class Condition<T> implements Cloneable{

    private ConditionType conditionType;
    private String configName;
    private String editorName;
    private String [] editorDescription;
    private T condition;
    private String defaultErrorMsg;
    private Optional<String> customErrorMsg;

    public Condition(ConditionType conditionType, String configName, String editorName, String [] editorDescription, T condition, String defaultErrorMsg) {
        this.conditionType = conditionType;
        this.configName = configName;
        this.editorName = editorName;
        this.editorDescription = editorDescription;
        this.condition = condition;
        this.defaultErrorMsg = defaultErrorMsg;
        this.customErrorMsg = Optional.empty();
    }

    public Object clone() {
        Object o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver, car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }

    public void sendErrorMsg(Optional<Player> playerOpt, SendMessage messageSender){
        if(playerOpt.isPresent() && hasErrorMsg()) messageSender.sendMessage(playerOpt.get(), getErrorMsg());
    }

    public boolean hasErrorMsg(){
        return customErrorMsg.isPresent() && StringConverter.decoloredString(customErrorMsg.get().trim()).length() > 0;
    }

    public String getErrorMsg(){
        if(customErrorMsg.isPresent()) return customErrorMsg.get();
        else return defaultErrorMsg;
    }
}
