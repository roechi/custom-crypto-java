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
public class RSACorrectEncryptionBatchTest {

    Logger logger = Logger.getLogger(getClass());

    @Test
    @UseDataProvider(value = "keyGenData", location = TestData.class)
    public void testEncription(TestData testData) throws Exception {
        String testName = testData.getTestName();
        String decStringP = testData.getDecStringP();
        String decStringQ = testData.getDecStringQ();
        String decStringF = testData.getDecStringF();
        String decStringE = testData.getDecStringE();
        String decStringD = testData.getDecStringD();
        String decStringN = testData.getDecStringN();
        String gPlain = testData.getDecStringGPlain();
        String gEncr = testData.getDecStringGEncr();
        String hPlain = testData.getDecStringHPlain();
        String hEncr = testData.getDecStringHEncr();
        String iPlain = testData.getDecStringIPlain();
        String iEncr = testData.getDecStringIEncr();

        BigInt p = new BigInt(decStringP);
        BigInt q = new BigInt(decStringQ);
        BigInt e = new BigInt(decStringE);
        BigInt d = new BigInt(decStringD);
        BigInt n = new BigInt(decStringN);

        PublicKey publicKey = new PublicKey(e, n);
        PrivateKey privateKey = new PrivateKey(p, q, d, n);

        BigInt g = new BigInt(gPlain);
        BigInt G = new BigInt(gEncr);
        BigInt h = new BigInt(hPlain);
        BigInt H = new BigInt(hEncr);
        BigInt i = new BigInt(iPlain);
        BigInt I = new BigInt(iEncr);

        String gEncrResult = RSA.encrypt(publicKey, g).toString();
        logger.info(testData.getTestName() + ": encrypt " + gPlain + " -> expected: " + gEncr + " actual: " + gEncrResult);
        assertThat(gEncrResult).isEqualTo(gEncr);
        String gDecrResult = RSA.decrypt(privateKey, G).toString();
        logger.info(testData.getTestName() + ": decrypt " + gEncr + " -> expected: " + gPlain + " actual: " + gDecrResult);
        assertThat(gDecrResult).isEqualTo(gPlain);

        String hEncrResult = RSA.encrypt(publicKey, h).toString();
        logger.info(testData.getTestName() + ": encrypt " + hPlain + " -> expected: " + hEncr + " actual: " + hEncrResult);
        assertThat(hEncrResult).isEqualTo(hEncr);
        String hDecrResult = RSA.decrypt(privateKey, H).toString();
        logger.info(testData.getTestName() + ": decrypt " + hEncr + " -> expected: " + hPlain + " actual: " + hDecrResult);
        assertThat(hDecrResult).isEqualTo(hPlain);

        String iEncrResult = RSA.encrypt(publicKey, i).toString();
        logger.info(testData.getTestName() + ": encrypt " + iPlain + " -> expected: " + iEncr + " actual: " + iEncrResult);
        assertThat(iEncrResult).isEqualTo(iEncr);
        String iDecrResult = RSA.decrypt(privateKey, I).toString();
        logger.info(testData.getTestName() + ": decrypt " + iEncr + " -> expected: " + iPlain + " actual: " + iDecrResult);
        assertThat(iDecrResult).isEqualTo(iPlain);
    }


    public static class TestData {
        String testName;
        String decStringP;
        String decStringQ;
        String decStringF;
        String decStringE;
        String decStringD;
        String decStringN;
        String decStringGPlain;
        String decStringGEncr;
        String decStringHPlain;
        String decStringHEncr;
        String decStringIPlain;
        String decStringIEncr;


        public TestData(
                String testName,
                String decStringP,
                String decStringQ,
                String decStringF,
                String decStringE,
                String decStringD,
                String decStringN,
                String decStringGPlain,
                String decStringGEncr,
                String decStringHPlain,
                String decStringHEncr,
                String decStringIPlain,
                String decStringIEncr
        ) {
            this.testName = testName;
            this.decStringP = decStringP;
            this.decStringQ = decStringQ;
            this.decStringF = decStringF;
            this.decStringE = decStringE;
            this.decStringD = decStringD;
            this.decStringN = decStringN;
            this.decStringGPlain = decStringGPlain;
            this.decStringGEncr = decStringGEncr;
            this.decStringHPlain = decStringHPlain;
            this.decStringHEncr = decStringHEncr;
            this.decStringIPlain = decStringIPlain;
            this.decStringIEncr = decStringIEncr;
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
            String decStringN = "";
            String decStringGPlain = "";
            String decStringGEncr = "";
            String decStringHPlain = "";
            String decStringHEncr = "";
            String decStringIPlain = "";
            String decStringIEncr = "";


            InputStream fis = new FileInputStream("src/test/resources/rsa_correct_tests.txt");
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
                            break;
                        case 'n':
                            decStringN = line.substring(2);
                            break;
                        case 'g':
                            decStringGPlain = line.substring(2);
                            break;
                        case 'G':
                            decStringGEncr = line.substring(2);
                            break;
                        case 'h':
                            decStringHPlain = line.substring(2);
                            break;
                        case 'H':
                            decStringHEncr = line.substring(2);
                            break;
                        case 'i':
                            decStringIPlain = line.substring(2);
                            break;
                        case 'I':
                            decStringIEncr = line.substring(2);

                            dataList.add(
                                    new TestData(
                                            testName,
                                            decStringP,
                                            decStringQ,
                                            decStringF,
                                            decStringE,
                                            decStringD,
                                            decStringN,
                                            decStringGPlain,
                                            decStringGEncr,
                                            decStringHPlain,
                                            decStringHEncr,
                                            decStringIPlain,
                                            decStringIEncr
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

        public String getDecStringN() {
            return decStringN;
        }

        public String getDecStringGPlain() {
            return decStringGPlain;
        }

        public String getDecStringGEncr() {
            return decStringGEncr;
        }

        public String getDecStringHPlain() {
            return decStringHPlain;
        }

        public String getDecStringHEncr() {
            return decStringHEncr;
        }

        public String getDecStringIPlain() {
            return decStringIPlain;
        }

        public String getDecStringIEncr() {
            return decStringIEncr;
        }
    }
}