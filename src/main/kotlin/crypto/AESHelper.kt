package crypto

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class AESHelper {

    companion object {
        /**
         * get random AES key
         */
        fun getRandomAESKey(keySize: Int = 256): SecretKey {
            val keyGenerator = javax.crypto.KeyGenerator.getInstance("AES")
            keyGenerator.init(keySize)
            return keyGenerator.generateKey()
        }

        /**
         * contruct key from byte array
         */
        fun constructKey(keyBytes: ByteArray): SecretKey {
            if (keyBytes.size != 16 && keyBytes.size != 24 && keyBytes.size != 32) {
                //throw java.lang.IllegalArgumentException("key bytes wrong for AES key")
            }
            return SecretKeySpec(keyBytes, "AES")
        }

        /**
         * NEVER USE SAME IV VALUE FOR ENCRYPT
         * iv value can be added to the end or beginning of the encrypted data.
         */
        fun ctrEncrypt(key: SecretKey, data: ByteArray): Array<ByteArray> {
            val cipher = Cipher.getInstance("AES/CTR/NoPadding")
            var bytes = ByteArray(16)
            Drbg.getSecureRandom().nextBytes(bytes)
            cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(bytes))
            bytes = ByteArray(12)
            return arrayOf(cipher.iv, cipher.doFinal(data))
        }

        /**
         * ctr mode decrypt
         */
        fun ctrDecrypt(key: SecretKey, iv: ByteArray, cipherText: ByteArray): ByteArray {
            val cipher = Cipher.getInstance("AES/CTR/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
            return cipher.doFinal(cipherText)
        }
    }
}