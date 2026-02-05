package com.ssomar.score.utils.strings;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StringArgExtractorTest {

    @org.junit.jupiter.api.Test
    void inspectMethodResult() {
        String sampleText = "executableitems_id:Focalors_var:shield_slot:10";
        String result[] = StringArgExtractor.extractArgValue(sampleText, "var");
        if (!result[0].equals("shield")) fail();

    }

}