package ca.jahed.rtpoet.rtmodel.builders.cppproperties

import ca.jahed.rtpoet.rtmodel.cppproperties.RTEnumerationProperties

interface RTEnumerationPropertiesBuilder {
    fun headerPreface(headerPreface: String): RTEnumerationPropertiesBuilder
    fun headerEnding(headerEnding: String): RTEnumerationPropertiesBuilder
    fun implementationPreface(implementationPreface: String): RTEnumerationPropertiesBuilder
    fun implementationEnding(implementationEnding: String): RTEnumerationPropertiesBuilder
    fun dontGenerate(): RTEnumerationPropertiesBuilder
    fun build(): RTEnumerationProperties
}