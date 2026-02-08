package com.ssomar.score.utils.strings;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StringArgExtractorTest {

    @org.junit.jupiter.api.Test
    void inspectMethodResult() {
        String sampleText1 = "executableitems_checkamount_id:Focalors_var:shield_slot:10";
        String result1[] = StringArgExtractor.extractArgValue(sampleText1, "var");
        if (!result1[0].equals("shield")) fail();

        String sampleText2 = "executableitems_checkamount_id:Focalors_slot:1,2,4";
        String result2[] = StringArgExtractor.extractArgValue(sampleText2, "slot");
        if (!Arrays.toString(result2).equals("[1,2,4]")) fail();

        String sampleText3 = "executableitems_checkvar_id:Focalors_var:shield_slot";
        String result3[] = StringArgExtractor.extractArgValue(sampleText3, "var");
        if (!result3[0].equals("shield_slot")) fail();

        String sampleText4 = "executableitems_checkvar_id:Focalors_var:shield";
        String result4[] = StringArgExtractor.extractArgValue(sampleText4, "slot");
        if (!(result4.length == 0)) fail();

    }

}