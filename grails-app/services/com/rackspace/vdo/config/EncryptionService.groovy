package com.rackspace.vdo.config

import org.springframework.beans.factory.InitializingBean

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.security.SecureRandom

class EncryptionService implements InitializingBean {
    /**
     * Name of the cipher to use for encryption.
     */
    private static final String CIPHER_NAME = 'AES/CBC/PKCS5Padding'

    /**
     * Name of the secret key type.
     */
    private static final String KEY_TYPE_NAME = 'AES'

    /**
     * Name of the algorithm to generate random bytes for use with the salt.
     */
    private static final String SECURE_RANDOM_NAME = 'SHA1PRNG'

    /**
     * Charset to use when converting strings to and from bytes.
     */
    private static final String CHARSET = 'UTF-8'

    /**
     * Super-dooper-secret password to be used as the seed for the encryption/decryption key.
     */
    String password

    /**
     * Encrypts the given string using AES-128 encryption.
     *
     * @param source
     * @return
     */
    String encrypt(String source) {
        Cipher cipher = Cipher.getInstance(CIPHER_NAME)

        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM_NAME)
        byte[] iv = new byte[cipher.getBlockSize()]
        secureRandom.nextBytes(iv)

        cipher.init(
            Cipher.ENCRYPT_MODE,
            new SecretKeySpec((byte[]) password.encodeAsMD5Bytes(), KEY_TYPE_NAME),
            new IvParameterSpec(iv)
        )

        byte[] encrypted = cipher.doFinal(source.getBytes(CHARSET))

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        outputStream.write(iv)
        outputStream.write(encrypted)

        return outputStream.toByteArray().encodeBase64().toString()
    }

    /**
     * Decrypts the given string using AES-128 encryption. Encrypted data must have
     * been generated using the {@link #encrypt} method.
     *
     * @param source
     * @return
     */
    String decrypt(String source) {
        byte[] encrypted = (byte[]) source.getBytes(CHARSET).decodeBase64()

        Cipher cipher = Cipher.getInstance(CIPHER_NAME)

        if (encrypted.size() < cipher.getBlockSize()) {
            throw new IllegalArgumentException("encrypted data is corrupt")
        }

        byte[] iv = encrypted[0..cipher.getBlockSize() - 1]
        encrypted = encrypted[cipher.getBlockSize()..encrypted.size() - 1]

        cipher.init(
            Cipher.DECRYPT_MODE,
            new SecretKeySpec((byte[]) password.encodeAsMD5Bytes(), KEY_TYPE_NAME),
            new IvParameterSpec(iv)
        )

        byte[] decrypted = cipher.doFinal(encrypted)

        return new String(decrypted, CHARSET)
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     * @throws Exception in the event of misconfiguration (such
     * as failure to set an essential property) or if initialization fails.
     */
    @Override
    void afterPropertiesSet() throws Exception {
        assert password, 'encryption/decryption password must be configured (crypto.password)'
    }
}
