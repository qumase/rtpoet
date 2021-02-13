package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTArtifactBuilder
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTArtifactPropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTArtifactProperties
import ca.jahed.rtpoet.rtmodel.types.RTType

class RTArtifact(name: String, var fileName: String) : RTType(name) {
    var properties: RTArtifactProperties? = null

    private class Builder(private val name: String) : RTArtifactBuilder {
        private var fileName: String = String()
        private var propertiesBuilder: RTArtifactPropertiesBuilder? = null

        override fun fileName(fileName: String) = apply { this.fileName = fileName }
        override fun properties(properties: RTArtifactPropertiesBuilder) = apply { this.propertiesBuilder = properties }

        override fun build(): RTArtifact {
            val artifact = RTArtifact(this.name, fileName)
            artifact.properties = propertiesBuilder?.build()
            return artifact
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTArtifactBuilder {
            return Builder(name)
        }
    }
}