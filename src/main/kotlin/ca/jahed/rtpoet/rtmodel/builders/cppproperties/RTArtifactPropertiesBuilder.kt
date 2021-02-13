package ca.jahed.rtpoet.rtmodel.builders.cppproperties

import ca.jahed.rtpoet.rtmodel.cppproperties.RTArtifactProperties

interface RTArtifactPropertiesBuilder {
    fun includeFile(includeFile: String): RTArtifactPropertiesBuilder
    fun sourceFile(sourceFile: String): RTArtifactPropertiesBuilder
    fun build(): RTArtifactProperties
}