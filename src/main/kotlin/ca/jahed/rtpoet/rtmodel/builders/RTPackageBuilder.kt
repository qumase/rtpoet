package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.*

interface RTPackageBuilder {
    fun capsule(capsule: RTCapsule): RTPackageBuilder
    fun protocol(protocol: RTProtocol): RTPackageBuilder
    fun klass(klass: RTClass): RTPackageBuilder
    fun enumeration(enumeration: RTEnumeration): RTPackageBuilder
    fun artifact(artifact: RTArtifact): RTPackageBuilder
    fun pkg(pkg: RTPackage): RTPackageBuilder
    fun build(): RTPackage
}