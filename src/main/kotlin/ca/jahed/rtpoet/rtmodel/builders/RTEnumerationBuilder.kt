package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTEnumeration
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTEnumerationPropertiesBuilder

interface RTEnumerationBuilder {
    fun literal(literal: String): RTEnumerationBuilder
    fun properties(properties: RTEnumerationPropertiesBuilder): RTEnumerationBuilder
    fun build(): RTEnumeration
}
