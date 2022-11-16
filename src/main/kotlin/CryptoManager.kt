import kotlin.random.Random

class CryptoManager {
    init {
        // Crypto Setup, Create Cert, Create Public Key
    }
    public fun getPublicKey(): String {
        return "test_publi_key_${Random.nextInt(0, 100)}"
    }
    public fun encryption(data:String) {}
    public fun decryption(publicKey:String, data:String) {}
}