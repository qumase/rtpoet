package ca.jahed.rtpoet.rtmodel.builders.cppproperties

import ca.jahed.rtpoet.rtmodel.cppproperties.RTOperationProperties

interface RTOperationPropertiesBuilder {
    fun friend(): RTOperationPropertiesBuilder
    fun global(): RTOperationPropertiesBuilder
    fun inline(): RTOperationPropertiesBuilder
    fun polymorphic(): RTOperationPropertiesBuilder
    fun dontGenerateDefinition(): RTOperationPropertiesBuilder
    fun build(): RTOperationProperties
}