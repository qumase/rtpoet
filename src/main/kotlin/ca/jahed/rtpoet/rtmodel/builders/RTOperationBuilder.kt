package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTAction
import ca.jahed.rtpoet.rtmodel.RTOperation
import ca.jahed.rtpoet.rtmodel.RTParameter
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTOperationPropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTOperationProperties

interface RTOperationBuilder {
    fun publicVisibility(): RTOperationBuilder
    fun privateVisibility(): RTOperationBuilder
    fun protectedVisibility(): RTOperationBuilder
    fun parameter(param: RTParameter): RTOperationBuilder
    fun parameter(param: RTParameterBuilder): RTOperationBuilder
    fun ret(param: RTParameter): RTOperationBuilder
    fun ret(param: RTParameterBuilder): RTOperationBuilder
    fun action(action: RTAction): RTOperationBuilder
    fun action(action: RTActionBuilder): RTOperationBuilder
    fun properties(properties: RTOperationProperties): RTOperationBuilder
    fun properties(properties: RTOperationPropertiesBuilder): RTOperationBuilder
    fun build(): RTOperation
}