package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTPackageBuilder

open class RTPackage(name: String) : RTElement(name) {
    val capsules = mutableListOf<RTCapsule>()
    val protocols = mutableListOf<RTProtocol>()
    val classes = mutableListOf<RTClass>()
    val enumerations = mutableListOf<RTEnumeration>()
    val artifacts = mutableListOf<RTArtifact>()
    val packages = mutableListOf<RTPackage>()

    private class Builder(private val name: String) : RTPackageBuilder {
        private val capsules = mutableListOf<RTCapsule>()
        private val protocols = mutableListOf<RTProtocol>()
        private val classes = mutableListOf<RTClass>()
        private val enumerations = mutableListOf<RTEnumeration>()
        private val artifacts = mutableListOf<RTArtifact>()
        private val packages = mutableListOf<RTPackage>()

        override fun capsule(capsule: RTCapsule) = apply { capsules.add(capsule) }
        override fun protocol(protocol: RTProtocol) = apply { protocols.add(protocol) }
        override fun klass(klass: RTClass) = apply { classes.add(klass) }
        override fun enumeration(enumeration: RTEnumeration) = apply { enumerations.add(enumeration) }
        override fun artifact(artifact: RTArtifact) = apply { artifacts.add(artifact) }
        override fun pkg(pkg: RTPackage) = apply { packages.add(pkg) }

        override fun build(): RTPackage {
            val pkg = RTPackage(this.name)
            pkg.capsules.addAll(capsules)
            pkg.protocols.addAll(protocols)
            pkg.classes.addAll(classes)
            pkg.enumerations.addAll(enumerations)
            pkg.artifacts.addAll(artifacts)
            pkg.packages.addAll(packages)
            return pkg
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTPackageBuilder {
            return Builder(name)
        }
    }
}