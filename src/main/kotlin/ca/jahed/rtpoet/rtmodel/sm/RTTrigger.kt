package ca.jahed.rtpoet.rtmodel.sm

import ca.jahed.rtpoet.exceptions.BuildException
import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.RTPort
import ca.jahed.rtpoet.rtmodel.RTSignal
import ca.jahed.rtpoet.rtmodel.builders.sm.RTTriggerBuilder

open class RTTrigger(var signal: RTSignal, port: RTPort? = null) : RTElement() {
    val ports = mutableListOf<RTPort>()

    init {
        if (port != null) ports.add(port)
    }

    private class Builder(
        private var signal: RTSignal,
        port: RTPort? = null,
    ) : RTTriggerBuilder {
        private val ports = mutableListOf<RTPort>()

        init {
            if (port != null) ports.add(port)
        }

        override fun port(port: RTPort) = apply { ports.add(port) }

        override fun build(): RTTrigger {
            val trigger = RTTrigger(signal)

            ports.forEach {
                if (!it.inputs().contains(this.signal)) throw  BuildException("""
                    Signal ${this.signal} is not an input to port $it(${it.protocol})
                """.trimIndent())

                trigger.ports.add(it)
            }

            return trigger
        }
    }

    companion object {
        @JvmStatic
        fun builder(signal: RTSignal, port: RTPort? = null): RTTriggerBuilder {
            return Builder(signal, port)
        }
    }
}