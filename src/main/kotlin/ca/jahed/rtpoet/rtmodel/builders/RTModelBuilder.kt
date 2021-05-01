package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.*

interface RTModelBuilder {
    fun top(top: RTCapsule): RTModelBuilder
    fun capsule(capsule: RTCapsule): RTModelBuilder
    fun protocol(protocol: RTProtocol): RTModelBuilder
    fun klass(klass: RTClass): RTModelBuilder
    fun enumeration(enumeration: RTEnumeration): RTModelBuilder
    fun artifact(artifact: RTArtifact): RTModelBuilder
    fun pkg(pkg: RTPackage): RTModelBuilder
    fun build(): RTModel
}