package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTModelBuilder
import java.io.*

open class RTModel(name: String, var top: RTCapsulePart? = null) : RTPackage(name) {
    val imports = mutableListOf<RTModel>()

    private class Builder(private val name: String, private var top: RTCapsule? = null) : RTModelBuilder {
        private val capsules = mutableListOf<RTCapsule>()
        private val protocols = mutableListOf<RTProtocol>()
        private val classes = mutableListOf<RTClass>()
        private val enumerations = mutableListOf<RTEnumeration>()
        private val artifacts = mutableListOf<RTArtifact>()
        private val packages = mutableListOf<RTPackage>()
        private val imports = mutableListOf<RTModel>()

        override fun top(top: RTCapsule) = apply { this.top = top }
        override fun capsule(capsule: RTCapsule) = apply { capsules.add(capsule) }
        override fun protocol(protocol: RTProtocol) = apply { protocols.add(protocol) }
        override fun klass(klass: RTClass) = apply { classes.add(klass) }
        override fun enumeration(enumeration: RTEnumeration) = apply { enumerations.add(enumeration) }
        override fun artifact(artifact: RTArtifact) = apply { artifacts.add(artifact) }
        override fun pkg(pkg: RTPackage) = apply { packages.add(pkg) }
        override fun imprt(model: RTModel) = apply { imports.add(model) }

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
            model.imports.addAll(imports)
            return model
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTModelBuilder {
            return Builder(name, null)
        }

        @JvmStatic
        fun builder(name: String, top: RTCapsule): RTModelBuilder {
            return Builder(name, top)
        }

        @JvmStatic
        fun load(path: String): RTModel {
            return load(File(path))
        }

        @JvmStatic
        fun load(file: File): RTModel {
            val objectStream = ObjectInputStream(FileInputStream(file))
            val model = objectStream.readObject() as RTModel
            objectStream.close()
            return model
        }
    }

    fun save(path: String) {
        save(File(path))
    }

    fun save(file: File) {
        val objectStream = ObjectOutputStream(FileOutputStream(file))
        objectStream.writeObject(this)
        objectStream.close()
    }
}