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
public class PseudoPrimeFermatBatchTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Ignore
    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testNotPrimeFermat(TestData testData) throws Exception {
        String decStringP = testData.getDecStringP();
        String decStringA = testData.getDecStringA();

        BigInt pseudoPrime = new BigInt(decStringP);
        BigInt baseOfFailure = new BigInt(decStringA);

        boolean result = pseudoPrime.isPrimeFermat(new BigInt[]{baseOfFailure});

        logger.info(String.format("%s: Fermat: %s is prime -> %s ",
                testData.getTestName(), decStringP, result));
        assertThat(result).isEqualTo(true);
    }

    public static class TestData {
        private String testName;
        private String decStringP;
        private String decStringA;

        public TestData(String testName, String decStringP, String decStringA) {
            this.testName = testName;
            this.decStringP = decStringP;
            this.decStringA = decStringA;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String line = "";
            String testName = "";
            String decStringP = "";
            String decStringA = "";

            InputStream fis = new FileInputStream("src/test/resources/pseudo_prime_fermat.txt");
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
                        case 'p':
                            decStringP = line.substring(2);
                            dataList.add(new TestData(
                                    testName,
                                    decStringP,
                                    decStringA
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

        public String getDecStringA() {
            return decStringA;
        }
    }
}
