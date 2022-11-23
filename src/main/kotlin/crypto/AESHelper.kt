package crypto

import org.bouncycastle.util.encoders.Hex
import java.security.GeneralSecurityException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class AESHelper {

    companion object {
        fun getRandomAESKey(): SecretKey {
            val keyGenerator = javax.crypto.KeyGenerator.getInstance("AES", "BCFIPS")
            keyGenerator.init(256)
            return keyGenerator.generateKey()
        }

        fun constructKey(keyBytes: ByteArray): SecretKey {
            if (keyBytes.size != 16 && keyBytes.size != 24 && keyBytes.size != 32) {
                //throw java.lang.IllegalArgumentException("key bytes wrong for AES key")
            }
            return SecretKeySpec(keyBytes, "AES")
        }

        fun ctrEncrypt(key: SecretKey, data: ByteArray): Array<ByteArray> {
            val cipher = Cipher.getInstance("AES/CTR/NoPadding", "BCFIPS")
            var bytes = ByteArray(12)
            Drbg.getSecureRandom().nextBytes(bytes)
            cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(bytes))
            bytes = ByteArray(12)
            return arrayOf(cipher.iv, cipher.doFinal(data))
        }

        fun ctrDecrypt(key: SecretKey, iv: ByteArray, cipherText: ByteArray): ByteArray {
            val cipher = Cipher.getInstance("AES/CTR/NoPadding", "BCFIPS")
            cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
            return cipher.doFinal(cipherText)
        }
    }
}