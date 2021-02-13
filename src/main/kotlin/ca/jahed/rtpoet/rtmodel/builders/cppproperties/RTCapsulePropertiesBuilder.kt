package ca.jahed.rtpoet.rtmodel.builders.cppproperties

import ca.jahed.rtpoet.rtmodel.cppproperties.RTCapsuleProperties

interface RTCapsulePropertiesBuilder {
    fun headerPreface(headerPreface: String): RTCapsulePropertiesBuilder
    fun headerEnding(headerEnding: String): RTCapsulePropertiesBuilder
    fun implementationPreface(implementationPreface: String): RTCapsulePropertiesBuilder
    fun implementationEnding(implementationEnding: String): RTCapsulePropertiesBuilder
    fun publicDeclarations(publicDeclarations: String): RTCapsulePropertiesBuilder
    fun privateDeclarations(privateDeclarations: String): RTCapsulePropertiesBuilder
    fun protectedDeclarations(protectedDeclarations: String): RTCapsulePropertiesBuilder
    fun dontGenerateHeader(): RTCapsulePropertiesBuilder
    fun dontGenerateImplementation(): RTCapsulePropertiesBuilder
    fun build(): RTCapsuleProperties
}