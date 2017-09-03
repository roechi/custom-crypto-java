package bigint;

public class BigIntDiv {

    private BigInt divResult;
    private BigInt remainder;

    public BigIntDiv(BigInt divResult, BigInt remainder) {
        this.divResult = divResult;
        this.remainder = remainder;
    }

    public BigInt getDivResult() {
        return divResult;
    }

    public BigInt getRemainder() {
        return remainder;
    }
}
