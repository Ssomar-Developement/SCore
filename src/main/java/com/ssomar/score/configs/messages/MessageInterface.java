package com.ssomar.score.configs.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface MessageInterface {

    static List<MessageInterface> getMessagesEnum(MessageInterface[] tab) {
        List<MessageInterface> result = new ArrayList<>(Arrays.asList(tab));
        return result;
    }

    String getName();
}
