import crypto.CryptoInit
import crypto.Drbg
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider
import java.security.Security


fun main(args: Array<String>) {

    val mithrundealNetwork: Mithrundeal = Mithrundeal("my-network-secret-separator")
    mithrundealNetwork.start()

    CryptoInit.setSecurityProvider()

    println(Drbg.getSecureRandom().nextInt())
    println(Drbg.getSecureRandom().nextInt())
    println(Drbg.getSecureRandom().nextInt())

}