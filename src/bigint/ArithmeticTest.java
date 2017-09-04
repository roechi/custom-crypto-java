package bigint;

import org.junit.Test;

public class ArithmeticTest {

    @Test
    public void testWithInputFile() throws Exception {
        TestParser testParser = new TestParser("arithmetic_tests.txt");
        testParser.parseAndRun();
    }

}