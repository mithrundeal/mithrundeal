import crypto.CryptoInit
import crypto.RSAHelper
import crypto.CryptoUtility.Companion.toHexString
import java.security.KeyPair
import java.security.PrivateKey

class CryptoManager {
    lateinit var rsaKeyPair: KeyPair

    init {
        // Crypto Setup, Create Cert, Create Public Key

        CryptoInit.setSecurityProvider()
        CryptoInit.setJVMProperties()

        rsaKeyPair = RSAHelper.getKeyPair()
    }

    fun getPublicKey(): String {
        //return "test_publi_key_${Random.nextInt(0, 100)}"
        return rsaKeyPair.public.encoded.toHexString()
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