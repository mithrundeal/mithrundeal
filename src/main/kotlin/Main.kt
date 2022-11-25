import crypto.CryptoInit
import crypto.Drbg
import crypto.RSAHelper

import java.security.Provider
import java.security.SecureRandom
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider
import org.bouncycastle.util.encoders.Hex
import java.security.KeyPair
import java.security.Security
import java.util.*


lateinit var rsaKeyPair: KeyPair
fun main(args: Array<String>) {

    runBlocking {
        async {
            val mithrundealNetwork: Mithrundeal = Mithrundeal("my-network-secret-separator")
            mithrundealNetwork.start()
        }
    }

    rsaKeyPair = RSAHelper.getKeyPair()



}