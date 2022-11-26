package crypto

class CryptoInit {
    companion object {
        /**
         * adding bc-fips security provider at first index to so every object can use it
         */
        fun setSecurityProvider() {
            //Security.addProvider(BouncyCastleFipsProvider())
            //TODO:Security add, inserting for first index causing exception on ktor
        }

        /**
         * setting jvm properties for both using of enc/sign
         */
        fun setJVMProperties() {
            //val properties = Properties()
            //properties.setProperty("org.bouncycastle.rsa.allow_multi_use", "true")
            //System.setProperties(properties)
        }
    }
}