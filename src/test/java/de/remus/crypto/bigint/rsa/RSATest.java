package de.remus.crypto.bigint.rsa;

import de.remus.crypto.bigint.BigInt;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RSATest {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void shouldEncrypt() throws Exception {
        KeyPair keyPair = RSA.generateKeyPair(BigInt.THIRTEEN, 16);
        logger.info(keyPair);
        BigInt plain = new BigInt("7489");
        BigInt cipher = RSA.encrypt(keyPair.getPublicKey(), plain);
        BigInt decrypted = RSA.decrypt(keyPair.getPrivateKey(), cipher);

        assertThat(decrypted).isEqualTo(plain);
    }
}