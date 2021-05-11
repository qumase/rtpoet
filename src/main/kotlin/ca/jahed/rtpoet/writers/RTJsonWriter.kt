package ca.jahed.rtpoet.writers

import ca.jahed.rtpoet.rtmodel.RTModel
import com.google.gson.GsonBuilder

class RTJsonWriter private constructor(
    private val model: RTModel,
    private val pretty: Boolean,
) {

    companion object {
        @JvmStatic
        fun write(model: RTModel): String {
            return write(model, false)
        }

        @JvmStatic
        fun write(model: RTModel, pretty: Boolean): String {
            return RTJsonWriter(model, pretty).doWrite()
        }
    }

    private fun doWrite(): String {
        val gson = GsonBuilder()
        if (pretty) gson.setPrettyPrinting()
        return gson.create().toJson(model)
    }
}