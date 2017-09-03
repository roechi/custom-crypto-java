package bigint;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public class BigInt {

    private final long base = (long) Math.pow(10, 8);

    private long[] cells;
    private final int size = 128;
    private int sign = 1;
    private int sPart = 0;
    private int digitsPerCell = 8;

    public final static BigInt ZERO = new BigInt("0");


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
        this.cells = new long[size];
        IntStream.range(0, cells.length).forEach(l -> this.cells[l] = cells[l]);
        reduce();
        if (this.cells[getsPart()] < 0) {
            this.cells[getsPart()] *= -1;
            sign = -1;
        }
    }

    public static BigInt negative(BigInt bigInt) {
        BigInt negative = new BigInt(bigInt.getCells());
        if (!bigInt.equals(ZERO)) {
            negative.setSign(bigInt.getSign() * -1);
        }
        return negative;
    }

    private void reduce() {
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

        if (isNegative()) {
            if (other.isNegative()) {
                return negative(negative(this).add(negative(other)));
            } else {
                return other.subtract(negative(this));
            }
        } else if (other.isNegative() && negative(other).compare(this) == 1) {
            return negative(negative(other).subtract(this));
        }

        long[] resultCells = new long[size];
        int highestSPart = sPart > other.getsPart() ? sPart : other.getsPart();

        IntStream.rangeClosed(0, highestSPart).forEach(i -> {
            long sum = sign * this.getCells()[i] + other.getSign() * other.getCells()[i];
            long carryOver = 0;
            if (sum < 0) {
                if (i == highestSPart) {
                    sum *= -1;
                } else {
                    carryOver = -1;
                    sum += base;
                }
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
            if (!other.isNegative()) {
                if (other.compare(this) == 1) {
                    return negative(other.subtract(this));
                }
            }
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

    public long estimate(long cell1, long cell2, long divCell, int thisSign, int otherSign) {
        long dividend = cell1 < divCell ? (cell1 * base) + cell2 : cell1;
        return (thisSign * dividend) / (otherSign * divCell);
    }

    public BigIntDiv divide(BigInt other) {
        if (other.equals(new BigInt("0"))) {
            throw new IllegalArgumentException("Division by zero!");
        }
        if (sPart < other.sPart) {
            return new BigIntDiv(ZERO, new BigInt(cells));
        }
        if (sPart == other.sPart && abs().compare(other.abs()) == -1) {
            return new BigIntDiv(ZERO, new BigInt(cells));
        }
        /**boolean flipSign = false;
         if (isNegative()) {
         this.setSign(1);
         flipSign = true;
         }
         boolean otherFlipSign = false;
         if (other.isNegative()) {
         other = negative(other);
         otherFlipSign = true;
         }
         if (compare(other) == -1) {
         return new BigIntDiv(new BigInt("0"), this);
         } else if (compare(other) == 0) {
         return new BigIntDiv(new BigInt("1"), new BigInt("0"));
         }
         if (flipSign) {
         this.setSign(-1);
         }
         if (otherFlipSign) {
         other = negative(other);
         }*/
        int k = other.getsPart();
        int l = sPart - k;
        BigInt r = new BigInt("0");
        for (int i = 0; i <= k; i++) {
            r.getCells()[i] = cells[l + i];
        }
        //r.setSign(sign);
        r.reduce();
        long[] qCells = new long[l + 1];
        for (int i = l; i >= 0; i--) {
            long e = 0;
            if (r.getsPart() < 1) {
                e = estimate(0L, r.getCells()[r.getsPart()], other.getCells()[other.getsPart()], sign, other.getSign());
            } else {
                e = estimate(r.getCells()[r.getsPart()], r.getCells()[r.getsPart() - 1], other.getCells()[other.getsPart()], sign, other.getSign());
            }
            BigInt tmp = other.multiply(new BigInt("" + e));
            if (tmp.getsPart() == r.getsPart()) {

                if (!tmp.equals(r)) {
                    while (tmp.compare(r) == 1) {
                        if (e > 0) {
                            e--;
                            tmp = tmp.subtract(other);
                        } else {
                            e++;
                            tmp = tmp.add(other);
                        }
                    }
                    while (r.subtract(tmp).compare(other.abs()) == 1) {
                        if (e < 0) {
                            e--;
                            tmp = tmp.subtract(other);
                        } else {
                            e++;
                            tmp = tmp.add(other);
                        }
                    }
                }
                qCells[i] = e;
                r = r.subtract(tmp);
            }
            if (i != 0) {
                r = r.multiply(new BigInt("" + base)).add(new BigInt("" + cells[i - 1]));
            }
        }

        BigInt q = new BigInt(qCells);
        return new BigIntDiv(q, r);
    }

    public int compare(BigInt other) {
        if (equals(other)) {
            return 0;
        }

        if (!this.isNegative() && other.isNegative()) {
            return 1;
        } else if (this.isNegative() && !other.isNegative()) {
            return -1;
        } else {
            if (sPart != other.getsPart()) {
                if (sPart > other.getsPart()) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                int count = sPart;
                int greater = 0;
                while (greater == 0 && count >= 0) {
                    if (cells[count] > other.getCells()[count]) {
                        greater = 1;
                    } else if (cells[count] < other.getCells()[count]) {
                        greater = -1;
                    }
                    count--;
                }
                return greater;
            }
        }
    }

    public BigInt abs() {
        BigInt abs = new BigInt(cells);
        abs.setSign(1);
        return abs;
    }
}

