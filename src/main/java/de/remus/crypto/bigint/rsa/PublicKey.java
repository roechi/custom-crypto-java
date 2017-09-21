package de.remus.crypto.bigint.rsa;

import de.remus.crypto.bigint.BigInt;

public class PublicKey {
    private BigInt e;
    private BigInt n;

    public PublicKey(BigInt e, BigInt n) {
        this.e = e;
        this.n = n;
    }

    public BigInt getE() {
        return e;
    }

    public BigInt getN() {
        return n;
    }

    @Override
    public String toString() {
        return "PublicKey{" +
                "e=" + e +
                ", n=" + n +
                '}';
    }
}
