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
public class ShiftBatchTests {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    @UseDataProvider(value = "testData", location = TestData.class)
    public void testRightShift(TestData testData) throws Exception {
        String testName = testData.getTestName();
        int size = testData.getSize();

        BigInt bigInt = BigInt.fromHexStringTwosComplement(testData.getA());

        logger.info(String.format("%s: %s >> 0 %s : actual -> %s", testName, testData.getA(), testData.getA(), bigInt.toHexStringTwosComplement(size / 4)));
        assertThat(bigInt.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getA());

        bigInt = bigInt.shiftRight(1);
        logger.info(String.format("%s: %s >> 1 %s : actual -> %s", testName, testData.getA(), testData.getB(), bigInt.toHexStringTwosComplement(size / 4)));
        assertThat(bigInt.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getB());

        bigInt = bigInt.shiftRight(1);
        logger.info(String.format("%s: %s >> 2 %s : actual -> %s", testName, testData.getA(), testData.getC(), bigInt.toHexStringTwosComplement(size / 4)));
        assertThat(bigInt.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getC());

        bigInt = bigInt.shiftRight(1);
        logger.info(String.format("%s: %s >> 3 %s : actual -> %s", testName, testData.getA(), testData.getD(), bigInt.toHexStringTwosComplement(size / 4)));
        assertThat(bigInt.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getD());

        bigInt = bigInt.shiftRight(1);
        logger.info(String.format("%s: %s >> 4 %s : actual -> %s", testName, testData.getA(), testData.getE(), bigInt.toHexStringTwosComplement(size / 4)));
        assertThat(bigInt.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getE());

        bigInt = bigInt.shiftRight(1);
        logger.info(String.format("%s: %s >> 5 %s : actual -> %s", testName, testData.getA(), testData.getF(), bigInt.toHexStringTwosComplement(size / 4)));
        assertThat(bigInt.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getF());

        bigInt = bigInt.shiftRight(1);
        logger.info(String.format("%s: %s >> 6 %s : actual -> %s", testName, testData.getA(), testData.getG(), bigInt.toHexStringTwosComplement(size / 4)));
        assertThat(bigInt.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getG());

        bigInt = bigInt.shiftRight(1);
        logger.info(String.format("%s: %s >> 7 %s : actual -> %s", testName, testData.getA(), testData.getH(), bigInt.toHexStringTwosComplement(size / 4)));
        assertThat(bigInt.toHexStringTwosComplement(size / 4)).isEqualTo(testData.getH());
    }

    public static class TestData {

        String testName;
        int size;
        String a;
        String b;
        String c;
        String d;
        String e;
        String f;
        String g;
        String h;

        public TestData(
                String testName,
                int size,
                String a,
                String b,
                String c,
                String d,
                String e,
                String f,
                String g,
                String h
        ) {
            this.testName = testName;
            this.size = size;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
            this.h = h;
        }

        @DataProvider
        public static List<TestData> testData() throws IOException {
            String line, testName = "";
            String a = "";
            String b = "";
            String c = "";
            String d = "";
            String e = "";
            String f = "";
            String g = "";
            String h = "";
            int size = 0;

            BigInt aBigInt = new BigInt();

            InputStream fis = new FileInputStream("src/test/resources/shift_tests.txt");
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);

            ArrayList<TestData> testData = new ArrayList<>();
            {
                while ((line = br.readLine()) != null) {
                    switch (line.charAt(0)) {
                        case 't':
                            testName = line.substring(2);
                            break;
                        case 's':
                            size = Integer.valueOf(line.substring(2));
                            break;
                        case 'a':
                            a = line.substring(2);
                            break;
                        case 'b':
                            b = line.substring(2);
                            break;
                        case 'c':
                            c = line.substring(2);
                            break;
                        case 'd':
                            d = line.substring(2);
                            break;
                        case 'e':
                            e = line.substring(2);
                            break;
                        case 'f':
                            f = line.substring(2);
                            break;
                        case 'g':
                            g = line.substring(2);
                            break;
                        case 'h':
                            h = line.substring(2);
                            testData.add(new TestData(testName, size, a, b, c, d, e, f, g, h));
                            break;
                    }
                }
            }
            return testData;
        }

        public String getTestName() {
            return testName;
        }

        public int getSize() {
            return size;
        }

        public String getA() {
            return a;
        }

        public String getB() {
            return b;
        }

        public String getC() {
            return c;
        }

        public String getD() {
            return d;
        }

        public String getE() {
            return e;
        }

        public String getF() {
            return f;
        }

        public String getG() {
            return g;
        }

        public String getH() {
            return h;
        }
    }
}