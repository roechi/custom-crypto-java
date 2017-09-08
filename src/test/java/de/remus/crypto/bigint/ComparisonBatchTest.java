package de.remus.crypto.bigint;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class ComparisonBatchTest {

    Logger logger = Logger.getLogger(getClass());

    @Test
    @UseDataProvider(value = "comparisonData", location = TestData.class)
    public void testComparisonHexAToB(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        int expectedCompAB = testData.getExpectedCompAB();

        int resultAB = BigInt.fromHexStringTwosComplement(hexStringA).compare(BigInt.fromHexStringTwosComplement(hexStringB));

        logger.info(String.format("%s: %s comp %s -> %d : actual -> %d", testName, hexStringA, hexStringB, expectedCompAB, resultAB));
        assertThat(expectedCompAB).isEqualTo(resultAB);
    }

    @Test
    @UseDataProvider(value = "comparisonData", location = TestData.class)
    public void testComparisonHexBToA(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        int expectedCompBA = testData.getExpectedCompBA();

        int resultBA = BigInt.fromHexStringTwosComplement(hexStringB).compare(BigInt.fromHexStringTwosComplement(hexStringA));

        logger.info(String.format("%s: %s comp %s -> %d : actual -> %d", testName, hexStringB, hexStringA, expectedCompBA, resultBA));
        assertThat(expectedCompBA).isEqualTo(resultBA);
    }

    @Test
    @UseDataProvider(value = "comparisonData", location = TestData.class)
    public void testComparisonDecAToB(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringB = testData.getDecStringB();
        String decStringA = testData.getDecStringA();
        int expectedCompAB = testData.getExpectedCompAB();

        int resultAB = new BigInt(decStringA).compare(new BigInt(decStringB));

        logger.info(String.format("%s: %s comp %s -> %d : actual -> %d", testName, decStringA, decStringB, expectedCompAB, resultAB));
        assertThat(expectedCompAB).isEqualTo(resultAB);
    }

    @Test
    @UseDataProvider(value = "comparisonData", location = TestData.class)
    public void testComparisonDecBToA(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        int expectedCompBA = testData.getExpectedCompBA();

        int resultBA = new BigInt(decStringB).compare(new BigInt(decStringA));

        logger.info(String.format("%s: %s comp %s -> %d : actual -> %d", testName, decStringB, decStringA, expectedCompBA, resultBA));
        assertThat(expectedCompBA).isEqualTo(resultBA);
    }

    public static class TestData {
        String testName;
        String hexStringA;
        String decStringA;
        String hexStringB;
        String decStringB;
        int expectedCompAB;
        int expectedCompBA;

        public TestData(
                String testName,
                String hexStringA,
                String decStringA,
                String hexStringB,
                String decStringB,
                int expectedCompAB,
                int expectedCompBA
        ) {
            this.testName = testName;
            this.hexStringA = hexStringA;
            this.decStringA = decStringA;
            this.hexStringB = hexStringB;
            this.decStringB = decStringB;
            this.expectedCompAB = expectedCompAB;
            this.expectedCompBA = expectedCompBA;
        }

        @DataProvider
        public static List<TestData> comparisonData() throws IOException {
            String testName = "";
            String line = "";
            String hexStringA = "";
            String decStringA = "";
            String hexStringB = "";
            String decStringB = "";
            int expectedCompAB = 0;
            int expectedCompBA = 0;

            InputStream fis = new FileInputStream("src/test/resources/comparison_tests.txt");
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            List<TestData> dataList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    switch (line.charAt(0)) {
                        case 't':
                            testName = line.substring(2);
                            break;
                        case 'a':
                            hexStringA = line.substring(2);
                            break;
                        case 'A':
                            decStringA = line.substring(2);
                            break;
                        case 'b':
                            hexStringB = line.substring(2);
                            break;
                        case 'B':
                            decStringB = line.substring(2);
                            break;
                        case 'c':
                            expectedCompAB = Integer.valueOf(line.substring(2));
                            break;
                        case 'd':
                            expectedCompBA = Integer.valueOf(line.substring(2));

                            dataList.add(
                                    new TestData(
                                            testName,
                                            hexStringA,
                                            decStringA,
                                            hexStringB,
                                            decStringB,
                                            expectedCompAB,
                                            expectedCompBA
                                    )
                            );
                            break;
                    }
                }
            }

            return dataList;
        }

        public String getTestName() {
            return testName;
        }

        public String getHexStringA() {
            return hexStringA;
        }

        public String getDecStringA() {
            return decStringA;
        }

        public String getHexStringB() {
            return hexStringB;
        }

        public String getDecStringB() {
            return decStringB;
        }

        public int getExpectedCompAB() {
            return expectedCompAB;
        }

        public int getExpectedCompBA() {
            return expectedCompBA;
        }
    }
}