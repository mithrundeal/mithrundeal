package crypto

import java.io.ByteArrayOutputStream
import java.security.KeyStore
import javax.crypto.SecretKey

class BCFKSHelper {

    companion object {

        /**
         * get KeyStore object
         */

        /*fun getKeyStore(): KeyStore {
            val keyStore = KeyStore.getInstance()
            keyStore.load(null, null)
            return keyStore;
        }*/


        /**
         * @param alias stores with
         * @param secretKey to store
         * @param keyPass encrypt to key
         *
         * stores key to bcfks
         */
        /*fun storeSecretKey(
            alias: String,
            storePassword: CharArray,
            keyPass: CharArray,
            secretKey: SecretKey
        ): ByteArray {

            val keyStore = getKeyStore()
            keyStore.setKeyEntry(alias, secretKey, keyPass, null)
            val bOut = ByteArrayOutputStream()
            keyStore.store(bOut, storePassword)
            return bOut.toByteArray()
        }*/


    }

}