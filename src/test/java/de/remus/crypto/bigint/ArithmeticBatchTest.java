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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class ArithmeticBatchTest {


    Logger logger = Logger.getLogger(getClass());

    @DataProvider
    public static Object[][] comparisonData() throws IOException {
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
        int expectedCompAB = 0;
        int expectedCompBA = 0;

        InputStream fis = new FileInputStream("src/test/resources/arithmetic_tests.txt");
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        List<List<Object>> dataList = new ArrayList<>();

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
                                Arrays.asList(
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

        Object[][] result = new Object[dataList.size()][20];
        for (int i = 0; i < dataList.size(); i++) {
            List currentList = dataList.get(i);
            for (int j = 0; j < 20; j++) {
                result[i][j] = currentList.get(j);
            }
        }
        return result;
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testAdditionHex(
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
    ) throws Exception {
        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.add(bFromHex);
        logger.info(String.format("%s: %s + %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexAddResult, result.toHexStringTwosComplement(size)));
        assertThat(hexAddResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testAdditionDec(
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
    ) throws Exception {
        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.add(bFromDec);
        logger.info(String.format("%s: %s + %s = %s : actual -> %s", testName, decStringA, decStringB, decAddResult, result));
        assertThat(decAddResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testSubtractionHex(
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
    ) throws Exception {
        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.subtract(bFromHex);
        logger.info(String.format("%s: %s - %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexSubResult, result.toHexStringTwosComplement(size)));
        assertThat(hexSubResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testSubtractionDec(
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
    ) throws Exception {
        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.subtract(bFromDec);
        logger.info(String.format("%s: %s - %s = %s : actual -> %s", testName, decStringA, decStringB, decSubResult, result));
        assertThat(decSubResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testMultiplicationHex(
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
    ) throws Exception {
        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.multiply(bFromHex);
        logger.info(String.format("%s: %s * %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexMulResult, result.toHexStringTwosComplement(size)));
        assertThat(hexMulResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testMultiplicationDec(
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
    ) throws Exception {
        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.multiply(bFromDec);
        logger.info(String.format("%s: %s + %s = %s : actual -> %s", testName, decStringA, decStringB, decMulResult, result));
        assertThat(decMulResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testDivisionHex(
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
    ) throws Exception {
        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.divide(bFromHex).getDivResult();
        logger.info(String.format("%s: %s / %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexDivResult, result.toHexStringTwosComplement(size)));
        assertThat(hexDivResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testDivisionDec(
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
    ) throws Exception {
        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.divide(bFromDec).getDivResult();
        logger.info(String.format("%s: %s / %s = %s : actual -> %s", testName, decStringA, decStringB, decDivResult, result));
        assertThat(decDivResult).isEqualTo(result.toString());
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testModHex(
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
    ) throws Exception {
        BigInt aFromHex = BigInt.fromHexStringTwosComplement(hexStringA);
        BigInt bFromHex = BigInt.fromHexStringTwosComplement(hexStringB);
        BigInt result = aFromHex.divide(bFromHex).getRemainder();
        logger.info(String.format("%s: %s mod %s = %s : actual -> %s", testName, hexStringA, hexStringB, hexModResult, result.toHexStringTwosComplement(size)));
        assertThat(hexModResult).isEqualTo(result.toHexStringTwosComplement(size));
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testModDec(
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
    ) throws Exception {
        BigInt aFromDec = new BigInt(decStringA);
        BigInt bFromDec = new BigInt(decStringB);
        BigInt result = aFromDec.divide(bFromDec).getRemainder();
        logger.info(String.format("%s: %s mod %s = %s : actual -> %s", testName, decStringA, decStringB, decModResult, result));
        assertThat(decModResult).isEqualTo(result.toString());
    }

}