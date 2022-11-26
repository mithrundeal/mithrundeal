package model

import com.google.gson.Gson

data class TransferData(var processCode: Int, var data: String? = null) {

    fun toJSON() : String {
        return Gson().toJson(this)
    }

    companion object{
        fun fromJSON(data: String) : TransferData {
            return Gson().fromJson(data, TransferData::class.java)
        }
    }
}