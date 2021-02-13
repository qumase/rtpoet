package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTModelBuilder

class RTModel(name: String, var top: RTCapsulePart) : RTPackage(name) {
    private class Builder(private val name: String, private val top: RTCapsule) : RTModelBuilder {
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

        override fun build(): RTModel {
            val model = RTModel(name, RTCapsulePart(top.name, top))
            if (!capsules.contains(top))
                model.capsules.add(top)

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
        fun builder(name: String, top: RTCapsule): RTModelBuilder {
            return Builder(name, top)
        }
    }
}