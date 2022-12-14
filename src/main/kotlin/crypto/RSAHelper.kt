package crypto

import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.interfaces.RSAPublicKey
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RSAHelper {
    companion object {
        /**
         * @return fixed key size RSA key pair
         */
        fun getKeyPair(): KeyPair {
            val keyPair: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPair.initialize(
                RSAKeyGenParameterSpec(
                    3072,
                    //4th permat prime, can be increased
                    RSAKeyGenParameterSpec.F4
                )
            )

            return keyPair.genKeyPair()
        }

        /**
         * PKCS#1.5 signature
         * @param rsaPrivate for sign
         * @param input to sign
         * @return signed data
         */
        fun generatePKCS1Signature(rsaPrivate: PrivateKey, input: ByteArray): ByteArray {
            val signature: Signature = Signature.getInstance("SHA384withRSA")
            signature.initSign(rsaPrivate)
            signature.update(input)
            return signature.sign()
        }

        /**
         * PKCS#1.5 signature
         * @param rsaPublicKey for verify
         * @param input to verify
         * @return verification value as boolean
         */
        fun verifyPKCS1Signature(rsaPublicKey: PublicKey, input: ByteArray, encSignature: ByteArray): Boolean {
            val signature = Signature.getInstance("SHA384withRSA")
            signature.initVerify(rsaPublicKey)
            signature.update(input)
            return signature.verify(encSignature)
        }


        /**
         * @param publicKey for encrypt
         * @param plainData to encrypt
         * @return encrypted data as ByteArray
         */
        fun rsaEncrypt(publicKey: PublicKey, plainData: ByteArray): ByteArray {
            val encryptCipher = Cipher.getInstance("RSA")
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey)
            return encryptCipher.doFinal(plainData)
        }

        /**
         * @param privateKey for decrypt
         * @param encryptData to decrypt
         * @return decrypted data as ByteArray
         */
        fun rsaDecrypt(privateKey: PrivateKey, encryptData: ByteArray): ByteArray {
            val decryptCipher = Cipher.getInstance("RSA")
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey)
            return decryptCipher.doFinal(encryptData)
        }

        /**
         * generates rsaPublicKey from byteArray
         * @param key rsaPublicKey x509 encoded
         * @return publicKey object
         */
        fun generatePublicKey(key: ByteArray): PublicKey {
            return KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(key))
        }
    }


}