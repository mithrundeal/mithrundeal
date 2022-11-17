import crypto.RSAHelper
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import kotlin.random.Random

class CryptoManager {
    lateinit var rsaKeyPair: KeyPair

    init {
        // Crypto Setup, Create Cert, Create Public Key
        rsaKeyPair = RSAHelper.getKeyPair()
    }

    fun getPublicKey(): PublicKey {
        //return "test_publi_key_${Random.nextInt(0, 100)}"
        return rsaKeyPair.public
    }

    fun getPrivateKey(): PrivateKey {
        return rsaKeyPair.private
    }

    fun rsaEncryption(plainData: String): ByteArray {
        return RSAHelper.rsaEncrypt(rsaKeyPair.public, plainData.toByteArray())
    }

    fun rsaEncryption(plainData: ByteArray): ByteArray {
        return RSAHelper.rsaEncrypt(rsaKeyPair.public, plainData)
    }

    fun rsaDecryption(encryptData: ByteArray): ByteArray {
        return RSAHelper.rsaDecrypt(rsaKeyPair.private, encryptData)
    }
}