package de.remus.crypto.bigint.rsa;

import de.remus.crypto.bigint.BigInt;

import static de.remus.crypto.bigint.BigInt.ONE;
import static de.remus.crypto.bigint.BigInt.ZERO;

public class RSA {

    public static KeyPair generateKeyPair(BigInt e, int bitLength) {
        BigInt p = ZERO;
        BigInt q = ZERO;
        BigInt d = ZERO;
        BigInt check = ZERO;
        BigInt n, phi;

        while (!check.equals(ONE) || d.compare(ZERO) == -1) {
            p = findPrime(10, bitLength - 1);
            q = findPrime(10, bitLength);

            phi = p.subtract(ONE).multiply(q.subtract(ONE));
            check = e.gcd(phi);
            d = e.modInverse(phi);
        }
        n = p.multiply(q);

        PrivateKey privateKey = new PrivateKey(p, q, d, n);
        PublicKey publicKey = new PublicKey(e, n);
        return new KeyPair(privateKey, publicKey);
    }

    private static BigInt findPrime(int testRounds, int lengthInBits) {
        BigInt p = ZERO;
        while (!p.isPrimeMR(testRounds) || p.equals(ZERO)) {
            p = BigInt.getRandomOdd(lengthInBits);
        }
        return p;
    }

    public static BigInt encrypt(PublicKey publicKey, BigInt plain) {
        return plain.powMod(publicKey.getE(), publicKey.getN());
    }

    public static BigInt decrypt(PrivateKey privateKey, BigInt cipher) {
        return cipher.powMod(privateKey.getD(), privateKey.getN());
    }
}
