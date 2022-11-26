import crypto.CryptoInit
import java.security.KeyPair

lateinit var rsaKeyPair: KeyPair
fun main(args: Array<String>) {

    val mithrundealNetwork: Mithrundeal = Mithrundeal("my-network-secret-separator")
    mithrundealNetwork.start()


    /*rsaKeyPair = RSAHelper.getKeyPair()


    val keyPair = RSAHelper.getKeyPair()

    val encrypted = RSAHelper.rsaEncrypt(keyPair.public, "deneme-data".toByteArray())
    println(encrypted)
    val decrypted = RSAHelper.rsaDecrypt(keyPair.private, encrypted)
    println(decrypted)

    val sign = RSAHelper.generatePKCS1Signature(keyPair.private, "signData".toByteArray())
    val verify = RSAHelper.verifyPKCS1Signature(keyPair.public, "signData".toByteArray(), sign)
    println(verify.toString())

    println(Hex.toHexString(rsaKeyPair.private.encoded))
    println(Hex.toHexString(keyPair.private.encoded))*/


}