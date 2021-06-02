package com.ssomar.score.configs.messages;

import java.util.ArrayList;
import java.util.List;

public interface MessageInterface {

	public String getName();
	
	public static List<MessageInterface> getMessagesEnum(MessageInterface[] tab) {
		List<MessageInterface> result = new ArrayList<>();
		for(MessageInterface msg : tab){
			result.add(msg);
		}
		return result;
	}
}
