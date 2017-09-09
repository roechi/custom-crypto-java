package de.remus.crypto.bigint;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class PowerHighExpBatchTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testHighPowDec(TestData testData) throws Exception {
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decStringC = testData.getDecStringC();

        BigInt base = new BigInt(decStringA);
        int exp = (int) new BigInt(decStringB).getCells()[0];
        BigInt expected = new BigInt(decStringC);

        BigInt result = base.pow(exp);

        logger.info(String.format("%s: %s ^ %s = %s -> actual: %s ",
                testData.getTestName(), decStringA, decStringB, decStringC, result));
        assertThat(result).isEqualTo(expected);
    }

    @Ignore("Parsing of these long hex strings takes way too much time. The Decimal Tests should show the correctness and speed of" +
            "the pow operation sufficiently")
    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testHighPowHex(TestData testData) throws Exception {
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexStringC = testData.getHexStringC();

        // parsing would just take too long
        if (hexStringC.length() <= 256) {

            BigInt base = BigInt.fromHexString(hexStringA);
            int exp = (int) BigInt.fromHexString(hexStringB).getCells()[0];
            BigInt expected = BigInt.fromHexString(hexStringC);

            BigInt result = base.pow(exp);

            logger.info(String.format("%s: %s ^ %s = %s -> actual: %s ",
                    testData.getTestName(), hexStringA, hexStringB, hexStringC, result.toHexStringTwosComplement(testData.getSize() / 4)));
            assertThat(result).isEqualTo(expected);
        }
    }

    public static class TestData {
        private String testName;
        private int size;
        private String hexStringA;
        private String decStringA;
        private String hexStringB;
        private String decStringB;
        private String hexStringC;
        private String decStringC;

        public TestData(
                String testName,
                int size,
                String hexStringA,
                String decStringA,
                String hexStringB,
                String decStringB,
                String hexStringC,
                String decStringC
        ) {
            this.testName = testName;
            this.size = size;
            this.hexStringA = hexStringA;
            this.decStringA = decStringA;
            this.hexStringB = hexStringB;
            this.decStringB = decStringB;
            this.hexStringC = hexStringC;
            this.decStringC = decStringC;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String line = "";
            int size = 0;
            String testName = "";
            String hexStringA = "";
            String decStringA = "";
            String hexStringB = "";
            String decStringB = "";
            String hexStringC = "";
            String decStringC = "";


            InputStream fis = new FileInputStream("src/test/resources/pow_high_tests.txt");
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            List<TestData> dataList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    switch (line.charAt(0)) {
                        case 't':
                            testName = line.substring(2);
                            break;
                        case 's':
                            size = Integer.valueOf(line.substring(2));
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
                            hexStringC = line.substring(2);
                            break;
                        case 'C':
                            decStringC = line.substring(2);

                            dataList.add(new TestData(
                                    testName,
                                    size,
                                    hexStringA,
                                    decStringA,
                                    hexStringB,
                                    decStringB,
                                    hexStringC,
                                    decStringC));
                            break;
                    }
                }
            }
            return dataList;
        }

        public String getTestName() {
            return testName;
        }

        public int getSize() {
            return size;
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

        public String getHexStringC() {
            return hexStringC;
        }

        public String getDecStringC() {
            return decStringC;
        }
    }

}