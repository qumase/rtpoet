package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.exceptions.BuildException
import ca.jahed.rtpoet.rtmodel.builders.RTConnectorEndBuilder

class RTConnectorEnd(var port: RTPort, var part: RTCapsulePart? = null) : RTElement() {

    private class Builder private constructor() : RTConnectorEndBuilder {
        private var port: RTPort? = null
        private var part: RTCapsulePart? = null

        private var portStr: String? = null
        private var partStr: String? = null

        constructor(port: RTPort, part: RTCapsulePart? = null) : this() {
            this.port = port
            this.part = part
        }

        constructor(port: String, part: String? = null) : this() {
            portStr = port
            partStr = part
        }

        override fun build(): RTConnectorEnd {
            if (partStr != null || portStr != null)
                throw BuildException("build() must not be called when Builder(String, String?) is used")

            if (port == null)
                throw BuildException("Connector end needs a port")

            return RTConnectorEnd(port!!, part)
        }

        override fun build(capsule: RTCapsule): RTConnectorEnd {
            part = part ?: capsule.parts.find { it.name == partStr }
                    ?: throw BuildException("""
                            Part $partStr not found in capsule $capsule
                        """.trimIndent())

            val partType = (part?.type ?: capsule) as RTCapsule

            port = port ?: partType.ports.find { it.name == portStr }
                    ?: throw BuildException("""
                            Port $portStr not found in capsule $capsule
                        """.trimIndent())


            return RTConnectorEnd(port!!, part)
        }
    }

    companion object {
        @JvmStatic
        fun builder(port: RTPort, part: RTCapsulePart? = null): RTConnectorEndBuilder {
            return Builder(port, part)
        }

        @JvmStatic
        fun builder(port: String, part: String? = null): RTConnectorEndBuilder {
            return Builder(port, part)
        }
    }
}