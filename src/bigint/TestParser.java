package bigint;

import java.io.*;
import java.nio.charset.Charset;

public class TestParser {

    private String path;

    public TestParser(String filePath) {
        path = filePath;
    }

    public void parseAndRun() throws IOException {
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
        int failed = 0;
        try (
                InputStream fis = new FileInputStream(path);
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);
        ) {
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
                    }
                    if (line.charAt(0) == 'D') {
                        operandD = new BigInt(line.substring(2));
                        System.out.println(testName + ": " + operandA + " - " + operandB + " = " + operandD + " : " + operandA.subtract(operandB) + " -> " + operandA.subtract(operandB).equals(operandD));
                        if (!operandA.subtract(operandB).equals(operandD)) {
                            failed++;
                        }
                    }
                    if (line.charAt(0) == 'E') {
                        operandE = new BigInt(line.substring(2));
                        System.out.println(testName + ": " + operandA + " * " + operandB + " = " + operandE + " : " + operandA.multiply(operandB) + " -> " + operandA.multiply(operandB).equals(operandE));
                        if (!operandA.multiply(operandB).equals(operandE)) {
                            failed++;
                        }
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
                            try {
                                BigIntDiv divResult = operandA.divide(operandB);
                            } catch (IllegalArgumentException e) {
                                success = true;
                                System.out.println(testName + ": " + operandA + " / " + operandB + " = undefined "  + success);
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
                    }
                }
            }
            System.out.println("Tests failed: " + failed);
        }
    }
}
