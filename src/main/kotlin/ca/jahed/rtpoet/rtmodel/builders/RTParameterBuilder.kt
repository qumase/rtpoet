package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTParameter
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTParameterPropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTParameterProperties

interface RTParameterBuilder {
    fun replication(replication: Int): RTParameterBuilder
    fun properties(properties: RTParameterProperties): RTParameterBuilder
    fun properties(properties: RTParameterPropertiesBuilder): RTParameterBuilder
    fun build(): RTParameter
}