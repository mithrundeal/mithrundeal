import crypto.CryptoInit
import crypto.RSAHelper
import org.bouncycastle.util.encoders.Hex
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import kotlin.random.Random

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
        return Hex.toHexString(rsaKeyPair.public.encoded)
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