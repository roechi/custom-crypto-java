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
public class PowModPrimeBatchTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testHighPowDec(TestData testData) throws Exception {
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decStringC = testData.getDecStringC();
        String decStringM = testData.getDecStringM();

        BigInt base = new BigInt(decStringA);
        BigInt exp = new BigInt(decStringB);
        BigInt mod = new BigInt(decStringM);
        BigInt expected = new BigInt(decStringC);

        BigInt result = base.powMod(exp, mod);

        logger.info(String.format("%s: %s ^ %s mod %s = %s -> actual: %s ",
                testData.getTestName(), decStringA, decStringB, decStringM, decStringC, result));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testHighPowHex(TestData testData) throws Exception {
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexStringC = testData.getHexStringC();
        String hexStringM = testData.getHexStringM();


        BigInt base = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt exp = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt mod = BigInt.fromHexStringTwosComplement(hexStringM);
        BigInt expected = BigInt.fromHexStringTwosComplement(hexStringC);

        BigInt result = base.powMod(exp, mod);

        logger.info(String.format("%s: %s ^ %s mod %s = %s -> actual: %s ",
                testData.getTestName(), hexStringA, hexStringB, hexStringM, hexStringC, result.toHexStringTwosComplement(testData.getSize() / 4)));
        assertThat(result).isEqualTo(expected);
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
        private String hexStringM;
        private String decStringM;

        public TestData(
                String testName,
                int size,
                String hexStringA,
                String decStringA,
                String hexStringB,
                String decStringB,
                String hexStringC,
                String decStringC,
                String hexStringM,
                String decStringM
        ) {
            this.testName = testName;
            this.size = size;
            this.hexStringA = hexStringA;
            this.decStringA = decStringA;
            this.hexStringB = hexStringB;
            this.decStringB = decStringB;
            this.hexStringC = hexStringC;
            this.decStringC = decStringC;
            this.hexStringM = hexStringM;
            this.decStringM = decStringM;
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
            String hexStringM = "";
            String decStringM = "";


            InputStream fis = new FileInputStream("src/test/resources/pow_mod_prime_tests.txt");
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
                        case 'm':
                            hexStringM = line.substring(2);
                            break;
                        case 'M':
                            decStringM = line.substring(2);
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
                                    decStringC,
                                    hexStringM,
                                    decStringM
                            ));
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

        public String getHexStringM() {
            return hexStringM;
        }

        public String getDecStringM() {
            return decStringM;
        }
    }

}