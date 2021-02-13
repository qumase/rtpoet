package ca.jahed.rtpoet.rtmodel.builders.cppproperties

import ca.jahed.rtpoet.rtmodel.cppproperties.RTParameterProperties

interface RTParameterPropertiesBuilder {
    fun type(type: String): RTParameterPropertiesBuilder
    fun pointsToConst(): RTParameterPropertiesBuilder
    fun pointsToVolatile(): RTParameterPropertiesBuilder
    fun pointsToType(): RTParameterPropertiesBuilder
    fun build(): RTParameterProperties
}