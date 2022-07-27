package com.ssomar.score.configs.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface MessageInterface {

    static List<MessageInterface> getMessagesEnum(MessageInterface[] tab) {
        return new ArrayList<>(Arrays.asList(tab));
    }

    String getName();
}
