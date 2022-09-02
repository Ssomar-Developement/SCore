package com.ssomar.score.configs.messages;

public enum Message implements MessageInterface {

    /* commands */
    ACTIONBAR_MESSAGE("actionbarMessage"),
    ACTIONBAR_END("actionbarEnd"),
    SET_ACTIONBAR_ON("setActionbarOn"),
    SET_ACTIONBAR_OFF("setActionbarOff"),
    HAVE_ACTIONBAR_ON("haveActionbarOn"),
    HAVE_ACTIONBAR_OFF("haveActionbarOff"),
    ERROR_MONEY("errorMoneyMsg"),
    NEW_BALANCE_NEGATIVE("newBalanceMsg"),
    NEW_BALANCE_POSITIVE("newBalancePositiveMsg"),
    NO_PLAYER_HIT("noPlayerHit"),
    NO_ENTITY_HIT("noEntityHit"),
    DAMAGE_COMMAND_KILL("damageCommandKill");

    private String name;

    Message(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
