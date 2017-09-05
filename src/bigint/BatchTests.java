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
}