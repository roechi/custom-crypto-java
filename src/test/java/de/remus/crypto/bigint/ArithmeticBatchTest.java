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
public class ArithmeticBatchTest {

    Logger logger = Logger.getLogger(getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testAdditionHex(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexAddResult = testData.getHexAddResult();

        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.add(bFromHex);
        logger.info(String.format("%s: %s + %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexAddResult, result.toHexStringTwosComplement(size)));
        assertThat(hexAddResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testAdditionDec(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decAddResult = testData.getDecAddResult();

        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.add(bFromDec);
        logger.info(String.format("%s: %s + %s = %s : actual -> %s", testName, decStringA, decStringB, decAddResult, result));
        assertThat(decAddResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testSubtractionHex(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexSubResult = testData.getHexSubResult();

        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.subtract(bFromHex);

        logger.info(String.format("%s: %s - %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexSubResult, result.toHexStringTwosComplement(size)));
        assertThat(hexSubResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testSubtractionDec(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decSubResult = testData.getDecSubResult();

        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.subtract(bFromDec);

        logger.info(String.format("%s: %s - %s = %s : actual -> %s", testName, decStringA, decStringB, decSubResult, result));
        assertThat(decSubResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testMultiplicationHex(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexMulResult = testData.getHexMulResult();

        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.multiply(bFromHex);
        logger.info(String.format("%s: %s * %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexMulResult, result.toHexStringTwosComplement(size)));
        assertThat(hexMulResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testMultiplicationDec(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decMulResult = testData.getDecMulResult();

        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.multiply(bFromDec);

        logger.info(String.format("%s: %s + %s = %s : actual -> %s", testName, decStringA, decStringB, decMulResult, result));
        assertThat(decMulResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testDivisionHex(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexDivResult = testData.getHexDivResult();

        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.divide(bFromHex).getDivResult();

        logger.info(String.format("%s: %s / %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexDivResult, result.toHexStringTwosComplement(size)));
        assertThat(hexDivResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testDivisionDec(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decDivResult = testData.getDecDivResult();

        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.divide(bFromDec).getDivResult();

        logger.info(String.format("%s: %s / %s = %s : actual -> %s", testName, decStringA, decStringB, decDivResult, result));
        assertThat(decDivResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testModHex(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexModResult = testData.getHexModResult();

        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.divide(bFromHex).getRemainder();
        logger.info(String.format("%s: %s mod %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexModResult, result.toHexStringTwosComplement(size)));
        assertThat(hexModResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testModDec(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decModResult = testData.getDecModResult();

        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.divide(bFromDec).getRemainder();

        logger.info(String.format("%s: %s mod %s = %s : actual -> %s", testName, decStringA, decStringB, decModResult, result));
        assertThat(decModResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testDivisionBouteHex(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexDivResultBoute = testData.getHexDivResultBoute();

        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.divideBoute(bFromHex).getDivResult();

        logger.info(String.format("%s: %s / %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexDivResultBoute, result.toHexStringTwosComplement(size)));
        assertThat(hexDivResultBoute).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testDivisionBouteDec(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decDivResultBoute = testData.getDecDivResultBoute();

        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.divideBoute(bFromDec).getDivResult();

        logger.info(String.format("%s: %s / %s = %s : actual -> %s", testName, decStringA, decStringB, decDivResultBoute, result));
        assertThat(result.toString()).isEqualTo(decDivResultBoute);
    }


    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testModBouteHex(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();
        String hexStringA = testData.getHexStringA();
        String hexStringB = testData.getHexStringB();
        String hexModResultBoute = testData.getHexModResultBoute();

        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.divideBoute(bFromHex).getRemainder();

        logger.info(String.format("%s: %s mod %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexModResultBoute, result.toHexStringTwosComplement(size)));
        assertThat(hexModResultBoute).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testModBouteDec(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decModResultBoute = testData.getDecModResultBoute();

        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.divideBoute(bFromDec).getRemainder();

        logger.info(String.format("%s: %s mod %s = %s : actual -> %s", testName, decStringA, decStringB, decModResultBoute, result));
        assertThat(decModResultBoute).isEqualTo(result.toString());
    }

    public static class TestData {
        String testName;
        int size;
        String hexStringA;
        String decStringA;
        String hexStringB;
        String decStringB;
        String hexAddResult;
        String decAddResult;
        String hexSubResult;
        String decSubResult;
        String hexMulResult;
        String decMulResult;
        String hexDivResultBoute;
        String decDivResultBoute;
        String hexModResultBoute;
        String decModResultBoute;
        String hexDivResult;
        String decDivResult;
        String hexModResult;
        String decModResult;

        public TestData(
                String testName,
                int size,
                String hexStringA,
                String decStringA,
                String hexStringB,
                String decStringB,
                String hexAddResult,
                String decAddResult,
                String hexSubResult,
                String decSubResult,
                String hexMulResult,
                String decMulResult,
                String hexDivResultBoute,
                String decDivResultBoute,
                String hexModResultBoute,
                String decModResultBoute,
                String hexDivResult,
                String decDivResult,
                String hexModResult,
                String decModResult
        ) {
            this.testName = testName;
            this.size = size;
            this.hexStringA = hexStringA;
            this.decStringA = decStringA;
            this.hexStringB = hexStringB;
            this.decStringB = decStringB;
            this.hexAddResult = hexAddResult;
            this.decAddResult = decAddResult;
            this.hexSubResult = hexSubResult;
            this.decSubResult = decSubResult;
            this.hexMulResult = hexMulResult;
            this.decMulResult = decMulResult;
            this.hexDivResultBoute = hexDivResultBoute;
            this.decDivResultBoute = decDivResultBoute;
            this.hexModResultBoute = hexModResultBoute;
            this.decModResultBoute = decModResultBoute;
            this.hexDivResult = hexDivResult;
            this.decDivResult = decDivResult;
            this.hexModResult = hexModResult;
            this.decModResult = decModResult;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String testName = "";
            String line = "";
            String hexStringA = "";
            String decStringA = "";
            String hexStringB = "";
            String decStringB = "";
            String hexAddResult = "";
            String decAddResult = "";
            String hexSubResult = "";
            String decSubResult = "";
            String hexMulResult = "";
            String decMulResult = "";
            String hexDivResultBoute = "";
            String decDivResultBoute = "";
            String hexModResultBoute = "";
            String decModResultBoute = "";
            String hexDivResult = "";
            String decDivResult = "";
            String hexModResult = "";
            String decModResult = "";
            int size = 0;

            InputStream fis = new FileInputStream("src/test/resources/arithmetic_tests.txt");
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
                            size = Integer.valueOf(line.substring(2)) / 4;
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
                        case '+':
                            hexAddResult = line.substring(2);
                            break;
                        case 'C':
                            decAddResult = line.substring(2);
                            break;
                        case '-':
                            hexSubResult = line.substring(2);
                            break;
                        case 'D':
                            decSubResult = line.substring(2);
                            break;
                        case '*':
                            hexMulResult = line.substring(2);
                            break;
                        case 'E':
                            decMulResult = line.substring(2);
                            break;
                        case '/':
                            hexDivResultBoute = line.substring(2);
                            break;
                        case 'F':
                            decDivResultBoute = line.substring(2);
                            break;
                        case '%':
                            hexModResultBoute = line.substring(2);
                            break;
                        case 'G':
                            decModResultBoute = line.substring(2);
                            break;
                        case 'n':
                            hexDivResult = line.substring(2);
                            break;
                        case 'N':
                            decDivResult = line.substring(2);
                            break;
                        case 'm':
                            hexModResult = line.substring(2);
                            break;
                        case 'M':
                            decModResult = line.substring(2);

                            dataList.add(
                                    new TestData(
                                            testName,
                                            size,
                                            hexStringA,
                                            decStringA,
                                            hexStringB,
                                            decStringB,
                                            hexAddResult,
                                            decAddResult,
                                            hexSubResult,
                                            decSubResult,
                                            hexMulResult,
                                            decMulResult,
                                            hexDivResultBoute,
                                            decDivResultBoute,
                                            hexModResultBoute,
                                            decModResultBoute,
                                            hexDivResult,
                                            decDivResult,
                                            hexModResult,
                                            decModResult
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

        public String getHexAddResult() {
            return hexAddResult;
        }

        public String getDecAddResult() {
            return decAddResult;
        }

        public String getHexSubResult() {
            return hexSubResult;
        }

        public String getDecSubResult() {
            return decSubResult;
        }

        public String getHexMulResult() {
            return hexMulResult;
        }

        public String getDecMulResult() {
            return decMulResult;
        }

        public String getHexDivResultBoute() {
            return hexDivResultBoute;
        }

        public String getDecDivResultBoute() {
            return decDivResultBoute;
        }

        public String getHexModResultBoute() {
            return hexModResultBoute;
        }

        public String getDecModResultBoute() {
            return decModResultBoute;
        }

        public String getHexDivResult() {
            return hexDivResult;
        }

        public String getDecDivResult() {
            return decDivResult;
        }

        public String getHexModResult() {
            return hexModResult;
        }

        public String getDecModResult() {
            return decModResult;
        }
    }
}