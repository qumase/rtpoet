package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTAttribute
import ca.jahed.rtpoet.rtmodel.RTClass
import ca.jahed.rtpoet.rtmodel.RTOperation
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTClassPropertiesBuilder

interface RTClassBuilder {
    fun superClass(parent: RTClass): RTClassBuilder
    fun attribute(attr: RTAttribute): RTClassBuilder
    fun attribute(attr: RTAttributeBuilder): RTClassBuilder
    fun operation(op: RTOperation): RTClassBuilder
    fun operation(op: RTOperationBuilder): RTClassBuilder
    fun properties(properties: RTClassPropertiesBuilder): RTClassBuilder
    fun build(): RTClass
}