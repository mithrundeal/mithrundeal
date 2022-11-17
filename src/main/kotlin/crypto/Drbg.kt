package crypto

import org.bouncycastle.crypto.EntropySource
import org.bouncycastle.crypto.EntropySourceProvider
import org.bouncycastle.crypto.fips.FipsDRBG
import org.bouncycastle.crypto.fips.FipsSecureRandom
import org.bouncycastle.crypto.util.BasicEntropySourceProvider
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider
import java.security.SecureRandom

class Drbg {
    companion object {
        /**
         * get random for general purposes
         * @return SecureRandom based on SP800-90A
         */
        fun getSecureRandom(): FipsSecureRandom {
            val entropySourceProvider: EntropySourceProvider = BasicEntropySourceProvider(SecureRandom(), true)
            val drbgBuilder: FipsDRBG.Builder = FipsDRBG.SHA512_HMAC.fromEntropySource(entropySourceProvider)
                .setSecurityStrength(256)
                .setEntropyBitsRequired(256)

            val byteArray = ByteArray(256)
            SecureRandom().nextBytes(byteArray)//provided from BC-FIPS
            return drbgBuilder.build(byteArray, true)
        }

        /**
         * @param numBytes given number of seed bytes
         * @return seed for general purposes
         */
        fun getSeed(numBytes: Int): ByteArray {
            return getSecureRandom().generateSeed(numBytes)
        }
    }
}