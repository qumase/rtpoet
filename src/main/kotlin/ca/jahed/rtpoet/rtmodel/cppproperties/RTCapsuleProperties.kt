package ca.jahed.rtpoet.rtmodel.cppproperties

import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTCapsulePropertiesBuilder

class RTCapsuleProperties(
    var headerPreface: String? = null,
    var headerEnding: String? = null,
    var implementationPreface: String? = null,
    var implementationEnding: String? = null,
    var publicDeclarations: String? = null,
    var privateDeclarations: String? = null,
    var protectedDeclarations: String? = null,
    var generateHeader: Boolean = false,
    var generateImplementation: Boolean = false,
) : RTProperties() {


    private class Builder : RTCapsulePropertiesBuilder {
        private var headerPreface: String? = null
        private var headerEnding: String? = null
        private var implementationPreface: String? = null
        private var implementationEnding: String? = null
        private var publicDeclarations: String? = null
        private var privateDeclarations: String? = null
        private var protectedDeclarations: String? = null
        private var generateHeader = true
        private var generateImplementation = true

        override fun headerPreface(headerPreface: String) = apply { this.headerPreface = headerPreface }
        override fun headerEnding(headerEnding: String) = apply { this.headerEnding = headerEnding }
        override fun implementationPreface(implementationPreface: String) =
            apply { this.implementationPreface = implementationPreface }

        override fun implementationEnding(implementationEnding: String) =
            apply { this.implementationEnding = implementationEnding }

        override fun publicDeclarations(publicDeclarations: String) =
            apply { this.publicDeclarations = publicDeclarations }

        override fun privateDeclarations(privateDeclarations: String) =
            apply { this.privateDeclarations = privateDeclarations }

        override fun protectedDeclarations(protectedDeclarations: String) =
            apply { this.protectedDeclarations = protectedDeclarations }

        override fun dontGenerateHeader() = apply { this.generateHeader = false }
        override fun dontGenerateImplementation() = apply { this.generateImplementation = false }

        override fun build(): RTCapsuleProperties {
            return RTCapsuleProperties(
                headerPreface,
                headerEnding,
                implementationPreface,
                implementationEnding,
                publicDeclarations,
                privateDeclarations,
                protectedDeclarations,
                generateHeader,
                generateImplementation)
        }
    }

    companion object {
        @JvmStatic
        fun builder(): RTCapsulePropertiesBuilder {
            return Builder()
        }
    }
}