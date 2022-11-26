package model

import com.google.gson.Gson

data class AESCryptedData(var encryptedData: String, val iv: ByteArray) {
    fun toJSON() : String {
        return Gson().toJson(this)
    }

    companion object{
        fun fromJSON(data : String) : AESCryptedData {
            return Gson().fromJson(data, AESCryptedData::class.java)
        }
    }
}