package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.exceptions.BuildException
import ca.jahed.rtpoet.rtmodel.builders.RTConnectorBuilder
import ca.jahed.rtpoet.rtmodel.builders.RTConnectorEndBuilder

open class RTConnector(
    var end1: RTConnectorEnd,
    var end2: RTConnectorEnd,
) : RTElement() {

    fun isInternal(): Boolean {
        return end1.part == null || end2.part == null
    }

    fun isExternal(): Boolean {
        return !isInternal()
    }

    private class Builder() : RTConnectorBuilder {
        private var end1: RTConnectorEnd? = null
        private var end2: RTConnectorEnd? = null

        private var end1Builder: RTConnectorEndBuilder? = null
        private var end2Builder: RTConnectorEndBuilder? = null

        constructor(end1: RTConnectorEnd? = null, end2: RTConnectorEnd? = null) : this() {
            this.end1 = end1
            this.end2 = end2
        }

        override fun end1(end: RTConnectorEnd) = apply {
            this.end1 = end
            this.end1Builder = null
        }

        override fun end1(end: RTConnectorEndBuilder) = apply {
            this.end1Builder = end
            this.end1 = null
        }

        override fun end2(end: RTConnectorEnd) = apply {
            this.end2 = end
            this.end2Builder = null
        }

        override fun end2(end: RTConnectorEndBuilder) = apply {
            this.end2Builder = end
            this.end2 = null
        }

        override fun build(): RTConnector {
            end1 = end1 ?: end1Builder?.build()
            end2 = end2 ?: end2Builder?.build()

            if (end1 == null || end2 == null)
                throw BuildException("Connector needs two ends")

            return RTConnector(end1!!, end2!!)
        }

        override fun build(capsule: RTCapsule): RTConnector {
            end1 = end1 ?: end1Builder?.build(capsule)
            end2 = end2 ?: end2Builder?.build(capsule)
            return build()
        }
    }

    companion object {
        @JvmStatic
        fun builder(
            end1: RTConnectorEnd? = null,
            end2: RTConnectorEnd? = null,
        ): RTConnectorBuilder {
            return Builder(end1, end2)
        }

        @JvmStatic
        fun builder(): RTConnectorBuilder {
            return Builder()
        }
    }
}