package com.ssomar.score.configs.messages;

public enum Message implements MessageInterface{

	/* commands */
	SET_ACTIONBAR_ON ("setActionbarOn"),
	SET_ACTIONBAR_OFF ("setActionbarOff"),
	HAVE_ACTIONBAR_ON ("haveActionbarOn"),
	HAVE_ACTIONBAR_OFF ("haveActionbarOff"),
	ERROR_MONEY ("errorMoneyMsg"),
	NEW_BALANCE ("newBalanceMsg");

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
