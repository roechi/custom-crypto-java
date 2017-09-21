package de.remus.crypto.bigint;

public class GCDLinearCombination {
    private BigInt gcd;
    private BigInt u;
    private BigInt v;

    public GCDLinearCombination(BigInt gcd, BigInt u, BigInt v) {
        this.gcd = gcd;
        this.u = u;
        this.v = v;
    }

    public BigInt getGcd() {
        return gcd;
    }

    public BigInt getU() {
        return u;
    }

    public BigInt getV() {
        return v;
    }
}
