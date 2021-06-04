package com.ssomar.score.configs.messages;

public enum Message implements MessageInterface{

	/* commands */
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
