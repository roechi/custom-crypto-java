package bigint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class BigInt {

    private final long base = (long) Math.pow(10, 8);

    private long[] cells;
    private final int size = 128;
    private int sign = 1;
    private int sPart = 0;
    private int digitsPerCell = 8;

    public final static BigInt ZERO = new BigInt(0);
    public final static BigInt ONE = new BigInt(1);

    public BigInt() {
        cells = new long[size];
    }

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

    public BigInt(long number) {
        if (number < 0) {
            sign = -1;
            number = number * -1;
        }
        ArrayList<Long> longs = new ArrayList<>();
        do {
            longs.add(number % base);
            number /= base;
        } while (number > 0);

        if (longs.size() > size) {
            throw new IllegalArgumentException("Number too large.");
        }
        cells = new long[size];
        IntStream.range(0, longs.size()).forEach(i -> cells[i] = longs.get(i));
        reduce();
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
            if (cells[i] != 0)
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
        return sPart == other.sPart && Arrays.equals(cells, other.cells) && sign == other.getSign();
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
        BigInt anInt = ZERO;
        int length = sPart > other.getsPart() ? 2 * sPart + 1 : 2 * other.getsPart() + 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                long temp = cells[j] * other.cells[i];
                String zeros = IntStream.range(0, (i + j) * 8).mapToObj($ -> "0").reduce((a, b) -> a + b).orElse("");
                anInt = anInt.add(new BigInt("" + temp + zeros));
            }
        }
        if (!anInt.equals(ZERO)) {
            anInt.setSign(sign * other.getSign());
        }
        return anInt;
    }

    public long estimate(long cell1, long cell2, long divCell, int thisSign, int otherSign) {
        long dividend = cell1 < divCell ? (cell1 * base) + cell2 : cell1;
        return (thisSign * dividend) / (otherSign * divCell);
    }

    public BigIntDiv divide(BigInt other) {
        return divideInternal(other, 1);
    }

    public BigIntDiv divideInternal(BigInt other, long factor) {
        if (other.equals(ZERO)) {
            throw new IllegalArgumentException("Division by zero!");
        }
        if (sPart > 0) {
            if (isNegative() && other.isNegative()) {
                BigInt result = negative(this).divide(negative(other)).getDivResult().add(ONE);
                BigInt remainder = this.subtract(result.multiply(other));
                return new BigIntDiv(result, remainder);
            }
            if (isNegative() && !other.isNegative()) {
                BigInt result = negative(this).divide(other).getDivResult().add(ONE);
                result.setSign(sign);
                BigInt remainder = this.subtract(result.multiply(other));
                return new BigIntDiv(result, remainder);
            }
        }
        if (sPart < other.sPart) {
            if (other.isNegative() && isNegative()) {
                BigInt one = ONE;
                BigInt remainder = this.subtract(other.multiply(one));
                return new BigIntDiv(one, remainder);
            }
            if (isNegative()) {
                BigInt minusOne = new BigInt("-1");
                BigInt remainder = this.subtract(other.multiply(minusOne));
                return new BigIntDiv(minusOne, remainder);
            } else {
                return new BigIntDiv(ZERO, new BigInt(cells));
            }
        }
        if (sPart == other.sPart && compare(other) == -1 && !isNegative() && !other.isNegative()) {
            return new BigIntDiv(ZERO, new BigInt(cells));
        }
        if (sPart > 0 && other.getsPart() > 0) {
            if (other.getCells()[other.getsPart()] < base / 2) {
                factor = base / (other.getCells()[other.getsPart()] + 1);
                BigInt bigIntFactor = new BigInt("" + factor);
                return multiply(bigIntFactor).divideInternal(other.multiply(bigIntFactor), factor);
            }
        }
        int k = other.getsPart();
        int l = sPart - k;
        BigInt r = new BigInt();
        for (int i = 0; i <= k; i++) {
            r.getCells()[i] = cells[l + i];
        }
        r.setSign(sign);
        r.reduce();
        long[] qCells = new long[l + 1];
        for (int i = l; i >= 0; i--) {
            long e = 0;
            e = getEstimate(other, r);
            BigInt tmp = other.multiply(new BigInt("" + e));
            if (i != 0 && tmp.getsPart() > r.getsPart()) {
                r = r.multiply(new BigInt("" + base)).add(new BigInt("" + (sign * cells[i - 1])));
                i--;
            }

            if (!tmp.equals(r)) {

                while (tmp.compare(r) == 1 && abs(e) < base) {
                    if (e == 0 && r.abs().subtract(other.abs()).abs().compare(ZERO) == 1) {
                        if ((r.isNegative() && !other.isNegative()) || (!r.isNegative() && other.isNegative())) {
                            e = -1;
                        } else {
                            e = 1;
                        }
                        tmp = other.multiply(new BigInt("" + e));
                    }
                    if ((r.isNegative() && e > 0) || (!r.isNegative() && e < 0)) {
                        e++;
                        tmp = tmp.add(other);
                    } else {
                        e--;
                        tmp = tmp.subtract(other);
                    }
                }

                while (r.subtract(tmp).compare(other.abs()) == 1 && abs(e) < base) {
                    if ((r.isNegative() && e > 0) || (!r.isNegative() && e < 0)) {
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
            if (i != 0) {
                r = r.multiply(new BigInt("" + base)).add(new BigInt("" + (sign * cells[i - 1])));
            }
        }

        BigInt q = new BigInt(qCells);
        if (factor != 1) {
            r = r.divide(new BigInt("" + factor)).getDivResult();
        }
        return new BigIntDiv(q, r);
    }

    private long getEstimate(BigInt other, BigInt r) {
        long e;
        if (r.getsPart() < 1) {
            e = estimate(0L, r.getCells()[r.getsPart()], other.getCells()[other.getsPart()], r.getSign(), other.getSign());
        } else {
            e = estimate(r.getCells()[r.getsPart()], r.getCells()[r.getsPart() - 1], other.getCells()[other.getsPart()], r.getSign(), other.getSign());
        }
        return e;
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
                    if (sign * cells[count] > other.getSign() * other.getCells()[count]) {
                        greater = 1;
                    } else if (sign * cells[count] < other.getSign() * other.getCells()[count]) {
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

    private long abs(long number) {
        return number < 0 ? number * -1 : number;
    }
}

