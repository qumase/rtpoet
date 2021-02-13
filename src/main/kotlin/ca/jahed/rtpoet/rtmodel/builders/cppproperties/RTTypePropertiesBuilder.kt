package ca.jahed.rtpoet.rtmodel.builders.cppproperties

import ca.jahed.rtpoet.rtmodel.cppproperties.RTTypeProperties

interface RTTypePropertiesBuilder {
    fun definitionFile(file: String): RTTypePropertiesBuilder
    fun build(): RTTypeProperties
}