package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTAttributeBuilder
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTAttributePropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTAttributeProperties
import ca.jahed.rtpoet.rtmodel.cppproperties.RTProperties
import ca.jahed.rtpoet.rtmodel.types.RTType

open class RTAttribute(name: String, var type: RTType, var value: String? = null) : RTElement(name) {
    var replication = 1
    var visibility = RTVisibilityKind.PROTECTED
    var properties: RTProperties? = null

    private class Builder(private val name: String, private val type: RTType) : RTAttributeBuilder {
        private var replication = 1
        private var value: String? = null
        private var visibility = RTVisibilityKind.PROTECTED
        private var properties: RTAttributeProperties? = null

        private var propertiesBuilder: RTAttributePropertiesBuilder? = null

        override fun replication(replication: Int) = apply { this.replication = replication }
        override fun value(value: String) = apply { this.value = value }
        override fun publicVisibility() = apply { visibility = RTVisibilityKind.PUBLIC }
        override fun privateVisibility() = apply { visibility = RTVisibilityKind.PRIVATE }
        override fun protectedVisibility() = apply { visibility = RTVisibilityKind.PROTECTED }

        override fun properties(properties: RTAttributeProperties) = apply {
            this.properties = properties
            this.propertiesBuilder = null
        }

        override fun properties(properties: RTAttributePropertiesBuilder) = apply {
            this.propertiesBuilder = properties
            this.properties = null
        }

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