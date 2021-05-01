package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTModelBuilder

open class RTModel(name: String, var top: RTCapsulePart? = null) : RTPackage(name) {
    private class Builder(private val name: String, private var top: RTCapsule? = null) : RTModelBuilder {
        private val capsules = mutableListOf<RTCapsule>()
        private val protocols = mutableListOf<RTProtocol>()
        private val classes = mutableListOf<RTClass>()
        private val enumerations = mutableListOf<RTEnumeration>()
        private val artifacts = mutableListOf<RTArtifact>()
        private val packages = mutableListOf<RTPackage>()

        override fun top(capsule: RTCapsule) = apply { this.top = top }
        override fun capsule(capsule: RTCapsule) = apply { capsules.add(capsule) }
        override fun protocol(protocol: RTProtocol) = apply { protocols.add(protocol) }
        override fun klass(klass: RTClass) = apply { classes.add(klass) }
        override fun enumeration(enumeration: RTEnumeration) = apply { enumerations.add(enumeration) }
        override fun artifact(artifact: RTArtifact) = apply { artifacts.add(artifact) }
        override fun pkg(pkg: RTPackage) = apply { packages.add(pkg) }

        override fun build(): RTModel {
            val model = RTModel(name, top?.let { RTCapsulePart(top!!.name, top!!) })
            if (top != null && !capsules.contains(top))
                model.capsules.add(top!!)

            model.capsules.addAll(capsules)
            model.protocols.addAll(protocols)
            model.classes.addAll(classes)
            model.enumerations.addAll(enumerations)
            model.artifacts.addAll(artifacts)
            model.packages.addAll(packages)
            return model
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String, top: RTCapsule? = null): RTModelBuilder {
            return Builder(name, top)
        }
    }
}