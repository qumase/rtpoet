package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTEnumerationBuilder
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTEnumerationPropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTEnumerationProperties
import ca.jahed.rtpoet.rtmodel.types.RTType

class RTEnumeration(name: String) : RTType(name) {
    val literals = mutableListOf<String>()
    var properties: RTEnumerationProperties? = null

    private class Builder(private var name: String) : RTEnumerationBuilder {
        private val literals = mutableListOf<String>()
        private var propertiesBuilder: RTEnumerationPropertiesBuilder? = null

        override fun literal(literal: String) = apply { literals.add(literal) }

        override fun properties(properties: RTEnumerationPropertiesBuilder) = apply {
            this.propertiesBuilder = properties
        }

        override fun build(): RTEnumeration {
            val enumeration = RTEnumeration(name)
            enumeration.properties = propertiesBuilder?.build()
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
