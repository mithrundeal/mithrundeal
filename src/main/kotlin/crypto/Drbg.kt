package crypto

import java.security.SecureRandom

class Drbg {
    companion object {
        /**
         * get random for general purposes
         * default value is 256 bit
         * @return SecureRandom based on SP800-90A
         */
        fun getSecureRandom(size: Int = 256): SecureRandom {
            return SecureRandom()
        }

        /**
         * @param numBytes given number of seed bytes
         * @return seed for general purposes
         */
        fun getSeed(numBytes: Int): ByteArray {
            return getSecureRandom().generateSeed(numBytes)
        }


        //TODO: seed secure random from two separate sources
    }
}