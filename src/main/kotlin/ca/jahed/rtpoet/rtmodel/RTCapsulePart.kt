package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTCapsulePartBuilder

open class RTCapsulePart(name: String, var capsule: RTCapsule) : RTAttribute(name, capsule) {
    var optional = false
    var plugin = false

    private class Builder(private val name: String, private val capsule: RTCapsule) : RTCapsulePartBuilder {
        private var replication = 1
        private var optional = false
        private var plugin = false

        override fun replication(replication: Int) = apply { this.replication = replication }
        override fun fixed() = apply { this.optional = false; this.plugin = false }
        override fun optional() = apply { this.optional = true }
        override fun plugin() = apply { this.plugin = true }

        override fun build(): RTCapsulePart {
            val part = RTCapsulePart(this.name, this.capsule)
            part.replication = replication
            part.optional = optional
            part.plugin = plugin
            return part
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String, capsule: RTCapsule): RTCapsulePartBuilder {
            return Builder(name, capsule)
        }
    }
}