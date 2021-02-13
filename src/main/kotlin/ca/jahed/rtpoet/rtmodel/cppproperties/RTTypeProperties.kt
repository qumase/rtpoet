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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as RTTypeProperties

        if (name != other.name) return false
        if (definitionFile != other.definitionFile) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (definitionFile?.hashCode() ?: 0)
        return result
    }
}