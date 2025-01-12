package com.ssomar.score.languages.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface TextInterface {

    static List<TextInterface> getTextsEnum(TextInterface[] tab) {
        return new ArrayList<>(Arrays.asList(tab));
    }

    String getKey();

    String getDefaultValueString();

    String[] getDefaultValueArray();

    TypeText getType();



}
