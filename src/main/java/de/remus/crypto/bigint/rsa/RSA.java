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
        BigInt phi = ZERO;
        BigInt n;
        PrivateKey privateKey = null;
        PublicKey publicKey = null;
        boolean foundKeys = false;
        while (!foundKeys) {
            while (!check.equals(ONE)) {
                p = findPrime(10, bitLength - 1);
                q = findPrime(10, bitLength);

                phi = p.subtract(ONE).multiply(q.subtract(ONE));
                check = e.gcd(phi);
            }
            d = computeD(phi, e);

            n = p.multiply(q);

            privateKey = new PrivateKey(p, q, d, n);
            publicKey = new PublicKey(e, n);

            BigInt plainText = BigInt.getRandomOdd(bitLength);
            BigInt encrypted = encrypt(publicKey, plainText);
            BigInt decrypted = decrypt(privateKey, encrypted);
            if (plainText.equals(decrypted)) {
                foundKeys = true;
            }
        }

        return new KeyPair(privateKey, publicKey);
    }

    public static BigInt computeD(BigInt phi, BigInt e) {
        BigInt d = e.modInverse(phi);
        if (d.compare(ZERO) == -1) {
            d = phi.add(d);
        }
        return d;
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
