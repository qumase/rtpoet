package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTAttributeBuilder
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTAttributePropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTProperties
import ca.jahed.rtpoet.rtmodel.types.RTType

open class RTAttribute(name: String, var type: RTType, var value: String? = null) : RTElement(name) {
    var replication = 0
    var visibility = VisibilityKind.PROTECTED
    var properties: RTProperties? = null

    enum class VisibilityKind {
        PUBLIC, PRIVATE, PROTECTED, PACKAGE
    }

    private class Builder(private val name: String, private val type: RTType) : RTAttributeBuilder {
        private var replication = 1
        private var value: String? = null
        private var visibility = VisibilityKind.PROTECTED
        private var propertiesBuilder: RTAttributePropertiesBuilder? = null

        override fun replication(replication: Int) = apply { this.replication = replication }
        override fun value(value: String) = apply { this.value = value }
        override fun publicVisibility() = apply { visibility = VisibilityKind.PUBLIC }
        override fun privateVisibility() = apply { visibility = VisibilityKind.PRIVATE }
        override fun protectedVisibility() = apply { visibility = VisibilityKind.PROTECTED }
        override fun properties(properties: RTAttributePropertiesBuilder) =
            apply { this.propertiesBuilder = properties }

        override fun build(): RTAttribute {
            val attribute = RTAttribute(name, type)
            attribute.replication = replication
            attribute.visibility = visibility
            attribute.properties = propertiesBuilder?.build()
            return attribute
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String, type: RTType): RTAttributeBuilder {
            return Builder(name, type)
        }
    }
}