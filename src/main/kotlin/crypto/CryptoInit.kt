package crypto

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider
import java.security.Security

class CryptoInit {
    companion object{
        /**
         * adding bc-fips security provider at first index to so every object can use it
         */
        fun setSecurityProvider(){
            Security.insertProviderAt(BouncyCastleFipsProvider(),1)
        }
    }
}