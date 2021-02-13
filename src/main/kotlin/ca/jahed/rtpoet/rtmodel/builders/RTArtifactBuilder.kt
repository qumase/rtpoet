package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTArtifact
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTArtifactPropertiesBuilder

interface RTArtifactBuilder {
    fun fileName(fileName: String): RTArtifactBuilder
    fun properties(properties: RTArtifactPropertiesBuilder): RTArtifactBuilder
    fun build(): RTArtifact
}