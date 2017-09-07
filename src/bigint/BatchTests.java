package bigint;


import org.junit.Test;

public class BatchTests {

    @Test
    public void testArithmeticOperations() throws Exception {
        TestParser.parseAndRunArithmeticTests("arithmetic_tests.txt");
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