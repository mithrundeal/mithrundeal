package crypto

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider
import java.security.Security
import java.util.*

class CryptoInit {
    companion object {
        /**
         * adding bc-fips security provider at first index to so every object can use it
         */
        fun setSecurityProvider() {
            Security.insertProviderAt(BouncyCastleFipsProvider(), 1)
        }

        /**
         * setting jvm properties for both using of enc/sign
         */
        fun setJVMProperties() {
            var properties = Properties()
            properties.setProperty("org.bouncycastle.rsa.allow_multi_use", "true")
            System.setProperties(properties)
        }
    }
}