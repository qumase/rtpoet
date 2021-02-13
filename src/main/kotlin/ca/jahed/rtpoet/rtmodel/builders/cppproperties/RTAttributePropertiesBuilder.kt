package ca.jahed.rtpoet.rtmodel.builders.cppproperties

import ca.jahed.rtpoet.rtmodel.cppproperties.RTAttributeProperties

interface RTAttributePropertiesBuilder {
    fun initialization(initialization: RTAttributeProperties.InitKind): RTAttributePropertiesBuilder
    fun kind(kind: RTAttributeProperties.Kind): RTAttributePropertiesBuilder
    fun size(size: String): RTAttributePropertiesBuilder
    fun type(type: String): RTAttributePropertiesBuilder
    fun pointsToConstType(): RTAttributePropertiesBuilder
    fun pointsToVolatileType(): RTAttributePropertiesBuilder
    fun pointsToType(): RTAttributePropertiesBuilder
    fun isVolatile(): RTAttributePropertiesBuilder
    fun build(): RTAttributeProperties
}