import crypto.CryptoInit
import crypto.Drbg
import java.security.Provider
import java.security.SecureRandom
import java.security.Security
import java.util.*


fun main(args: Array<String>) {

    val mithrundealNetwork: Mithrundeal = Mithrundeal("my-network-secret-separator")
    mithrundealNetwork.start()

    CryptoInit.setSecurityProvider()

    println(Drbg.getSecureRandom().nextInt())
    println(Drbg.getSecureRandom().nextInt())
    println(Drbg.getSecureRandom().nextInt())

    println(Drbg.getSecureRandom())

    val secureRandom:SecureRandom=SecureRandom()
    println(secureRandom.provider)

}