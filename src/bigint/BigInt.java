package bigint;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public class BigInt {

    private final long base = (long) Math.pow(10, 8);

    private long[] cells;
    private final int size = 128;
    private int sign = 1;
    private int sPart;
    private int digitsPerCell = 8;


    public BigInt(String s) {
        String trimmed;
        if (s.charAt(0) == '-') {
            sign = -1;
            trimmed = s.substring(1);
        } else {
            trimmed = s;
        }
        int remainder = trimmed.length() % digitsPerCell;
        int numberOfCells = trimmed.length() / digitsPerCell;
        sPart = remainder > 0 ? numberOfCells : numberOfCells - 1;
        cells = new long[size];
        IntStream
                .range(0, numberOfCells)
                .forEach(i -> getCells()[i] = readCellFromString(trimmed, i));

        if (remainder > 0) {
            getCells()[sPart] = Long.valueOf(trimmed.substring(0, trimmed.length() - numberOfCells * digitsPerCell));
        }
    }

    public BigInt(long[] cells) {
        this.cells = cells;
        reduce(cells);
        if (cells[getsPart()] < 0) {
            cells[getsPart()] *= -1;
            sign = -1;
        }
    }

    public static BigInt negative(BigInt bigInt) {
        BigInt negative = new BigInt(bigInt.getCells());
        negative.setSign(bigInt.getSign() * -1);
        return negative;
    }

    private void reduce(long[] cells) {
        IntStream.range(0, size).forEach(i -> {
            if (cells[i] > 0)
                sPart = i;
        });
    }

    private Long readCellFromString(String s, int i) {
        return Long.valueOf(s.substring(s.length() - ((i + 1) * digitsPerCell), s.length() - (i * digitsPerCell)));
    }

    @Override
    public String toString() {
        String tail = reverseOrderStream(IntStream.range(0, sPart))
                .mapToLong(i -> getCells()[i])
                .mapToObj(Long::toString)
                .map(this::fillZeros)
                .reduce((a, b) -> a + b)
                .orElse("");
        String number = Long.toString(getCells()[sPart]) + tail;
        return isNegative() ? "-" + number : number;
    }

    private String fillZeros(String s) {
        String zeroString = IntStream.range(0, digitsPerCell - s.length()).mapToObj(i -> "0").reduce((a, b) -> a + b).orElse("");
        return zeroString + s;
    }

    private static IntStream reverseOrderStream(IntStream intStream) {
        int[] tempArray = intStream.toArray();
        return IntStream.range(1, tempArray.length + 1).boxed()
                .mapToInt(i -> tempArray[tempArray.length - i]);
    }

    public BigInt add(BigInt other) {

        long[] resultCells = new long[size];

        IntStream.range(0, size).forEach(i -> {
            long sum = sign * this.getCells()[i] + other.getSign() * other.getCells()[i];
            long carryOver = 0;
            if (sum < 0) {
                carryOver = -1;
                sum += base;
            } else {
                carryOver = sum / base;
                sum = sum % base;
            }
            resultCells[i] += sum;
            if (i < size - 1) {
                resultCells[i + 1] += carryOver;
            } else if (carryOver > 0) {
                throw new OverFlowException();
            }
        });

        return new BigInt(resultCells);
    }

    public long[] getCells() {
        return cells;
    }

    public int getSize() {
        return size;
    }

    public int getsPart() {
        return sPart;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public boolean isNegative() {
        return sign == -1;
    }

    @Override
    public boolean equals(Object obj) {
        assert obj instanceof BigInt;
        BigInt other = (BigInt) obj;
        return sPart == other.sPart && Arrays.equals(cells, other.cells);
    }

    public BigInt subtract(BigInt other) {
        if (isNegative()) {
            if (other.isNegative()) {
                return negative(other).subtract(negative(this));
            } else {
                return negative(negative(this).add(other));
            }
        } else {
            return add(negative(other));
        }
    }

    public BigInt multiply(BigInt other) {
        BigInt anInt = new BigInt("0");
        int length = sPart > other.getsPart() ? 2 * sPart + 1 : 2 * other.getsPart() + 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                long temp = cells[j] * other.cells[i];
                String zeros = IntStream.range(0, (i + j) * 8).mapToObj($ -> "0").reduce((a, b) -> a + b).orElse("");
                anInt = anInt.add(new BigInt("" + temp + zeros));
            }
        }

        return anInt;
    }
}
