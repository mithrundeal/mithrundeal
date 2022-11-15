package crypto

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider
import java.security.Security

class CryptoInit {
    companion object{
        fun setSecurityProvider(){
            Security.addProvider(BouncyCastleFipsProvider())
        }
    }
}