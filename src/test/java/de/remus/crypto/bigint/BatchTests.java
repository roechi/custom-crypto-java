package de.remus.crypto.bigint;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(DataProviderRunner.class)
public class BatchTests {

    Logger logger = Logger.getLogger(getClass());

    @Test
    public void testArithmeticOperations() throws Exception {
        TestParser.parseAndRunArithmeticTests("src/test/resources/arithmetic_tests.txt");
    }

    @Test
    public void testHexToDecConversion() throws Exception {
        TestParser.parseAndRunHexConversionTests("convert_hex_tests.txt");
    }

    @Test
    public void testMul10() throws Exception {
        TestParser.parseAndRunMul10Tests("mult10_tests.txt");
    }

    @Test
    public void testShift() throws Exception {
        TestParser.parseAndRunShiftTests("shift_tests.txt");
    }

    @Test
    public void testInverse() throws Exception {
        TestParser.parseAndRunTwosComplementNegationTests("twos_complement_negation_tests.txt");
    }
}