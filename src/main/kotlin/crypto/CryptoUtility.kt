package crypto

class CryptoUtility {
    companion object{
        fun ByteArray.toHexString() : String {
            return this.joinToString("") {
                java.lang.String.format("%02x", it)
            }
        }
    }
}