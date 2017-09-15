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
public class NotAPrimeBatchTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testNotPrimeFermat(TestData testData) throws Exception {
        String decStringP = testData.getDecStringP();

        BigInt number = new BigInt(decStringP);

        boolean result = number.isPrimeFermat(30);

        logger.info(String.format("%s: Fermat: %s is prime -> %s ",
                testData.getTestName(), decStringP, result));
        assertThat(result).isEqualTo(false);
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testNotPrimeEuler(TestData testData) throws Exception {
        String decStringP = testData.getDecStringP();

        BigInt number = new BigInt(decStringP);

        boolean result = number.isPrimeEuler(30);

        logger.info(String.format("%s: Euler: %s is prime -> %s ",
                testData.getTestName(), decStringP, result));
        assertThat(result).isEqualTo(false);
    }

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testNotPrimeMillerRabin(TestData testData) throws Exception {
        String decStringP = testData.getDecStringP();

        BigInt number = new BigInt(decStringP);

        boolean result = number.isPrimeMR(30);

        logger.info(String.format("%s: Miller-Rabin: %s is prime -> %s ",
                testData.getTestName(), decStringP, result));
        assertThat(result).isEqualTo(false);
    }

    public static class TestData {
        private String testName;
        private String decStringP;

        public TestData(
                String testName,
                String decStringP
        ) {
            this.testName = testName;
            this.decStringP = decStringP;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String line = "";
            String testName = "";
            String decStringP = "";

            InputStream fis = new FileInputStream("src/test/resources/not_a_prime_tests.txt");
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            List<TestData> dataList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    switch (line.charAt(0)) {
                        case 't':
                            testName = line.substring(2);
                            break;
                        case 'p':
                            decStringP = line.substring(2);
                            dataList.add(new TestData(
                                    testName,
                                    decStringP
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

        public String getDecStringP() {
            return decStringP;
        }
    }
}
