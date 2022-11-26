package crypto

class CryptoUtility {
    companion object{
        fun ByteArray.toHexString() : String {
            return this.joinToString("") {
                java.lang.String.format("%02x", it)
            }
        }

        fun String.decodeHex(): ByteArray {
            check(length % 2 == 0) { "Must have an even length" }

            return chunked(2)
                .map { it.toInt(16).toByte() }
                .toByteArray()
        }
        
    }
}