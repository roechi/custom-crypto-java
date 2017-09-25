package de.remus.crypto.bigint.rsa;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import de.remus.crypto.bigint.BigInt;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class RSAKeyGenBatchTest {

    Logger logger = Logger.getLogger(getClass());

    @Test
    @UseDataProvider(value = "keyGenData", location = TestData.class)
    public void testComparisonHexAToB(TestData testData) throws Exception {
        String decStringF = testData.getDecStringF();
        String decStringE = testData.getDecStringE();
        String decStringD = testData.getDecStringD();

        BigInt f = new BigInt(decStringF);
        BigInt e = new BigInt(decStringE);
        BigInt d = new BigInt(decStringD);

        assertThat(RSA.computeD(f, e)).isEqualTo(d);
        logger.info(testData.getTestName() + ": compute d for phi=" + decStringF + " and e=" + decStringE + " -> expected: " + decStringD + " actual: " + decStringD);
    }


    public static class TestData {
        String testName;
        String decStringP;
        String decStringQ;
        String decStringF;
        String decStringE;
        String decStringD;

        public TestData(
                String testName,
                String decStringP,
                String decStringQ,
                String decStringF,
                String decStringE,
                String decStringD
        ) {
            this.testName = testName;
            this.decStringP = decStringP;
            this.decStringQ = decStringQ;
            this.decStringF = decStringF;
            this.decStringE = decStringE;
            this.decStringD = decStringD;
        }

        @DataProvider
        public static List<TestData> keyGenData() throws IOException {
            String testName = "";
            String line = "";
            String decStringP = "";
            String decStringQ = "";
            String decStringF = "";
            String decStringE = "";
            String decStringD = "";

            InputStream fis = new FileInputStream("src/test/resources/key_gen_tests.txt");
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
                            break;
                        case 'q':
                            decStringQ = line.substring(2);
                            break;
                        case 'f':
                            decStringF = line.substring(2);
                            break;
                        case 'e':
                            decStringE = line.substring(2);
                            break;
                        case 'd':
                            decStringD = line.substring(2);

                            dataList.add(
                                    new TestData(
                                            testName,
                                            decStringP,
                                            decStringQ,
                                            decStringF,
                                            decStringE,
                                            decStringD
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

        public String getDecStringP() {
            return decStringP;
        }

        public String getDecStringQ() {
            return decStringQ;
        }

        public String getDecStringF() {
            return decStringF;
        }

        public String getDecStringE() {
            return decStringE;
        }

        public String getDecStringD() {
            return decStringD;
        }
    }
}