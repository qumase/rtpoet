package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTAttribute
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTAttributePropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTAttributeProperties

interface RTAttributeBuilder {
    fun replication(replication: Int): RTAttributeBuilder
    fun value(value: String): RTAttributeBuilder
    fun publicVisibility(): RTAttributeBuilder
    fun privateVisibility(): RTAttributeBuilder
    fun protectedVisibility(): RTAttributeBuilder
    fun properties(properties: RTAttributeProperties): RTAttributeBuilder
    fun properties(properties: RTAttributePropertiesBuilder): RTAttributeBuilder
    fun build(): RTAttribute
}