package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTActionBuilder

open class RTAction(var body: String = String(), var language: String = "C++") : RTElement() {
    private class Builder(private val body: String? = null) : RTActionBuilder {
        var language: String = "C++"

        override fun language(language: String) = apply { this.language = language }

        override fun build(): RTAction {
            return RTAction(body ?: String(), language)
        }
    }

    companion object {
        @JvmStatic
        fun builder(body: String? = null): RTActionBuilder {
            return Builder(body)
        }
    }
}