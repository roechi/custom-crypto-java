package de.remus.crypto.bigint;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EmpiricalPrimeBatchTest {

    Logger logger = Logger.getLogger(this.getClass());

    public static List<BigInt> primesData() throws IOException {
        return testData("src/test/resources/primes.txt");
    }

    public static List<BigInt> pseudoprimesData() throws IOException {
        return testData("src/test/resources/pseudoprimes.txt");
    }

    public static List<BigInt> strongPseudoPrimes() throws IOException {
        return testData("src/test/resources/strong_pseudoprimes.txt");
    }

    public static List<BigInt> testData(String path) throws IOException {
        String line = "";

        InputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        List<BigInt> dataList = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            if (line.charAt(0) != '#') {
                dataList.add(new BigInt(line.trim()));
            }
        }

        return dataList;
    }

    @Test
    @Ignore
    public void testPrimeEmpirical() throws Exception {
        List<BigInt> primes = primesData();
        int rounds = 20;
        float accuracy = 0;
        int runs = 100;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithFermat(primes, true, rounds, "Testing real primes with fermat...");
        }
        logger.info("Fermat accuracy for primes over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
        accuracy = 0;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithEuler(primes, true, rounds, "Testing real primes with euler...");
        }
        logger.info("Euler accuracy for primes over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
        accuracy = 0;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithMR(primes, true, rounds, "Testing real primes with MR...");
        }
        logger.info("MR accuracy for primes over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
    }

    @Test
    @Ignore
    public void testPseudoPrimeEmpirical() throws Exception {
        List<BigInt> primes = pseudoprimesData();
        int rounds = 20;
        float accuracy = 0;
        int runs = 100;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithFermat(primes, false, rounds, "Testing pseudo primes with fermat...");
        }
        logger.info("Fermat accuracy for pseudo primes over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
        accuracy = 0;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithEuler(primes, false, rounds, "Testing pseudo primes with euler...");
        }
        logger.info("Euler accuracy for pseudo primes over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
        accuracy = 0;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithMR(primes, false, rounds, "Testing pseudo primes with MR...");
        }
        logger.info("MR accuracy for pseudo primes over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
    }

    @Test
    @Ignore
    public void testStrongPseudoPrimes() throws Exception {
        List<BigInt> primes = strongPseudoPrimes();
        int rounds = 20;
        float accuracy = 0;
        int runs = 100;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithFermat(primes, false, rounds, "Testing carmichael numbers with fermat...");
        }
        logger.info("Fermat accuracy for carmichael numbers over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
        accuracy = 0;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithEuler(primes, false, rounds, "Testing carmichael numbers with euler...");
        }
        logger.info("Euler accuracy for carmichael numbers over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
        accuracy = 0;
        for (int i = 0; i < runs; i++) {
            accuracy += testWithMR(primes, false, rounds, "Testing carmichael numbers with MR...");
        }
        logger.info("MR accuracy for carmichael numbers over " + runs + " runs with " + rounds + " probes: " + accuracy / runs);
    }

    @Test
    @Ignore
    public void millerRabinExperiment() throws Exception {
        List<BigInt> pseudoPrimes = pseudoprimesData();
        List<BigInt> carmichaelNumbers = strongPseudoPrimes();
        pseudoPrimes.addAll(carmichaelNumbers);
        int runs = 100;
        int fails = 0;
        int count = 0;
        for (int i = 1; i <= 19; i++) {
            for (int j = 0; j < runs; j++) {
                for (BigInt nonPrime : pseudoPrimes) {
                    if (nonPrime.isPrimeMR(i)) {
                        fails++;
                    }
                    count++;
                }
            }
        }
        logger.info("Accuracy for MR with 1 to 19 probes over " + runs + " runs: " + (100. - (100. / count * fails)));
        fails = 0;
        count = 0;
        for (int i = 20; i <= 29; i++) {
            for (int j = 0; j < runs; j++) {
                for (BigInt nonPrime : pseudoPrimes) {
                    if (nonPrime.isPrimeMR(i)) {
                        fails++;
                    }
                    count++;
                }
            }
        }
        logger.info("Accuracy for MR with 20 to 29 probes over " + runs + " runs: " + (100. - (100. / count * fails)));
        fails = 0;
        count = 0;
        for (int i = 30; i <= 39; i++) {
            for (int j = 0; j < runs; j++) {
                for (BigInt nonPrime : pseudoPrimes) {
                    if (nonPrime.isPrimeMR(i)) {
                        fails++;
                    }
                    count++;
                }
            }
        }
        logger.info("Accuracy for MR with 30 to 39 probes over " + runs + " runs: " + (100. - (100. / count * fails)));
        fails = 0;
        count = 0;
        for (int i = 40; i <= 50; i++) {
            for (int j = 0; j < runs; j++) {
                for (BigInt nonPrime : pseudoPrimes) {
                    if (nonPrime.isPrimeMR(i)) {
                        fails++;
                    }
                    count++;
                }
            }
        }
        logger.info("Accuracy for MR with 40 to 50 probes over " + runs + " runs: " + (100. - (100. / count * fails)));
    }

    private float testWithFermat(List<BigInt> numbers, boolean shouldBePrimes, int rounds, String comment) {
        int fails = 0;
        for (BigInt number : numbers) {
            if (number.isPrimeFermat(rounds) != shouldBePrimes) {
                fails++;
            }
        }
        return 100f - (100f * fails / numbers.size());
    }

    private float testWithEuler(List<BigInt> numbers, boolean shouldBePrimes, int rounds, String comment) {
        int fails = 0;
        for (BigInt number : numbers) {
            if (number.isPrimeEuler(rounds) != shouldBePrimes) {
                fails++;
            }
        }
        return 100f - (100f * fails / numbers.size());
    }

    private float testWithMR(List<BigInt> numbers, boolean shouldBePrimes, int rounds, String comment) {
        int fails = 0;
        for (BigInt number : numbers) {
            if (number.isPrimeMR(rounds) != shouldBePrimes) {
                fails++;
            }
        }
        return 100f - (100f * fails / numbers.size());
    }
}