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
public class ConversionBatchTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testDecToHexConversion(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decString = testData.getDecString();
        String hexString = testData.getHexString();

        BigInt bigInt = new BigInt(decString);

        logger.info(String.format("%s: %s -> %s : actual -> %s", testName, decString, hexString, bigInt.toHexString()));
        assertThat(bigInt.toHexString()).isEqualTo(hexString);
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testHexToDecConversion(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decString = testData.getDecString();
        String hexString = testData.getHexString();

        BigInt bigInt = BigInt.fromHexString(hexString);

        logger.info(String.format("%s: %s -> %s : actual -> %s", testName, hexString, decString, bigInt.toString()));
        assertThat(bigInt.toString()).isEqualTo(decString);
    }

    public static class TestData {
        String testName;
        String decString;
        String hexString;

        public TestData(String testName, String decString, String hexString) {
            this.testName = testName;
            this.decString = decString;
            this.hexString = hexString;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String testName = "";
            String line = "";
            String decString = "";
            String hexString = "";

            InputStream fis = new FileInputStream("src/test/resources/convert_hex_tests.txt");
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            List<TestData> dataList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    switch (line.charAt(0)) {
                        case 't':
                            testName = line.substring(2);
                            break;
                        case 'd':
                            decString = line.substring(2);
                            break;
                        case 'h':
                            hexString = line.substring(2);

                            // parsing hex strings with a length north of 200 takes way to long
                            if (hexString.length() < 200) {
                                dataList.add(
                                        new TestData(testName, decString, hexString)
                                );
                            }
                            break;
                    }
                }
            }

            return dataList;
        }

        public String getTestName() {
            return testName;
        }

        public String getHexString() {
            return hexString;
        }

        public String getDecString() {
            return decString;
        }
    }
}