package ca.jahed.rtpoet.rtmodel.sm

import ca.jahed.rtpoet.exceptions.BuildException
import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.RTPort
import ca.jahed.rtpoet.rtmodel.RTSignal
import ca.jahed.rtpoet.rtmodel.builders.sm.RTTriggerBuilder

class RTTrigger(var signal: RTSignal, var port: RTPort) : RTElement() {
    private class Builder(
        private var signal: RTSignal,
        private var port: RTPort,
    ) : RTTriggerBuilder {
        override fun build(): RTTrigger {
            if (!port.inputs().contains(this.signal)) throw  BuildException("""
                        Signal ${this.signal} is not an input to port ${this.port}(${this.port.protocol})
                    """.trimIndent())

            return RTTrigger(signal, port)
        }
    }

    companion object {
        @JvmStatic
        fun builder(signal: RTSignal, port: RTPort): RTTriggerBuilder {
            return Builder(signal, port)
        }
    }
}