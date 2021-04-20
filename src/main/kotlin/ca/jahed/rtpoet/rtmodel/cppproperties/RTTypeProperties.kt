package ca.jahed.rtpoet.rtmodel.cppproperties

import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTTypePropertiesBuilder

class RTTypeProperties(
    override var name: String,
    var definitionFile: String? = null,
) : RTProperties() {

    private class Builder(private val name: String) : RTTypePropertiesBuilder {
        private var definitionFile: String? = null

        override fun definitionFile(file: String) = apply { definitionFile = file }

        override fun build(): RTTypeProperties {
            return RTTypeProperties(name, definitionFile)
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTTypePropertiesBuilder {
            return Builder(name)
        }
    }
}