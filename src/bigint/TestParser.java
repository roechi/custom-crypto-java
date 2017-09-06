package bigint;

import java.io.*;
import java.nio.charset.Charset;

public class TestParser {

    public static void parseAndRunHexConversionTests(String path) throws IOException {
        String line, testName = "";
        String decimal = "";
        String hexadecimal = "";

        InputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);

        int failed = 0;
        int run = 0;

        {
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    if (line.charAt(0) == 't') {
                        testName = line.substring(2);
                    }
                    if (line.charAt(0) == 'd') {
                        decimal = line.substring(2);
                    }
                    if (line.charAt(0) == 'h') {
                        hexadecimal = line.substring(2);

                        BigInt bigIntDec = new BigInt(decimal);
                        BigInt bigIntFromHex = BigInt.fromHexString(hexadecimal);
                        String bigIntToHex = bigIntDec.toHexString();
                        boolean success = bigIntDec.equals(bigIntFromHex);
                        System.out.println(testName + " from Hex: " + hexadecimal + " -> " + "Expected: " + decimal + " Actual: " + bigIntFromHex + " -> " + success);
                        if (!success) {
                            failed++;
                        }
                        run++;
                        success = hexadecimal.equals(bigIntToHex);
                        System.out.println(testName + " to Hex: " + decimal + " -> " + "Expected: " + hexadecimal + " Actual: " + bigIntToHex + " -> " + success);
                        if (!success) {
                            failed++;
                        }
                        run++;
                    }
                }
            }
        }
        System.out.println("Failed tests: " + failed + " out of " + run + ".");
    }

    public static void parseAndRunMul10Tests(String path) throws IOException {
        String line, testName = "";
        BigInt a = new BigInt();
        BigInt b = new BigInt();
        String c = "";
        String d = "";
        String e = "";
        String f = "";
        String g = "";
        String h = "";

        InputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);

        int failed = 0;
        int run = 0;

        {
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    if (line.charAt(0) == 'a') {
                        a = BigInt.fromHexString(line.substring(2));
                    }
                    if (line.charAt(0) == 'b') {
                        b = BigInt.fromHexString(line.substring(2));
                        BigInt result = a.mul10(1);
                        boolean success = result.equals(b);
                        System.out.println(a + " * 10 -> expected: " + b + " actual: " + result + " -> " + success);
                        if (!success) {
                            failed++;
                        }
                        run++;
                    }
                    if (line.charAt(0) == 'c') {

                    }
                    if (line.charAt(0) == 'd') {

                    }
                    if (line.charAt(0) == 'e') {

                    }
                    if (line.charAt(0) == 'f') {

                    }
                    if (line.charAt(0) == 'g') {

                    }
                    if (line.charAt(0) == 'h') {

                    }


                }
            }
        }
        System.out.println("Failed tests: " + failed + " out of " + run + ".");
    }

    public static void parseAndRunArithmeticTests(String path) throws IOException {
        String line, testName = "";
        BigInt operandA = new BigInt("0");
        BigInt operandB = new BigInt("0");
        BigInt operandC = new BigInt("0");
        BigInt operandD = new BigInt("0");
        BigInt operandE = new BigInt("0");
        BigInt operandF = new BigInt("0");
        BigInt operandG = new BigInt("0");
        BigInt operandN = new BigInt("0");
        BigInt operandM = new BigInt("0");
        int run = 0;
        int failed = 0;

        InputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);

        {

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    if (line.charAt(0) == 't') {
                        testName = line.substring(2);
                    }
                    if (line.charAt(0) == 'A') {
                        operandA = new BigInt(line.substring(2));
                    }
                    if (line.charAt(0) == 'B') {
                        operandB = new BigInt(line.substring(2));
                    }
                    if (line.charAt(0) == 'C') {
                        operandC = new BigInt(line.substring(2));
                        System.out.println(testName + ": " + operandA + " + " + operandB + " = " + operandC + " : " + operandA.add(operandB) + " -> " + operandA.add(operandB).equals(operandC));
                        if (!operandA.add(operandB).equals(operandC)) {
                            failed++;
                        }
                        run++;
                    }
                    if (line.charAt(0) == 'D') {
                        operandD = new BigInt(line.substring(2));
                        System.out.println(testName + ": " + operandA + " - " + operandB + " = " + operandD + " : " + operandA.subtract(operandB) + " -> " + operandA.subtract(operandB).equals(operandD));
                        if (!operandA.subtract(operandB).equals(operandD)) {
                            failed++;
                        }
                        run++;
                    }
                    if (line.charAt(0) == 'E') {
                        operandE = new BigInt(line.substring(2));
                        System.out.println(testName + ": " + operandA + " * " + operandB + " = " + operandE + " : " + operandA.multiply(operandB) + " -> " + operandA.multiply(operandB).equals(operandE));
                        if (!operandA.multiply(operandB).equals(operandE)) {
                            failed++;
                        }
                        run++;
                    }
                    if (line.charAt(0) == 'F') {
                        if (line.length() == 2) {
                            operandF = BigInt.ZERO;
                        } else {
                            operandF = new BigInt(line.substring(2));
                        }
                    }
                    if (line.charAt(0) == 'G') {
                        boolean success = false;
                        if (line.length() == 2) {
                            operandG = BigInt.ZERO;
                            success = true;
                            try {
                                BigIntDiv divResult = operandA.divide(operandB);
                                success = false;
                            } catch (IllegalArgumentException e) {
                                System.out.println(testName + ": " + operandA + " / " + operandB + " = undefined " + success);
                            }
                        } else {
                            operandG = new BigInt(line.substring(2));
                            BigIntDiv divResult = operandA.divide(operandB);
                            success = divResult.getDivResult().equals(operandF) && divResult.getRemainder().equals(operandG);
                            System.out.println(testName + ": " + operandA + " / " + operandB + " = " + operandF + " | " + operandG + " : " + divResult.getDivResult() + " | " + divResult.getRemainder() + " -> " + success);
                        }
                        if (!success) {
                            failed++;
                        }
                        run++;
                    }
                }
            }
            System.out.println("Failed tests: " + failed + " out of " + run + ".");
        }
    }
}
