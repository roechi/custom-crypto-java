package de.remus.crypto.bigint.rsa;

import de.remus.crypto.bigint.BigInt;

public class PrivateKey {
    private BigInt p;
    private BigInt q;
    private BigInt d;
    private BigInt n;

    public PrivateKey(BigInt p, BigInt q, BigInt d, BigInt n) {
        this.p = p;
        this.q = q;
        this.d = d;
        this.n = n;
    }

    public BigInt getP() {
        return p;
    }

    public BigInt getQ() {
        return q;
    }

    public BigInt getD() {
        return d;
    }

    public BigInt getN() {
        return n;
    }

    @Override
    public String toString() {
        return "PrivateKey{" +
                "p=" + p +
                ", q=" + q +
                ", d=" + d +
                ", n=" + n +
                '}';
    }
}
