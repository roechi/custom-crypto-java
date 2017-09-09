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
public class InversionBatchTests {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testInversionAtoB(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();
        BigInt bigIntA = BigInt.fromHexStringTwosComplement(testData.getA());

        BigInt inverted = BigInt.negative(bigIntA);

        logger.info(String.format("%s: %s * (-1) = %s -> actual: %s", testName, testData.getA(), testData.getB(), inverted.toHexStringTwosComplement(size / 4)));
        assertThat(inverted.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getB());
    }

    public static class TestData {
        String testName;
        String a;
        String b;
        int size;

        public TestData(String testName, String a, String b, int size) {
            this.testName = testName;
            this.a = a;
            this.b = b;
            this.size = size;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String line, testName = "";
            String a = "";
            String b = "";
            int s = 0;

            InputStream fis = new FileInputStream("src/test/resources/twos_complement_negation_tests.txt");
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);

            ArrayList<TestData> testData = new ArrayList<>();
            {
                while ((line = br.readLine()) != null) {
                    if (line.charAt(0) != '#') {
                        if (line.charAt(0) == 't') {
                            testName = line.substring(2);
                        }
                        if (line.charAt(0) == 's') {
                            s = Integer.valueOf(line.substring(2));
                        }
                        if (line.charAt(0) == 'a') {
                            a = line.substring(2);
                        }
                        if (line.charAt(0) == 'b') {
                            b = line.substring(2);
                            testData.add(new TestData(testName, a, b, s));
                        }
                    }
                }
            }

            return testData;
        }

        public String getTestName() {
            return testName;
        }

        public String getA() {
            return a;
        }

        public String getB() {
            return b;
        }

        public int getSize() {
            return size;
        }
    }
}