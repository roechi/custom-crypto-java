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
public class ComparisonBatchTest {

    Logger logger = Logger.getLogger(getClass());

    @DataProvider
    public static Object[][] comparisonData() throws IOException {
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
        List<List<Object>> dataList = new ArrayList<>();

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
                                Arrays.asList(testName, hexStringA, decStringA, hexStringB, decStringB, expectedCompAB, expectedCompBA)
                        );
                        break;
                }
            }
        }

        Object[][] result = new Object[dataList.size()][7];
        for (int i = 0; i < dataList.size(); i++) {
            List currentList = dataList.get(i);
            for (int j = 0; j < 7; j++) {
                result[i][j] = currentList.get(j);
            }
        }
        return result;
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testComparisonHexAToB(String testName, String hexStringA, String decStringA, String hexStringB, String decStringB,
                                      int expectedCompAB, int expectedCompBA) throws Exception {
        int resultAB = BigInt.fromHexStringTwosComplement(hexStringA).compare(BigInt.fromHexStringTwosComplement(hexStringB));
        logger.info(String.format("%s: %s comp %s -> %d : actual -> %d", testName, hexStringA, hexStringB, expectedCompAB, resultAB));
        assertThat(expectedCompAB).isEqualTo(resultAB);
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testComparisonHexBToA(String testName, String hexStringA, String decStringA, String hexStringB, String decStringB,
                                      int expectedCompAB, int expectedCompBA) throws Exception {
        int resultBA = BigInt.fromHexStringTwosComplement(hexStringB).compare(BigInt.fromHexStringTwosComplement(hexStringA));
        logger.info(String.format("%s: %s comp %s -> %d : actual -> %d", testName, hexStringB, hexStringA, expectedCompBA, resultBA));
        assertThat(expectedCompBA).isEqualTo(resultBA);
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testComparisonDecAToB(String testName, String hexStringA, String decStringA, String hexStringB, String decStringB,
                                      int expectedCompAB, int expectedCompBA) throws Exception {
        int resultAB = new BigInt(decStringA).compare(new BigInt(decStringB));
        logger.info(String.format("%s: %s comp %s -> %d : actual -> %d", testName, decStringA, decStringB, expectedCompAB, resultAB));
        assertThat(expectedCompAB).isEqualTo(resultAB);
    }

    @Test
    @UseDataProvider("comparisonData")
    public void testComparisonDecBToA(String testName, String hexStringA, String decStringA, String hexStringB, String decStringB,
                                      int expectedCompAB, int expectedCompBA) throws Exception {
        int resultBA = new BigInt(decStringB).compare(new BigInt(decStringA));
        logger.info(String.format("%s: %s comp %s -> %d : actual -> %d", testName, decStringB, decStringA, expectedCompBA, resultBA));
        assertThat(expectedCompBA).isEqualTo(resultBA);
    }
}