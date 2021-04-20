package ca.jahed.rtpoet.rtmodel.cppproperties

import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTArtifactPropertiesBuilder

class RTArtifactProperties(
    var includeFile: String? = null,
    var sourceFile: String? = null,
) : RTProperties() {
    private class Builder : RTArtifactPropertiesBuilder {
        private var includeFile: String? = null
        private var sourceFile: String? = null

        override fun includeFile(includeFile: String) = apply { this.includeFile = includeFile }
        override fun sourceFile(sourceFile: String) = apply { this.sourceFile = sourceFile }

        override fun build(): RTArtifactProperties {
            return RTArtifactProperties(includeFile, sourceFile)
        }
    }

    companion object {
        @JvmStatic
        fun builder(): RTArtifactPropertiesBuilder {
            return Builder()
        }
    }
}