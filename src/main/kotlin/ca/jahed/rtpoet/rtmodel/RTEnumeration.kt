package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTEnumerationBuilder
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTEnumerationPropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTEnumerationProperties
import ca.jahed.rtpoet.rtmodel.types.RTType

open class RTEnumeration(name: String) : RTType(name) {
    val literals = mutableListOf<String>()
    var properties: RTEnumerationProperties? = null

    private class Builder(private var name: String) : RTEnumerationBuilder {
        private val literals = mutableListOf<String>()
        private var properties: RTEnumerationProperties? = null

        private var propertiesBuilder: RTEnumerationPropertiesBuilder? = null

        override fun literal(literal: String) = apply { literals.add(literal) }

        override fun properties(properties: RTEnumerationProperties) = apply {
            this.properties = properties
            this.propertiesBuilder = null
        }

        override fun properties(properties: RTEnumerationPropertiesBuilder) = apply {
            this.propertiesBuilder = properties
            this.properties = null
        }

        override fun build(): RTEnumeration {
            val enumeration = RTEnumeration(name)
            enumeration.properties = propertiesBuilder?.build() ?: properties
            literals.forEach { enumeration.literals.add(it) }
            return enumeration
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTEnumerationBuilder {
            return Builder(name)
        }
    }


}
