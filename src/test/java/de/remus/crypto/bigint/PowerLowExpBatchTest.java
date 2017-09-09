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
public class PowerLowExpBatchTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testHighPowDec(TestData testData) throws Exception {
        String decStringA = testData.getDecStringA();
        String decStringB = testData.getDecStringB();
        String decStringC = testData.getDecStringC();
        String decStringD = testData.getDecStringD();
        String decStringE = testData.getDecStringE();
        String decStringF = testData.getDecStringF();
        String decStringG = testData.getDecStringG();
        String decStringH = testData.getDecStringH();
        String decStringI = testData.getDecStringI();

        BigInt base = new BigInt(decStringA);

        BigInt result = base.pow(0);
        logger.info(String.format("%s: %s ^ %d = %s -> actual: %s ",
                testData.getTestName(), decStringA, 0, decStringB, result));
        assertThat(result).isEqualTo(new BigInt(decStringB));

        result = base.pow(1);
        logger.info(String.format("%s: %s ^ %d = %s -> actual: %s ",
                testData.getTestName(), decStringA, 1, decStringC, result));
        assertThat(result).isEqualTo(new BigInt(decStringC));

        result = base.pow(2);
        logger.info(String.format("%s: %s ^ %d = %s -> actual: %s ",
                testData.getTestName(), decStringA, 2, decStringD, result));
        assertThat(result).isEqualTo(new BigInt(decStringD));

        result = base.pow(3);
        logger.info(String.format("%s: %s ^ %d = %s -> actual: %s ",
                testData.getTestName(), decStringA, 3, decStringE, result));
        assertThat(result).isEqualTo(new BigInt(decStringE));

        result = base.pow(4);
        logger.info(String.format("%s: %s ^ %d = %s -> actual: %s ",
                testData.getTestName(), decStringA, 4, decStringF, result));
        assertThat(result).isEqualTo(new BigInt(decStringF));

        result = base.pow(5);
        logger.info(String.format("%s: %s ^ %d = %s -> actual: %s ",
                testData.getTestName(), decStringA, 5, decStringG, result));
        assertThat(result).isEqualTo(new BigInt(decStringG));

        result = base.pow(6);
        logger.info(String.format("%s: %s ^ %d = %s -> actual: %s ",
                testData.getTestName(), decStringA, 6, decStringH, result));
        assertThat(result).isEqualTo(new BigInt(decStringH));

        result = base.pow(7);
        logger.info(String.format("%s: %s ^ %d = %s -> actual: %s ",
                testData.getTestName(), decStringA, 8, decStringI, result));
        assertThat(result).isEqualTo(new BigInt(decStringI));
    }

    public static class TestData {
        private String testName;
        private int size;
        private String decStringA;
        private String decStringB;
        private String decStringC;
        private String decStringD;
        private String decStringE;
        private String decStringF;
        private String decStringG;
        private String decStringH;
        private String decStringI;

        public TestData(
                String testName,
                int size,
                String decStringA,
                String decStringB,
                String decStringC,
                String decStringD,
                String decStringE,
                String decStringF,
                String decStringG,
                String decStringH,
                String decStringI
        ) {
            this.testName = testName;
            this.size = size;
            this.decStringA = decStringA;
            this.decStringB = decStringB;
            this.decStringC = decStringC;
            this.decStringD = decStringD;
            this.decStringE = decStringE;
            this.decStringF = decStringF;
            this.decStringG = decStringG;
            this.decStringH = decStringH;
            this.decStringI = decStringI;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String line = "";
            int size = 0;
            String testName = "";
            String decStringA = "";
            String decStringB = "";
            String decStringC = "";
            String decStringD = "";
            String decStringE = "";
            String decStringF = "";
            String decStringG = "";
            String decStringH = "";
            String decStringI = "";


            InputStream fis = new FileInputStream("src/test/resources/pow_low_tests.txt");
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
                            size = Integer.valueOf(line.substring(2));
                            break;
                        case 'A':
                            decStringA = line.substring(2);
                            break;
                        case 'B':
                            decStringB = line.substring(2);
                            break;
                        case 'C':
                            decStringC = line.substring(2);
                            break;
                        case 'D':
                            decStringD = line.substring(2);
                            break;
                        case 'E':
                            decStringE = line.substring(2);
                            break;
                        case 'F':
                            decStringF = line.substring(2);
                            break;
                        case 'G':
                            decStringG = line.substring(2);
                            break;
                        case 'H':
                            decStringH = line.substring(2);
                            break;
                        case 'I':
                            decStringI = line.substring(2);

                            dataList.add(new TestData(
                                            testName,
                                            size,
                                            decStringA,
                                            decStringB,
                                            decStringC,
                                            decStringD,
                                            decStringE,
                                            decStringF,
                                            decStringG,
                                            decStringH,
                                            decStringI
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

        public String getDecStringA() {
            return decStringA;
        }

        public String getDecStringB() {
            return decStringB;
        }

        public String getDecStringC() {
            return decStringC;
        }

        public String getDecStringD() {
            return decStringD;
        }

        public String getDecStringE() {
            return decStringE;
        }

        public String getDecStringF() {
            return decStringF;
        }

        public String getDecStringG() {
            return decStringG;
        }

        public String getDecStringH() {
            return decStringH;
        }

        public String getDecStringI() {
            return decStringI;
        }
    }

}
