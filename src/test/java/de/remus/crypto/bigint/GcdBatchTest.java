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
public class GcdBatchTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testNotPrimeFermat(TestData testData) throws Exception {
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decStringG = testData.getDecStringG();

        BigInt a = new BigInt(decStringA);
        BigInt b = new BigInt(decStringB);

        BigInt result = a.gcd(b);

        logger.info(String.format("%s: gcd(%s, %s) = %s actual: %s",
                testData.getTestName(), decStringA, decStringB, decStringG, result));
        assertThat(result.toString()).isEqualTo(decStringG);
    }

    public static class TestData {
        private String testName;
        private String decStringA;
        private String decStringB;
        private String decStringG;

        public TestData(String testName, String decStringA, String decStringB, String decStringG) {
            this.testName = testName;
            this.decStringA = decStringA;
            this.decStringB = decStringB;
            this.decStringG = decStringG;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String line = "";
            String testName = "";
            String decStringA = "";
            String decStringB = "";
            String decStringG = "";

            InputStream fis = new FileInputStream("src/test/resources/gcd_tests.txt");
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
                            decStringA = line.substring(2);
                            break;
                        case 'b':
                            decStringB = line.substring(2);
                            break;
                        case 'g':
                            decStringG = line.substring(2);
                            dataList.add(new TestData(
                                    testName,
                                    decStringA,
                                    decStringB,
                                    decStringG
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

        public String getDecStringA() {
            return decStringA;
        }

        public String getDecStringB() {
            return decStringB;
        }

        public String getDecStringG() {
            return decStringG;
        }
    }
}
