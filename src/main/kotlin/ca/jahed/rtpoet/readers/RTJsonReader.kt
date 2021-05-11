package ca.jahed.rtpoet.readers

import ca.jahed.rtpoet.rtmodel.RTModel
import com.google.gson.Gson

class RTJsonReader private constructor(
    private val json: String,
) {

    companion object {
        @JvmStatic
        fun read(json: String): RTModel {
            return RTJsonReader(json).doRead()
        }
    }

    private fun doRead(): RTModel {
        val gson = Gson()
        return gson.fromJson(json, RTModel::class.java)
    }
}