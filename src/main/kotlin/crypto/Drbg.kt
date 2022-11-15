package crypto

import org.bouncycastle.crypto.EntropySource
import org.bouncycastle.crypto.EntropySourceProvider
import org.bouncycastle.crypto.fips.FipsDRBG
import org.bouncycastle.crypto.fips.FipsSecureRandom
import org.bouncycastle.crypto.util.BasicEntropySourceProvider
import java.security.SecureRandom

class Drbg {
    companion object {
        fun getSecureRandom(): FipsSecureRandom {
            val entropySourceProvider: EntropySourceProvider = BasicEntropySourceProvider(SecureRandom(), true)
            val drbgBuilder: FipsDRBG.Builder = FipsDRBG.SHA512_HMAC.fromEntropySource(entropySourceProvider)
                .setSecurityStrength(256)
                .setEntropyBitsRequired(256)

            return drbgBuilder.build("dummyseed".toByteArray(), false)
        }
    }
}