package bigint;

import org.junit.Test;

public class TestParserTest {

    @Test
    public void test() throws Exception {
        TestParser testParser = new TestParser("arithmetic_tests.txt");
        testParser.parseAndRun();
    }

}